import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This is the test class for the DeleteAtom Prolog exercise
 * 
 * @author Mike Dusenberry
 * @date 03/28/12
 * @file DeleteAtomTestClass.java
 *
 */
public class DeleteAtomTestClass {

    /**
     * Entry point of program
     *
     * Gets solution and student execution strings passed in as arguments
     *
     */
    public static void main(String args[]) {

    	// These strings contain all necessary information for executing these files
    	//	-Will just have to append any arguments needed
        String solutionExecString = args[0];
        String studentExecString = args[1];

        new DeleteAtomTestClass(solutionExecString, studentExecString);
    }

    /**
     * Constructor
     *
     * Gets random lists and tests them
     *
     * In order to perform exhaustive testing, need to make sure at least one 
     *  case involves: removing zero atoms, and more than 1 atoms
     *
     * Lots of extra logic since we don't want the end-user to realize we are rigging the
     *  testing, so we make it appear as random as possible
     *
     */
    public DeleteAtomTestClass(String solutionExecString, String studentExecString)
    {
    	boolean success = true; // whether or not the student's solution worked overall
    	
        boolean zeroHits = false;
        boolean moreThanOneHits = false;
        
        int[] listArray;
    	String list;
        int num, randomOffset, length, i, j, hits, index1, index2;
        Random random = new Random();
        
        // test 4 different random cases
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
            //  at least one case of zero hits, and one case of >1 hits
            //  is tested
            // -one hit is fine, but student's code should be able to remove more 
            //   than one occurrence
            if (!zeroHits || !moreThanOneHits)  // only do this if necessary
            {
                hits = 0;
                for (j = 0; j < length; j++)
                {
                    if (num == listArray[j]) // if in array, then removal will happen
                        hits++;
                }
                // now see how many hits were found
                if (hits == 0)
                    zeroHits = true;
                else if (hits > 1) 
                    moreThanOneHits = true;
            }
            
            // rig the third and fourth cases if needed to make sure there has been at least
            //  one case of zero hits, and one case of >1 hits
            if ( (i == 2 || i == 3) && (!zeroHits || !moreThanOneHits) )
            {
                if (!zeroHits) // if no cases of zero removals
                {
                    // change num so that it can not be in list
                    //  by placing it oustide of the range of numbers
                    // highest num could be is 9 + randomOffset
                    num =  10 + randomOffset;
                    
                    zeroHits = true;
                }
                else // if no case of more than 1 removals
                {
                    // make sure list is at least 2 atoms long
                    while (length <= 1)
                    {
                        // get a random list
                        listArray = createRandomList(randomOffset);
                        length = listArray.length;
                    }
                    
                    // just set num to a random number in the list
                    //  to ensure that there will be at least 1 removal
                    index1 = random.nextInt(length);
                    num = listArray[index1];
                    
                    // and replace another number in the list with this value,
                    //  to ensure that num exists >1 times in list,
                    //  but don't replace the number we just randomly found
                    do {
                        index2 = random.nextInt(length);
                    } while (index2 == index1);
                    
                    listArray[index2] = num;
                    
                    moreThanOneHits = true;
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

            // test files on this list
            //	-if case was false (student answer was incorrect), then
            //	 success of exercise is a failure
            if (!testCase(solutionExecString, studentExecString, num, list))
            	success = false;
        }

        // Print final success/failure outcome
        if (success)
            System.out.println("\nSuccess");
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
            // make sure to add the offset
        	// just place spaces between numbers
            list[i] = random.nextInt(10) + randomOffset;
        }
        
        return list;
    }

    /**
     * Run the prolog file on the given arguments
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
    private boolean testCase(String solutionExecString, String studentExecString, int num, String list)
    { 
        // compile arguments
        String args = "" + num + " " + list;
        
        // Test the list on the solution and student prolog files
        String solutionOutput = runFile(solutionExecString, args);
        String studentOutput  = runFile(studentExecString, args);
        
        // Determine if answer is correct
        boolean isCorrect = true;
        if (!studentOutput.equals(solutionOutput))
            isCorrect = false;

        // Print out results
        //	-Looks cleaner to print original list with commas instead of spaces
        System.out.println("Atom To Remove:      " + num);
        System.out.println("Original List:       [" + list.replaceAll(" ", ",") + "]");
        System.out.println("Correct Result List: " + solutionOutput);
        System.out.println("Your Result List:    " + studentOutput + "\n");
        
        if(isCorrect)
            System.out.println("Answer: Correct! \n");
        else
            System.out.println("Answer: Incorrect! \n");
        
        // Return results of this case
        return isCorrect;
    }
    
}
