package microlabs.dst.shared;

import java.util.ArrayList;

import java.util.Stack;
import com.google.gwt.user.client.rpc.IsSerializable;
import microlabs.dst.client.EdgeParent;
import microlabs.dst.client.EdgeUndirected;
import microlabs.dst.client.Node;

public class Evaluation_PostOrderBST extends Evaluation  implements IsSerializable
{	
	private ArrayList<EvaluationNode> treeNodes;
	
	public String evaluate(String problemName, String[] arguments, ArrayList<Node> nodes, ArrayList<EdgeParent> edges)
	{	
		errorMessage = "";
		treeNodes = new ArrayList<EvaluationNode>();
		
		for(int i = 0; i < edges.size(); i++)
		{
			EdgeUndirected e = (EdgeUndirected) edges.get(i);
			System.out.println(e.getN1().getValue() + " " + e.getN2().getValue());
		}
		if(testRootNodeForPostOrderTraversal(arguments[0], nodes, edges) == false)
		{
			return errorMessage;
		}
		
		EvaluationNode rootEvalNode = buildEvaluationTree(nodes, edges);

		if(testInorderTraversal(arguments[0], arguments[1], rootEvalNode, nodes, edges) == false)
		{
			return errorMessage;
		}
		
		if(testPostOrderTraversal(arguments[0], rootEvalNode, nodes, edges) == false)
		{
			return errorMessage;
		}
		
		emailResult(problemName);
		return "Feedback: Congratulations! Your tree is correct.";
	}

	private boolean testRootNodeForPostOrderTraversal(String postTrav, ArrayList<Node> nodes, ArrayList<EdgeParent> edges)
	{
		ArrayList<Node> noParentNodes = (ArrayList<Node>) nodes.clone();

		for(int i = 0; i < edges.size(); i++)
		{
			EdgeParent edge = edges.get(i);
			noParentNodes.remove(edge.getN2());
		}

		if(noParentNodes.size() > 1)
		{
			errorMessage = "Feedback: Your tree is incomplete.  Continue adding edges to complete the tree.";
			return false;
		}
		else if(noParentNodes.size() == 1)
		{
			char rootVal = postTrav.charAt(postTrav.length()-1);
			Node rootNode = noParentNodes.get(0);
			if(rootVal != rootNode.getValue())
			{
				errorMessage += "Feedback: Your solution is not correct.  Remember that in a \n" +
				"postorder traversal, the last node visited is always the root node.";
				return false;
			}
		}
		return true;
	}

	private Boolean testInorderTraversal(String postTrav, String correctTrav, EvaluationNode rootEvalNode, ArrayList<Node> nodes, ArrayList<EdgeParent> edges)
	{
		EvaluationNode current = rootEvalNode;

		String inorderTrav = "";

		while(!(current == null))
		{
			Stack<EvaluationNode> travNodes = new Stack<EvaluationNode>();
			while((travNodes.size() > 0) || (!(current == null)))
			{
				if(!(current == null))
				{
					travNodes.push(current);
					current = current.left == null ? null : convertNodeToEvalNode(treeNodes, current.left);
				}
				else
				{
					current = travNodes.pop();
					inorderTrav += current.node.getValue();
					current = current.right == null ? null : convertNodeToEvalNode(treeNodes, current.right);
				}
			}
		}

		System.out.println(inorderTrav);

		if(!inorderTrav.equals(correctTrav))
		{
			errorMessage = "Feedback: Incorrect inorder traversal.  Your traversal is " + inorderTrav;
			errorMessage += "\nCorrect inorder traversal is " + correctTrav;
			return false;
		}

		return true;
	}
	
