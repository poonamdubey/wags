/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: FindNumTest.java
 *
 * Description: Test class for findNumDigits method.
 */

public class FindNumTest 
{
    public static void main(String[] args) 
    {
        new FindNumTest();
    }

    public FindNumTest() 
    {
        System.out.println(" *** Testing findNumDigits Method ***");
        Integer[] testArray = new Integer[10];
        for (int i = 0; i < 10; i++) 
        {
            // ensures the input array will have values of many different digit lengths
            testArray[i] = (Integer) (int) (Math.random() * Math.pow(10, Math.random() * 9));
        }
        RadixSortSolution sol = new RadixSortSolution();
        int correct = sol.findNumDigits(testArray);
        RadixSortStudent theirs = new RadixSortStudent();
        int test = theirs.findNumDigits(testArray);
        if (correct == test) 
        {
            System.out.println(" *** Activity One Tests Passed! ***");
            System.out.println("Success");
        } 
        else 
        {
            System.out.println(" *** Error: Digit output incorrect ***");
            System.out.print("Given the Integer[]: ");
            for (Integer i : testArray) 
                System.out.print(i + " ");
            System.out.println();
            System.out.println("Correct output: " + correct);
        }
    }
}
