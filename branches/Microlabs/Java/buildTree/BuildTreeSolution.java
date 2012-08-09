/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: BuildTreeSolution.java
 *
 * Description: The solution class for buildTree exercise.
 */

public class BuildTreeSolution
{
    Node buildTreeSolution(String preorder, String inorder)
    {
        if (preorder.length() == 0)
            return null;
        char rootValue = preorder.charAt(0);
        Node root = new Node(rootValue);
        if (inorder.length() == 1)
            return root;
        String leftInorder = inorder.substring(0, inorder.indexOf(rootValue));
        String leftPreorder = preorder.substring(1, leftInorder.length() + 1);
        root.setLeft(buildTreeSolution(leftPreorder, leftInorder));
        String rightInorder = inorder.substring(inorder.indexOf(rootValue) + 1);
        String rightPreorder = preorder.substring(leftInorder.length() + 1);
        root.setRight(buildTreeSolution(rightPreorder, rightInorder));
        return root;
    }
}
