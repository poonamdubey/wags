/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: InitializeTest.java
 *
 * Description: Test class for initialize method.
 */

public class InitializeTest 
{
    public static void main(String[] args) 
    {
        new InitializeTest(args[0]);
    }

    public InitializeTest(String nonce) 
    {
        System.out.println(" *** Testing initialize Method ***");
        Integer[] testArray = new Integer[10];
        for (int i = 0; i < 10; i++) 
        {
            // ensures the input array will have values of different digit lengths
            testArray[i] = (Integer) (int) (Math.random() * Math.pow(10, Math.random() * 9));
        }
        RadixSortSolution sol = new RadixSortSolution();
        LinkedQueue<Integer> correct = sol.initialize(testArray);
        RadixSortStudent theirs = new RadixSortStudent();
        LinkedQueue<Integer> test = theirs.initialize(testArray);
        boolean passed = true;
        if (test == null) 
        {
            System.out.println(" *** Error: Returned LinkedQueue is null! ***");
            passed = false;
        }
        for (int i = 0; i < 10 && passed; i++) 
        {
            Integer right = correct.dequeue();
            Integer maybe = test.dequeue();
            System.out.println("Correct Dequeue: " + right + "\nYour Dequeue: " + maybe);
            // Test LinkedQueue return 
            if (right != maybe) 
            {
                System.out.println(" *** Error: LinkedQueue not initialized properly! ***");
                passed = false;
            } 
            // Also make sure these got initialized
            else if (theirs.buckets[i] == null) 
            {
                System.out.println(" *** Error: Buckets not initialized properly! ***");
                passed = false;
            }
        }
        System.out.println((passed)? " *** Activity Two Tests Passed! ***\n" + nonce : 
                " *** Activity Two : Failed. ***");
    }
}
