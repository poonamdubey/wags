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
    LinkedQueue<Integer> values;
    QueueToBucketsTest queueToBucketsTest = new QueueToBucketsTest();

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

    //<end!TopSection>
    // Activity Four - queueToBuckets
    //
    // Given a LinkedQueue (queue) and a digit position (digitPosition), this method
    //  will distribute the values from queue into their appropriate buckets (buckets).
    //  bucket[3] should contain all numbers from the queue that have a 3 at the specified 
    //  digit position, etc. The buckets (declaration below) have already been initialized.
    //
    // Note: use queueToBucketsTest.returnDigit(Integer value, int position) 
    LinkedQueue<Integer>[] buckets = new LinkedQueue[RADIX];
    
    void queueToBuckets(int digitPosition, LinkedQueue<Integer> queue,
            LinkedQueue<Integer>[] buckets) 
    {
        Integer temp;
        Integer digitInTemp;

        // Your code goes here
    }
    //<end!MidSection>

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
