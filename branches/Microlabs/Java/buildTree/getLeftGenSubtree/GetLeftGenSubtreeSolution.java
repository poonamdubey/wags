/**
 * @author: Michael Kepple
 * @date: Mar 17, 2012
 * @file: GetLeftGenSubtreeSolution.java
 *
 * Description: Solution class for getLeftGenSubtree sub-exercise.
 */

public class GetLeftGenSubtreeSolution
{
    protected String leftInorder;
    protected String leftPreorder;

    void getLeftGenSubtreeSolution(String preOrder, String inOrder)
    {
        char rootValue = preOrder.charAt(0);
        leftInorder = inOrder.substring(0, inOrder.indexOf(rootValue));
        leftPreorder = preOrder.substring(1, leftInorder.length()+1);
    }
}
