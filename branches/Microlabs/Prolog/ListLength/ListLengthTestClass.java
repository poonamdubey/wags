import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This is the test class for the ListLength Prolog exercise
 * 
 * @author Mike Dusenberry
 * @date 03/28/12
 * @file ListLengthTestClass.java
 *
 */
public class ListLengthTestClass {

    /**
     * Entry point of program
     *
     * Gets nonce value, and solution and student execution strings passed in as arguments
     *
     */
    public static void main(String args[]) {

    	// A nonce value will be passed in and used to validate a success
        String nonce = args[0];
        
        // These strings contain all necessary information for executing these files
    	// 	-Will just have to append any arguments needed
        String solutionExecString = args[1];
        String studentExecString = args[2];

        new ListLengthTestClass(nonce, solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Gets random lists and tests them
     *
     * May have to rig cases in order to test at least one case of empty and non-empty
     *  lists
     *
     */
    public ListLengthTestClass(String nonce, String solutionExecString, String studentExecString)
    {
    	boolean success = true; // whether or not the student's solution worked overall
    	
        boolean empty = false;
        boolean nonEmpty = false;
    	String list = "";
        
        // test 4 different random cases
        for (int i = 0; i < 4; i++)
        {
            // get a random list
            list = createRandomList();
            
            // need to keep track of cases to make sure that
            //  at least one case of each empty list and non-empty list
            //  is tested
            if (list.length() == 0)
                empty = true;
            else
                nonEmpty = true;
            
            // rig the last case if needed to make sure there has been at least one empty
            //  and one non-empty list
            if ( (i == 3) && (!empty || !nonEmpty) )
            {
                if (!empty) // if there has not been an empty list, make one
                    list = "";
                else
                {
                    while(!nonEmpty)
                    {
                        // get a random list
                        list = createRandomList();
                        
                        if (list.length() > 0)
                            nonEmpty = true;
                    }
                }
            }

            // test files on this list
            //	-if case was false (student answer was incorrect), then
            //	 success of exercise is a failure
            if (!testCase(solutionExecString, studentExecString, list))
            	success = false;
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
    private String createRandomList()
    {
        String list = "";
        
        Random random = new Random();
        
        // create random lists of length 0 to 9 
        // consisting of numbers 0 to 99
        int length = random.nextInt(10);
        for (int i = 0; i < length; i++)
        {
        	// just place spaces between numbers
            list += random.nextInt(100) + " ";
        }
        
        return list.trim();
    }

    /**
     * Run the prolog file on the given arguments and get results
     *
     */
    private String runFile(String fileExecString, String args)
    {
        String results = "";

        try {
            // get local runtime and execute both student and solution files
            Runtime runtime = Runtime.getRuntime();

            // run the file name with the list argument
            Process process = runtime.exec(fileExecString + " " + args);

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
     * Tests solution and student files on given list
     * 
     * Returns the boolean result of this case
     *
     */
    private boolean testCase(String solutionExecString, String studentExecString, String list)
    { 
        // Test the list on the solution and student prolog files
        String solutionOutput = runFile(solutionExecString, list);
        String studentOutput  = runFile(studentExecString, list);
        
        // Determine if answer is correct
        boolean isCorrect = true;
        if (!studentOutput.equals(solutionOutput))
            isCorrect = false;

        // Print out results
        //	-Looks cleaner to print original list with commas instead of spaces
        System.out.println("Original List:  [" + list.replaceAll(" ", ",") + "]");
        System.out.println("Correct Length: " + solutionOutput);
        System.out.println("Your Length:    " + studentOutput + "\n");
        
        if(isCorrect)
            System.out.println("Answer: Correct! \n");
        else
            System.out.println("Answer: Incorrect! \n");
        
        // Return results of this case
        return isCorrect;
    }
    
}
