/**
 * @author: Philip Meznar
 * @date: Aug 22, 2011
 * @file: TraversalTest.java
 * 
 * Description: The test class to be
 * used with WAGS for the traversal 
 * microlabs
 */
package traversals;

import java.util.Random;
import traversals.TraversalSkeleton.Node;

public class TraversalTest {
	TraversalSolution solution;
	TraversalSkeleton student;
	
	public TraversalTest(){
		solution = new TraversalSolution();
		student = new TraversalSkeleton();
	}
	
	/**
	 * Randomly generates roughly equal trees of length
	 * 5-13
	 * @return The root of the constructed tree
	 * 
	 */
	Node metaBuildTree(){
		String alphabet = "abcdefghijklmnopqrstuvxyz";
		String preOrder, inOrder;
		preOrder = inOrder = "";
		
		Random random = new Random();
		
		for(int i = 0; i < random.nextInt(8) + 5;){
			String add = alphabet.charAt(random.nextInt(alphabet.length())) + "";
			
			//Generates a random sequence of non-repeating
			//characters to be used as the preorder
			if(!preOrder.contains(add)){
				preOrder += add;
				i++;
			}		 
		}
		
		//inorder must be constructed with certain rules,
		//relocated to a new method
		inOrder = createInOrder(preOrder);
		
		Node tree = buildTree(preOrder, inOrder);
		return tree;
		
	}
	
	/**
	 * createInOrder
	 * Takes a preorder, creates a suitable
	 * somewhat random inorder listing
	 * @param preorder The preorder traversal
	 * @return The inorder traversal
	 */
	String createInOrder(String preorder){
		String inorder = "";
		
		String root = preorder.charAt(0) + "";
		preorder = preorder.substring(1);
		
		int len = preorder.length();
		String LST = preorder.substring(0, len / 2);
		String RST = preorder.substring(len / 2);
		
		LST = scramble(LST);
		RST = scramble(RST);
		
		inorder += LST + root + RST;
		
		return inorder;
	}
	
	/**
	 * Switches up some characters in the inorder
	 * as to not always have subtrees with only 
	 * right subtrees (i.e., the tree would have
	 * a left and right subtree, but those subtrees
	 * would only have right subtrees)
	 * @param string A subtree of the actual tree
	 * @return A potentially scrambled traversal
	 */
	String scramble(String string){
		Random random = new Random();
		
		//This is skewed towards scrambling, as only trees with
		//at least six nodes can be scrambled anyway
		for(int index = 1; index < string.length() - 1; index += 2){
			if(random.nextInt(3) != 2){ 
				char temp = string.charAt(index + 1);
				string = string.replace(string.charAt(index + 1), string.charAt(index));
				string = string.replaceFirst("["+string.charAt(index)+"]", temp + "");
			}
		}
		
		return string;
	}
		
		
	/**
	 * Builds a tree with a preorder and inorder traversal
	 * @param preorder The preorder traversal
	 * @param inorder The inorder traversal
	 * @return The root of the tree
	 */
    Node buildTree(String preorder, String inorder){
        if(preorder.length()==0) return null;
        // create root, the first node in the preorder
        char rootValue = preorder.charAt(0);
        Node root = student.new Node(rootValue);
        if(preorder.length()==1) return root;
     
        String leftInorder = inorder.substring(0,inorder.indexOf(rootValue));
        String leftPreorder = preorder.substring(1,leftInorder.length()+1);
     
        root.left = buildTree(leftPreorder,leftInorder);
        String rightInorder = inorder.substring(inorder.indexOf(rootValue)+1);
        String rightPreorder = preorder.substring(leftInorder.length()+1);
     
        root.right = buildTree(rightPreorder,rightInorder);
        return root;
    }
    
    void displayTree(Node tree, int indent){
        if(tree != null){
            displayTree(tree.right, indent+1);
            for(int j=0; j < indent; j++) System.out.print("   ");
            System.out.println(tree.key+" ");
            displayTree(tree.left, indent+1);
        } 
    }
    
    public boolean test(){
    	boolean correct = true;
    	//Construct 4 random trees to run student and solution methods on
    	for(int i = 0; i < 4; i++){
    		Node tree = metaBuildTree();
    		
    		if(!checkTree(tree)) correct = false;
    	}
    	
    	Node empty = null;
    	Node single = student.new Node('A');
    	
    	
    	if(!checkTree(single)) correct = false;
    	System.out.println("*Empty tree*");
    	if(!checkTree(empty)) correct = false;
    	
    	return correct;
    }
    
    private boolean checkTree(Node tree){
		String correctPre = solution.preorderTraversal(tree);
		String studentPre = student.preorderTraversal(tree);
		String correctIn = solution.inorderTraversal(tree);
		String studentIn = student.inorderTraversal(tree);
		String correctPost = solution.postorderTraversal(tree);
		String studentPost = student.postorderTraversal(tree);
		
		displayTree(tree, 4);
		
		System.out.println("Preorder traversal:      " + correctPre);
		System.out.println("Your preorder traversal: " + studentPre +"\n");
		
		System.out.println("Inorder traversal:      " + correctIn);
		System.out.println("Your inorder traversal: " + studentIn+"\n");
		
		System.out.println("Postorder traversal:      " + correctPost);
		System.out.println("Your postorder traversal: " + studentPost+"\n\n");
		
		if(!correctPre.equals(studentPre) || !correctIn.equals(studentIn) || !correctPost.equals(studentPost)){
			return false;
		}
		
		return true;
    }
    
    public static void main(String[] args) {
		TraversalTest tester = new TraversalTest();
		boolean correct = tester.test();
		
		if(correct) System.out.println("Success");
		else System.out.println("Failure");
		
	}

}
