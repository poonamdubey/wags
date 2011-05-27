<?php

/**
 * GetFileContents
 *
 * Get file contents by name.
 *
 * @author Robert Bost <bostrt at tux dot appstate dot edu>
 */

class GetFileContents extends Command 
{
    public function execute()
    {
        // Use needs to be logged in to save a file.
        if(!Auth::isLoggedIn()){
            return JSON::error('Must be logged in to get a file.');
        }

        // A file name must be asked for.
        if(isset($_REQUEST['name'])){
            $user = Auth::getCurrentUser();
            $file = CodeFile::getCodeFileByName($_REQUEST['name'], $user);
            if(empty($file)){
                return JSON::warn("File not found with name ".$_REQUEST['name']);
            }
            echo ($file->getContents());
        }else{
            return JSON::error("No file name given.");
        }
    }
}

?>