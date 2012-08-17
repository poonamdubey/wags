/**
 * @author: Michael Kepple
 * @date: Mar 16, 2012
 * @file: GetLeftSubtreeTest.java
 *
 * Description: Test class for getLeftSubtree sub-exercise.
 */
import java.util.Random;

public class GetLeftSubtreeTest
{
    private static int maxPos;

    public static void main(String[] args)
    {
        new GetLeftSubtreeTest(args[0]);
    }

    public GetLeftSubtreeTest(String nonce)
    {
        GetLeftSubtreeSolution sol = new GetLeftSubtreeSolution();
        GetLeftSubtreeStudent theirs = new GetLeftSubtreeStudent();
        boolean allRight = true;
        for (int i = 0; i < 4; i++)
        {
            Node tree = buildRandomTree();
            String testString = postOrderTraversal(tree);
            System.out.println("Test [" + i + "]: PostOrder Traversal -> " + testString);
            System.out.println("Tree Depiction: ");
            displayVTree(tree);
            String correct = sol.getLeftSubtree(testString);
            String student = theirs.getLeftSubtree(testString);
            System.out.println("Correct Left Subtree String: " + correct);
            System.out.println("Your Left Subtree String: " + student);
            if (!correct.equals(student))
                allRight = false;
        }
        System.out.println("Test Status: " + (allRight? "Success!\n" + nonce : "Test failed, please try again"));
    }

    static int countLeftSubtree(String postOrder)
    {
        char rootValue = findRoot(postOrder).getKey();
        int countLeft = 0;
        for (int j = 0; j < postOrder.length(); j++)
            if (postOrder.charAt(j) < rootValue)
                countLeft++;
        return countLeft;
    }

    static Node findRoot(String postOrder)
    {
        if (postOrder.length() == 0)
            return null;
        char rootValue = postOrder.charAt(postOrder.length() - 1);
        Node root = new Node(rootValue);
        return root;
    }

    Node buildRandomTree()
    {
        Node node = null;
        Random random = new Random();
        while (maxDepth(node) < 5 && postOrderTraversal(node).length() < 25)
        {
            char randomKey = (char)(int)(65 + random.nextInt(25));
            node = insertRandom(node, randomKey);
        }
        return node;
    }

    Node insertRandom(Node node, char key)
    {
        if (maxDepth(node) > 5)
            return node;
        if (node == null)
            return new Node(key);
        else if (key < node.getKey())
            node.setLeft(insertRandom(node.getLeft(), key));
        else if (key > node.getKey())
            node.setRight(insertRandom(node.getRight(), key));
        return node;
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
        for (int i = 0; i < n; i++) temp += " ";
        return temp;
    }
}
