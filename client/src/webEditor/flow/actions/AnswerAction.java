package webEditor.flow.actions;

import webEditor.flow.view.ActionState;
import webEditor.flow.view.DropPoint;
import webEditor.flow.view.FlowUi;
import webEditor.flow.view.VariableMap;

import com.google.gwt.user.client.Window;

public class AnswerAction extends ActionState{
	DropPoint dp;
	public AnswerAction(DropPoint dp){
		this.dp = dp;
	}

	@Override
	public void undo() {
		FlowUi.executeIndex--;
		
	}

	@Override
	public void execute() {
	//	if(parseAnswerChoice(dp.getInsideContent()) == FlowUi.getAnswer()){
			Window.alert("Correct Answer");
	//	}
		if(dp.getNextExecuteID() != -1){
			FlowUi.executeIndex = dp.getNextExecuteID();
		} else{
			FlowUi.executeIndex++;
		}
	}

	//TODO expand this, right now it only accepts 'Var +/- xx'
	public int parseAnswerChoice(String content){
		int result = 0;
		for(String s: VariableMap.INSTANCE.getKeySet()){
			if(content.contains(s)){
				result = VariableMap.INSTANCE.getValue(s);
			}
		}
		if(content.contains("+")){
			String num = content.substring(content.indexOf("+")).trim();
			result += Integer.parseInt(num);
		} else if(content.contains("-")){
			String num = content.substring(content.indexOf("-")).trim();
			result -= Integer.parseInt(num);
		}
		return result;
	}
}
