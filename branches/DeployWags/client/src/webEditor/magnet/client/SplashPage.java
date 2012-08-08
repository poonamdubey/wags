package webEditor.magnet.client;

import webEditor.client.MagnetProblem;
import webEditor.client.Proxy;
import webEditor.client.view.Wags;

import java.util.Random;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class SplashPage extends AbsolutePanel {
	protected static PickupDragController dc = new PickupDragController(RootPanel.get(), false);
	static String[] structuresList = {"choose structure...","for","while","if","else if", "else"};
	final HorizontalPanel problemPane = new HorizontalPanel();
	
	String title;
	
	public SplashPage(Wags wags) {
		add(problemPane);
		problemPane.clear();
		Proxy.getMagnetExercises(wags,problemPane);
	}
	
	/**
	 * Makes the problem
	 */
	public static RefrigeratorMagnet makeProblem(MagnetProblem magnet) {
		return new RefrigeratorMagnet(
				magnet.title,
				magnet.directions,
				getMainContainer(magnet.mainFunction),
				buildFunctions(magnet.innerFunctions),
				magnet.type,
				decodePremade(magnet.statements), 
				structuresList,
				randomizeArray(magnet.forLeft),
				randomizeArray(magnet.forMid),
				randomizeArray(magnet.forRight),
				randomizeArray(magnet.bools),
				magnet.solution,
				magnet.statements,
				dc
		);
	}
	public static String[] randomizeArray(String[] arr){
		Random rand = new Random();
		int temp1;
		int temp2;
		for(int i=0;i<20;i++){
			temp1 = rand.nextInt(arr.length);
			temp2 = rand.nextInt(arr.length);
			
			String sTemp1 = arr[temp1];
			arr[temp1] = arr[temp2];
			arr[temp2] = sTemp1;
		}
		return arr;
	}
	
	public String[] convertArray(JSONArray arr) {
		if (arr.toString().equals("[null]")) {
			return null;
		}
		
		String[] result = new String[arr.size()];
		
		for (int i = 0; i < arr.size(); i++) {
			result[i] = arr.get(i).isString().stringValue();
		}
		
		return result;
	}
	public static StackableContainer[] decodePremade(String[] segments) {
		if (segments == null) {
			return null;
		}
		
		StackableContainer[] preMadeList = new StackableContainer[segments.length]; //should never need this many
		int counter = 0;
			while (counter < segments.length) {
				if (segments[counter].contains(Consts.TOP)) {
					preMadeList[counter] = new StackableContainer(segments[counter], dc);
				} else {
					preMadeList[counter] = new StackableContainer(segments[counter] + Consts.TOP + Consts.INSIDE + Consts.BOTTOM, dc, false);
				}
				
				counter++;
			}
			
		return preMadeList;
	}
	
	public static StackableContainer getMainContainer(String str) {
		return new StackableContainer(str + " {<br /><span id=\"inside_of_block\">"
				+ Consts.TOP + Consts.INSIDE + Consts.BOTTOM + "</span><br />}", dc, Consts.MAIN);
	}
	
	
	public static StackableContainer[] buildFunctions(String[] insideFunctions) {
		if (insideFunctions == null) {
			return null;
		}
		
		StackableContainer[] insideFunctionsList = new StackableContainer[insideFunctions.length]; //should never need this many
		int counter = 0;
		while (counter < insideFunctions.length) {
			insideFunctionsList[counter] = new StackableContainer(insideFunctions[counter], dc, Consts.NONDRAGGABLE);
			counter++;
		}
		
		return insideFunctionsList;
	}

}
