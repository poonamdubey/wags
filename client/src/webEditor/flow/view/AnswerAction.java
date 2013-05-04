package webEditor.flow.view;

import com.google.gwt.user.client.Window;

public class AnswerAction extends ActionState{
	DropPoint dp;
	public AnswerAction(DropPoint dp){
		this.dp = dp;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		FlowUi.executeIndex--;
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub		
		Window.alert("Executing Answer");
		Window.alert(dp.getInsideContent());
		if(VariableMap.INSTANCE.getValue(dp.getInsideContent()) == FlowUi.ANSWER){
			Window.alert("Correct Answer");
		}
		FlowUi.executeIndex++;
	}

}
