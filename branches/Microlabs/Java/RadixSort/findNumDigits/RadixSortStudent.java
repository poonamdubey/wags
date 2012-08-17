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

    //<end!TopSection>
    // Activity One - findNumDigits
    // An array of Integer will be passed into this method. This method must 
    //  determine the max amount of digits required to represent any number in the
    //  array. The suggested steps are:
    //  1) Find the maximum (absolute) value
    //  2) Convert the maximum value to a String.
    //  3) Find the length of the String.
    int findNumDigits(Integer[] data) 
    {
        Integer max = 0;
        
        // Your code goes here

        return max;
    }
    //<end!MidSection>
    
    
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

    // Activity Seven
    Integer[] radixSort(Integer[] data) 
    {
        // Implementation Hidden!
        return null;
    }
}
