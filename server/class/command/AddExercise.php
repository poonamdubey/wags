<?php

/**
*AddExercise
*
*Heavily based off of UploadFile.php
*Should receive two files as well as a 
*text description from the url
*Hopefully will add contents to database
*/


class AddExercise extends Command
{
    public function execute()
    {
        
        if(!Auth::isLoggedIn()){
            return JSON::error('Must be logged in as administrator
                to log an exercise');   
		}
		
		$user = Auth::getCurrentUser();
		$name = $_POST['fileName'];

		$e = Exercise::getExerciseByTitle($name);
		if(isSet($e)){
			$update = true;
		}else{
			//Check all files were uploaded
			if(!isset($_FILES['Solution']) || !isset($_FILES['Skeleton']) ||
				!isset($_FILES['TestClass'])){
				return JSON::error('Each exercise must have a Solution, Skeleton,
					and Testing Class');
			}

        	$solution = $_FILES['Solution'];
      		$skeleton = $_FILES['Skeleton'];
		$testClass = $_FILES['TestClass'];

        	//check all files for plain text
        	$finfo = finfo_open(FILEINFO_MIME_TYPE);
       		$type = finfo_file($finfo, $solution['tmp_name']);
	        if(strpos($type, 'text') === FALSE){
           	    return JSON::error('Please only upload plain text or source files (solution)');
       		}
        
        	$type = finfo_file($finfo, $skeleton['tmp_name']);
        	if(strpos($type, 'text') === FALSE){
        	    return JSON::error('Please only upload plain text or source files (skeleton)');
       		}	

        	$type = finfo_file($finfo, $testClass['tmp_name']);
        	if(strpos($type, 'text') === FALSE){
        	    return JSON::error('Please only upload plain text or source files (skeleton)');
       		}	


       		$solutionContents = file_get_contents($solution['tmp_name']);
       	 	$skeletonContents = file_get_contents($skeleton['tmp_name']);
		$testClassContents = file_get_contents($testClass['tmp_name']);

		$description = $_POST['desc'];

		$e = new Exercise;
                $e->setSolution($solutionContents);
                $e->setSkeleton($skeletonContents);
                $e->setDescription($description);
                $e->setTitle($name);
       		$e->setAdded(time());
		$e->setSection($user->getSection());
		$e->setTestClass($testClassContents);//Temporary server side handling
		//of TestClass as client side hasn't been updated yet
	}


	$visible = $_POST['visible'];
	if($visible == "on") $visible = 1;
	else $visible = 0;
	$e->setVisible($visible);

	$multi = $_POST['multiUser'];
	if($multi == "on") $multiUser = 1;
	else $multiUser = 0;
	$e->setMultiUser($multiUser);

	$now = time();
	$e->setUpdated($now);	

        try{
	    $e->save();
            if(isset($update) && $update)
                JSON::success('Overwrote exercise '.$e->getTitle());
            else
                JSON::success('Uploaded exercise '.$e->getTitle());
        }catch(PDOException $e){
            echo $f->getMessage();
	    logError($f);
	    JSON::error($f);
        }

	//Seems to only work on second "adding" of exercise...
	//Problem seems to be within the loop at the end of 
	//addSkeletons
        if($visible == 1){
		$e->addSkeletons();
        }


	finfo_close($finfo);

    }

}


?>
