/**
 * @author: Michael Kepple
 * @date: Mar 17, 2012
 * @file: GetRightGenSubtreeSolution.java
 *
 * Description: Solution class for getRightGenSubtree sub-exercise.
 */

public class GetRightGenSubtreeSolution
{
    protected String rightInorder;
    protected String rightPreorder;

    void getRightGenSubtreeSolution(String preOrder, String inOrder)
    {
        char rootValue = preOrder.charAt(0);
        rightInorder = inOrder.substring(inOrder.indexOf(rootValue)+1);
        rightPreorder = preOrder.substring(inOrder.length() - rightInorder.length());
    }
}
