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

        //Grab solution
        if(!isset($_FILES['Solution'])){
            return JSON::error('No solution');
        }

        $solution = $_FILES['Solution'];

        //Grab skeleton
        if(!isset($_FILES['Skeleton'])){
            return JSON::error('No skeleton');
        }
        
        $skeleton = $_FILES['Skeleton'];

        //check both files for plain text
        $finfo = finfo_open(FILEINFO_MIME_TYPE);
        $type = finfo_file($finfo, $solution['tmp_name']);
        if(strpos($type, 'text') === FALSE){
            return JSON::error('Please only upload plain text or source files (solution)');
        }
        
        $type = finfo_file($finfo, $skeleton['tmp_name']);
        if(strpos($type, 'text') === FALSE){
            return JSON::error('Please only upload plain text or source files (skeleton)');
        }

        $solutionContents = file_get_contents($solution['tmp_name']);
        $skeletonContents = file_get_contents($skeleton['tmp_name']);
	$description = $_POST['desc'];
	$name = $_POST['fileName'];
	$visible = $_POST['visible'];

	if($visible == "on") $visible = 1;
	else $visible = 0;

	if(Exercise::exerciseExistsByTitle($name)){
		$e = Exercise::getExerciseByTitle($name);
		$update = true;
    	} else {
		$e = new Exercise();
		$e->setSolution($solutionContents);
		$e->setSkeleton($skeletonContents);
		$e->setDescription($description);
		$e->setTitle($name);
	}

	$e->setVisible($visible);

	$now = time();
	$e->setAdded($now);	

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
