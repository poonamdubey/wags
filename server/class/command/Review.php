<?php

/**
 * Review
 *
 * Compile code that was submitted by the user.
 * TODO: Dr. Kurtz said that the file may be named prior to student's
 * typing anything into the web editor. This will make things easier on
 * our side. We can just pass around and "Exercise" object with a filename
 * already set. More on this later.
 *
 * @author Robert Bost <bostrt at appstate dot edu>
 */

define("EXEC_ERROR", 1);
define("EXEC_SUCCESS", 0);

class Review extends Command
{
    public function execute()
    {
	$classRegex = "/class\s+([^\d]\w+)/";
	$successRegex = "/Success<br>$/";
	$code = $_POST['code'];

	//see str_replace comment in SaveFileContents.php
	$code = str_replace("%2B", "+", $code);
	$exerciseId = $_POST['id'];

	//Sets files exerciseId in case it changed
	//Note, however, that the exerciseId really shouldn't change
	$user = Auth::getCurrentUser();
	$file = CodeFile::getcodeFileByName($_POST['name'], $user);

	if(!empty($file) && $file instanceof CodeFile){
	    $file->setExerciseId($exerciseId);
	}

	$file->save();

	//Update or create the submission for this user/exercise pairing
	if(Submission::submissionExistsByExerciseId($exerciseId, $user->getId())){
	    $subList = Submission::getSubmissionByExerciseId($exerciseId, $user->getId());
	    $sub = $subList[0];	
	    $sub->setFileId($file->getId());
	    $sub->setUpdated(time());
	} else {
            $sub = new Submission();
            $sub->setExerciseId($exerciseId);
            $sub->setFileId($file->getId());
            $sub->setUserId($user->getId());
            $now = time();
            $sub->setUpdated($now);
	    $sub->setAdded($now);
	    $sub->setSuccess(0);
	}

	//Necessary for compilation, as we do not
	//require that the file name matches the class name
        preg_match($classRegex, $code, $matches);
        if(empty($matches)){
            /* No class name was found. Tell the user to check their code. */
            return JSON::error("Please check you class name.");
 	}      

        /**
         * Regex found a class name to use.
         * Create a file called $className.java 
         * in /tmp/<username>
         */
        $className = $matches[1];
        $user = Auth::getCurrentUser();
        $username = $user->getUsername();
        $dir = "/tmp/$username";
        if(!is_dir($dir)){
            mkdir($dir);
            chmod($dir, 0777);
        }
        $fullPath = "$dir/$className.java";

	$f = fopen($fullPath, "w+");

	/**
	 * Creating solutions direcotry,
	 * adding or grabbing solution from directory
	 * using exerciseName as key
	 */
	$solutionDir = "/tmp/Solutions";
	if(!is_dir($solutionDir)){
		mkdir($solutionDir);
		chmod($solutionDir, 0777);
	}

	//Creates the solution file
	//this part may have to be modified with the
	//addition of helper and test classes
	$exerciseArray = Exercise::getExerciseById($exerciseId);
	$exercise = $exerciseArray[0];
	preg_match($classRegex, $exercise->getSolution(), $solMatch);
	$exerciseName = $solMatch[1];
	$solutionPath = "$solutionDir/"."$exerciseName.java";

	$solutionFile = fopen($solutionPath, "w+");
	$solutionResult = fwrite($solutionFile, $exercise->getSolution());

        if($f === FALSE || $solutionFile === FALSE){
            return JSON::error("Error occurred while testing your code. [1]");
        }

        // Write code to file.
	$result = fwrite($f, $code);

        if($result === FALSE || $solutionResult === FALSE){
            return JSON::error("Error occurred while testing your code. [2]");
        }
		
        // Flush and close file
	fflush($f);
	fflush($solutionFile);
	fclose($solutionFile);
        fclose($f);
		
        /**
	 * Compile code using java compiler.
	 * Here, we will need to include all the helper
	 * files found by using exerciseId and class administratorId.
	 *
	 * Perhaps do a for each to fill the classes, and then explode
	 * the array of file names for use in the exec statement?
	 * I imagine we'll cheat and force the file name to be the
	 * classname.java, as there is no reason for that not to be the case
         */
        exec("/usr/bin/javac $solutionPath $fullPath 2>&1", $output, $result);
        if($result == EXEC_ERROR){
            /* Print out error message returned from command line. */
 	    foreach($output as $line){
                  $error .= $line;
            }
            return JSON::error($error);
        }else if($result == EXEC_SUCCESS){
            /**
             * Attempt to run. Must set classpath since we're running it from
	     * where ever www-data users's home is.
	     *
	     * Edits: Now, we take the compiled solution class and move it to
	     * the student directory so it can find the linked classes. 
	     * --> Will have to consider moving helper classes as well
	     *
	     * Also, this may get more complicated depending on the requirements
	     * of a package structure... there may be none, now that I think
	     * about it....
	     */
	    exec("cp $solutionDir/$exerciseName.class $dir/$exerciseName.class");
//	    exec("/usr/bin/java -cp $dir $exerciseName 2>&1", $output);
	    $output = $this->runCode($dir, $exerciseName);

	    //Checks for success just by looking to see if the last line
	    //printed was "Success<br>"
	    if(preg_match($successRegex, $output[0])){
		    $sub->setSuccess(1); 
	    }else{
		    $sub->setSuccess(0);
	    }
	    $sub->save();
		
	    JSON::success($output);
        }
    }

     /** 
       * Attempt to run. Must set classpath since we're running it from
       * where ever www-data users's home is.
       */  
      private function runCode($dir, $className){
          exec("/usr/bin/php class/command/runcode.php $dir $className 2>&1", $output);          
          return $output;
      }   

}

?>




