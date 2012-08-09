/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: Node.java
 *
 * Description: The node class used to represent Nodes in the tree.
 */

public class Node
{
    char key;
    Node left, right;
    public Node(char ch)
    {
        key = ch;
        left = null;
        right = null;
    }

    public char getKey() {return key;}

    public Node getLeft() {return left;}

    public Node getRight() {return right;}

    public void setLeft(Node l) {left = l;}

    public void setRight(Node r) {right = r;}
}
