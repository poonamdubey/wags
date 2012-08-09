/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: PostOrderSolution.java
 *
 * Description: The solution class for postOrder exercise.
 */

public class PostOrderSolution
{
    Node postOrderBuild(String postorder)
    {
        if (postorder.length() == 0)
            return null;
        char rootValue = postorder.charAt(postorder.length() - 1);
        Node root = new Node(rootValue);
        if (postorder.length() == 1)
            return root;
        int countLeft = 0;
        for (int j = 0; j < postorder.length(); j++)
            if (postorder.charAt(j) < rootValue)
                countLeft++;
        String leftPostorder = postorder.substring(0, countLeft);
        String rightPostorder = postorder.substring(countLeft, postorder.length() - 1);
        root.setLeft(postOrderBuild(leftPostorder));
        root.setRight(postOrderBuild(rightPostorder));
        return root;
    }
}
