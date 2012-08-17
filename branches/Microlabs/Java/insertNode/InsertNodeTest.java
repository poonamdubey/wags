/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: InsertNodeTest.java
 *
 * Description: Test class to test students' insertNode methods.
 */

import java.util.Random;

public class InsertNodeTest
{
    private static int maxPos;
    
    public static void main(String[] args)
    {
        new InsertNodeTest(args[0]);
    }

    public InsertNodeTest(String nonce)
    {
        InsertNodeSolution sol = new InsertNodeSolution();
        InsertNodeStudent theirs = new InsertNodeStudent();
        boolean allRight = true;
        for (int i = 0; i < 2; i++)
        {
            Random random = new Random();
            Node tree = buildRandomTree();
            String preOrder = preOrderTraversal(tree);
            String inOrder = inOrderTraversal(tree);
            String postOrder = postOrderTraversal(tree);
            System.out.println("Test [" + i + "]: Pre-insertion preOrder Traversal -> " + preOrder);
            System.out.println("Test [" + i + "]: Pre-insertion inOrder Traversal -> " + inOrder);
            System.out.println("Test [" + i + "]: Pre-insertion postOrder Traversal -> " + postOrder);
            System.out.println("Tree Depiction: ");
            displayVTree(tree);
            char toInsert = (char)(int)(65 + random.nextInt(25));
            // Ensure that the char to insert isn't already in the tree
            while (preOrder.indexOf(toInsert) != -1)
            {
                toInsert = (char) (int) (65 + random.nextInt(25));
            }
            System.out.println("Random Character to Insert: " + toInsert);
            Node solution = sol.insertBST(tree, toInsert);
            Node student = theirs.insertBST(tree, toInsert);
            System.out.println("Your tree after insertBST method called with : " + toInsert);
            displayVTree(student);
            //System.out.println();
            if (!equalTrees(student, solution))
            {
                allRight = false;
                System.out.println("Error: Your tree does not match expected tree result: ");
                displayVTree(solution);
                System.out.println("Test [" + i + "]: Post-insertion preOrder Traversal -> " + preOrder);
                System.out.println("Test [" + i + "]: Post-insertion inOrder Traversal -> " + inOrder);
                System.out.println("Test [" + i + "]: Post-insertion postOrder Traversal -> " + postOrder + "\n");
            }
        }
        System.out.println("Test Status: " + (allRight? "Success!\n" + nonce : "Test failed, please try again"));
    }

    private boolean equalTrees(Node solution, Node theirs)
    {
        if (solution == null || theirs == null)
            return ((solution == null && theirs == null) ? true : false);
        return solution.getKey() == theirs.getKey() && equalTrees(solution.getLeft(),
                theirs.getLeft()) && equalTrees(solution.getRight(), theirs.getRight());
    }

    private static int maxDepth(Node n)
    {
        if (n == null) // if no elements in tree or leaf is reached, return 0.
            return 0;
        else
        {
            // recurse down leftwards until the left-most leaf is reached. It will return 0.
            int leftDepth = maxDepth(n.getLeft());
            // move back up the chain, recursing down to the right this time.
            int rightDepth = maxDepth(n.getRight());
            // for each node, return it's deepest depth (left or right).
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

    // BST, no duplicates
    Node buildRandomTree()
    {
        Node node = null;
        Random random = new Random();
        while (maxDepth(node) < 5 && preOrderTraversal(node).length() < 25)
        {
            char randomKey = (char)(int)(65 + random.nextInt(25));
            node = insertRandom(node, randomKey);
        }
        return node;
    }

    // BST, no duplicates
    Node insertRandom(Node node, char key)
    {
        if (node == null)
            return new Node(key);
        else if (key < node.getKey())
            node.setLeft(insertRandom(node.getLeft(), key));
        else if (key > node.getKey())
            node.setRight(insertRandom(node.getRight(), key));
        return node;
    }

    String preOrderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += tree.getKey();
            traversal += preOrderTraversal(tree.getLeft());
            traversal += preOrderTraversal(tree.getRight());
        }
        return traversal;
    }

    String inOrderTraversal(Node tree)
    {
    	String traversal = "";
        if(tree != null)
        {
            traversal += inOrderTraversal(tree.getLeft());
            traversal += tree.getKey();
            traversal += inOrderTraversal(tree.getRight());
        }
        return traversal;
    }
}
