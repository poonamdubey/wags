/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: TraversalSolution.java
 *
 * Description: The solution class for traversals exercise.
 */

public class TraversalSolution
{
    String preorderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += tree.getKey();
            traversal += preorderTraversal(tree.getLeft());
            traversal += preorderTraversal(tree.getRight());
        }
        return traversal;
    }

    String inorderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += inorderTraversal(tree.getLeft());
            traversal += tree.getKey();
            traversal += inorderTraversal(tree.getRight());
        }
        return traversal;
    }

    String postorderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += postorderTraversal(tree.getLeft());
            traversal += postorderTraversal(tree.getRight());
            traversal += tree.getKey();
        }
        return traversal;
    }
}
