<?php

require_once("Model.php");

class Problem extends Model{
	protected $name;
	protected $problemText;
	protected $evaluation;
	protected $rules;
	protected $arguments;
	protected $section;

	//Getters
	public function getName(){
		return $this->name;
	}

	public function getProblemText(){
		return $this->problemText;
	}

	public function getEvaluation(){
		return $this->evaluation;
	}

	public function getRules(){
		return $this->rules;
	}

	public function getArguments(){
		return $this->arguments;
	}

	public function getSection(){
		return $this->section;
	}

	public function getNodes(){
		require_once('Database.php');
		$db = Database::getDb();

		$sth = $db->prepare('SELECT * FROM Node WHERE problem = :prob');
		$sth->setFetchMode(PDO::FETCH_CLASS, 'Node');
		$sth->execute(array(':prob' => $this->getId()));

		return $sth->fetchAll();
	}

	//Setters
	//I'm gonna wait to implement these as,
	//once more, they shouldn't be used...

}
