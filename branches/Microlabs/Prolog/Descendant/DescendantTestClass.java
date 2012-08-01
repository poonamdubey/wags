import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This is the test class for the Descendant Prolog exercise
 * 
 * @author Mike Dusenberry
 * @date 04/01/12
 * @file DescendantTestClass.java
 *
 */
public class DescendantTestClass {

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

        new DescendantTestClass(nonce, solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Test files on 4 known cases with a bit of randomness
     *
     */
    public DescendantTestClass(String nonce, String solutionExecString, String studentExecString)
    {
    	boolean success = true; // whether or not the student's solution worked overall
    	
        boolean empty = false;
        boolean nonEmpty = false;
        
        Random random = new Random();
        
        String[] parentsOfRosa = {"Douglas", "Rita"}; // used in case 1
        String[] notAncestorsOfDouglas = {"Rita", "Gregory", "Jim", "Brittany"};
        String[] ancestorsOfMaria = {"Douglas", "Rita"};
        String[] descendantsOfGregory = {"Cameron", "Linda"};
        
    	String person1 = "";
        String person2 = "";
        
        // test 4 different cases
        for (int i = 0; i < 4; i++)
        {
            switch (i)
            {
                // direct parent
                case(0): 
                    person1 = "Rosa";
                    person2 = parentsOfRosa[random.nextInt(2)];
                    break;
                
                // not
                case(1): 
                    person1 = "Douglas";
                    person2 = notAncestorsOfDouglas[random.nextInt(4)];
                    break;
                
                // ancestor
                case(2): 
                    person1 = "Maria";
                    person2 = ancestorsOfMaria[random.nextInt(2)];
                    break;
                    
                // ancestor
                case(3): 
                    person1 = descendantsOfGregory[random.nextInt(2)];
                    person2 = "Gregory";
                    break;
            }

            // test files on this list
            //	-if case was false (student answer was incorrect), then
            //	 success of exercise is a failure
            if (!testCase(solutionExecString, studentExecString, person1, person2))
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
    private boolean testCase(String solutionExecString, String studentExecString, String person1, 
                             String person2)
    { 
        String args = "" + person1.toLowerCase() + " " + person2.toLowerCase();
        
        // Test the list on the solution and student prolog files
        String solutionOutput = runFile(solutionExecString, args);
        String studentOutput  = runFile(studentExecString, args);
        
        // Determine if answer is correct
        boolean isCorrect = true;
        if (!studentOutput.equals(solutionOutput))
            isCorrect = false;

        // Print out results
        //	-Looks cleaner to print original list with commas instead of spaces
        System.out.println("Is " + person1 + " a descendant of " + person2 + "?");
        System.out.println("Correct Answer: " + solutionOutput);
        System.out.println("Your Answer:    " + studentOutput + "\n");
        
        if(isCorrect)
            System.out.println("Answer: Correct! \n");
        else
            System.out.println("Answer: Incorrect! \n");
        
        // Return results of this case
        return isCorrect;
    }
    
}
