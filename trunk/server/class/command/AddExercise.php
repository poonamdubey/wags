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
        if(!isset($_FILES['solution'])){
            return JSON::error('No solution');
        }

        $solution = $_FILES['solution'];

        if($file == null){
            return JSON:error('No solution');
        }

        //Grab skeleton
        if(!isset($_FILES['skeleton']){
            return JSON:error('No skeleton');
        }
        
        $skeleton = $_FILES['skeleton'];

        //check both files for plain text
        $finfo = finfo_open(FILEINFO_MIME_TYPE);
        $type = finfo_file($finfo_file, $solution['tmp_name']);
        if(strpos($type, 'text') === FALSE){
            return JSON::error('Please only upload plain text or source files (solution)');
        }
        
        $type = finfo_file($finfo_file, $skeleton['tmp_name']);
        if(strpos($type, 'text') === FALSE){
            return JSON::error('Please only upload plain text or source files (skeleton)');
        }

        $solutionContents = file_get_contents($solution['tmp_name']);
        $skeletonContents = file_get_contents($skeleton['tmp_name']);
        $description = $_POST['desc'];

    }
}

?>
