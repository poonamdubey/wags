package webEditor.flow.view;

import com.google.gwt.user.client.Window;

public class SetAction extends ActionState {
	DropPoint dp;
	String varName;
	int oldValue;
	boolean hasOldValue = false;
	public SetAction(DropPoint dp){
		this.dp = dp;
	}
	@Override
	public void undo() {
		if(hasOldValue){
			Window.alert("undoing. setting "+varName+" to "+oldValue);
			VariableMap.INSTANCE.addVar(varName, oldValue);
		} else {
			// if no oldValue then
			Window.alert("removing value");
			VariableMap.INSTANCE.removeVar(varName);
		}
		
		Window.alert("undoing set "+ VariableMap.INSTANCE.hasVar(varName)+" to "+oldValue);
		FlowUi.executeIndex--;
	}

	@Override
	public void execute() {

		this.varName = dp.getInsideContent();
		if(VariableMap.INSTANCE.hasVar(varName)){
			this.oldValue = VariableMap.INSTANCE.getValue(varName);
			this.hasOldValue = true;
			Window.alert("setting oldValue");
		}
		Window.alert("Setting "+varName+" to "+dp.getBoxValue());
		VariableMap.INSTANCE.addVar(varName,dp.getBoxValue());
		FlowUi.executeIndex++;
	}

}
