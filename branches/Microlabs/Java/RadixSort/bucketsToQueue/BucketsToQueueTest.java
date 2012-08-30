/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: BucketsToQueueTest.java
 *
 * Description: Test class for bucketsToQueue method.
 */

public class BucketsToQueueTest {

    public static void main(String[] args) 
    {
        new BucketsToQueueTest();
    }

    public BucketsToQueueTest() 
    {
        System.out.println(" *** Testing bucketsToQueue Method ***");
        Integer[] testArray = new Integer[10];
        for (int i = 0; i < 10; i++) {
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
        // ensure that everything up until here is correctly done.
        sol.queueToBuckets(1, test, sBuckets);
        sol.queueToBuckets(1, test2, tBuckets);
        sol.bucketsToQueue(test, sBuckets);
        theirs.bucketsToQueue(test2, tBuckets);
        boolean error = false;
        while (!test.isEmpty()) 
        {
            if (test2.isEmpty() || !test.getFront().equals(test2.getFront()))
                error = true;
            System.out.println("Expected: " + test.dequeue());
            System.out.println((test2.isEmpty())? "Yours: NULL." : "Yours: " + test2.dequeue());
            
        }
        System.out.println((error)? " *** Error: Incorrect return values. *** " :
                " *** Activity Five Tests Passed! ***");
    }
}