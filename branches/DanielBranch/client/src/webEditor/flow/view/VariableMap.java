package webEditor.flow.view;

import java.util.HashMap;

import com.google.gwt.user.client.Window;

public enum VariableMap {
	INSTANCE;
	
	private final HashMap<String, Integer> varMap;
	
	private VariableMap(){
		varMap = new HashMap<String,Integer>();
	}
	
	public int getValue(String varName){
		return varMap.get(varName);
	}
	
	public void addVar(String varName, int value){
		Window.alert("Adding Var to Map! "+varName+" : "+value);
		varMap.put(varName, value);
	}
	
	public void removeVar(String varName){
		varMap.remove(varName);
	}
	
	public boolean hasVar(String varName){
		return varMap.containsKey(varName);
	}
	
	public void clear(){
		varMap.clear();
	}
}
