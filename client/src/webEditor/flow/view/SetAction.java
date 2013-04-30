package webEditor.flow.view;

import com.google.gwt.user.client.Window;

public class SetAction extends ActionState {
	DropPoint dp;
	String varName;
	public SetAction(DropPoint dp){
		this.dp = dp;
	}
	@Override
	public void undo() {
		VariableMap.INSTANCE.removeVar(varName);
	}

	@Override
	public void execute() {
		Window.alert("Inside setAction");
		this.varName = dp.getInsideContent();
		
		VariableMap.INSTANCE.addVar(varName,dp.getBoxValue());

	}

}
