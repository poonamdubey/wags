/**
 * @author: Michael Kepple
 * @date: Mar 17, 2012
 * @file: PreorderTraversalSolution.java
 *
 * Description: Solution class for preOrderTraversal sub-exercise.
 */

public class PreorderTraversalSolution
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
}
