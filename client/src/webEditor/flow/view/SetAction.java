package webEditor.flow.view;

import com.google.gwt.user.client.Window;

public class SetAction extends ActionState {
	DropPoint dp;
	String varName;
	int oldValue;
	public SetAction(DropPoint dp){
		this.dp = dp;
	}
	@Override
	public void undo() {
		try{
			// should set restore the variable to it's value before it got set and if it's
			// undoing the first time this variable got set then it should throw an exception
			// because oldValue will be null and then we will remove that variable from the map.
			Integer.valueOf(oldValue); 
			Window.alert("undoing. setting "+varName+" to "+oldValue);
			VariableMap.INSTANCE.addVar(varName, oldValue);
		} catch(NumberFormatException ex){
			// if no oldValue then
			Window.alert("removing value");
			VariableMap.INSTANCE.removeVar(varName);
		}
		
		Window.alert("undoing set"+ VariableMap.INSTANCE.hasVar(varName));
		FlowUi.executeIndex--;
	}

	@Override
	public void execute() {
		Window.alert("Inside setAction");
		this.varName = dp.getInsideContent();
		if(VariableMap.INSTANCE.hasVar(varName)){
			this.oldValue = VariableMap.INSTANCE.getValue(varName);
			Window.alert("setting oldValue");
		}
		
		VariableMap.INSTANCE.addVar(varName,dp.getBoxValue());
		FlowUi.executeIndex++;
	}

}
