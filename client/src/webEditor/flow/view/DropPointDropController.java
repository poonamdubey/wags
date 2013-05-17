package webEditor.flow.view;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.Window;


/**
 * Stackable Container drop controller that calls method within the stackableContainer class
 * to achieve the stacking functionality.  This is mostly glue code that connects 
 * stackableContainer's stacking methods with the drag and drop events that occur as the user drags magnets.
 *
 */
public class DropPointDropController extends SimpleDropController {

	private final DropPoint dropTarget;
	private final FlowUi flow;

	public DropPointDropController(DropPoint dropTarget, FlowUi flow) {
		super(dropTarget);
		this.dropTarget = dropTarget;
		this.flow = flow;
	}

	@Override
	public void onDrop(DragContext context) {
		DropPoint sw = new DropPoint(((DropPoint)context.selectedWidgets.get(0)).getType(),flow, dropTarget.getNextExecuteID());
		if(dropTarget.isStackable() && (sw.getType() != SegmentType.VARIABLE) && (sw.getType() != SegmentType.CONDITION)
				&& (sw.getType() != SegmentType.ANSWER_CHOICE) && dropTarget.getInsidePanel().getWidgetCount() <1){
			dropTarget.addInsideContainer(sw ,context);
			flow.updateArrows(dropTarget.getArrowList());
		}

	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
		if (dropTarget.getWidget() != null) {
			dropTarget.setEngaged(true);
		}
	}

	@Override
	public void onLeave(DragContext context) {
		dropTarget.setEngaged(false);
		super.onLeave(context);
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		if (dropTarget.getWidget() == null || !dropTarget.isStackable()) {
			throw new VetoDragException();
		}
		super.onPreviewDrop(context);
	}

}
