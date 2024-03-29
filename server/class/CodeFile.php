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
   
	public static function getHelperId(){
		return 0;
	}

    /**
     * Static helpers
     */

    /**
     * Get a CodeFile object from database by it's name.
     *
     * @return CodeFile 
     */
    public static function getCodeFileByName($name)
    {
        require_once('Database.php');

        $db = Database::getDb();
		$user = Auth::getCurrentUser();
        
        $sth = $db->prepare('SELECT * FROM file WHERE name LIKE :name AND ownerId = :id OR ownerId = :helper 
			AND name LIKE :name');
        $sth->execute(array(':name' => $name, ':helper' => CodeFile::getHelperId(), ':id' => $user->getId()));

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
			OR ownerId = :helper and section = :section');
        $sth->execute(array(':id' => $user->getId(), ':helper' => CodeFile::getHelperId(), ':section' => $user->getSection()));
        
        return $sth->fetchAll(PDO::FETCH_CLASS, 'CodeFile');
    }

    public static function getPartnerFile($partnerId, $fileName){
        require_once('Database.php');

        $db = Database::getDb();

        $sth = $db->prepare('SELECT * FROM file WHERE name LIKE :name AND ownerId like :id');
        $sth->execute(array(':name' => $fileName, ':id' =>  $partnerId));

        return $sth->fetchObject('CodeFile');
    }

	public static function getCodeFileById($id){
		require_once('Database.php');

		$db = Database::getDb();

		$sth = $db->prepare('SELECT * FROM file WHERE id like :id');
		$sth->execute(array(':id' => $id));

		return $sth->fetchObject('CodeFile');
	}

}
?>
