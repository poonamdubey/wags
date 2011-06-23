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
		$this->desc = $desc;
	}

	public function getDescription(){
		return $this->desc;
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

	public function setTitle($etitle){
		$this->title = $etitle;
	}

	public function getTitle(){
		return $this->etitle;
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

	public static function getExerciseById($id){
		require_once('Database.php');

		$db = Database::getDb();
		$sth =$db->prepare('SELECT * FROM exercise WHERE id LIKE :id');
		$sth->execute(array(':id' => $id));

		if($result instanceof User){
			return $result;
		}

		return NULL;
	}

	public static function getVisibleExercises()
	{
		require_once('Database.php');
		$db = Database::getDb();
		
		$sth = $db->prepare('SELECT * FROM exercise WHERE visible like 1');
		$sth->execute();		

		return $sth->fetchAll(PDO::FETCH_CLASS, 'Exercise');
	}

}

?>
