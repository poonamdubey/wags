package webEditor.flow.actions;

import webEditor.flow.view.ActionState;
import webEditor.flow.view.DropPoint;
import webEditor.flow.view.FlowUi;
import webEditor.flow.view.VariableMap;

public class ModAction extends ActionState {
	DropPoint dp;
	int original;
	int value;
	String varName;
	private int oldExecuteID;
	public ModAction(DropPoint dp){
		this.dp = dp;
	}
	@Override
	public void undo() {
		VariableMap.INSTANCE.addVar(varName, original);
		FlowUi.executeIndex = oldExecuteID;
	}

	@Override
	public void execute() {
		this.varName = dp.getInsideContent();
		this.value = dp.getBoxValue();
		this.original = VariableMap.INSTANCE.getValue(varName);
		this.oldExecuteID = FlowUi.executeIndex;
		
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)%value);
		if(dp.getNextExecuteID() != -1){
			FlowUi.executeIndex = dp.getNextExecuteID();
		} else{
			FlowUi.executeIndex++;
		}
	}

}
