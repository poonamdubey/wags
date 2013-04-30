package webEditor.flow.view;

public class ModAction extends ActionState {
	DropPoint dp;
	int original;
	int value;
	String varName;
	public ModAction(DropPoint dp){
		this.dp = dp;
	}
	@Override
	public void undo() {
		VariableMap.INSTANCE.addVar(varName, original);
		
	}

	@Override
	public void execute() {
		this.varName = dp.getInsideContent();
		this.value = dp.getBoxValue();
		this.original = VariableMap.INSTANCE.getValue(varName);
		
		VariableMap.INSTANCE.addVar(varName, VariableMap.INSTANCE.getValue(varName)%value);
		
	}

}
