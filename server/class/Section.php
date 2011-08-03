<?php

require_once("Model.php");

/**
* This class is used for grouping
* Specifically, this class allows the administrator
* to only view the information pertinent to users
* in their section.
*
* Thus, it's mainly used in WHERE clauses in 
* database queries
*/

class Section extends Model
{
	protected $name;
	protected $administrator;

	public function getTable(){
		return 'section';
	}
	public function getName(){
		return $this->name;
	}
	public function setName($name){
		$this->name = $name;
	}
	public function getAdmin(){
		return $this->administrator;
	}
	public function setAdmin($admin){
		$this->administrator = $admin;
	}

	public static function getSectionNames(){
		require_once('Database.php');
		$db = Database::getDb();

		$sth = $db->prepare('SELECT name FROM section;');
		$sth->setFetchMode(PDO::FETCH_NUM); 
		$sth->execute();

		return $sth->fetchAll();
	}
}

?>
