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

    public function  setContents($text){
        $this->contents = $text;
    }
    public function setName($name){
        $this->name = $name;
    }
    public function setOwnerId($id){
        $this->ownerId = $id;
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
        
        $sth = $db->prepare('SELECT * FROM file WHERE name LIKE :name AND ownerId = :id');
        $sth->execute(array(':name' => $name, ':id' => $user->getId()));

        return $sth->fetchObject('CodeFile');
    }

    /**
     * Get a CodeFile object from database by it's ID.
     *
     * @return CodeFile
     */
    public static function getCodeFileById($id, User $user)
    {
        require_once('Database.php');
        
        $db = Database::getDb();

        $sth = $db->prepare('SELECT * FROM file WHERE id = :id');
        $sth->execute(array(':id' => $id));
        
        return $sth->fetchObject('CodeFile');
    }

    /**
     * Get an array of CodeFile objects by user.
     */
    public static function getCodeFilesByUser(User $user)
    {
        require_once('Database.php');
        $db = Database::getDb();
        
        $sth = $db->prepare('SELECT * FROM file WHERE ownerId = :id');
        $sth->execute(array(':id' => $user->getId()));
        
        return $sth->fetchAll(PDO::FETCH_CLASS, 'CodeFile');
    }
}
?>