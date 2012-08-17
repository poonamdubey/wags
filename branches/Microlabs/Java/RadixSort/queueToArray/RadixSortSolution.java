/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: RadixSortSolution.java
 *
 * Description: The solution class for all RadixSort programming exercises.
 */

public class RadixSortSolution
{
    static final int RADIX = 10;
    int queueSize = 8;
    int maxDigitPosition;
    @SuppressWarnings("unchecked")
    LinkedQueue<Integer>[] buckets = new LinkedQueue[RADIX];
    LinkedQueue<Integer> values;

    // Activity One Solution
    int findNumDigits(Integer[] data) 
    {
        Integer max = data[0];
        for (int index = 1; index < data.length; index++) 
        {
            if (data[index] > max)
                max = data[index];
        }
        return max.toString().length();
    }

    // Activity Two Solution
    LinkedQueue<Integer> initialize(Integer[] data) 
    {
        LinkedQueue<Integer> temp = new LinkedQueue<Integer>();
        for (Integer val : data)
            temp.enqueue(val);
        for (int j = 0; j < RADIX; j++)
            buckets[j] = new LinkedQueue<Integer>();
        return temp;
    }

    // Activity Three Solution  
    int returnDigit(Integer value, int position) 
    {
        for (int j = position; j > 1; j--)
            value /= RADIX;
        return value % RADIX;
    }

    // Activity Four Solution
    void queueToBuckets(int digitPosition, LinkedQueue<Integer> queue,
            LinkedQueue<Integer>[] buckets) 
    {
        while (!queue.isEmpty()) 
        {
            Integer value = queue.dequeue();
            buckets[returnDigit(value, digitPosition)].enqueue(value);
        }
    }

    // Activity Five Solution
    void bucketsToQueue(LinkedQueue<Integer> queue,
            LinkedQueue<Integer>[] buckets) 
    {
        for (int j = 0; j < RADIX; j++) 
            while (!buckets[j].isEmpty())
                queue.enqueue(buckets[j].dequeue());
    }

    // Activity Six Solution   
    Integer[] queueToArray(LinkedQueue<Integer> queue, int size) 
    {
        Integer[] data = new Integer[size];
        int index = 0;
        while (!queue.isEmpty())
            data[index++] = queue.dequeue();
        return data;
    }

    // Activity Seven Solution  
    Integer[] radixSort(Integer[] data) 
    {
        int size = data.length;
        maxDigitPosition = findNumDigits(data);
        values = initialize(data);
        for (int k = 1; k <= maxDigitPosition; k++) 
        {
            queueToBuckets(k, values, buckets);
            bucketsToQueue(values, buckets);
        }
        data = queueToArray(values, size);
        return data;
    }
}
