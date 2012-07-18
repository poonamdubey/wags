import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This is the test class for the IsOdd Prolog exercise
 * 
 * @author Mike Dusenberry
 * @date 03/20/12
 * @file IsOddTestClass.java
 *
 */
public class IsOddTestClass { 

    /**
     * Entry point of program
     *
     * Gets solution and student execution strings passed in as arguments
     *
     */
    public static void main(String args[]) {

        // These strings contain all necessary information for executing these files
    	// 	-Will just have to append any arguments needed
        String solutionExecString = args[0];
        String studentExecString = args[1];

        new IsOddTestClass(solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Gets random number and tests it
     *
     * May have to rig last case to ensure that at least one odd and one
     * even case is tested
     *
     */
    public IsOddTestClass(String solutionExecString, String studentExecString)
    {
    	boolean success = true; // whether or not the student's solution worked overall
        
    	boolean odd = false;
        boolean even = false;
        
        int num;
        Random random = new Random();
        
        // test 4 different random cases
        for (int i = 0; i < 4; i++)
        {
            // get a random number
            num = random.nextInt(10000) + 1;
            
            // need to keep track of cases to make sure that
            // at least one case of each odd and even is tested
            if (num % 2 == 0)
                even = true;
            else
                odd = true;
            
            // on last trial, figure out if we need to rig this test case
            if ( (i == 3) && (!odd || !even))
            {
                if (!odd) // if no odd case, we need to test with odd num
                    num = num + 1; // make num an odd number
                else // if no even case, we need to test with even num
                    num = num + 1; // make num an even number
            }

            // test files on this number
            if (!testCase(solutionExecString, studentExecString, num))
            	success = false;
        }

        // Print final success/failure outcome
        if (success)
            System.out.println("\nSuccess");
        else
            System.out.println("\nFailure");
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

            // run the file name with the number argument
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
     * Tests solution and student files on given number
     * 
     * Returns the boolean result of this case
     *
     */
    private boolean testCase(String solutionExecString, String studentExecString, int num)
    { 
        String args = "" + num;
        // Test the solution and student prolog files on the number
        String solutionOutput = runFile(solutionExecString, args);
        String studentOutput  = runFile(studentExecString, args);
        
        // Determine if answer is correct
        boolean isCorrect = true;
        if (!studentOutput.equals(solutionOutput))
            isCorrect = false;

        // Print out results
        System.out.println("Original Number: " + num);
        System.out.println("Correct Answer : " + solutionOutput);
        System.out.println("Your Answer    : " + studentOutput + "\n");
        
        if(isCorrect)
            System.out.println("Answer: Correct! \n");
        else
            System.out.println("Answer: Incorrect! \n");
        
        // Return results of this case
        return isCorrect;
    }
    
}
