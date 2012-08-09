/**
 * @author: Michael Kepple
 * @date: Mar 14, 2012
 * @file: InsertNodeSolution.java
 *
 * Description: Solution to insertNode.
 */

public class InsertNodeSolution
{
    Node insertBST(Node node, char key)
    {
        if (node == null)
            return new Node(key);
        else if (key < node.getKey())
            node.setLeft(insertBST(node.getLeft(), key));
        else if (key > node.getKey())
            node.setRight(insertBST(node.getRight(), key));
        return node;
    }

    Node buildBSTbyInsertion(String nodes)
    {
        Node tree = null;
        for (int i = 0; i < nodes.length(); i++)
            tree = insertBST(tree, nodes.charAt(i));
        return tree;
    }
}
