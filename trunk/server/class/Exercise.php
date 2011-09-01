<?php

require_once("Model.php");


class Exercise extends Model
{
	protected $description;
	protected $skeleton;
	protected $solution;
	protected $testClass;
	protected $title;
	protected $visible;
	protected $section;
	protected $multiUser;
	
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

	public function getTestClass(){
		return $this->testClass;
	}

	public function setTestClass($testClass){
		$this->testClass = $testClass;
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

	public function setSection($section=0){
		$this->section = $section;
	}

	public function getSection(){
		return $this->section;
	}

	public function setMultiUser($multiUser){
		$this->multiUser = $multiUser;
	}

	public function isMultiUser(){
		if($this->multiUser == 1) 
			return TRUE;

		return FALSE;
	}

	public function getHelperClasses(){
		require_once('Database.php');
		$db = Database::getDb();

		$sth = $db->prepare('SELECT * FROM file WHERE ownerId LIKE 0 AND
			exerciseId LIKE :exId AND section LIKE :section');
		$sth->execute(array(':exId' => $this->id, ':section' => $this->section));

		return $sth->fetchAll(PDO::FETCH_CLASS, 'CodeFile');
	}

	public static function hasMultiUser(){
		require_once('Database.php');
		$db = Database::getDb();
		$user = Auth::getCurrentUser();

		$sth =$db->prepare('SELECT title FROM exercise WHERE section LIKE
			:section AND multiUser LIKE 1');
		$sth->execute(array(':section' => $user->getSection()));
		
		return sizeof($sth->fetchAll());
	}

	//there are going to be problems if the teacher ever uploads
	//more than one group exercise at a time...
	public static function needsPartner(){
		require_once('Database.php');
		$db = Database::getDb();
		$user = Auth::getCurrentUser();

		$sth = $db->prepare('SELECT DISTINCT exercise.title FROM exercise JOIN submission WHERE
			submission.partner = "" AND exercise.section LIKE :section
			AND exercise.multiUser = 1');
		$sth->execute(array(':section'=>$user->getSection()));
		$sth->setFetchMode(PDO::FETCH_NUM);
		return $sth->fetch();
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

		$user = Auth::getCurrentUser();

		$db = Database::getDb();
		$sth = $db->prepare('SELECT * FROM exercise WHERE title LIKE :title
			AND section like :section');
		$sth->execute(array(':title' => $title, ':section' => $user->getSection()));

		return $sth->fetchObject('Exercise');
	}

	public static function getExerciseById($id){
		require_once('Database.php');

		$db = Database::getDb();
		$sth =$db->prepare('SELECT * FROM exercise WHERE id = :thisId');
		$sth->execute(array(':thisId' => $id));

		$exerciseList =  $sth->fetchAll(PDO::FETCH_CLASS, 'Exercise');
		return $exerciseList[0];
	}

	public static function getVisibleExercises()
	{
		require_once('Database.php');
		$user = Auth::getCurrentUser();

		$db = Database::getDb();
		
		$sth = $db->prepare('SELECT * FROM exercise WHERE visible LIKE 1
			AND section LIKE :section');
		$sth->execute(array(':section' => $user->getSection()));		

		return $sth->fetchAll(PDO::FETCH_CLASS, 'Exercise');
	}

	public static function exerciseExistsByTitle($title){
		require_once('Database.php');
		$db = Database::getDb();

		$user = Auth::getCurrentUser();

		$sth = $db->prepare('SELECT * FROM exercise WHERE title LIKE :title AND section like :section');
		$sth->execute(array(':title' => $title, ':section' => $user->getSection()));

		return sizeof($sth->fetchAll()) > 0;

	}
	
	public static function getSubmissions($exerciseId){
		require_once('Database.php');
		$db = Database::getDb();

		$sth = $db->prepare('SELECT user.username, file.name, submission.success
			FROM submission JOIN file, user
			ON submission.fileId = file.id
			AND submission.userId = user.id
			WHERE submission.exerciseId LIKE :exId
			ORDER BY username');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute(array(':exId' => $exerciseId));

		return $sth->fetchAll();
	}

	public function addSkeletons(){
		require_once('Database.php');
		$db = Database::getDb();

		$allUsers = array();
		$exUsers = array();

		//Due to my extremely limited database knowledge, I'm doing this
		//method in an extremely ugly way.  First, I grab all users in this
		//section.  Then, I grab all users who have an file for this 
		//exercise already.  If the user exists in the first list, but not
		//the second, they get a skeleton
		$sth = $db->prepare('SELECT id FROM user WHERE section LIKE :section');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute(array('section' => $this->section));

		while($row = $sth->fetch()){
			$allUsers[] = $row['id'];
		}

		$sth = $db->prepare('SELECT DISTINCT ownerId FROM file WHERE
			exerciseId LIKE :exerciseId');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute(array(':exerciseId' => $this->id));

		while($row = $sth->fetch()){
			$exUsers[] = $row['ownerId'];
		}

		foreach ($allUsers as $curUser){
			if (!in_array($curUser, $exUsers)){
			     $file = new CodeFile();
 			     $file->setContents($this->skeleton);
		             $now = time();
		             $file->setName("/".$this->title."/skeleton");
		             $file->setExerciseId($this->id);
		             $file->setOwnerId($curUser);
					 $file->setSection($this->getSection());
		             $file->setUpdated($now);
			     $file->setAdded($now);
			     $file->save();
			}
		}

		JSON::error("Added Skeletons for section ".$this->getSection());

	}

}

?>
