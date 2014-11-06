<?php

/**
 * LoginAsGuest.php
 * 
 * @author Dakota Murray 
 */

class LoginAsGuest extends Command
{
    public function execute()
    {

        //CREATE THE NEW GUEST USER
        $now = time();
        $username = "guest" . $now;
        $user = new User();
        $user->setUsername($username);
        $user->setEmail("");
        $user->setFirstName("guest");
        $user->setLastName("guest");
        $user->setPassword(md5($username));
        $user->setSection(130);     //CHANGE TO GUEST SECTION
        $user->setLastLogin(0);
        $user->setAdded($now);
        $user->setUpdated($now);
        $user->setAdmin(0);

        try {
            $user->save();
        } catch (Exception $e) {
            $msg = "Falied to login as guest ".$e->getMessage();
            return 0;
        }

        $result = Auth::login($username, $username);
        if($result) {
            return JSON::success(Auth::getCurrentUser()->toArray());
        }

        return JSON::error('Login failed. Check username and password.');
    }
}
