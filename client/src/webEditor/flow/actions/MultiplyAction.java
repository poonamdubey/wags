package webEditor.flow.actions;

import webEditor.flow.view.ActionState;
import webEditor.flow.view.DropPoint;
import webEditor.flow.view.FlowUi;
import webEditor.flow.view.VariableMap;

public class MultiplyAction extends ActionState {
	DropPoint dp;
	String varName;
	int value;
	private int oldExecuteID;
	
	public MultiplyAction(DropPoint dp){
		this.dp = dp;
	}
	
	@Override
	public void undo() {
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)/value);
		FlowUi.executeIndex = oldExecuteID;
	}

	@Override
	public void execute() {
		this.varName = dp.getInsideContent();
		this.value = dp.getBoxValue();
		this.oldExecuteID = FlowUi.executeIndex;
		
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)*value);
		if(dp.getNextExecuteID() != -1){
			FlowUi.executeIndex = dp.getNextExecuteID();
		} else{
			FlowUi.executeIndex++;
		}
	}

}
