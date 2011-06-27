<?php

/**
 * Submission
 *
 * Represents an entry in the submission table
 *
 */

class Submission extends Model
{
	protected $exerciseId;
	protected $fileId;
	protected $userId;	

	public function getTable(){
		return 'submission';
	}
	public function getExerciseId(){
		return $this->exerciseId;
	}
	public function getFileId(){
		return $this->fileId;
	}
	public function getUserId(){
		return $this->userId;
	}

	public function setExerciseId($id){
		$this->exerciseId = $id;
	}
	public function setFileId($id){
		$this->fileId = $id;
	}
	public function setUserId($id){
		$this->userId = $id;
	}


	public static function getSubmissionByExerciseId($exerciseId, User $user){
		require_once('Database.php');

		$db = Database::getDb();

		$sth = $db->prepare('SELECT * FROM submission WHERE exerciseId = :exId
			and userId = :userId');
		$sth->execute(array(':exId' => $exerciseId, ':userId' => $user->getId()));

		return $sth->fetchObject('Submission');
	}

	public static function submissionExistsByExerciseId($exerciseId, $user){
		require_once('Database.php');
		$db = Database::getDb();

		$sth = $db->prepare('SELECT * FROM submission WHERE exerciseId = :exId
			AND userId = :userId');
		$sth->execute(array(':exId' => $exerciseId, ':userId' => $user));

		return sizeof($sth->fetchAll()) > 0;
	}

}
?>
