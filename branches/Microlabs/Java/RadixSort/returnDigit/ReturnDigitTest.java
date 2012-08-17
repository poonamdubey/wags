/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: ReturnDigitTest.java
 *
 * Description: Test class for returnDigit method.
 */

public class ReturnDigitTest 
{

    public static void main(String[] args) 
    {
        new ReturnDigitTest();
    }

    public ReturnDigitTest() 
    {
        System.out.println(" *** Testing returnDigit Method ***");
        Integer[] testArray = new Integer[10];
        for (int i = 0; i < 10; i++) 
        {
            // ensures the input array will have values of different digit lenths
            testArray[i] = (Integer) (int) (Math.random() * Math.pow(10, Math.random() * 9));
        }
        RadixSortSolution sol = new RadixSortSolution();
        RadixSortStudent theirs = new RadixSortStudent();
        boolean error = false;
        for (int i = 0; i < 10; i++) 
        {
            // Make sure pos is valid position in the number.
            int pos = (int) (Math.random() * (testArray[i].toString().length()) + 1);
            int right = sol.returnDigit(testArray[i], pos);
            int maybe = theirs.returnDigit(testArray[i], pos);
            if (right != maybe)
                error = true;
            System.out.println("Expected: " + testArray[i] + " @ " + pos + " = " + right);
            System.out.println("Your Output: " + testArray[i] + " @ " + pos + " = " + maybe);
        }
        System.out.println((error)? " *** Error: Incorrect return values. ***" :
                " *** Activity Three Tests Passed! ***");
    }
}
