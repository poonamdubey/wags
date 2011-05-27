<?php

/**
 * User
 * 
 * Models a user. (username, password, last login time, ...)
 *
 * @author Robert Bost <bostrt at appstate dot edu>
 */

require_once("Model.php");

class User extends Model
{
    protected $username;
    protected $firstName;
    protected $lastName;
    protected $email;
    protected $lastLogin;
    protected $password;

    public function getTable(){
        return 'user';
    }

    /**
     * Get all code files related to this user.
     *
     * @return CodeFile[]
     */
    public function getCodeFiles()
    {
        require_once('Database.php');
        require_once('CodeFile.php');
        
        $db = Database::getDb();

        $sth = $db->prepare('SELECT * FROM file WHERE ownerId = :ownerId');
        $sth->execute(array(':ownerId' => $this->id));
        
        return $sth->fetchAll(PDO::FETCH_CLASS, 'CodeFile');
    }

    /**
     * Getters, Setters.
     */
    public function setUsername($username){
        $this->username = $username;
    }
    public function getUsername(){
        return $this->username;
    }
    public function getFirstName(){
        return $this->firstName;
    }
    public function getLastName(){
        return $this->lastName;
    }
    public function getEmail(){
        return $this->email;
    }
    public function setEmail($email){
        $this->email = $email;
    }
    public function setFirstName($name){
        $this->firstName = $name;
    }
    public function setLastName($name){
        $this->lastName = $name;
    }
    public function setPassword($password){
        $this->password = $password;
    }
    public function getPassword(){
        return $this->password;
    }
    public function getLastLogin(){
        return $this->lastLogin;
    }

    public function setLastLogin($time){
        $this->lastLogin = $time;
    }

    /************
     * Static helpers
     ************/
    
    /**
     * Check if a user exists in DB by their username.
     *
     * @return boolean - TRUE if exists, FALSE otherwise.
     */
    public static function isUserValid(User $user)
    {
        require_once('Database.php');

        $sql = 'SELECT count(*) FROM user WHERE username LIKE :username';

        $db = Database::getDb();
        $sth = $db->prepare($sql);
        $sth->execute(array(':username' => $user->getUsername()));
        $result = $sth->fetch();

        // The returned count should be only 1.
        return $result[0] == 1 ? TRUE : FALSE;
    }

    /**
     * Get a User object from the databse by the given username.
     *
     * @return User
     */
    public static function getUserByUsername($username)
    {
        require_once('Database.php');
        
        $db = Database::getDb();
        $sth = $db->prepare('SELECT * FROM user WHERE username LIKE :username');
        $sth->execute(array(':username' => $username));

        $result = $sth->fetchObject('User');

        if($result instanceof User){
            return $result;
        }

        return NULL;
    }

    /**
     * Check if the username exists in the database.
     *
     * @return boolean
     */
    public static function isUsername($username)
    {
        require_once('Database.php');
        
        $db = Database::getDb();
        $sth = $db->prepare('SELECT count(*) as count FROM user WHERE username LIKE :username');
        $sth->execute(array(':username' => $username));
        
        $result = $sth->fetch(PDO::FETCH_ASSOC);
        
        return $result['count'] == 1;
    }

    /**
     * Check if $password is the password of the user with $username.
     *
     * @param password - plain text password.
     * @return boolean
     */
    public static function passwordMatches($username, $password)
    {
        require_once('Database.php');
        
        $db = Database::getDb();
        $sth = $db->prepare('SELECT count(*) as count FROM user WHERE username LIKE :username AND password LIKE :password');
        $sth->execute(array(':username' => $username, ':password' => md5($password)));

        $result = $sth->fetch(PDO::FETCH_ASSOC);      
        
        return $result['count'] == 1;
    }
}
?>