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


	public static function getSubmissionByExerciseId($exerciseId, $user){
		require_once('Database.php');

		$db = Database::getDb();

		$sth = $db->prepare('SELECT * FROM submission WHERE exerciseId LIKE :exId
			and userId LIKE :userId');
		$sth->execute(array(':exId' => $exerciseId, ':userId' => $user));

		return $sth->fetchAll(PDO::FETCH_CLASS, 'Submission');
	}

	public static function submissionExistsByExerciseId($exerciseId, $user){
		require_once('Database.php');

		$db = Database::getDb();

		$sth = $db->prepare('SELECT * FROM submission WHERE exerciseId LIKE :exId 
			AND userId LIKE :userId');

		$sth->execute(array(':exId' => $exerciseId, ':userId' => $user));

		if ($sth->fetch()) return TRUE;
		return FALSE;
	}

}
?>