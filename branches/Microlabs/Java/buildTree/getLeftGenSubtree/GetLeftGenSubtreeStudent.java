/**
 * @author: Michael Kepple
 * @date: Mar 16, 2012
 * @file: GetLeftGenSubtreeStudent.java
 *
 * Description: Student class for getLeftGenSubtree sub-exercise.
 */

public class GetLeftGenSubtreeStudent
{
    protected String leftInorder;
    protected String leftPreorder;

    //<end!TopSection>
    /* With general binary trees, we have to adapt our strategy a bit. With Binary
     * Search trees once you know what the root is, you know that any other node
     * whose key is 'less' than the root's will be in its left subtree. General
     * binary trees are not so easy, but thankfully we're given *two* ordered traversals
     * for this exercise; all that you'll need to get the job done.
     */
    void getLeftGenSubtreeStudent(String preOrder, String inOrder)
    {
        // Review inOrder traversals. Your first order of business is to set the
        // String leftInorder (declared elsewhere) to the root node's left subtree.
        // Hint: root node can be determined just as before, but you'll need to
        //   utilize the param inOrder String as well.
        char rootValue = '?';
        leftInorder = null;

        // Your final task is to assign the String leftPreorder the string representing
        // the root node's left subtree preorder traversal.

        leftPreorder = null;

        // In a general binary tree being able to know:
        // 1) What is in a given node's left subtree
        // 2) What that subtree's preorder traversal is.
        // Will later allow you to recurse leftwards through a general binary tree,
        // assigning nodes as you go!

        // Don't worry about returning anything, we'll take a look at what you
        // assigned to the String's and handle things from there. Too easy, right?
    }
    //<end!MidSection>
}
