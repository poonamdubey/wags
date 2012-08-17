/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: QueueToBucketsTest.java
 *
 * Description: Test class for queueToBuckets method.
 */

public class QueueToBucketsTest {

    public int RADIX = 10;

    public static void main(String[] args) {
        new QueueToBucketsTest();
    }

    // Method for student to use in their implementations
    public static int returnDigit(Integer value, int position) {
        for (int j = position; j > 1; j--) {
            value /= 10;
        }
        return value % 10;
    }

    public QueueToBucketsTest() 
    {
        System.out.println(" *** Testing queueToBuckets Method ***");
        Integer[] testArray = new Integer[10];
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
        // Need two test LQ's as well so we can dequeue in queueToBuckets
        LinkedQueue<Integer> test = sol.initialize(testArray);
        LinkedQueue<Integer> test2 = sol.initialize(testArray);
        // Simulate class initialization of buckets array
        for (int i = 0; i < 10; i++) 
        {
            sBuckets[i] = new LinkedQueue<Integer>();
            tBuckets[i] = new LinkedQueue<Integer>();
        }
        sol.queueToBuckets(1, test, sBuckets);
        theirs.queueToBuckets(1, test2, tBuckets);
        // Boolean to keep track of whether an error was enountered
        boolean error = false;
        for (int i = 0; i < 10; i++) 
        {
            while (!sBuckets[i].isEmpty()) 
            {
                // int/Integer fix, now they can use either.
                Integer fix = tBuckets[i].getFront();
                if (tBuckets[i].isEmpty()) 
                    error = true;
                else if (!sBuckets[i].getFront().equals(fix))
                    error = true;
                System.out.println("Expected: " + sBuckets[i].dequeue());
                System.out.println((tBuckets[i].isEmpty())? "Yours: NULL" : "Yours: "
                        + tBuckets[i].dequeue());
            }
        }
        System.out.println((error)? " *** Error: Incorrect return values. ***" :
                " *** Activity Four Tests Passed! *** ");
    }
}
