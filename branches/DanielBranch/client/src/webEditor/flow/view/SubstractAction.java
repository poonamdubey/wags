package webEditor.flow.view;

public class SubstractAction extends ActionState {
	DropPoint dp;
	String varName;
	int value;
	public SubstractAction(DropPoint dp){
		this.dp = dp;
	}

	@Override
	public void undo() {
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)+value);
		
	}

	@Override
	public void execute() {
		this.varName = dp.getInsideContent();
		this.value = dp.getBoxValue();
		
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)-value);
		
	}

}
