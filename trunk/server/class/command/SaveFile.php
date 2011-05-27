<?php

/**
 * SaveFile
 *
 * Update a file in database with contents passed
 * in GET request.  If the file does not exist
 * in database then create one.
 *
 * @author Robert Bost <bostrt at appstate dot edu>
 */

class SaveFile extends Command
{
    public function execute()
    {
        // Use needs to be logged in to save a file.
        if(!Auth::isLoggedIn()){
            return JSON::error('Must be logged in to save a file.');
        }
        // Check if upload was successful
        // file and tmp_name must be set
        if(!isset($_FILES['file']) || !isset($_FILES['file']['tmp_name'])){
            return JSON::error('No file uploaded to server.');
        }

        // Get the user obj.
        $user = Auth::getCurrentUser();

        $file = CodeFile::getCodeFileByName($_REQUEST['name'], $user);

        if(!empty($file) && $file instanceof CodeFile){
            // User is wanting to save an existing file. Get that file.
            $file->setContents(file_get_contents($_FILES['file']['tmp_name']));
            $file->save();
            x($file);
        }else{
            // User is saving a new file. 
            $file = new CodeFile();
            $file->setOwnerId($user->getId());

            // File needs name.
            if(isset($_REQUEST['name'])){
                $file->setName($_REQUEST['name']);
            }else{
                return JSON::error('No name set for file.');
            }

            // Set rest of fields in file.
            $file->setContents(file_get_contents($_FILES['file']['tmp_name']));
            $now = time();
            $file->setAdded($now);
            $file->setUpdated($now);
            
            // Save file.
            try{
                $file->save();
            } catch( PDOException $e) {
                logError($e);
                return JSON::error($e->getMessage());
            }

            // Success.
            return JSON::success($file->getName().' Saved');
        }
    }
}

?>