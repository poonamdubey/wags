<?php

class SetPartner extends Command
{
	public function execute()
	{
		$user = Auth::GetCurrentUser();
		$exTitle = $_GET['ex'];
		$partner = $_GET['partner'];

		$exercise = Exercise::getExerciseByTitle($exTitle);

		if(Submission::submissionExistsByExerciseId($exercise->getId(), $user->getId()))
		{
			$submission = Submission::getSubmissionByExerciseId($exercise->getId(), $user->getId());
			$submission->setPartner($partner);
		}else{
			$submission = new Submission();
			$submission->setExerciseId($exercise->getId());
			$submission->setFileId(0);
			$submission->setUserId($user->getId());
			$submission->setSuccess(0);
			$submission->setPartner($partner);
			$submission->setAdded(time());
			$submission->setUpdated(time());
		}
		
		$submission->save();

		return JSON::success($partner." is your partner for ".$exTitle);
	}
}
