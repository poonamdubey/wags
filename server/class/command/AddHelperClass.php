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
			return JSON::error('Must be logged in as administrator
				to add a class');
		}

		$user = Auth::getCurrentUser();
		$exerciseTitle = $_POST['Exercises'];
		$exercise = Exercise::getExerciseByTitle($exerciseTitle);
		
		if(!isset($_FILES['HelperClass'])){
			return JSON::error('No file selected for uploading');
		}

		$helper = $_FILES['HelperClass'];

		$finfo = finfo_open(FILEINFO_MIME_TYPE);
		$type = finfo_file($finfo, $helper['tmp_name']);

		if(strpos($type, 'text' === FALSE)){
			return JSON::error('Please only upload plain text or source files');
		}

		$helperContents = file_get_contents($helper['tmp_name']);

		$helperName = $_FILES['HelperClass']['name'];

		$file = new CodeFile();
		$file->setContents($helperContents);
		$file->setName($helperName);
		$file->setExerciseId($exercise->getId());
		$file->setOwnerId(0); //0 is used specifically for helper classes
		$file->setSection($user->getSection());
		$file->setAdded(time());
		$file->setUpdated(time());

		$file->save();

		echo "$helperName";
	}
}

?>
