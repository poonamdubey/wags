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
            $name = $_REQUEST['name'];
            // File name must begin with '/'
            if(substr($name, 0, 1) != '/')
                $name = '/'.$name;
            $user = Auth::getCurrentUser();
            $file = CodeFile::getCodeFileByName($name, $user);
            if(empty($file)){
                return JSON::warn("File not found with name ".$name);
            }

			$status = 1;
			if($file->getOwnerId() == 0) $status = 0;

			echo($status."<pre>".$file->getContents()."</pre>");

			
        }else{
            return JSON::error("No file name given.");
        }
    }
}

?>
