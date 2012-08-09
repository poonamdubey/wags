/**
 * @author: Michael Kepple
 * @date: Mar 16, 2012
 * @file: GetRightSubtreeSolution.java
 *
 * Description: Solution class for getRightSubtree sub-exercise.
 */

public class GetRightSubtreeSolution
{
    String getRightSubtree(String postOrder)
    {
        int countLeft = GetRightSubtreeTest.countLeftSubtree(postOrder);
        String rightPostOrder = postOrder.substring(countLeft, postOrder.length() - 1);
        return rightPostOrder;
    }
}
