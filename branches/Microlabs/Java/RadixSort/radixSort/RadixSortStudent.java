/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: RadixSortStudent.java
 *
 * Description: Student class for all RadixSort programming exercises.
 */

public class RadixSortStudent
{
    static final int RADIX = 10;
    int queueSize = 8;
    int maxDigitPosition;
    @SuppressWarnings("unchecked")
    static LinkedQueue<Integer>[] buckets = new LinkedQueue[RADIX];
    LinkedQueue<Integer> values;

    // Activity One
    int findNumDigits(Integer[] data) 
    {
        // Implementation Hidden!
        return 0;
    }

    // Activity Two
    LinkedQueue<Integer> initialize(Integer[] data) 
    {
        // Implementation Hidden!
        return null;
    }

    // Activity Three
    int returnDigit(Integer value, int position) 
    {
        // Implementation Hidden!
        return 0;
    }

    // Activity Four
    void queueToBuckets(int digitPosition, LinkedQueue<Integer> queue,
            LinkedQueue<Integer>[] buckets) 
    {
        // Implementation Hidden!
    }

    // Activity Five
    void bucketsToQueue(LinkedQueue<Integer> queue,
            LinkedQueue<Integer>[] buckets) 
    {
        // Implementation Hidden!
    }

    // Activity Six
    Integer[] queueToArray(LinkedQueue<Integer> queue, int size) 
    {
        // Implementation Hidden!
        return null;
    }

    //<end!TopSection>
    // Activity Seven - radixSort
    //
    // This method ties it all together. The unsorted Integer[] data is sent into
    //   the method and a sorted Integer[] is returned after the radixSort. 
    // Suggested Steps:
    // 1) Find the maximum number of digits of any number in the data array
    //    - Note: use RadixSortTest.findNumDigits(...);
    // 2) Call the initialize method to transfer values to queue, initialize buckets.
    //    - Note: use RadixSortTest.initialize(...);
    // 3) For each digit position, queueToBuckets and bucketsToQueue.
    //    - Note: use RadixSortTest.queueToBuckets(...); and
    //    -           RadixSortTest.bucketsToQueue(...);
    // 4) When done, transfer the queue back to an array and return the (now sorted)
    //    Integer[]!
    //    - Note: use RadixSortTest.queueToArray(...);
    //
    Integer[] radixSort(Integer[] data) 
    {
        // Your code goes here.
        
        return null;
    }
    //<end!MidSection>
}
