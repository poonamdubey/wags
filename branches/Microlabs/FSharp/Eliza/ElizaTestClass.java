import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

/**
 * This is the test class for the Eliza F# exercise
 * 
 * @author Mike Dusenberry
 * @date 07/31/12
 * @file ElizaTestClass.java
 *
 */
public class ElizaTestClass {

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

        new ElizaTestClass(nonce, solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Builds different string statements to test against the Eliza programs
     *
     *
     */
    public ElizaTestClass(String nonce, String solutionExecString, String studentExecString)
    {
    	boolean success = true; // whether or not the student's solution worked overall
    	
        // Get the 4 statements to be tested
    	String[] statements = createStatements();
        
        // shuffle the different statements to test
        //Collections.shuffle(Arrays.asList(statements));
        
        // test 4 different statements
        for (int i = 0; i < 4; i++)
        {
            // test files on this list for this operation
            //	-if case was false (student answer was incorrect), then
            //	 success of exercise is a failure
            if (!testCase(solutionExecString, studentExecString, statements[i]))
            	success = false;
        }

        // Print nonce value if successful
        if (success)
            System.out.println("\n" + nonce);
        else
            System.out.println("\nFailure");
    }
    
    /**
     * This creates a list of statements
     *
     */
    private String[] createStatements()
    {
        Random random = new Random();
        
        // Possible statements for each rule
        //  -Rule 1
        //      -match "I am phrase", and return
        //        "I am sorry to hear you are phrase"
        //
        //  -Rule 2
        //      -match "My word ... me ....", and return
        //        "Tell me about your word"
        //
        //  -Rule 3
        //      -match "Can I phrase with you ...", and return
        //        "Yes you can phrase with me"
        //
        //  -Rule 4
        //      -unrecognized input; should return "In what way?"
        //
        String[] rule1 = {  "I am tired",
                            "I am stressed",
                            "I am bored",
                            "I am done talking" };
        String[] rule2 = {  "My major makes me happy",
                            "My professor makes me do hard assignments",
                            "My room makes me sleepy",
                            "My dog makes me run"   };
        String[] rule3 = {  "Can I talk with you longer?",
                            "Can I speak with you tomorrow?",
                            "Can I meet with you next Monday?",
                            "Can I work with you on this project?" };
        String[] rule4 = {  "You won't recongnize this",
                            "Why can't you understand me?",
                            "This unknown phrase is crazy",
                            "It's like you don't even speak my language" };
        
        // Now, pick out one random statement from each rule,
        //  and combine into single array
        String[] statements = new String[4];
        
        statements[0] = rule1[random.nextInt(4)];
        statements[1] = rule2[random.nextInt(4)];
        statements[2] = rule3[random.nextInt(4)];
        statements[3] = rule4[random.nextInt(4)];
        
        return statements;
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
    private boolean testCase(String solutionExecString, String studentExecString, String statement)
    {
        // Test the list on the solution and student files
        //  -Note: Java has bugs in the Runtime.exec cmd where it can't pass in escaped quotes
        //    properly. So, we have to replace spaces with a character here, and the programs
        //    called have to replace those characters with spaces again.
        String solutionOutput = runFile(solutionExecString, statement.replaceAll(" ", "_"));
        String studentOutput  = runFile(studentExecString, statement.replaceAll(" ", "_"));
        
        // Determine if answer is correct
        boolean isCorrect = true;
        if (!studentOutput.equals(solutionOutput))
            isCorrect = false;
        
        // Print out results
        System.out.println("Input Statement: " + statement);
        System.out.println("Correct Result:  " + solutionOutput);
        System.out.println("Your Result:     " + studentOutput + "\n");
        
        if(isCorrect)
            System.out.println("Answer: Correct! \n");
        else
            System.out.println("Answer: Incorrect! \n");
        
        // Return results of this case
        return isCorrect;
    }
    
}
