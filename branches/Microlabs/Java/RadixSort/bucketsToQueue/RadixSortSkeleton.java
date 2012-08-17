// Michael Kepple
// November 26th, 2011
package bucketsToQueue;

public class RadixSortSkeleton
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

  //<end!TopSection>

  // Activity Five - bucketsToQueue
  // This method will dequeue the data from each bucket, subsequently enqueueing it back into the main queue.
  void bucketsToQueue(LinkedQueue<Integer> queue,
    LinkedQueue<Integer>[] buckets)
  {
    // Implementation Hidden!
  }

  //<end!MidSection>

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


