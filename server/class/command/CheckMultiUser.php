<?php

/**
*Class CheckMultiUser
*Author: Philip Meznar
*
*This class is meant to help with the partner functionality.
*When the user loads the editor page, this class is called
*to check for the existence of a multiUser exercise without
*a declared partner for this user.
*/

class CheckMultiUser extends Command
{
	public function execute()
	{
		$exerciseTitle = Exercise::needsPartner();
		if($exerciseTitle != 0){
			$exerciseTitle = $exerciseTitle[0];
			return JSON::error($exerciseTitle);
		}
		return JSON::success("");
	}
}

?>
