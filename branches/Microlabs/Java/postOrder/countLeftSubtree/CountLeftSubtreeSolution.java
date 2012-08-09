/**
 * @author: Michael Kepple
 * @date: Mar 16, 2012
 * @file: CountLeftSubtreeSolution.java
 *
 * Description: solution class for countLeftSubtree sub-exercise.
 */

public class CountLeftSubtreeSolution
{
    int countLeftSubtree(String postOrder)
    {
        char rootValue = CountLeftSubtreeTest.findRoot(postOrder).getKey();
        int countLeft = 0;
        for (int j = 0; j < postOrder.length(); j++)
            if (postOrder.charAt(j) < rootValue)
                countLeft++;
        return countLeft;
    }
}
