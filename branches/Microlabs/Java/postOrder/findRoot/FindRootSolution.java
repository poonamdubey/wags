/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: FindRootSolution.java
 *
 * Description: The solution class for findRoot sub-exercise.
 */

public class FindRootSolution
{
    Node findRoot(String postOrder)
    {
        if (postOrder.length() == 0)
            return null;
        char rootValue = postOrder.charAt(postOrder.length() - 1);
        Node root = new Node(rootValue);
        return root;
    }
}
