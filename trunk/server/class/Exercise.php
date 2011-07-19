<?php

require_once("Model.php");


class Exercise extends Model
{
	protected $description;
	protected $skeleton;
	protected $solution;
	protected $title;
	protected $visible;
	
	public function getTable(){
		return 'exercise';
	}

	public function setDescription($desc){
		$this->description = $desc;
	}

	public function getDescription(){
		return $this->description;
	}

	public function setSkeleton($skeleton){
		$this->skeleton = $skeleton;
	}

	public function getSkeleton(){
		return $this->skeleton;
	}

	public function setSolution($solution){
		$this->solution = $solution;
	}

	public function getSolution(){
		return $this->solution;
	}

	public function setTitle($title){
		$this->title = $title;
	}

	public function getTitle(){
		return $this->title;
	}

	public function setVisible($visible){
		$this->visible = $visible;
	}

	public function getVisible(){
		return $this->visible;
	}

	public static function isVisible(Exercise $exercise)
	{
		require_once('Database.php');
		$db = Database::getDb();
		$sth = $db->prepare('SELECT visible FROM exercise WHERE id = :id');
		$sth->execute(array(':id' => $this->id));
		$result =$sth->fetch();
			
		x($db->fetchAll(),1);

		return $result[0] == 1 ? TRUE : FALSE;
	}

	public static function getExerciseByTitle($title){
		require_once('Database.php');

		$db = Database::getDb();
		$sth = $db->prepare('SELECT * FROM exercise WHERE title LIKE :title');
		$sth->execute(array(':title' => $title));

		return $sth->fetchObject('Exercise');
	}

	public static function getExerciseById($id){
		require_once('Database.php');

		$db = Database::getDb();
		$sth =$db->prepare('SELECT * FROM exercise WHERE id = :thisId');
		$sth->execute(array(':thisId' => $id));

		return $sth->fetchAll(PDO::FETCH_CLASS, 'Exercise');
	}

	public static function getVisibleExercises()
	{
		require_once('Database.php');
		$db = Database::getDb();
		
		$sth = $db->prepare('SELECT * FROM exercise WHERE visible like 1');
		$sth->execute();		

		return $sth->fetchAll(PDO::FETCH_CLASS, 'Exercise');
	}

	public static function exerciseExistsByTitle($title){
		require_once('Database.php');
		$db = Database::getDb();


		$sth = $db->prepare('SELECT * FROM exercise WHERE title LIKE :title');
		$sth->execute(array(':title' => $title));

		return sizeof($sth->fetchAll()) > 0;

	}
	
	public static function getSubmissions($exerciseId){
		require_once('Database.php');
		$db = Database::getDb();

		$sth = $db->prepare('SELECT user.username, file.name, submission.success
			FROM exercise JOIN file, submission, user
			ON exercise.id = file.exerciseId	
			AND file.ownerId = user.id
			AND submission.fileId = file.id
			WHERE exercise.id LIKE :exId');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute(array(':exId' => $exerciseId));

		return $sth->fetchAll();
	}

	public function addSkeletons(){
		require_once('Database.php');
		$db = Database::getDb();

		$allUsers = array();
		$exUsers = array();

		$sth = $db->prepare('SELECT id FROM user');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute();

		while($row = $sth->fetch()){
			$allUsers[] = $row['id'];
		}

		$sth = $db->prepare('SELECT DISTINCT ownerId FROM file WHERE
			exerciseId = :exerciseId');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute(array(':exerciseId' => $this->exerciseId));

		while($row = $sth->fetch()){
			$exUsers[] = $row['ownerId'];
		}

		foreach ($allUsers as $curUser){
			if (!in_array($curUser, $exUsers)){
				JSON::error("accessed1");
			     $file = new CodeFile();
 			     $file->setContents($this->skeleton);
		             $now = time();
		             $file->setName("/".$this->title."/skeleton");
		             $file->setExerciseId($this->id);
		             $file->setOwnerId($curUser);
		             $file->setUpdated($now);
			     $file->setAdded($now);
			     $file->save();
				JSON::error("accessed2");
			}
		}

	}

}

?>
