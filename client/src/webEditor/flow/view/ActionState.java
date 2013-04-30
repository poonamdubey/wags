package webEditor.flow.view;

/**
 * Abstract class that holds information so that an undo operation can be performed.
 * The only method that must be overridden is undo().
 * 
 * ActionState should be pushed and popped from an UNDO stack, and upon being popped,
 * their undo() method should be called.
 * @author Reed Phillips 04/13
 *
 */
public abstract class ActionState {
	public abstract void undo();
	public abstract void execute();
}
