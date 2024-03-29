package microlabs.dst.server;

import microlabs.dst.client.ProblemsFetchService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProblemsFetchServiceImpl extends RemoteServiceServlet implements ProblemsFetchService
{
	//NOTE: THE INDEX IN THE ARRAY HERE REPRESENTS THE ID NUMBER OF THE PROBLEM
	//THE ID IS USED TO FETCH THE PROBLEM CONTENTS FROM THE PROBLEM SERVICE
	//THIS NEEDS TO BE CLEANED UP
	public String[] getProblems() {
		return new String[] {"BST Preorder  Traversal (Help on)",
				 			 "BST Inorder   Traversal (Help on)",
				 			 "BST Postorder Traversal (Help on)",
				 			 "BST Preorder  Traversal (Help off)",
				 			 "BST Inorder   Traversal (Help off)",
				 			 "BST Postorder Traversal (Help off)",
				 			 "BST Preorder  Traversal 2 (Help off)",
				 			 "BST Inorder   Traversal 2 (Help off)",
				 			 "BST Postorder Traversal 2 (Help off)",
							 "Insert Nodes into a BST 1",
							 "Insert Nodes into a BST 2",
							 "Insert Nodes into a BST 3",
							 "Insert Nodes into a BST 4",
				 			 "Binary Search Tree from Postorder Traversal 1",
							 "Binary Search Tree from Postorder Traversal 2",
							 "Binary Search Tree from Postorder Traversal 3",
							 "Binary Search Tree from Postorder Traversal 4",
							 "Binary Tree from Pre/Inorder Traversals 1",
							 "Binary Tree from Pre/Inorder Traversals 2",
							 "Binary Tree from Pre/Inorder Traversals 3",
							 "Binary Tree from Pre/Inorder Traversals 4"};
	}
}
