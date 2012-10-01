<?php

/**
 * MagnetProblem.php 
 *
 * Represents an entry in the MagnetProblem table, which
 * is an aggregate of information necessary for composing
 * a magnet problem on the client
 *
 */

class MagnetProblem extends Model
{
	protected $timestamp;
	protected $title;
	protected $directions;
	protected $type;
	protected $innerFunctions;
	protected $forLeft;
	protected $forMid;
	protected $forRight;
    protected $booleans;
    protected $statements;
    protected $solution;
    protected $group;

    ###############
    # GETTERS     #
    ###############
	public function getTable(){
		return 'magnetProblem';
	}

    public function getTitle(){
        return $this->title;
    }

    public function getDirections(){
        return $this->directions;
    }

    public function getProblemType(){
        return $this->type;
    }

    public function getInnerFunctions(){
        return $this->innerFunctions;
    }

    public function getForLeft(){
        return $this->forLeft;
    }

    public function getForMid(){
        return $this->forMid;
    }

    public function getForRight(){
        return $this->forRight;
    }

    public function getBooleans(){
        return $this->booleans;
    }

    public function getStatements(){
        return $this->statements;
    }

    public function getGroup(){
        return $this->group;
    }

    public function getSolution(){
        return $this->solution;
    }

    #############
    # SETTERS   #
    #############
    public function setTitle($var){
         $this->title = $var;
    }

    public function setDirections($var){
         $this->directions = $var;
    }

    public function setProblemType($var){
         $this->type = $var;
    }

    public function setInnerFunctions($var){
         $this->innerFunctions = $var;
    }

    public function setForLeft($var){
         $this->forLeft = $var;
    }

    public function setForMid($var){
         $this->forMid = $var;
    }

    public function setForRight($var){
         $this->forRight = $var;
    }

    public function setBooleans($var){
         $this->booleans = $var;
    }

    public function setStatements($var){
         $this->statements = $var;
    }

    public function setGroup($var){
         $this->group = $var;
    }

    public function setSolution($var){
         $this->solution = $var;
    }

    // Can't currently use JSON.php to encode objects,
    // and casting it to an array/get_object_vars() wasn't
    // working, so I cheat.
    //
    // Note: Some fields aren't send, because they are not
    // used by the client
    public function toArray(){
        $objArray = array(
            "Object" => "MagnetProblem", // used for client side creation
            "id" => $this->getId(),
            "timestamp" => $this->timestamp,
            "title" => $this->title,
            "directions" => $this->directions,
            "type" => $this->type,
            "innerFunctions" => $this->innerFunctions,
            "forLeft" => $this->forLeft,
            "forMid" => $this->forMid,
            "forRight" => $this->forRight,
            "bools" => $this->booleans,
            "statements" => $this->statements,
            "solution" => $this->solution,
        );
        return $objArray;
    }

