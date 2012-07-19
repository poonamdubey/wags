/**
 * @author: Philip Meznar
 * @date: Aug 22, 2011
 * @file: TraversalSolution.java
 * 
 * Description: The solution class to be used
 * with WAGS for the traversal exercises
 */
package traversals;

import traversals.TraversalSkeleton.Node;
public class TraversalSolution {
	
    String preorderTraversal(Node tree){
    	String traversal = "";
    	
        if(tree != null){
            traversal += tree.key;
            traversal += preorderTraversal(tree.left);
            traversal += preorderTraversal(tree.right);
        }
        
        return traversal;
    }

    String inorderTraversal(Node tree){
    	String traversal = "";
    	
        if(tree != null){
            traversal += inorderTraversal(tree.left);
            traversal += tree.key;
            traversal += inorderTraversal(tree.right);
        }
        
        return traversal;
    }

    String postorderTraversal(Node tree){
    	String traversal = "";
    	
        if(tree != null){
            traversal += postorderTraversal(tree.left);
            traversal += postorderTraversal(tree.right);
            traversal += tree.key;
        }
        
        return traversal;
    }

}
