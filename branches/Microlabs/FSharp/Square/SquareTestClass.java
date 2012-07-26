import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This is the test class for the Square F# exercise
 * 
 * @author Mike Dusenberry
 * @date 07/11/12
 * @file SquareTestClass.java
 *
 */
public class SquareTestClass { 

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

        new SquareTestClass(nonce, solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Gets random number and tests it
     *
     *
     */
    public SquareTestClass(String nonce, String solutionExecString, String studentExecString)
    {
    	boolean success = true; // whether or not the student's solution worked overall
        
        int num;
        Random random = new Random();
        
        // test 4 different random cases
        for (int i = 0; i < 4; i++)
        {
            // get a random number
            num = random.nextInt(100) + 1;
            
            // test files on this number
            if (!testCase(solutionExecString, studentExecString, num))
            	success = false;
        }

        // Print nonce value if successful
        if (success)
            System.out.println("\n" + nonce);
        else
            System.out.println("\nFailure");
    }

    /**
     * Run the file on the given arguments and get results
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
        // Test the solution and student files on the number
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
