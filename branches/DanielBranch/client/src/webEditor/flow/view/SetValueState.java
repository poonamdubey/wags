package webEditor.flow.view;

import com.google.gwt.user.client.ui.Label;

/**
 * This class allows for the undo of a value being set by a student 
 *
 */
public class SetValueState extends ActionState {
	private String previousContent;
	private Label label;
	
	public SetValueState(Label label, ActionType action, String previousContent) {
		this.label = label;
		this.action = action;
		this.previousContent = previousContent;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}
}
