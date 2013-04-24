package webEditor.flow.view;

/**
 * This class allows for the undo of a Next or Restart button click
 *
 */
public class ClickState extends ActionState {
	
	public ClickState(ActionType action) {
		this.action = action;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}
}
