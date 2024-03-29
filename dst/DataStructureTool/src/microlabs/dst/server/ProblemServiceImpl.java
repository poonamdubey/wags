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
import microlabs.dst.shared.Evaluation_Preorder;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ProblemServiceImpl extends RemoteServiceServlet implements ProblemService
{
	public Problem getProblem(int id) {
		AddEdgeRules_TreeMode rules = new AddEdgeRules_TreeMode();
		Evaluation_PostOrderBST eval = new Evaluation_PostOrderBST();
		Evaluation_Preorder preEval = new Evaluation_Preorder();
		
		AddEdgeRules noEdgeAddition = new AddEdgeRules();
		Evaluation_BSTTraversal trav = new Evaluation_BSTTraversal();
		Evaluation_BSTTraversalWithHelp travHelp = new Evaluation_BSTTraversalWithHelp();
		
		
		int[] noLocs = new int[0]; //used as a placeholder for problems with no preset locations
		String[] noEdges = new String[0]; //used as a placeholder for problems with no preset edges
 		
		switch(id)
		{
		case 0: return new Problem("BST Preorder Traversal (Help on)",
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
		case 1: return new Problem("BST Inorder Traversal (Help on)",
				"Perform an inorder traversal of the binary tree below by clicking" +
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
		case 2: return new Problem("BST Postorder Traversal (Help on)",
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
		case 3: return new Problem("BST Preorder Traversal (Help off)",
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
		case 4: return new Problem( "BST Inorder Traversal (Help off)",
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
		case 5: return new Problem("BST Postorder Traversal (Help off)",
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
		case 6: return new Problem("BST Preorder Traversal 2 (Help off)",
				"Perform a preorder traversal of the binary tree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"ABCDEFGHI",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{250,150,350,100,230,300,400,170,200},
				new int[]{75,175,175,275,275,275,275,375,475},
				new String[]{"AB","AC","BD","BE","EH", "HI", "CF", "CG"},
				new String[]{"ABDEHICFG"},
				trav,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE);
		case 7: return new Problem("BST Inorder Traversal 2 (Help off)",
				"Perform an inorder traversal of the binary tree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"CDFAJKLMQ",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{250,150,350,90,320,410,120,280,380},
				new int[]{75,175,175,275,275,275,375,375,375},
				new String[]{"CD","CF","DA","AL","FJ", "JM", "FK", "KQ"},
				new String[]{"ALDCMJFQK"},
				trav,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE);
		case 8: return new Problem("BST Postorder Traversal 2 (Help off)",
				"Perform a postorder traversal of the binary tree below by clicking" +
				" nodes in the order the traversal would visit them.",
				"PRGMNCLBS",
				DSTConstants.INSERT_METHOD_VALUE_AND_LOCATION,
				new int[]{400,200,450,100,300,50,150,250,350},
				new int[]{75,175,175,275,275,375,375,375,375},
				new String[]{"PR", "PG","RM","RN","MC", "ML", "NB", "NS"},
				new String[]{"CLMBSNRGP"},
				trav,
				noEdgeAddition,
				false,
				false,
				DSTConstants.NODE_CLICKABLE);
		case 9: return new Problem("Insert Nodes into a BST 1",
				"Given the following BST, insert the following " +
				"nodes in order to retain a BST structure." +
				"\nHint: Add the top node first, and work your way down",
				"QHVMTBPW",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"QHBMPVTW", "BHMPQTVW"}, //pre, in
				preEval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 10: return new Problem("Insert Nodes into a BST 2",
				"Given the following BST, insert the following " +
				"nodes in order to retain a BST structure." +
				"\nHint: Add the top node first, and work your way down",
				"OIVNRZFDP",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"OIFDNVRPZ", "DFINOPRVZ"}, //pre, in
				preEval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 11: return new Problem("Insert Nodes into a BST 3",
				"Given the following BST, insert the following " +
				"nodes in order to retain a BST structure." +
				"\nHint: Add the top node first, and work your way down",
				"PHQGNKORS",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"PHGNKOQRS", "GHKNOPQRS"}, //pre, in
				preEval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 12: return new Problem("Insert Nodes into a BST 4",
				"Given the following BST, insert the following " +
				"nodes in order to retain a BST structure." +
				"\nHint: Add the top node first, and work your way down",
				"PHQGSRKON",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"PHGKONQSR", "GHKNOPQRS"}, //pre, in
				preEval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 13: return new Problem("Binary Search Tree from Postorder Traversal 1",
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
		case 14: return new Problem("Binary Search Tree from Postorder Traversal 2",
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
		case 15: return new Problem("Binary Search Tree from Postorder Traversal 3",
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
		case 16: return new Problem("Binary Search Tree from Postorder Traversal 4",
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
		case 17: return new Problem("Binary Tree from Pre/Inorder Traversals 1",
				"Given the preorder traversal XDMLTKJ, and " +
				"the inorder traversal MDLXKTJ " +
				"construct the original binary tree.  " +
				"Hint: The binary tree is unique.",
				"DJKLMTX",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"XDMLTKJ", "MDLXKTJ"}, //post MLDKJTX
				preEval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 18: return new Problem("Binary Tree from Pre/Inorder Traversals 2",
				"Given the preorder traversal EXPORALS, and " +
				"the inorder traversal OPXELARS " +
				"construct the original binary tree.  " +
				"Hint: The binary tree is unique.",
				"AELOPRSX",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"EXPORALS", "OPXELARS"},  //post OPXLASRE
				preEval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 19: return new Problem("Binary Tree from Pre/Inorder Traversals 3",
				"Given the preorder traversal PDLSOQNTB, and " +
				"the inorder traversal SLODQPNTB " +
				"construct the original binary tree.  " +
				"Hint: The binary tree is unique.",
				"BDLNOPQST",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"PDLSOQNTB", "SLODQPNTB"}, //post SOLQDBTNP
				preEval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
		case 20: return new Problem("Binary Tree from Pre/Inorder Traversals 4",
				"Given the preorder traversal DLGNRAOPETM, and " +
				"the inorder traversal GNRLADPEOTM " +
				"construct the original binary tree.  " +
				"Hint: The binary tree is unique.",
				"ADEGLMNOPRT",
				DSTConstants.INSERT_METHOD_VALUE,
				noLocs,
				noLocs,
				noEdges,
				new String[]{"DLGNRAOPETM", "GNRLADPEOTM"}, //post RNGALEPMTOD
				preEval,
				rules,
				true,
				true,
				DSTConstants.NODE_DRAGGABLE);
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
