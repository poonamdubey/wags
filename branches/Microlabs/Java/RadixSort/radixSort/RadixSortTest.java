/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: RadixSortTest.java
 *
 * Description: Test class for radixSort method.
 */

public class RadixSortTest
{
    public static void main(String[] args)
    {
        new RadixSortTest();
    }

    public static int findNumDigits(Integer[] data)
    {
        Integer max = data[0];
        for (int index = 1; index < data.length; index++)
            if (data[index] > max)
                max = data[index];
        return max.toString().length();
    }

    public static void queueToBuckets(int digitPosition,
      LinkedQueue<Integer> values, LinkedQueue<Integer>[] buckets)
    {
        while (!values.isEmpty())
        {
            Integer value = values.dequeue();
            buckets[returnDigit(value,digitPosition)].enqueue(value);
        }
    }

    public static int returnDigit(Integer value, int position)
    {
        for (int j = position; j>1; j--)
            value /= 10;
         return value % 10;
    }

    public static void bucketsToQueue(LinkedQueue<Integer> queue,
       LinkedQueue<Integer>[] buckets)
    {
        for (int j = 0; j < 10; j++)
            while (!buckets[j].isEmpty())
                queue.enqueue((Integer) buckets[j].dequeue());
    }

    public static Integer[] queueToArray(LinkedQueue<Integer> queue, int size)
    {
        Integer[] data = new Integer[size];
        int index = 0;
        while (!queue.isEmpty())
            data[index++] = queue.dequeue();
        return data;
    }

    public static LinkedQueue<Integer> initialize (Integer[] data)
    {
        for (int j = 0; j < RadixSortStudent.RADIX; j++)
            RadixSortStudent.buckets[j] = new LinkedQueue<Integer>();
        LinkedQueue<Integer> temp = new LinkedQueue<Integer>();
        for (Integer val: data)
            temp.enqueue(val);
        return temp;
    }
    
    public RadixSortTest()
    {
        System.out.println(" *** Testing radixSort Method ***");
        Integer[] testArray = new Integer[10];
        for (int i = 0; i < 10; i++)
        {
          // ensures the input array will have values of different digit length
          testArray[i] = (Integer)(int)(Math.random() * Math.pow(10, Math.random() * 9));
        }
        Integer[] solArray = new Integer[10];
        Integer[] theirArray = new Integer[10];
        RadixSortSolution sol = new RadixSortSolution();
        RadixSortStudent theirs = new RadixSortStudent();
        solArray = sol.radixSort(testArray);
        theirArray = theirs.radixSort(testArray);
        boolean error = false;
        for (int i = 0; i < 10; i++)
        {
            try
            {
                System.out.println("Yours: " + theirArray[i]);
                System.out.println("Expected: " + solArray[i]);
                if (!solArray[i].equals(theirArray[i]))
                    error = true;
            }
            catch (NullPointerException ex)
            {
                System.out.println("Yours: null");
                System.out.println("Expected: " + solArray[i]);
                error = true;
            }
        }
        System.out.println((error)? " *** Error: Incorrect return values. *** " :
                " *** Activity Seven Tests Passed! ***");
    }
}
