import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This is the test class for the IsMember Prolog exercise
 * 
 * @author Mike Dusenberry
 * @date 03/20/12
 * @file IsMemberTestClass.java
 *
 */
public class IsMemberTestClass {

    private boolean success = true; // whether or not the student's solution worked overall

    /**
     * Entry point of program
     *
     * Gets solution and student execution strings passed in as arguments
     *
     */
    public static void main(String args[]) {

    	// A nonce value will be passed in and used to validate a success
        String nonce = args[0];
        
        // These strings contain all necessary information for executing these files
    	// 	-Will just have to append any arguments needed
        String solutionExecString = args[1];
        String studentExecString = args[2];

        new IsMemberTestClass(nonce, solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Gets random list and random number and tests them to see if the
     * number is a member of the list
     * 
     * Need to make sure that there are at least one true (number is a member)
     * and at least one false (number is not a member) cases so that the student's
     * answer is fully tested.
     * 	-Lists are not very long, so this extra logic is not very resource intensive
     *
     */
    public IsMemberTestClass(String nonce, String solutionExecString, String studentExecString)
    {    	
    	String list;
    	int[] listArray;
        int num, i, j, length, randomOffset;
        Random random = new Random();
        
        String solutionResult = "";
        boolean memberCase = false; // has there been a case where num is a member
        boolean nonMemberCase = false; // has there been a case where num is not a member
        boolean found;
        
        // test 4 different cases
        for (i = 0; i < 4; i++)
        {
            // to ensure there are plenty of hits, we only want to test on
            //  small range of numbers (~10), but we should create a random offset
            //  so that the numbers are not predictable
            randomOffset = random.nextInt(1000);
            
        	// get a random number
            //  range of values will be between randomOffset
            //  and 9 + randomOffset
            num = random.nextInt(10) + randomOffset;
            
            // get a random list
            listArray = createRandomList(randomOffset);
            length = listArray.length;
            
            // need to keep track of cases to make sure that
            //  at least one member and one non-member case
            //  is tested
            if (!memberCase || !nonMemberCase)  // only do this if necessary
            {
                found = false;
                for (j = 0; j < length; j++)
                {
                    if (num == listArray[j]) // if in array, then member
                    {
                        found = true;
                        break;
                    }
                }
                // now see if found
                if (found)
                    memberCase = true;
                else 
                    nonMemberCase = true;
            }
        	
        	// If last case, and there hasn't been either a member case or
        	//	a non-member case, need to force one
        	if (i == 3 && (!memberCase || !nonMemberCase))
        	{
        		if (!memberCase) // force a member case
        		{   
                    // make sure list is at least 1 atom long
                    while (length < 1)
                    {
                        // get a random list
                        listArray = createRandomList(randomOffset);
                        length = listArray.length;
                    }
                    
    	            // choose random number from this list
    	            num = listArray[random.nextInt(length)];
        		}
        		else // force a non-member case
        		{
        			// change num so that it can not be in list
                    //  by placing it oustide of the range of numbers
                    // highest num could be is 9 + randomOffset
                    num =  10 + randomOffset;
        		}
        	}
            
            // clear out string list
            list = "";
	        
            // build list as string
        	//	-just place spaces between numbers
            for (j = 0; j < length; j++)
            {
            	list += listArray[j] + " ";
            }
            list = list.trim(); // get rid of last space
            
        	// test files on number and list
            solutionResult = testCase(solutionExecString, studentExecString, num, list);
            
            // keep track of whether there has been a member and non-member cases
            if (solutionResult.equals("True"))
            	memberCase = true;
            else if (solutionResult.equals("False"))
            	nonMemberCase = true;

        }

        // Print nonce value if successful for validation
        if (success)
            System.out.println("\n" + nonce);
        else
            System.out.println("\nFailure");
    }
    
    /**
     * This creates a random list of random length
     *
     */
    private int[] createRandomList(int randomOffset)
    {   
        Random random = new Random();
        
        // create random lists of length 0 to 9 
        // consisting of numbers 0 to 10 offset by
        // the given random offset
        int length = random.nextInt(10);
        int[] list = new int[length];
        		
        for (int i = 0; i < length; i++)
        {
        	list[i] = random.nextInt(10) + randomOffset;
        }
        
        return list;
    }

    /**
     * Run the prolog file on the given number and get results
     *
     */
    private String runFile(String execString, int num, String list)
    {
        String results = "";

        try {
            // get local runtime and execute both student and solution files
            Runtime runtime = Runtime.getRuntime();

            // run the file name with the number and list arguments
            Process process = runtime.exec(execString + " " + num + " " + list);

            // set up a stream to the get output of the file            
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String processOutput = null;

            // get the output from the file
            while ((processOutput = input.readLine()) != null)
            {
                results += processOutput + "\n";
            }

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("There was an error running the files");
            System.exit(1);
        }

        // return resulting output from file
        return results.trim(); // trim to get rid of trailing newline
    }

    /**
     * Tests solution and student files on given list and number
     * 
     * Returns the solutionOutput so that we can keep track of types of
     * cases (is member vs. is not member)
     *
     */
    private String testCase(String solutionExecString, String studentExecString, int num, String list)
    { 
        // Test the solution and student prolog files on the list and number
        String solutionOutput = runFile(solutionExecString, num, list);
        String studentOutput  = runFile(studentExecString, num, list);
        
        // Determine if answer is correct
        Boolean isCorrect = true;
        if (!studentOutput.equals(solutionOutput))
        {
            success   = false;
            isCorrect = false;
        }

        // Print out results
        // 	-Looks cleaner to print original list with commas instead of spaces
        System.out.println("Original Number: " + num);
        System.out.println("Original List  : [" + list.replaceAll(" ", ",") + "]");
        System.out.println("Correct Answer : " + solutionOutput);
        System.out.println("Your Answer    : " + studentOutput + "\n");
        
        if(isCorrect)
            System.out.println("Answer: Correct! \n");
        else
            System.out.println("Answer: Incorrect! \n");
        
        return solutionOutput;
    }
    
}
