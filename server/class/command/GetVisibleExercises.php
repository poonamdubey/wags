<?php

class GetVisibleExercises extends Command
{
	public function execute()
	{
		$exercises = Exercise::getVisibleExercises();
		$exerciseTitles = "";

		foreach($exercises as $exercise){
			$exerciseIds[] = $exercise->getId();
			$exerciseTitles[] = $exercise->getTitle();
		}

		$exerciseArray = array_merge($exerciseIds, $exerciseTitles);
	
		if(!empty($exerciseArray)){
			return JSON::success($exerciseArray);
		}

		return JSON::success("No Assignments");
	}

}


?>
