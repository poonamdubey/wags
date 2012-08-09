/**
 * @author: Michael Kepple
 * @date: Mar 16, 2012
 * @file: GetLeftSubtreeSolution.java
 *
 * Description: Solution to getLeftSubtree sub-exercise
 */

public class GetLeftSubtreeSolution
{
    String getLeftSubtree(String postOrder)
    {
        int countLeft = GetLeftSubtreeTest.countLeftSubtree(postOrder);
        String leftPostorder = postOrder.substring(0, countLeft);
        return leftPostorder;
    }
}
