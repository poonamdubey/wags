<?php

class Logout extends Command
{
    public function execute()
    {
        if(!Auth::isLoggedIn()){
            return JSON::warn("You were not logged in.");
        }

        $user = Auth::getCurrentUser();
        $username = $user->getUsername();
        $password = $user->getPassword();

        
        Auth::logout();
        if(strpos($username, "guest") !== false) {
            User::deleteUser($user->getId());
            JSON::warn("deleted user");
        } 
        return JSON::success('Logged out.');
    }
}

?>
