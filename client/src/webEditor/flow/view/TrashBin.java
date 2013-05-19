package webEditor.flow.view;
/**
 * Standard trash bin behavior, eats any DropPoint that is dropped into its drop area
 */

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * Panel that removes DropPoints that are dragged onto it.
 */
final class TrashBin extends AbsolutePanel {
	FlowUi flow;
	private static final String CSS_TRASHBIN = "trash_bin";
	private static final String CSS_TRASHBIN_ENGAGE = "trash_bin-engage";

	public TrashBin(FlowUi flow) {
		this.flow = flow;
		HTML text = new HTML("<b>Trash Bin</b>");
		text.setStyleName("trash_label");
		add(text);
		setStyleName(CSS_TRASHBIN);
	}

	public void eatWidget(DropPoint dp) {
		if(dp.getParent().equals(flow.segmentsPanel) || dp.getType() == SegmentType.ANSWER_CHOICE){
			flow.addToSegmentsPanel(dp);
		}else{
			dp.removeFromParent();
		}
		flow.redrawArrows();
	}

	public boolean isWidgetEater() {
		return true;
	}

	public void setEngaged(boolean engaged) {
		if (engaged) {
			setStyleName(CSS_TRASHBIN_ENGAGE);
		} else {
			setStyleName(CSS_TRASHBIN);
		}
	}
	
	public AbsolutePanel getFlowSegmentPanel(){
		return flow.segmentsPanel;
	}

}