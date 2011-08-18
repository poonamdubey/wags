<?php

/**
 * CodeFile
 * 
 * Represents a code file on the server side. (this side)
 *
 * @author Robert Bost <bostrt at appstate dot edu>
 */

class CodeFile extends Model
{
    protected $name;
    protected $ownerId;
    protected $contents;
    protected $exerciseId;
	protected $section;
    
    public function getTable(){
        return 'file';
    }
    public function getContents(){
        return $this->contents;
    }
    public function getName(){
        return $this->name;
    }
    public function getOwnerId(){
        return $this->ownerId;
    }
    public function getExerciseId(){
		return $this->exerciseId;
    }
	public function getSection(){
		return $this->section;
	}


    public function  setContents($text){
        $this->contents = $text;
    }
    public function setName($name){
        $this->name = $name;
    }
    public function setOwnerId($id){
        $this->ownerId = $id;
    }
    public function setExerciseId($id){
	    $this->exerciseId = $id;
    }
	public function setSection($section){
		$this->section = $section;
	}
   

    /**
     * Static helpers
     */

    /**
     * Get a CodeFile object from database by it's name.
     *
     * @return CodeFile 
     */
    public static function getCodeFileByName($name, User $user)
    {
        require_once('Database.php');

        $db = Database::getDb();
        
        $sth = $db->prepare('SELECT * FROM file WHERE name LIKE :name AND ownerId = :id OR ownerId = 0
			AND name LIKE :name');
        $sth->execute(array(':name' => $name, ':id' => $user->getId()));

        return $sth->fetchObject('CodeFile');
    }

    /**
     * Get an array of CodeFile objects by user.
     */
    public static function getCodeFilesByUser(User $user)
    {
        require_once('Database.php');
        $db = Database::getDb();
        
        $sth = $db->prepare('SELECT * FROM file WHERE ownerId = :id 
			OR ownerId = 0 and section = :section');
        $sth->execute(array(':id' => $user->getId(), ':section' => $user->getSection()));
        
        return $sth->fetchAll(PDO::FETCH_CLASS, 'CodeFile');
    }

    /**
     * Check if one or more file exists with the passed name.
     */
    public static function codeFileExistsByName($name, User $user)
    {
      require_once('Database.php');
      $db = Database::getDb();
      
      $sth = $db->prepare('SELECT * FROM file WHERE ownerId = :id AND name LIKE :name');
      $sth->execute(array(':id' => $user->getId(), ':name' => $name));

      return sizeof($sth->fetchAll()) > 0;
    }
}
?>