	private boolean testPostOrderTraversal(String postTrav, EvaluationNode rootEvalNode, ArrayList<Node> nodes, ArrayList<EdgeParent> edges)
	{
		EvaluationNode current = rootEvalNode;
		String userPostOrderTrav = "";
		Stack<EvaluationNode> travNodes = new Stack<EvaluationNode>();
		
		travNodes.push(current);
		
		while(!travNodes.empty())
		{
			current = travNodes.peek();
			if((current.left != null) && (convertNodeToEvalNode(treeNodes, current.left).visited == false))
			{
				travNodes.push(convertNodeToEvalNode(treeNodes, current.left));
			}
			else
			{
				if((current.right != null) && (convertNodeToEvalNode(treeNodes, current.right).visited == false))
				{
					travNodes.push(convertNodeToEvalNode(treeNodes, current.right));
				}
				else
				{
					userPostOrderTrav += current.node.getValue();
					convertNodeToEvalNode(treeNodes, current.node).visited = true;
					travNodes.pop();
				}
			}	
		}
		
		System.out.println("post" + userPostOrderTrav);

		if(!userPostOrderTrav.equals(postTrav))
		{
			errorMessage = "Feedback: Incorrect postorder traversal.  Your postorder traversal is " + userPostOrderTrav;
			errorMessage += "\nCorrect postorder traversal is " + postTrav;
			return false;
		}

		return true;
	}
	
	private EvaluationNode buildEvaluationTree(ArrayList<Node> nodes, ArrayList<EdgeParent> edges)
	{
		ArrayList<Node> noParentNodes = (ArrayList<Node>) nodes.clone();

		for(int i = 0; i < edges.size(); i++)
		{
			EdgeParent edge = edges.get(i);
			noParentNodes.remove(edge.getN2());
		}

		Node rootNode = noParentNodes.get(0);
		EvaluationNode rootEvalNode = null;
		Node currNode = null;
		Node leftNode = null;
		Node parentNode = null;
		Node rightNode = null;

		for(int j = 0; j < nodes.size(); j++)
		{
			currNode = nodes.get(j);
			for(int i = 0; i < edges.size(); i++)
			{
				EdgeParent edge = edges.get(i);
				if(currNode.getValue() == edge.getN1().getValue())
				{
					if(currNode.getLeft() > edge.getN2().getLeft())
					{
						leftNode = edge.getN2();
					}
					else if(currNode.getLeft() < edge.getN2().getLeft())
					{
						rightNode = edge.getN2();
					}
				}
				else if(currNode.getValue() == edge.getN2().getValue())
				{
					parentNode = edge.getN1();
				}
			}

			EvaluationNode evalNode = new EvaluationNode(currNode, parentNode, leftNode, rightNode);
			treeNodes.add(evalNode);
			parentNode = null;
			leftNode = null;
			rightNode = null;

			if(currNode.getValue() == rootNode.getValue())
			{
				rootEvalNode = evalNode;
			}
		}
		
		for(int i = 0; i < treeNodes.size(); i++)
		{
			EvaluationNode n = treeNodes.get(i);
			System.out.print("Val: " + n.node.getValue());
			if(n.parent != null)
				System.out.print(" Par: " + n.parent.getValue());
			if(n.left != null)
				System.out.print(" Left: " + n.left.getValue());
			if(n.right != null)
				System.out.print(" Right: " + n.right.getValue());
			System.out.println("");
		}
		return rootEvalNode;
	}

	private EvaluationNode convertNodeToEvalNode(ArrayList<EvaluationNode> evalNodes, Node node)
	{
		for(int i = 0; i < evalNodes.size(); i++)
		{
			EvaluationNode theNode = evalNodes.get(i);
			if(theNode.node.getValue() == node.getValue())
			{
				return theNode;
			}
		}
		return null;
	}

	private class EvaluationNode implements IsSerializable
	{
		public Node parent;
		public Node node;
		public Node left;
		public Node right;
		public boolean visited;

		private EvaluationNode(){}

		public EvaluationNode(Node node, Node parent, Node left, Node right)
		{
			this.node = node;
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.visited = false;
		}
	}
}
