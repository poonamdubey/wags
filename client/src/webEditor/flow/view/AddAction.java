package webEditor.flow.view;

import com.google.gwt.user.client.Window;

public class AddAction extends ActionState{
	DropPoint dp;
	String varName;
	int value;
	public AddAction(DropPoint dp){
		this.dp = dp;
	}

	@Override
	public void undo() {
		Window.alert("print1 in addUndo");
		Window.alert("reversing add of "+value+" to "+varName);
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)-value);
		
		FlowUi.executeIndex--;
	}

	@Override
	public void execute() {
		this.varName = dp.getInsideContent();
		this.value = dp.getBoxValue();
		
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)+value);
		
		FlowUi.executeIndex++;
	}

}
