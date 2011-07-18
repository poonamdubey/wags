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
	$successRegex = "/Success$/";
	$code = $_POST['code'];
	$exerciseId = $_POST['id'];
	
	//Sets files exerciseID - note:
	//with submission table, I've lost sight of
	//why the file has an exercise Id.... oh well.
	$user = Auth::getCurrentUser();
	$file = CodeFile::getcodeFileByName($_POST['name'], $user);

	if(!empty($file) && $file instanceof CodeFile){
	    $file->setExerciseId($exerciseId);
	}

	$file->save();

	if(Submission::submissionExistsByExerciseId($exerciseId, $user->getId())){
	    $subList = Submission::getSubmissionByExerciseId($exerciseId, $user->getId());
	    $sub = $subList[0];	
	    $sub->setFileId($file->getId());
	    $sub->setUpdated(time());
	    $sub->save();
	} else {
            $sub = new Submission();
            $sub->setExerciseId($exerciseId);
            $sub->setFileId($file->getId());
            $sub->setUserId($user->getId());
            $now = time();
            $sub->setUpdated($now);
	    $sub->setAdded($now);
	    $sub->setSuccess(0);
            $sub->save();
	}

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

	//May change this to grab name from solution class rather
	//than depending on the exercise title which currently 
	//must be the same as the solution class
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
	     */
	    exec("cp $solutionDir/$exerciseName.class $dir/$exerciseName.class");
            exec("/usr/bin/java -cp $dir $exerciseName 2>&1", $output);

	    if(preg_match($successRegex, $output[0])){
		    $sub->setSuccess(1); 
		    $sub->save();
	    }
		
	    JSON::success($output);
        }
    }
    
}

?>
