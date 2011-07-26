<?php

/**
 * Since you cannot use pcntl_fork()
 * from within an Apache PHP script
 * we'll do this. 
 *
 * This script will be called from class/command/Review.php.
 * 
 * Spawn a process. Parent will wait around for about WAIT_TIME seconds
 * and if it has been longer than WAIT_TIME seconds and the child is not 
 * done running KILL IT! 
 */

define('WAIT_TIME', 10);

/* Get arguments passed in */
$dir = $argv[1];
$className = $argv[2];

/* fork it! */
$pid = pcntl_fork();

if($pid == -1){
    echo 'Could not run code';
}
//parent
else if ($pid){
    /* 
     * Manage group.
     * Wait WAIT_TIME seconds
     */
	$now = time()+WAIT_TIME;
	$result = null;
    while(time() <= $now && $result == null){
        $result = pcntl_waitpid($pid, $status, WNOHANG);
    }
//    print $result.' '.$status;
    if($result == $pid){
        /* Exited child ID */
//        print $result;
    }else{
        /* Kill the group */
	$pgid = posix_getpgid($pid);
	print "Ran too long - check for efficiency/infinite loops";
	exec("kill -9 -$pgid"); 
    }
}
//child
else{
    exec("/usr/bin/java -cp $dir $className 2>&1", $output);
    print_r($output[0]);
}

?>
