package webEditor.flow.view;

/**
 * This class allows for the undo of a Segment being dropped onto a DropPoint
 *
 */
public class DropSegmentState extends ActionState {
	private DropPoint parent;

	/**
	 * Constructor
	 * @param parent The DropPoint that this segment originally belonged to
	 * @param action The action that is being performed, i.e. whether it
	 * 				 originated from the segment panel or a canvas DropPoint
	 */
	public DropSegmentState(DropPoint parent, ActionType action) {
		this.parent = parent;
		this.action = action;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}
}
