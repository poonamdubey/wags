package microlabs.dst.server;

import java.util.ArrayList;

import microlabs.dst.client.DSTConstants;
import microlabs.dst.client.Problem;
import microlabs.dst.client.ProblemService;
import microlabs.dst.shared.AddEdgeRules;
import microlabs.dst.shared.AddEdgeRules_TreeMode;
import microlabs.dst.shared.Evaluation_BSTTraversalWithHelp;
import microlabs.dst.shared.Evaluation_PostOrderBST;
import microlabs.dst.shared.Evaluation_BSTTraversal;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProblemServiceImpl extends RemoteServiceServlet implements ProblemService
{
	public Problem getProblem(int id) {
		AddEdgeRules_TreeMode rules = new AddEdgeRules_TreeMode();
		Evaluation_PostOrderBST eval = new Evaluation_PostOrderBST();
		
		AddEdgeRules noEdgeAddition = new AddEdgeRules();
		Evaluation_BSTTraversal trav = new Evaluation_BSTTraversal();
		Evaluation_BSTTraversalWithHelp travHelp = new Evaluation_BSTTraversalWithHelp();
		
		
		int[] noLocs = new int[0]; //used as a placeholder for problems with no preset locations
		String[] noEdges = new String[0]; //used as a placeholder for problems with no preset edges
 		
		switch(id)
		{
		case 0: return new Problem("Binary Search Tree from Postorder Traversal 1",
				"Given the postorder traversal DBHJFPTUSM, " +
				"construct the original binary search tree.  " +
				"Hint: The binary search tree is unique.",
				"DBHJFPTUSM",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"DBHJFPTUSM", "BDFHJMPSTU"},
				eval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 1: return new Problem("Binary Search Tree from Postorder Traversal 2",
				"Given the postorder traversal, DAJELMONK, " +
				"construct the original binary search tree.  " +
				"Hint: The binary search tree is unique.",
				"DAJELMONK",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"DAJELMONK", "ADEJKLMNO"},
				eval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 2: return new Problem("Binary Search Tree from Postorder Traversal 3",
				"Given the postorder traversal BAOMFRXZUP, " +
				"construct the original binary search tree.  " +
				"Hint: The binary search tree is unique.",
				"BAOMFRXZUP",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"BAOMFRXZUP", "ABFMOPRUXZ"},
				eval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 3: return new Problem("Binary Search Tree from Postorder Traversal 4",
				"Given the postorder traversal BEAKJHTRQ, " +
				"construct the original binary search tree.  " +
				"Hint: The binary search tree is unique.",
				"BEAKJHTRQ",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"BEAKJHTRQ", "ABEHJKQRT"},
				eval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 4: return new Problem("BST Preorder Traversal (Help on) 1",
				"Perform a preorder traversal of the binary tree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"ABCDEFG",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{250,150,350,75,225,275,425},
				new int[]{75,175,175,275,275,275,275},
				new String[]{"AB","BD","BE","CF","CG", "AC"},
				new String[]{"ABDECFG"},
				travHelp,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE_FORCE_EVAL);
		case 5: return new Problem("BST Inorder Traversal (Help on) 1",
				"Perform an inorer traversal of the binary tree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"ABCDEFG",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{250,150,350,75,225,275,425},
				new int[]{75,175,175,275,275,275,275},
				new String[]{"AB","BD","BE","CF","CG", "AC"},
				new String[]{"DBEAFCG"},
				travHelp,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE_FORCE_EVAL);
		case 6: return new Problem("BST Postorder Traversal (Help on) 1",
				"Perform a postorder traversal of the binary tree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"ABCDEFG",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{250,150,350,75,225,275,425},
				new int[]{75,175,175,275,275,275,275},
				new String[]{"AB","BD","BE","CF","CG", "AC"},
				new String[]{"DEBFGCA"},
				travHelp,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE_FORCE_EVAL);
		case 7: return new Problem("BST Preorder Traversal (Help off) 1",
				"Perform a preorder traversal of the binary tree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"XRQDHJMZL",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{250,150,350,100,200,300,450,50,400},
				new int[]{75,175,175,275,275,275,275,375,375},
				new String[]{"XR","RD","DZ","RH","XQ", "QJ", "QM", "ML"},
				new String[]{"XRDZHQJML"},
				trav,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE);
		case 8: return new Problem( "BST Inorder Traversal (Help off) 1",
				"Perform an inorer traversal of the binary tree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"MCLPQNTSD",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{250,200,350,50,300,400,100, 450, 150},
				new int[]{75,175,175,275,275,275,375, 375, 475},
				new String[]{"MC","CP","PT","TD","ML", "LQ", "LN", "NS"},
				new String[]{"PTDCMQLNS"},
				trav,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE);
		case 9: return new Problem("BST Postorder Traversal (Help off) 1",
				"Perform a postorder traversal of the binarytree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"ARJMLQZND",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{250,150,350,100,200,300,400,50,450},
				new int[]{75,175,175,275,275,275,275,375,375},
				new String[]{"AR","RM","MN","AJ","RL","JQ","JZ","ZD"},
				new String[]{"NMLRQDZJA"},
				trav,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE);
		default:
			return new Problem("Binary Search Tree from Postorder Traversal 1",
					"Given the postorder traversal DBHJFPTUSM, " +
					"construct the original binary search tree.  " +
					"Hint: The binary search tree is unique.",
					"DBHJFPTUSM",
					DSTConstants.INSERT_METHOD_VALUE,
					noLocs,
					noLocs,
					noEdges,
					new String[]{"DBHJFPTUSM", "BDFHJMPSTU"},
					eval,
					rules,
					true,
					true,
					DSTConstants.NODE_DRAGGABLE);
		}
	}	
}
