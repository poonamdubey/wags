/**
 * @author: Michael Kepple 
 * @date: Apr 09, 2012 
 * @file: LinkedQueue.java
 *
 * Description: LinkedQueue Data Structure for use with Radix Sort Programming Micro-labs.
 */

public class LinkedQueue<T> implements java.io.Serializable 
{
    private Node firstNode;
    private Node lastNode;

    public LinkedQueue() 
    {
        firstNode = null;
        lastNode = null;
    }

    public void enqueue(T newEntry) 
    {
        LinkedQueue.Node newNode = new LinkedQueue.Node(newEntry, null);
        if (isEmpty())
            firstNode = newNode;
        else
            lastNode.setNextNode(newNode);
        lastNode = newNode;
    }

    public T getFront() 
    {
        T front = null;
        if (!isEmpty())
            front = firstNode.getData();
        return front;
    }

    public T dequeue() 
    {
        T front = null;
        if (!isEmpty()) 
        {
            front = firstNode.getData();
            firstNode = firstNode.getNextNode();
            if (firstNode == null)
                lastNode = null;
        }
        return front;
    }

    public boolean isEmpty() 
    {
        return ((firstNode == null) && (lastNode == null));
    }

    public void clear() 
    {
        firstNode = null;
        lastNode = null;
    }

    public class Node implements java.io.Serializable 
    {
        private T data;
        private LinkedQueue.Node next;

        public Node(T data, LinkedQueue.Node next) 
        {
            this.data = data;
            this.next = next;
        }

        public T getData() { return data; }

        public void setData(T data) { this.data = data; }

        public LinkedQueue.Node getNextNode() { return next; }

        public void setNextNode(LinkedQueue.Node next) { this.next = next; }
    }
}
