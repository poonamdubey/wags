<?php

/**
 * SaveFileContents
 *
 * Save a file with the given filename. Put text passed though REQUEST
 * into DB.
 */

class SaveFileContents extends Command
{
    public function execute()
    {
        if(!Auth::isLoggedIn()){
            return JSON::error('Must be logged in to save a file.');
        }
        
        if(!isset($_REQUEST['name'])){
            return JSON::error('File name needed.');
        }
        
        $user = Auth::getCurrentUser();
        $file = CodeFile::getCodeFileByName($_REQUEST['name'], $user);

        if(!empty($file) && $file instanceof CodeFile){
            // Update CodeFile.
            $file->setContents($_REQUEST['contents']);
            $file->setUpdated(time());
            $file->save();
        }else{
            // Create new CodeFile.
            $file = new CodeFile();
            $file->setContents($_REQUEST['contents']);
            $now = time();
	    $file->setName($_REQUEST['name']);
	    $file->setExerciseId(0);
            $file->setOwnerId($user->getId());
            $file->setUpdated($now);
            $file->setAdded($now);
            $file->save();
        }

        return JSON::success('File '.$_REQUEST['name'].' saved');
    }
}
?>
