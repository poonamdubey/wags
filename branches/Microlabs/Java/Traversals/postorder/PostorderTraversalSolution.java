/**
 * @author: Michael Kepple
 * @date: Mar 17, 2012
 * @file: PostorderTraversalSolution.java
 *
 * Description: Solution class for postOrderTraversal sub-exercise.
 */

public class PostorderTraversalSolution
{
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
