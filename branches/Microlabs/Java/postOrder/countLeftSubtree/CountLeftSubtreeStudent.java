/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: CountLeftSubtreeStudent.java
 *
 * Description: Student class for countLeftSubtree sub-exercise.
 */

public class CountLeftSubtreeStudent
{
    //<end!TopSection>
    /* Your objective in this method is to determine the amount of Nodes in
     *   the left subtree of the root node. Provided for you is a method call
     *   exactly similar to what you created in the last exercise: it returns
     *   the root node from a passed in postOrder String. 
     */
    int countLeftSubtree(String postOrder)
    {
        // Do not edit
        char rootValue = CountLeftSubtreeTest.findRoot(postOrder).getKey();
        int countLeft = 0;

        // For every Node key in the postOrder String traversal, determine if
        //   it is less than the root Node's key. Count them.

        return countLeft;
    }
    //<end!MidSection>
}
