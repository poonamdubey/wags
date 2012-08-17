/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: RadixSortStudent.java
 *
 * Description: Student class for all RadixSort programming exercises.
 */

public class RadixSortStudent 
{
    int queueSize = 8;
    int maxDigitPosition;
    @SuppressWarnings("unchecked")
    LinkedQueue<Integer> values;

    int findNumDigits(Integer[] data) 
    {
        // Implementation Hidden!
        return 0;
    }

    //<end!TopSection>
    // Activity Two - initialize
    // This method will perform two activities:
    //  1) The array values are transfered from input [] to a LinkedQueue
    //  2) The buckets are initialized to empty linked queues. (Hint: the amount of buckets you'll
    //       need to initialize is dependent upon the RADIX you're operating in.).+
    static final int RADIX = 10;
    LinkedQueue<Integer>[] buckets = new LinkedQueue[RADIX];
    
    LinkedQueue<Integer> initialize(Integer[] data) 
    {
        // Your code goes here.
        
        return null;
    }
    //<end!MidSection>
    
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

    // Activity Seven
    Integer[] radixSort(Integer[] data) 
    {
        // Implementation Hidden!
        return null;
    }
}
