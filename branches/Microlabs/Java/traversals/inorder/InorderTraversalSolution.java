/**
 * @author: Michael Kepple
 * @date: Mar 17, 2012
 * @file: InorderTraversalSolution.java
 *
 * Description: Solution class for inorderTraversal sub-exercise.
 */

public class InorderTraversalSolution
{
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
}