    # This function takes the ids of "problemData" entries in the database,
    # and constructs an array of the contents of the entries whose ID's were
    # given.  It then returns that array of strings
    private function getData($ids){
        require_once('Database.php');
        $db = Database::getDb();

        $inQuery = implode(',', array_fill(0, count($ids), '?'));
        $sth = $db->prepare('SELECT data FROM magnetData WHERE id
            IN(' . $inQuery . ')');

        $sth->execute($ids);
        $results = $sth->fetchall(PDO::FETCH_NUM);

        return $results;
    }
    
    ###########
    # STATIC  #
    ###########

    #  Currently, these DO NOT use sections as filters,
    #  THIS WILL HAVE TO CHANGE
    public static function getMagnetProblemById($id){
        // Database set up
        require_once('Database.php');
        $user = Auth::GetCurrentUser();
        $db = Database::getDb();

        $sth = $db->prepare('SELECT * FROM magnetProblem WHERE id = :id');
        $sth->execute(array(':id' => $id));
        return $sth->fetchObject('MagnetProblem');
    }

    #  Literally a copy/paste of the above function (which probably means
    #  I should have implemented it a different way) with substituting title
    #  for id
    public static function getMagnetProblemByTitle($title){
        // Database set up
        require_once('Database.php');
        $user = Auth::GetCurrentUser();
        $db = Database::getDb();

        $sth = $db->prepare('SELECT * FROM magnetProblem WHERE title = :title');
        $sth->execute(array(':title' => $title));
        return $sth->fetchObject('MagnetProblem');
    }
    
    #  Used by GetMagnetExercises.php to fill the magnetProblems listbox
    #  on the administrative tab
    public static function getMagnetProblemGroups(){
        require_once('Database.php');
        $user = Auth::GetCurrentUser();
        $db = Database::getDb();

        $sth = $db->prepare('SELECT MagnetProblemGroups.name 
            FROM MagnetProblemGroups, SectionMPG
            WHERE SectionMPG.section = :section
            AND SectionMPG.magnetGroup = MagnetProblemGroups.id');
        $sth->setFetchMode(PDO::FETCH_ASSOC);
        $sth->execute(array(':section' => $user->getSection()));

        $results = $sth->fetchAll();
        foreach($results as $result){
            $values[] = $result['name'];
        }
        return $values;
    }

    # Returns the names of all magnetProblems that are part
    # of the selected group AND available to that section
    #
    # NOTE:  A status of 1 is IMPLEMENTED, 2 is AVAILABLE
    #  because anything implemented is obviously AVAILABLE we
    #  use < 3 as the filter on the WHERE line
    public static function getMagnetProblemsByGroup($group){
        require_once('Database.php');
        $user = Auth::GetCurrentUser();
        $db = Database::getDb();

        $sth = $db->prepare('SELECT DISTINCT magnetProblem.title 
            FROM magnetProblem, SectionMP
            WHERE SectionMP.status < 3
            AND SectionMP.section = :section
            AND SectionMP.magnetP = magnetProblem.id
            AND magnetProblem.group = :group');
        $sth->setFetchMode(PDO::FETCH_NUM);
        $sth->execute(array(':section' => $user->getSection(),
            ':group' => $group));

        $results = $sth->fetchAll();
        return array_values($results);

    }

    # A utility function as MagnetProblemGroups are so basic
    # they don't warrant their own object.....  So, using PDO
    # may be sketchy, just you can't do PDO::FETCH_OBJ
    public static function getGroupIdByName($name){
        require_once('Database.php');
        $db = Database::getDb();

        $sth = $db->prepare('SELECT id FROM MagnetProblemGroups
            WHERE name = :name');
        $sth->setFetchMode(PDO::FETCH_ASSOC);
        $sth->execute(array(':name' => $name));
        
        // So, I can access $result['id'] that way, but not via array_values
        $result = $sth->fetch();
        $str = $result['id'];

        return $str;
    }
    
    public static function getAvailable(){
        require_once('Database.php');
        $db = Database::getDb();
        $user = Auth::getCurrentUser();

        $sth = $db->prepare('SELECT DISTINCT magnetProblem.title, magnetProblem.id 
            FROM magnetProblem, SectionMP
            WHERE SectionMP.section = :section 
            AND SectionMP.status = 1
            AND SectionMP.magnetP = magnetProblem.id');

        $sth->setFetchMode(PDO::FETCH_ASSOC);
        $sth->execute(array(':section' => $user->getSection()));
        $results = $sth->fetchAll();

        // So, this is really like working through two levels of array
        // Results is an array of arrays, each result is an array
        foreach($results as $result){
            $values[] = $result['id'];
            $values[] = $result['title'];
        }

        // Used to indicate to GetMagnetExercises.php that no 
        // exercises are assigned
        if($results == null){
            $values[] = "0";
            $values[] = "No Code Magnets Assigned!";
        }

        return $values;
    }

    # Set the entry in SectionMP with the given magnetP id to 
    # the given status - used in SetMagnetExercises.php
    public static function setProblemStatus($id, $status){
        require_once('Database.php');
        $db = Database::getDb();
        
        $sth = $db->prepare('UPDATE SectionMP
            SET status = :status
            WHERE magnetP = :id');
        $sth->execute(array(':status' => $status, ':id' => $id));
    }

    # Changes all entries in SectionMP for the users section to
    # '2' = which is AVAILABLE but not IMPLEMENTED
    # used in SetMagnetExercises.php
    public static function unAssignAll(){
        require_once('Database.php');
        $db = Database::getDb();
        $user = Auth::getCurrentUser();

        $sth = $db->prepare('UPDATE SectionMP
            SET status = 2
            WHERE section = :section');
        $sth->execute(array(':section' => $user->getSection()));
    }

    # This method adds the default group and all problems in the default
    # group to any newly created section.  This method is called from
    # LinkNewSection.php
    #
    # We will either have to continually update this method with any changes
    # to the database concerning defautl exercises, or make it more robust
    # so that it dynamically changes what values are added depending on what
    # is currently in the database
    public static function addDefaults($sectionId){
        require_once('Database.php');
        $db = Database::getDb();
        
        $sth = $db->prepare('INSERT INTO SectionMPG
            VALUES (NULL, :sectionId, 1)');
        $sth->execute(array(':sectionId' => $sectionId));

        $sth = $db->prepare('INSERT INTO SectionMP
            VALUES
                (NULL, :sectionId, 46, 2),
                (NULL, :sectionId, 47, 2),
                (NULL, :sectionId, 48, 2),
                (NULL, :sectionId, 60, 2)');
        $sth->execute(array(':sectionId' => $sectionId));
    }

}
?>