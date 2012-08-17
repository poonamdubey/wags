/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: QueueToArrayTest.java
 *
 * Description: Test class for queueToArray method.
 */

public class QueueToArrayTest 
{
    public static void main(String[] args) 
    {
        new QueueToArrayTest();
    }

    public QueueToArrayTest() 
    {
        System.out.println(" *** Testing bucketsToQueue Method ***");
        Integer[] testArray = new Integer[10];
        Integer[] solArray = new Integer[10];
        Integer[] theirArray = new Integer[10];
        for (int i = 0; i < 10; i++) 
        {
            // ensures the input array will have values of different digit length
            testArray[i] = (Integer) (int) (Math.random() * Math.pow(10, Math.random() * 9));
        }
        @SuppressWarnings("unchecked")
        LinkedQueue<Integer>[] sBuckets = new LinkedQueue[10];
        @SuppressWarnings("unchecked")
        LinkedQueue<Integer>[] tBuckets = new LinkedQueue[10];
        RadixSortSolution sol = new RadixSortSolution();
        RadixSortStudent theirs = new RadixSortStudent();
        // Need two test LQ's so we can dequeue in queueToBucket method
        LinkedQueue<Integer> test = sol.initialize(testArray);
        LinkedQueue<Integer> test2 = sol.initialize(testArray);
        // Simulare class initialization of buckets array
        for (int i = 0; i < 10; i++) 
        {
            sBuckets[i] = new LinkedQueue<Integer>();
            tBuckets[i] = new LinkedQueue<Integer>();
        }
        sol.queueToBuckets(1, test, sBuckets);
        sol.queueToBuckets(1, test2, tBuckets);
        sol.bucketsToQueue(test, sBuckets);
        sol.bucketsToQueue(test2, tBuckets);
        solArray = sol.queueToArray(test, 10);
        theirArray = theirs.queueToArray(test2, 10);
        boolean error = false;
        for (int i = 0; i < 10; i++) 
        {
            try 
            {
                System.out.println("Yours: " + theirArray[i]);
                System.out.println("Expected: " + solArray[i] + "\n");
                if (!solArray[i].equals(theirArray[i]))
                    error = true;
            } 
            catch (Exception ex) 
            {
                System.out.println("Yours: NULL.");
                System.out.println("Expected: " + solArray[i] + "\n");
                error = true;
            }
        }
        System.out.println((error)? " *** Error: Incorrect return values. ***" :
                " *** Activity Six Tests Passed! ***");
    }
}
