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
    LinkedQueue<Integer>[] buckets = new LinkedQueue[RADIX];
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

    //<end!TopSection>
    // Activity Six - queueToArray
    // In this method you will transfer what is in the LinkedQueue queue back 
    //   to an array of Integer for returning to the radixSort call method.
    // The second param size represents how many elements are stored in the queue
    //   (Hint: and correspondingly, how large your Integer[] should be).
    Integer[] queueToArray(LinkedQueue<Integer> queue, int size) 
    {
        // Your code goes here.
        
        return null;
    }
    //<end!MidSection>
    
    // Activity Seven
    Integer[] radixSort(Integer[] data) 
    {
        // Implementation Hidden!
        return null;
    }
}
