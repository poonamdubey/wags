import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

/**
 * This is the test class for the Map F# exercise
 * 
 * @author Mike Dusenberry
 * @date 07/28/12
 * @file MapTestClass.java
 *
 */
public class MapTestClass {

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

        new MapTestClass(nonce, solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Gets random lists and tests it against different functions
     *
     *
     */
    public MapTestClass(String nonce, String solutionExecString, String studentExecString)
    {
    	boolean success = true; // whether or not the student's solution worked overall
    	
    	String list = "";
        
        // shuffle the different functions to test
        String[] functions = {"Square", "Cube", "Increment", "Decrement"};
        Collections.shuffle(Arrays.asList(functions));
        
        // test 4 different functions on random lists
        for (int i = 0; i < 4; i++)
        {
            // get a random list
            list = createRandomList();
            
            // test files on this list for this function
            //	-if case was false (student answer was incorrect), then
            //	 success of exercise is a failure
            if (!testCase(solutionExecString, studentExecString, functions[i], list))
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
        
        // create random lists of length 1 to 10 
        // consisting of numbers 0 to 9
        int length = random.nextInt(10) + 1;
        for (int i = 0; i < length; i++)
        {
        	// just place spaces between numbers
            list += random.nextInt(10) + " ";
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
    						 String function, String list)
    {
    	// Combine the arguments
    	String args = function + " " + list;
    	
        // Test the list on the solution and student files
        String solutionOutput = runFile(solutionExecString, args);
        String studentOutput  = runFile(studentExecString, args);
        
        // Determine if answer is correct
        boolean isCorrect = true;
        if (!studentOutput.equals(solutionOutput))
            isCorrect = false;

        // Clean up output
        solutionOutput = solutionOutput.replaceAll("; ", ",");
        studentOutput = studentOutput.replaceAll("; ", ",");
        
        // Print out results
        System.out.println("Function:       " + function);
        System.out.println("Original List:  [" + list + "]");
        System.out.println("Correct Result: " + solutionOutput);
        System.out.println("Your Result:    " + studentOutput + "\n");
        
        if(isCorrect)
            System.out.println("Answer: Correct! \n");
        else
            System.out.println("Answer: Incorrect! \n");
        
        // Return results of this case
        return isCorrect;
    }
    
}
