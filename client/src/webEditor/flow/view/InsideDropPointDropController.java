package webEditor.flow.view;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.Window;
public class InsideDropPointDropController extends SimpleDropController {

	private final DropPoint dropTarget;
	private final FlowUi flow;

	public InsideDropPointDropController(DropPoint dropTarget, FlowUi flow) {
		super(dropTarget);
		this.dropTarget = dropTarget;
		this.flow = flow;
	}

	@Override
	public void onDrop(DragContext context) {
		SegmentType dtType = dropTarget.getType();             // dropTarget type
		SegmentType swType = ((DropPoint) context.selectedWidgets.get(0)).getType();     // type of widget being dropped
//		Window.alert(dropTarget.toString()+" | "+dropTarget.getParent().toString()+ " | "+dropTarget.getParent().getParent().toString()+ " | "+dropTarget.getParent().getParent().getParent().toString()+ " | "+dropTarget.getParent().getParent().getParent().getParent().toString());
		if(dropTarget.getInsidePanel().getWidgetCount() == 0){
//			Window.alert("On Drop: "+dropTarget.getType()+" "+((DropPoint) context.selectedWidgets.get(0)).getType());
			if( dtType == SegmentType.CONDITIONAL){
				if(swType == SegmentType.CONDITION){
	//				Window.alert("adding as condition");
					DropPoint dp = ((DropPoint)context.selectedWidgets.get(0)).getCopy();
					dropTarget.addInsideContainer(dp);
				}
			} else if((((DropPoint)dropTarget.getParent().getParent().getParent().getParent()).getType() == SegmentType.ANSWER) && swType == SegmentType.ANSWER_CHOICE){
//				Window.alert("Answer Parent!!");
				dropTarget.addInsideContainer((DropPoint)context.selectedWidgets.get(0));
			} else if(swType == SegmentType.VARIABLE || swType == SegmentType.MASTER_VARIABLE){
		//		Window.alert("adding as var");
				//TODO only do a copy if it's going from segmentsPanel to canvasPanel
				if(!(((DropPoint) dropTarget.getParent().getParent().getParent().getParent()).getType() == SegmentType.ANSWER)){  // making sure it's not an Answer droppoint.
					DropPoint dp = ((DropPoint)context.selectedWidgets.get(0)).getCopy();
					dropTarget.addInsideContainer(dp);
				}
			}
		}
		flow.redrawArrows();
	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
		if (dropTarget.getWidget() != null) {
			dropTarget.addStyleName("engaged");
		}
	}

	@Override
	public void onLeave(DragContext context) {
		dropTarget.removeStyleName("engaged");
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
