/**
 * @author: Michael Kepple
 * @date: Mar 17, 2012
 * @file: PreorderTraversalTest.java
 *
 * Description: Test class for preOrderTraversal sub-exercise.
 */
import java.util.Random;

public class PreorderTraversalTest
{
    Node tree;
    static int maxPos;
    PreorderTraversalSolution solution = new PreorderTraversalSolution();

    public static void main(String[] args)
    {
        new PreorderTraversalTest(args[0]);
    }

    public PreorderTraversalTest(String nonce)
    {
        boolean correct = true;
        for (int i = 0; i < 3; i++)
        {
            tree = buildRandomTree();
            PreorderTraversalStudent student = new PreorderTraversalStudent();
            String correctPre = solution.preOrderTraversal(tree);
            String studentPre = student.preOrderTraversal(tree);
            displayVTree(tree);
            System.out.println("Correct Preorder traversal:      " + correctPre);
            System.out.println("Your Preorder traversal: " + studentPre + "\n");
            if (!correctPre.equals(studentPre))
                correct = false;
        }
        System.out.println((correct? "Success!\n" + nonce : "Failed"));
    }

    // General tree, no duplicates, random root
    Node buildRandomTree()
    {
        Random random = new Random();
        char randomKey = (char) (int) (65 + random.nextInt(25));
        tree = new Node(randomKey);
        while (maxDepth(tree) < 5 && solution.preOrderTraversal(tree).length() < 25)
            tree = insertRandom(tree);
        return tree;
    }

    // General tree, no duplicates, random root.
    Node insertRandom(Node node)
    {
        String preOrder = solution.preOrderTraversal(tree);
        Random random = new Random();
        char randomKey = (char) (int) (65 + random.nextInt(25));
        while (preOrder.indexOf(randomKey) != -1)
            randomKey = (char) (int) (65 + random.nextInt(25));
        boolean randomBoolean = random.nextBoolean();
        if (node == null)
            return new Node(randomKey);
        else if (randomBoolean)
            node.setLeft(insertRandom(node.getLeft()));
        else if (!randomBoolean)
            node.setRight(insertRandom(node.getRight()));
        return node;
    }

    private static String postOrderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += postOrderTraversal(tree.getLeft());
            traversal += postOrderTraversal(tree.getRight());
            traversal += tree.getKey();
        }
        return traversal;
    }

    private static void constructBuffers(Node n, StringBuffer nodes, StringBuffer arrows, int currentLevel, int pos, int arrowPos)
    {
        if (pos > maxPos)
            maxPos = pos;
        arrowPos*=2;
        nodes.replace(pos, pos+1, Character.toString(n.getKey()));
        // Unicode char's 9585/9586 = Superslashes, WAGS compatible.
        if (n.getLeft() != null)
        {
            arrows.replace(arrowPos, arrowPos+1, Character.toString((char)9585));
            constructBuffers(n.getLeft(), nodes, arrows, currentLevel-1, pos - (int)Math.pow(2, currentLevel-1), arrowPos);
        }
        if (n.getRight() != null)
        {
            arrowPos++;
            arrows.replace(arrowPos, arrowPos+1, Character.toString((char)9586));
            constructBuffers(n.getRight(), nodes, arrows, currentLevel-1, pos + (int)Math.pow(2, currentLevel-1), arrowPos);
        }
        return;
    }

    public static void displayVTree(Node n)
    {
        int maxDepth = maxDepth(n);
        if (n == null)
        {
            System.out.println("Error: result is null tree (nothing to display).");
            return;
        }
        int maxLength = (int)Math.pow(2, maxDepth+1);
        // Initialize StringBuffers to all empty spaces.
        String temp = getSpaces(maxLength+20);
        StringBuffer nodes = new StringBuffer(temp);
        StringBuffer arrows = new StringBuffer(temp);
        maxPos = 0;
        constructBuffers(n, nodes, arrows, maxDepth, maxLength/2, 1);
        // Trim unneeded left whitespace
        int whiteSpace = 0;
        while (nodes.charAt(whiteSpace) == ' ') whiteSpace++;
        // WAGS max width = 99. Changed from depth check.
        maxPos = maxPos - whiteSpace + 2;
        if (maxPos > 99)
        {
            System.out.println("Error: tree width exceeds maximum displayable with WAGS");
            System.out.println("Postorder traversal: " + postOrderTraversal(n));
            System.out.println("Depth: " + maxDepth);
            return;
        }
        int currentLevel = maxDepth;
        for (int i = 1; i <= maxDepth; i++)
        {
            // Print Nodes
            int leftOffset = (int)Math.pow(2, currentLevel);
            int betweenOffset = (int)Math.pow(2, currentLevel+1);
            int placement = (whiteSpace < leftOffset? (leftOffset-whiteSpace) : (whiteSpace-leftOffset)%betweenOffset);
            for (int j = whiteSpace; j < maxLength; j+= betweenOffset)
            {
                System.out.print(getSpaces((j == whiteSpace? placement : (betweenOffset-1))) + nodes.charAt(j + placement));
            }
            System.out.println();
            // Print Arrows;
            int maxLevel = (int)Math.pow(2, i);
            int startIndex = (int)Math.pow(2, i)-1;
            boolean firstP = true;
            int base = leftOffset - (leftOffset/4);
            int jump = (int)Math.pow(2, ((maxDepth+2)-i));
            int stride = (int)Math.pow(2, currentLevel-1);
            for (int j = startIndex+1; j < maxLevel+startIndex; j++)
            {
                int first = base;
                int second = first + stride;
                if (first > whiteSpace)
                {
                    System.out.print(getSpaces((firstP? (first-whiteSpace) : (leftOffset+(leftOffset/2)-1))) + arrows.charAt(j));
                    firstP = false;
                }
                j++;
                if (second > whiteSpace)
                {
                    System.out.print(getSpaces((firstP? (second-whiteSpace) : (second-first-1))) + arrows.charAt(j));
                    firstP = false;
                }
                base += jump;
            }
            System.out.println();
            currentLevel--;
        }
    }

    private static String getSpaces(int n)
    {
        String temp = "";
        for (int i = 0; i < n; i++)
        {
            temp += " ";
        }
        return temp;
    }

    private static int maxDepth(Node n)
    {
        if (n == null)
            return 0;
        else
        {
            int leftDepth = maxDepth(n.getLeft());
            int rightDepth = maxDepth(n.getRight());
            return (Math.max(leftDepth, rightDepth) + 1);
        }
    }
}