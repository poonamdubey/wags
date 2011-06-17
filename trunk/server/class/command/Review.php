<?php

/**
 * Review
 *
 * Compile code that was submitted by the user.
 * Echo output of compiler and of program.
 *
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
        $code = $_POST['code'];
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

        if($f === FALSE){
            return JSON::error("Error occurred while testing your code. [1]");
        }

        // Write code to file.
        $result = fwrite($f, $code);
        if($result === FALSE){
            return JSON::error("Error occurred while testing your code. [2]");
        }

        // Flush and close file
        fflush($f);
        fclose($f);

        /**
         * Compile code using java compiler.
         */
        exec("/usr/bin/javac $fullPath 2>&1", $output, $result);
        if($result == EXEC_ERROR){
            /* Print out error message returned from command line. */
            foreach($output as $line){
                print_r(str_replace(" ", "&nbsp;", $line)."<br/>");
            }
        }else if($result == EXEC_SUCCESS){
            /**
             * Attempt to run. Must set classpath since we're running it from
             * where ever www-data users's home is.
             */
            exec("/usr/bin/java -cp $dir $className 2>&1", $output);
            x($output);
        }
    }
}

?>