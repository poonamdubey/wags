<?php

/**
*AddHelperClass
*
*Based off of UploadFile.php
*Uploads the helper classes for an exercise,
*setting the owner to 0, the exercise to
*whatever is selected on the client side,
*and the section to the current users section
*
*This is used for testing microlabs that require more
*than a solution, skeleton, and testclass
*/

class AddHelperClass extends Command
{
	public function execute()
	{


		if(!Auth::isLoggedIn()){
			echo 'Must be logged in as administrator to add a class';
			return;
		}

		$user = Auth::getCurrentUser();
		$exerciseTitle = $_POST['Exercises'];
		$exercise = Exercise::getExerciseByTitle($exerciseTitle);
		
		$helper = $_FILES['HelperClass'];
		#$description = $_FILES['descriptionPDF'];

		$finfo = finfo_open(FILEINFO_MIME_TYPE);
		$type = finfo_file($finfo, $helper['tmp_name']);

		if(strpos($type, 'text' === FALSE)){
			echo 'Please only upload plain text or source files';
			return;
		}

		$helperContents = file_get_contents($helper['tmp_name']);

		if($helperContents == "" || !isset($helperContents)){
			if($_FILES['descriptionPDF']['name'] == ""){
				echo 'Cannot upload empty files';
				return;
			}
		}

		/*$descTmp = $description['tmp_name'];
		$descName = $description['name'];
        #These will have to change when deployed publicly - should be extracted */

        #$section = $user->getSection();
        #$path = "/usr/local/apache2/htdocs/cs/wags/descriptions".$section;

        #if(!is_dir($path)) mkdir($path);

        #$moveTo = "$path/$descName";
        #$truncName = str_replace(".pdf", ".jpg", $descName); /* convert .pdf -> .jpg */
        #$fileLoc = $path;
        #$urlLoc = "http://cs.appstate.edu/wags/descriptions$section/$truncName";


		/*if($descName != ""){
			if(file_exists($moveTo)){
				echo "Desc file already exists. Please change filename";
				return;
			}

			if(!move_uploaded_file($_FILES['descriptionPDF']['tmp_name'], $moveTo)){
				echo "Error uploading description file";
				return;
			}else{
                exec("convert $fileLoc/$descName $fileLoc/$truncName"); 
            
				$exercise->setDescription($urlLoc);
				$exercise->save();
			}
		}*/

        if($helperContents != ""){
    		$helperName = "/".$exerciseTitle."/".$_FILES['HelperClass']['name'];

    		$file = new CodeFile();
    		$file->setContents($helperContents);
    		$file->setName($helperName);
    		$file->setExerciseId($exercise->getId());
    		$file->setOwnerId(CodeFile::getHelperId()); //0 is used specifically for helper classes
    		$file->setSection($user->getSection());
	    	$file->setAdded(time());
    		$file->setUpdated(time());

            $id = CodeFile::getHelperId();

    		$file->save();
        }
		echo "Class Uploaded";

	}
}

?>
