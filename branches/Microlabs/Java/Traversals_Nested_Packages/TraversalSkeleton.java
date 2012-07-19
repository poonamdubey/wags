/**
 * @author: Philip Meznar
 * @date: Aug 22, 2011
 * @file: TraversalSkeleton.java
 * 
 * Description: The skeleton class
 * to be used with WAGS for the 
 * traversal exercises
 */
package traversals.test;

public class TraversalSkeleton {
	
//<end!TopSection>	
	/**
	 * Each of these methods takes the root
	 * node of a tree, and should return
	 * a String showing the respective traversal
	 * @param tree
	 */
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
//<end!MidSection>


    public class Node {
        char key;
        Node left, right;
        public Node(char ch){
            key = ch; 
            left = null;
            right = null;
        }   
    
        public char getKey() { return key; }
        public Node getLeft() { return left; }
        public Node getRight() { return right; }
        public void setLeft(Node l) { left = l; }
        public void setRight(Node r) {right = r; }
    }  
}
