<?php

/**
 * @class AdminReview
 * @author Philip Meznar
 *
 * This class was created for the WAGS project in
 * summer '11.  It's function is to return the user,
 * most recent file, and success value for a given
 * exercise.  It is used by the administrator for quick
 * and easy access to students success in a microlab.
 */

class AdminReview extends Command
{
	public function execute(){
		if (isset($_REQUEST['exerciseId'])){
			$exId = $_REQUEST['exerciseId'];
			$subInfo = Exercise::getSubmissions($exId);

			//look at
			//http://stackoverflow.com/questions/1519872/pdo-looping-throug-and-printing-fetchall
			if(empty($subInfo)){
				return JSON::success("empty");
			}
			return JSON::success($subInfo);
		}else{
			return JSON::error("No exercise given");
		}

	}
}
