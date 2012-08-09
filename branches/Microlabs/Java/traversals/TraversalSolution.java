/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: TraversalSolution.java
 *
 * Description: The solution class for traversals exercise.
 */

public class TraversalSolution
{
    String preOrderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += tree.getKey();
            traversal += preOrderTraversal(tree.getLeft());
            traversal += preOrderTraversal(tree.getRight());
        }
        return traversal;
    }

    String inOrderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += inOrderTraversal(tree.getLeft());
            traversal += tree.getKey();
            traversal += inOrderTraversal(tree.getRight());
        }
        return traversal;
    }

    String postOrderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += postOrderTraversal(tree.getLeft());
            traversal += postOrderTraversal(tree.getRight());
            traversal += tree.getKey();
        }
        return traversal;
    }
}
