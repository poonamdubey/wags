<?php

require_once("Model.php");

/**
 * This class represents nodes stored
 * in different problem results.  The nodes
 * reference a key and a problem result - 
 * every key/problem result combination must
 * be distinct
 *
 * They also hold top and left values to
 * mimic original placement when results are 
 * reviewed
 */

class Node extends Model
{
	protected $key;
	protected $problemResult;
	protected $problem;
	protected $top;
	protected $left;

	//Getters
	public function getKey(){
		return $this->key;
	}

	public function getProblemResult(){
		return $this->problemResult;
	}

	public function getProblem(){
		return $this->problem;
	}

	public function getTop(){
		return $this->top;
	}

	public function getLeft(){
		return $this->left;
	}

	//Setters
	//Shouldn't ever need to be used,
	//all variables should effectively be final
	public function setKey($newKey){
		$this->key = $newKey;
	}

	public function setProblemResult($probResult){
		$this->problemResult = $probResult;
	}

	public function setTop($newTop){
		$this->top = $newTop;
	}

	public function setLeft($newLeft){
		$this->left = $newLeft;
	}
}
