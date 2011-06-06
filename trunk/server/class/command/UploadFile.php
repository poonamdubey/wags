<?php

/**
 * UploadFile
 *
 * Receive a file uploaded from the user's browser.
 * Only accept plain text files. Read contents of 
 * file and insert into database.
 *
 * @author Robert Bost <bostrt at appstate dot edu>
 */

class UploadFile extends Command
{
    public function execute()
    {
        if(!Auth::isLoggedIn()){
            return JSON::error('Must be logged in to upload file.');
        }
	if(!isset($_FILES['the_file'])){
		return JSON::Error('No file');
	}        

        $file = $_FILES['the_file'];
        $user = Auth::getCurrentUser();

	if($file == null){
		return JSON::Error('No file');
	}

        // Check that mimetype is plain text.
        $finfo = finfo_open(FILEINFO_MIME_TYPE);
        $type = finfo_file($finfo, $file['tmp_name']);
        if(strpos($type, 'text') === FALSE){
            // mimetype is not acceptable.
            return JSON::error('Please only upload plain text or source files.');
        }
        
        $contents = file_get_contents($file['tmp_name']);
	$directory = $_POST['curDir'];
	
        $f = new CodeFile();
        $f->setName($directory.$file['name']);
        //$f->setName($file['name']);
        $f->setOwnerId($user->getId());
        $f->setContents($contents);
        $now = time();
        $f->setAdded($now);

        try{
            $f->save();
        }catch(PDOException $e){
            echo $e->getMessage();
	    logError($e);
        }

        finfo_close($finfo);
    }
}

?>
