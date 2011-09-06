<?php

class SetPartner extends Command
{
	public function execute()
	{
		$user = Auth::GetCurrentUser();
		$exTitle = $_GET['ex'];
		$partner = $_GET['partner'];
		$partner = substr($partner, 1, strlen($partner)-2);

		$exercise = Exercise::getExerciseByTitle($exTitle);

		$submission = Submission::getSubmissionByExerciseId($exercise->getId(), $user->getId());
		$submission = $submission[0];
		$submission->setPartner($partner);
		
		$submission->save();

		return JSON::success($partner." is your partner for ".$exTitle);
	}
}
