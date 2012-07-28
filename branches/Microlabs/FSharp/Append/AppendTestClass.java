import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This is the test class for the Append F# exercise
 * 
 * @author Mike Dusenberry
 * @date 07/19/12
 * @file AppendTestClass.java
 *
 */
public class AppendTestClass {

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

        new AppendTestClass(nonce, solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Gets random lists and tests them
     *
     */
    public AppendTestClass(String nonce, String solutionExecString, String studentExecString)
    {
    	boolean success = true; // whether or not the student's solution worked overall
    	
    	String list1 = "";
        String list2 = "";
        
        // test 4 different random cases
        for (int i = 0; i < 4; i++)
        {
            // get two random lists
            list1 = createRandomList();
            list2 = createRandomList();
            
            // test files on this list
            //	-if case was false (student answer was incorrect), then
            //	 success of exercise is a failure
            if (!testCase(solutionExecString, studentExecString, list1, list2))
            	success = false;
        }

        // Print nonce value if successful
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
        
        // create random lists of length 1 to 5 
        // consisting of numbers 0 to 99
        int length = random.nextInt(5) + 1;
        for (int i = 0; i < length; i++)
        {
        	// just place spaces between numbers
            list += random.nextInt(100) + " ";
        }
        
        return list.trim().replaceAll(" ", ",");
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
    private boolean testCase(String solutionExecString, String studentExecString, 
                             String list1, String list2)
    { 
        String lists = list1 + " " + list2;
        
        // Test the list on the solution and student files
        String solutionOutput = runFile(solutionExecString, lists);
        String studentOutput  = runFile(studentExecString, lists);
        
        // Determine if answer is correct
        boolean isCorrect = true;
        if (!studentOutput.equals(solutionOutput))
            isCorrect = false;
        
        // Clean up output
        solutionOutput = solutionOutput.replaceAll("\"", "");
        studentOutput = studentOutput.replaceAll("\"", "");
        solutionOutput = solutionOutput.replaceAll("; ", ",");
        studentOutput = studentOutput.replaceAll("; ", ",");

        // Print out results
        System.out.println("Original Lists:  [" + list1 + "] & " + "[" + list2 + "]");
        System.out.println("Correct Appended List: " + solutionOutput);
        System.out.println("Your Appended List:    " + studentOutput + "\n");
        
        if(isCorrect)
            System.out.println("Answer: Correct! \n");
        else
            System.out.println("Answer: Incorrect! \n");
        
        // Return results of this case
        return isCorrect;
    }
    
}
