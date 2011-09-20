/**
 * @author: Philip Meznar
 * @date: Sep 19, 2011
 * @file: ProblemBuilder.java
 * 
 * Description: This class constructs a problem
 * from the server.  It is necessary as the server
 * contains the problem information in a few disparate places.
 */
package dst.client;

import java.util.ArrayList;
import java.util.Map;

import webEditor.client.Proxy;
import dst.logic.AddEdgeRules;
import dst.logic.DSTComponents;
import dst.logic.Evaluation;


public class ProblemBuilder {
	private static String name;
	private static String problemText;
	private static String nodes;
	private static int[] xPositions; //must be same size as nodes
	private static int[] yPositions; //must be sames size edges
	private static String insertMethod;
	private static String[] edges;	  //each array element contains two chars (i.e AB), 1st is parent, 2nd is child
	private static Evaluation eval;
	private static AddEdgeRules rules;
	private static String[] arguments;
	private static boolean edgesRemovable;
	private static boolean nodesDraggable;
	private static String nodeType;
	
	private static Map<String, String> problemSkel;
	/*LEFT TO IMPLEMENT
	private String nodes;
	private int[] xPositions; //must be same size as nodes
	private int[] yPositions; //must be sames size edges
	private String[] edges;	  //each array element contains two chars (i.e AB), 1st is parent, 2nd is child
	*/	
	/**
	 * Attributes that can be grabbed from database:
	 * NAME
	 * PROBLEMTEXT
	 * INPUTMETHOD
	 * EVALUATION-INTEGER
	 * RULES-INTEGER
	 * ARGUMENTS
	 * EDGESREMOVABLE
	 * NODESDRAGGABLE
	 * NODETYPE
	 */
	
	/**
	 * Gets called from ClickHandlers in DataStructureTool
	 * Returns a problem.
	 */
	private static void grabSkel(int problemId){
		//Need to add the whole "Id" thing to getProblem
		Proxy.getProblemSkel(problemSkel);
		
		name = problemSkel.get("name");
		problemText = problemSkel.get("problemText");
		insertMethod = problemSkel.get("inputMethod");
		
		eval = DSTComponents.getEvaluation(Integer.parseInt(problemSkel.get("eval")));
		rules = DSTComponents.getRules(Integer.parseInt(problemSkel.get("rules")));
		
		arguments = problemSkel.get("arguments").split(" ");
		if(problemSkel.get("edgesRemovable").equals("1"))
			edgesRemovable = true;
		else 
			edgesRemovable = false;
		if(problemSkel.get("nodesDraggable").equals("1"))
			nodesDraggable = true;
		else
			nodesDraggable = false;
	}

	private static void grabNodes(int problemId){
		/**
		 * An array of maps will be returned
		 * Each map corresponds to a node.
		 * We must cylce through the maps, setting the values correctly
		 */
		ArrayList<Map> nodesMap = new ArrayList<Map>();
		Proxy.getProblemNodes(nodesMap);
		Map<String, String> nodeMap;
		
		xPositions = new int[nodesMap.size()];
		yPositions = new int[nodesMap.size()];
		
		for(int i = 0; i < nodesMap.size(); i++){
			nodeMap = nodesMap.get(i);
			nodes += nodeMap.get("Key");
			xPositions[i] = Integer.parseInt(nodeMap.get("left"));
			yPositions[i] = Integer.parseInt(nodeMap.get("top"));
		}
		
	}
	
	private static void grabEdges(int problemId){
		ArrayList<Map> edgesMap = new ArrayList<Map>();
		Proxy.getProblemEdges(edgesMap);
		Map<String, String> edgeMap;
		
		edges = new String[edgesMap.size()];
		
		for(int i = 0; i < edgesMap.size(); i++){
			edgeMap = edgesMap.get(i);
			String edge = edgeMap.get("node1Key") + edgeMap.get("node2Key");
			
			edges[i] = edge;
		}
	}
	
	public static Problem build(int problemId){
		grabSkel(problemId);
		grabNodes(problemId);
		grabEdges(problemId);
		
		Problem problem = new Problem(name, problemText, nodes, insertMethod, xPositions, yPositions, 
				edges, arguments, eval, rules, edgesRemovable,
				nodesDraggable, nodeType);
		
		return problem;
	}
}
