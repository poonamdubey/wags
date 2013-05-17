package webEditor.flow.actions;

import webEditor.flow.view.ActionState;
import webEditor.flow.view.DropPoint;
import webEditor.flow.view.FlowUi;
import webEditor.flow.view.VariableMap;

import com.google.gwt.user.client.Window;

public class ConditionalAction extends ActionState {
	DropPoint dp;
	String content;
	private int originalIndex;
	public ConditionalAction(DropPoint dp){
		this.dp = dp;
	}
	@Override
	public void undo() {
		FlowUi.executeIndex = originalIndex;
		Window.alert("set EI to "+originalIndex);
	}

	@Override
	public void execute() {
//		Window.alert("inside conditional execute");
//		Window.alert("inside conditional execute! "+((DropPoint) dp.getInsidePanel().getWidget(0)).getConditionContent()+" | "+dp.getContent());
		this.originalIndex = FlowUi.executeIndex;
		//TODO Need to have a parser/evaluator that can handle more complex conditions like ((a < b) && (c < e)) || d)
		this.content = ((DropPoint) dp.getInsidePanel().getWidget(0)).getConditionContent();
		int val1;
		int val2;
		String[] components = this.content.trim().split(" ");
//		Window.alert("comp0 = "+components[0]);
		
		// getting val1
		if(VariableMap.INSTANCE.hasVar(components[0])){
			val1 = VariableMap.INSTANCE.getValue(components[0]);
		} else if(components[0].toLowerCase().equals("true")){
			val1 = 1;
		} else if(components[0].toLowerCase().equals("false")){
			val1 = 0;
		} else{
			val1 = Integer.parseInt(components[0]);
		}
		
//		Window.alert("val1 = "+val1);
		// getting val2
		
//		Window.alert("comp2 = "+components[2]);
		if(VariableMap.INSTANCE.hasVar(components[2])){
			val2 = VariableMap.INSTANCE.getValue(components[2]);
		} else if(components[2].toLowerCase().equals("true")){
			val2 = 1;
		} else if(components[2].toLowerCase().equals("false")){
			val2 = 0;
		} else{
			val2 = Integer.parseInt(components[2]);
		}
//		Window.alert("val2 = "+val2);
		

		String[] indexes = dp.getContent().split("|");
		for(int i=0; i < indexes.length; i++){
//			Window.alert(i+" : "+indexes[i]);
		}
		int trueIndex = Integer.parseInt(indexes[1]);
		int falseIndex =Integer.parseInt(indexes[3]);
		
//		Window.alert("true: "+trueIndex+" false: "+falseIndex);
		
		String sign = components[1];
		
//		Window.alert("sign: "+components[1]);
		
		if(sign.equals(">")){
			if(val1>val2)
				FlowUi.executeIndex = trueIndex;
			else
				FlowUi.executeIndex = falseIndex;
		} else if(sign.equals("<")){
			if(val1<val2)
				FlowUi.executeIndex = trueIndex;
			else
				FlowUi.executeIndex = falseIndex;
		} else if(sign.equals(">=")){
			if(val1>=val2)
				FlowUi.executeIndex = trueIndex;
			else
				FlowUi.executeIndex = falseIndex;
		} else if(sign.equals("<=")){
			if(val1<=val2)
				FlowUi.executeIndex = trueIndex;
			else
				FlowUi.executeIndex = falseIndex;
		} else if(sign.equals("==")){
			if(val1==val2)
				FlowUi.executeIndex = trueIndex;
			else
				FlowUi.executeIndex = falseIndex;
		} else if(sign.equals("!=")){
			if(val1!=val2)
				FlowUi.executeIndex = trueIndex;
			else
				FlowUi.executeIndex = falseIndex;
		}
		Window.alert("new Index: "+FlowUi.executeIndex);
		
	}
}
