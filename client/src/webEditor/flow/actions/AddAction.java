package webEditor.flow.actions;

import webEditor.flow.view.ActionState;
import webEditor.flow.view.DropPoint;
import webEditor.flow.view.FlowUi;
import webEditor.flow.view.VariableMap;

import com.google.gwt.user.client.Window;

public class AddAction extends ActionState{
	DropPoint dp;
	String varName;
	int value;
	int oldExecuteID;
	public AddAction(DropPoint dp){
		this.dp = dp;
	}

	@Override
	public void undo() {
		Window.alert("print1 in addUndo");
		Window.alert("reversing add of "+value+" to "+varName);
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)-value);
		
		FlowUi.executeIndex = this.oldExecuteID;
	}

	@Override
	public void execute() {
		this.varName = dp.getInsideContent();
		this.value = dp.getBoxValue();
		this.oldExecuteID = FlowUi.executeIndex;
		
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)+value);
		
		if(dp.getNextExecuteID() != -1){
			FlowUi.executeIndex = dp.getNextExecuteID();
		} else{
			FlowUi.executeIndex++;
		}
	}

}
