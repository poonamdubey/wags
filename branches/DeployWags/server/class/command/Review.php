<?php

/**
 * Review
 *
 * Compile code submitted by the user together with
 * the solution class, test class, and whatever 
 * helper classes were provided.
*/

define("EXEC_ERROR", 1);
define("EXEC_SUCCESS", 0);

class Review extends Command
{	
	public function execute(){
		// catch all exceptions -- May remove this
		try {
			
		$user = Auth::getCurrentUser();
		$section = $user->getSection();

		//Define the regular expressions used
		//for finding class and package name,
		//and success of program
		// ***NOTE*** Getting rid of classRegex soon
		//		- No reason to use this to get class names,
		//		  since Java file names must match class names.
		//        Also, removing this allows for code that runs 
		//		  universally on all languages.  Simply will be
		// 		  fetching actual file name (what was uploaded)
		//		  from database to use
		$classRegex = "/public\sclass\s+([^\d]\w+)/";  // remove this
		$packageRegex = "/package\s+([^\d]\w+)/";
		$successRegex = "/Success<br \/>/";

		//Grab posted information
		$code = $_POST['code'];  // code that student has inputed
		$exerciseTitle = $_POST['title'];
		$codeFileName = $_POST['name'];  // this is just the name Wags uses for storing
									     // and retrieve to/from database
		
		// Get exercise, associated solution and test codefiles
		//  and student codefile
		$exercise = Exercise::getExerciseByTitle($exerciseTitle);
		$exerciseId = $exercise->getId();
        $exerciseSkeleton = $exercise->getSkeleton();  // get rid of this
		$solutionCodeFile = CodeFile::getCodeFileById($exercise->getSolutionId());
		$testCodeFile = CodeFile::getCodeFileById($exercise->getTestClassId());
		$studentCodeFile = CodeFile::getCodeFileByName($codeFileName);
		
		// Get original file names for solution, test, and student files
		// 	-This will be used to replace the regular expressions for finding
		//   class names, since class names are specific to Java, and Java files
		//   must have same name as contained class anyways
		$solutionFileName = $solutionCodeFile->getOriginalFileName();
		$testFileName = $testCodeFile->getOriginalFileName();
		$studentFileName = $studentCodeFile->getOriginalFileName();
		
		// Get original file extensions for solution, test, and student files
		$solutionFileExtension = $solutionCodeFile->getOriginalFileExtension();
		$testFileExtension = $testCodeFile->getOriginalFileExtension();
		$studentFileExtension = $studentCodeFile->getOriginalFileExtension();
		
		// current time
		$now = time();
		
		// Invisible exercises = expired
        if(!$exercise->getVisible()){
            return JSON::error("Exercise not currently visible");
        }
        
        //If the exercise has expired:
		$closed = $exercise->getCloseDate();
		if($closed != '' && $closed < $now && $closed != 0){
			return JSON::error("This exercise has expired.");
		}
		
		// ***CHOOSE LANGUAGE***
		// Determine the language being used
		//	- The language of the solution file will dictate the protocol being
		//	  being used, and other files will simply use their file extensions
		switch($solutionFileExtension)
		{
			case "java":
				$lang = "Java";
				
				break;
				
			case "pl":
				// prolog
				$lang = "Prolog";
				
				break;
				
			default:
				// if not able to match language, return error 
				return JSON::error("Error in matching solution file extension");
				break;
		}
		
		
		// Start: get rid of this
		if ($lang == "Java")
		{
			//Grab the correct skeleton class name
			preg_match($classRegex, $exerciseSkeleton, $matches);
			if(empty($matches)){
				return JSON::error("Please check class name for the Administrative Skeleton");
			}
			$skeletonName = $matches[1];
	
			//If they uploaded the wrong file
			preg_match($classRegex, $code, $matches);
			if(empty($matches) || $matches[1] != $skeletonName){
				$badName = $matches[1];
				return JSON::error("Please check class name - it needs to match the skeleton class: $badName $skeletonName");
			}
		}
		// End: get rid of this


		// Update or create submission for user/exercise pairing
		$sub = Submission::getSubmissionByExerciseId($exerciseId);
		$sub_num = 0;
		if($sub){
			$sub->setFileId($studentCodeFile->getId());
			$sub->setUpdated($now);
			if($sub->getNumAttempts() == null){
				$sub->setNumAttempts(1);
				$sub_num = 1;
			} else {
				$sub_num = $sub->getNumAttempts() + 1;
				$sub->setNumAttempts($sub_num);
			}
		} else {
			$sub = new Submission();
			$sub->setExerciseId($exerciseId);
			$sub->setFileId($studentCodeFile->getId());
			$sub->setUserId($user->getId());
			$sub->setUpdated($now);
			$sub->setAdded($now);
			$sub->setSuccess(0);
			$sub->setNumAttempts(0);
		}

	// SAVE VERSIONS
		// Check to see if this file is a version or AdminSkeleton instead of the main file.
		//	- We don't want to save a version of a version, or a version of the AdminSkeleton.
		$prev_sub_num = $sub_num - 1;
		$subString = strstr($codeFileName, "_Versions", true);
		$isAdminSkel = strstr($codeFileName, "AdminSkeleton", true);
		if ($subString === FALSE && $isAdminSkel === FALSE) // if not found and NOT an Admin skeleton, then main file, so we need to save a new version
		{
		 	//	- Just get the bare name of the file, NOT the /EXERCISE/codeFileName string by
			//	 searcing for the second occurrence of "/", and then taking the string afterward
			//		- Probably a better way to do this instead of taking a substring twice
			$codeFileName_portion = substr($codeFileName, strpos($codeFileName, "/") + 1);
			$codeFileName_portion = substr($codeFileName_portion, strpos($codeFileName_portion, "/") + 1);
			
			$save_version = TRUE;
			if ($sub_num > 0) // if a version already exists,
			{
				// get the previous version and see if the current code has changed from the last version
				$prev_version = CodeFile::getCodeFileByName("$codeFileName"."_Versions/"."$codeFileName_portion"."_Version_"."$prev_sub_num");
				if (strcmp($prev_version->getContents(), $code) == 0)
				{
					$save_version = FALSE; // if same code, then don't save new version
					$sub->setNumAttempts($prev_sub_num); // Reset submission attempt to previous number
				}
			}
			
			// Save version if necessary
			if ($save_version === TRUE)
			{	
				$new_version = new CodeFile();
				$new_version->setContents($code);
				// File needs to be under same exercise, under a codeFileName_VERSIONS/ folder, 
				//	and have same name as normal file, but with _Version_# attached
				$new_version->setName("$codeFileName"."_Versions/"."$codeFileName_portion"."_Version_"."$sub_num");
				$new_version->setExerciseId($exerciseId);
				$new_version->setOwnerId($user->getId());
				$new_version->setSection($section);
				$new_version->setOriginalFileName($studentCodeFile->getOriginalFileName());
				$new_version->setOriginalFileExtension($studentCodeFile->getOriginalFileExtension());
				$new_version->setUpdated($now);
				$new_version->setAdded($now);
				$new_version->save();
			}
		} else {
			// Don't add a new submission if running a version or the AdminSkeleton
			$sub->setNumAttempts($prev_sub_num); // Reset submission attempt to previous number
		}

	// END SAVE VERSIONS


	// CONSTRUCTION OF PATHS
		
		$code = utf8_decode($code);
	
		// Check for the package statement for Java files
		//	-in effect, this tells us whether or not we'll be 
		//	 using an inner class in this microlab, and allows 
		//	 us to take the appropriate actions
		preg_match($packageRegex, $solutionCodeFile->getContents(), $matches);
		$codeFileName = substr($codeFileName, 1); //files start with a repetitive '/'
		
		if(empty($matches)){ 			// No package, no inner class
			$path = "/tmp/section$section/$codeFileName"."Dir";
			$pkg = FALSE;
		} else {						// Package, so inner class
			$pkgName = $matches[1];
			$path = "/tmp/section$section/$pkgName";
			$compilePath = "/tmp/section$section";
			$pkg = TRUE;
		}

		// Construct paths as admin, or if directory not already created
		// 	-construct solution, test, and helper class paths
		if(!is_dir($path) || $user->isAdmin()) {
			
			//Now that we know what the directory is, we check
	    	//to see if it already exists.  
			//	-If so, we remove contents completely
			//	-Else, create the directory and fail on error
			if(is_dir($path)) {
				exec("rm -rf $path/*");
			}
			else if(!mkdir($path, 0777, true) && !$user->isAdmin()){ //will need to edit permissions
				throw new Exception(error_get_last());
				return JSON::error("There was an internal error");          
         	}

			//Create solution file
//			preg_match($classRegex, $solutionCodeFile, $matches);
//			$className = $matches[1];
//			$solutionPath = "$path/$className.$solutionFileExtension";
			$solutionPath = "$path/$solutionFileName.$solutionFileExtension";
			$solutionFile = fopen($solutionPath, "w+");
			$solutionResult = fwrite($solutionFile, $solutionCodeFile->getContents());
			fflush($solutionFile);
			fclose($solutionFile);
	
			//Create tester file
//			preg_match($classRegex, $testCodeFile, $matches);
//			$testName = $matches[1];
//			$testPath = "$path/$testName.$testFileExtension";
			$testPath = "$path/$testFileName.$testFileExtension";
			$testFile = fopen($testPath, "w+");
			$testResult = fwrite($testFile, $testCodeFile->getContents());
			fflush($testFile);
			fclose($testFile);
	
			//create any helper files
			$helpers = $exercise->getHelperClasses();
			$helperResult = TRUE;
			$helperPaths = "";
			foreach($helpers as $helper){
//				preg_match($classRegex, $helper->getContents(), $matches);
//				$helperName = $matches[1];
//				$helperPath = "$path/$helperName.".helper->getOriginalFileExtension();
				$helperPath = "$path/".$helper->getOriginalFileName().".".$helper->getOriginalFileExtension();
				$helperPaths = $helperPaths." ".$helperPath;
				$helperFile = fopen($helperPath, "w+");
				$result = fwrite($helperFile, $helper->getContents());
	
				if(!$result){
					$helperResult = FALSE;
					$error = error_get_last();
					$errorMsg = $error['message'];
				}
	
				fflush($helperFile);
				fclose($helperFile);
			}
	
			//if any files weren't properly written, exit
			if(!($solutionResult && $testResult && $helperResult)){
				return JSON::error("Administrative file error while writing: $errorMsg");
			}
		}

        // Construct paths for Student
		// 	-This section is for students to create theirn own file, and grab solution, 
		//	 test, and helper files
		
		// Create and grab student file
		//	-each time this is run, the student class will be different,
		//	 so it's not lumped in with the other classes
//		preg_match($classRegex, $code, $matches);
//		if(empty($matches) || $matches[1] != $skeletonName){
//			$badName = $matches[1];
//        	return JSON::error("Please check class name - it needs to match the skeleton class: $badName $skeletonName");
//		}
//		$className = $matches[1];
//		$studentPath = "$path/$className.$studentFileExtension";
		$studentPath = "$path/$studentFileName.$studentFileExtension";
		$studentFile = fopen($studentPath, "w+");
		$classResult = fwrite($studentFile, $code);
		fflush($studentFile);
		fclose($studentFile);

        // Grab solutionPath as student
		//	- This file should already be present, so no need to write it
//		preg_match($classRegex, $solutionCodeFile, $matches);
//		$className = $matches[1];
//		$solutionPath = "$path/$className.$solutionFileExtension";
		$solutionPath = "$path/$solutionFileName.$solutionFileExtension";

        // Grab helper classes as student
		//	- These files should already be present, so no need to write them
		$helpers = $exercise->getHelperClasses();
        $helperPaths = "";
		foreach($helpers as $helper){
//	    	preg_match($classRegex, $helper->getContents(), $matches);
//			$helperName = $matches[1];
//			$helperPath = "$path/$helperName.".helper->getFileExtension();
			$helperPath = "$path/".$helper->getOriginalFileName().".".$helper->getOriginalFileExtension();
            $helperPaths = $helperPaths." ".$helperPath;
        }

		// Grab the test class as student
//		preg_match($classRegex, $testCodeFile, $matches);
//		$testName = $matches[1];
//		$testPath = "$path/$testName.$testFileExtension";
		$testPath = "$path/$testFileName.$testFileExtension";

		// Make sure student class was written
		if(!$classResult) return JSON::error("Problem writing student file");

	// END CONSTRUCTION OF PATHS


	// COMPILATION OF FILES
		
		// ***CHOOSE LANGUAGE***
		// Different protocols for compilation based on the language of the solution class
		// -All final executables should be same name as original file name, without the file extension
		switch($lang)
		{
			case "Java":
				// test, soluton, helper, and student files are all Java
				exec("/usr/bin/javac $solutionPath $testPath $helperPaths $studentPath 2>&1", $output, $result);
				
				break;
				
			case "Prolog":
				// Test class is Java still
				exec("/usr/bin/javac $testPath 2>&1", $output, $result);
				
				// The prolog files will just be scripted now instead of compiled and run as executables
				// 	-Only need to pass the solution and student file names to RunCodeNew, where the proper
				//	 strings to run the files will be created.
				//	-There were too many issues with permissions -- Will be a problem when wanting to 
				//	 implement C functionality
				
//				// Solution and student classes are Prolog - Use swipl to compile
//				//	-The student file is compiled last, as it should be the only file with 
//				//	 possible compilation errors
//				// 	- Executables will be same name as original file name, without the file extension
////				$currentPath = getcwd();
////				$solutionFileName = "$currentPath/executables/$solutionFileName";
////				$studentFileName = "$currentPath/executables/$studentFileName";
//				
//				$solutionFileName = "/usr/local/apache2/htdocs/cs/wags/Test_Version/executables/reverseListSkeleton";
//				$studentFileName = "/usr/local/apache2/htdocs/cs/wags/Test_Version/executables/reverseListSolution";
//				
////				exec("/usr/local/bin/swipl -q -O -t main -o $solutionFileName -c $solutionPath", $output, $result);
////				exec("/usr/local/bin/swipl -q -O -t main -o $studentFileName -c $studentPath", $output, $result);
//				
//				$result = EXEC_SUCCESS;
				
				break;
				
			default:
				// if not able to match language, return error
				return JSON::error("Error in matching language for compilation");
				break;
		}
		
	// END COMPILATION OF FILES
	
	
	// RUN FILES
		
		// If compilation was successful, then run code
		// Else, failure
		if ($result == EXEC_SUCCESS){

			//Running of microlab
			if($pkg){ 		//Within a package
				$output = $this->runCode($compilePath, $pkgName.".$testFileName", $solutionFileName, $studentFileName, $lang);
			}
			else{			//Not within a package
				$output = $this->runCode($path, $testFileName, $solutionFileName, $studentFileName, $lang);
			}
			
			//Check for success
			if(preg_match($successRegex, $output[0])){
				$sub->setSuccess(1);
				JSON::success($output);
			} else {
				$sub->setSuccess(0);
				JSON::warn($output);
			}

		} else { //if($result == EXEC_ERROR){
			$error = "Compilation Error: \n";
			foreach($output as $line){
				$error .= $line."\n";
				$sub->setSuccess(0); //failure to compile is failure for lab
			}
			JSON::error($error);
		}

		$sub->save();
		
		}catch(Exception $e){
	    	logError($e);
			JSON::error("There was a compilation error.");
        }
	}

	// Pass in directory containing all files, name of test, solution, and student files, and language of solution file
	private function runCode($dir, $testFileName, $solutionFileName, $studentFileName, $lang){
		exec("/usr/bin/php class/command/RunCodeNew.php $dir $testFileName $solutionFileName $studentFileName $lang 2>&1", $output);
		return $output;

	}

}

?>

