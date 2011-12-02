<?php

require_once("Model.php");


class Exercise extends Model
{
	protected $description;
	protected $skeletonId;
	protected $solutionId;
	protected $testClassId;
	protected $title;
	protected $visible;
	protected $section;
	protected $multiUser;
	protected $openDate;
	protected $closeDate;
	
	public function getTable(){
		return 'exercise';
	}

	public function setDescription($desc){
		$this->description = $desc;
	}

	public function getDescription(){
		return $this->description;
	}

	public function setSkeletonId($skeleton){
		$this->skeletonId = $skeleton;
	}

	public function getSkeletonId(){
		return $this->skeletonId;
	}

	public function setSolutionId($solution){
		$this->solutionId = $solution;
	}

	public function getSolutionId(){
		return $this->solutionId;
	}

	public function getTestClassId(){
		return $this->testClassId;
	}

	public function setTestClassId($testClass){
		$this->testClassId = $testClass;
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

	public function getOpenDate(){
		return $this->openDate;
	}

	public function setOpenDate($open){
		$this->openDate = $open;
	}

	public function getCloseDate(){
		return $this->closeDate;
	}

	public function setCloseDate($close){
		$this->closeDate = $close;
	}

	public function getSkeleton(){
		$file = CodeFile::getCodeFileById($this->skeletonId);

		return $file->getContents();
	}

	public function getSolution(){
		$file = CodeFile::getCodeFileById($this->solutionId);

		return $file->getContents();
	}

	public function getTestClass(){
		$file = CodeFile::getCodeFileById($this->testClassId);

		return $file->getContents();
	}

	public function setSolution($contents){
		$file = CodeFile::getCodeFileById($this->solutionId);
		$file->setContents($contents);

		$file->save();
	}

	public function setSkeleton($contents){
		$file = CodeFile::getCodeFileById($this->skeletonId);
		$file->setContents($contents);

		$file->save();
	}
	
	public function setTestClass($contents){
		$file = CodeFile::getCodeFileById($this->testClassId);
		$file->setContents($contents);

		$file->save();
	}

	public function getHelperClasses(){
		require_once('Database.php');
		$db = Database::getDb();

		$sth = $db->prepare('SELECT * FROM file WHERE ownerId = 100 AND
			exerciseId = :exId AND section = :section');
		$sth->execute(array(':exId' => $this->id, ':section' => $this->section));

		return $sth->fetchAll(PDO::FETCH_CLASS, 'CodeFile');
	}

	public static function getMultiUsers(){
		require_once('Database.php');
		$db = Database::getDb();
		$user = Auth::getCurrentUser();

		$sth =$db->prepare('SELECT * FROM exercise WHERE section =
			:section AND multiUser = 1 AND visible = 1');
		$sth->execute(array(':section' => $user->getSection()));
		$sth->setFetchMode(PDO::FETCH_CLASS, 'Exercise');
		
		return $sth->fetchAll();
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
		$sth = $db->prepare('SELECT * FROM exercise WHERE title = :title
			AND section = :section');
		$sth->execute(array(':title' => $title, ':section' => $user->getSection()));

		return $sth->fetchObject('Exercise');
	}

	public static function getExerciseById($id){
		require_once('Database.php');

		$db = Database::getDb();
		$sth =$db->prepare('SELECT * FROM exercise WHERE id = :thisId');
		$sth->execute(array(':thisId' => $id));

		return  $sth->fetchObject('Exercise');
	}

	public static function getVisibleExercises()
	{
		require_once('Database.php');
		$user = Auth::getCurrentUser();
		$db = Database::getDb();

		if(!$user->isAdmin()){
			$sth = $db->prepare('SELECT * FROM exercise WHERE visible = 1
				AND section = :section ORDER BY title');
			$sth->execute(array(':section' => $user->getSection()));		

			return $sth->fetchAll(PDO::FETCH_CLASS, 'Exercise');
		}

		$sth = $db->prepare('SELECT * FROM exercise WHERE section = :section ORDER BY title');
		$sth->execute(array(':section' => $user->getSection()));		

		return $sth->fetchAll(PDO::FETCH_CLASS, 'Exercise');


	}

	public static function getSubmissions($exerciseId){
		require_once('Database.php');
		$db = Database::getDb();

		$sth = $db->prepare('SELECT user.username, file.name, submission.success, submission.partner
			FROM submission JOIN file, user
			ON submission.fileId = file.id
			AND submission.userId = user.id
			WHERE submission.exerciseId = :exId
			AND user.admin = 0
			ORDER BY username');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute(array(':exId' => $exerciseId));

		return $sth->fetchAll();
	}

	public static function getTimedExercises(){
		require_once('Database.php');
		$db = Database::getDb();
		$user = Auth::getCurrentUser();

		$sth = $db->prepare("SELECT * FROM exercise WHERE section = :section AND
			openDate != '' ");
		$sth->execute(array(':section' => $user->getSection()));

		return $sth->fetchAll(PDO::FETCH_CLASS, 'Exercise');
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
		$sth = $db->prepare('SELECT id FROM user WHERE section = :section');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute(array('section' => $this->section));

		while($row = $sth->fetch()){
			$allUsers[] = $row['id'];
		}

		$sth = $db->prepare('SELECT DISTINCT ownerId FROM file WHERE
			exerciseId = :exerciseId');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute(array(':exerciseId' => $this->id));

		while($row = $sth->fetch()){
			$exUsers[] = $row['ownerId'];
		}

		foreach ($allUsers as $curUser){
			if (!in_array($curUser, $exUsers)){
			     $file = new CodeFile();
 			     $file->setContents($this->getSkeleton());
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

	}

}

?>
