package webEditor.flow.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;

import java.util.ArrayList;

/**
 * Stackable containers are the lifeblood of magnets.  This used to be a comment from r28,
 * but I honestly don't know enough about it in its current form to comment it to my liking currently.
 * 
 * I will be back to comment this, but I highly encourage anyone that has the time when they come accross 
 * this to redocument it.
 */
public class DropPoint extends FocusPanel {
	private AbsolutePanel primaryPanel = new AbsolutePanel();
	private AbsolutePanel insidePanel = new AbsolutePanel();
	
	private final PickupDragController dragController;
	private DropController dropController;

	private boolean stackable = true;
	private boolean isMain = false;
	private String containerID;
	
	String content = "";

	/**
	 * A stackable container for the overall code with main and such. This
	 * container is not draggable.
	 * 
	 * @param content
	 *            the HTML formatted string
	 * @param dc
	 *            the drag controller
	 * @param specialCondition
	 *            usually main
	 */
	public DropPoint(PickupDragController dc,
			int specialCondition, FlowUi flow) { // For mains, non draggable
		this.dragController = dc;
		this.dropController = new DropPointDropController(this, flow);
		add(primaryPanel);  // primaryPanel holds everything else, because the focusPanel can only hold one widget
		setStyleName("drop_point");
		switch(specialCondition){
		// isMain, stackable, dragController.makeDraggable(this)
			case 0: stackable = true; // 0 = dropPoint
			break;
			case 1: dragController.makeDraggable(this);
					stackable = true;
			break;
			default: System.err.println("Bad - you shouldn't be here!  DropPoint container constructor error.");
			break;
		}	
		primaryPanel.add(insidePanel);
	}
	
	public DropPoint(String content, PickupDragController dc,
			int specialCondition,  FlowUi flow) { // For mains, non draggable
		this.dragController = dc;
		this.dropController = new DropPointDropController(this, flow);
		add(primaryPanel);  // primaryPanel holds everything else, because the focusPanel can only hold one widget
		setStyleName("drop_point");
		switch(specialCondition){
		// isMain, stackable, dragController.makeDraggable(this)
			case 0: stackable= true; // 0 = dropPoint
			break;
			case 1: dragController.makeDraggable(this);
			break;
			default: System.err.println("Bad - you shouldn't be here!  DropPoint container constructor error.");
			break;
		}	
		primaryPanel.add(new Label(content));
		primaryPanel.add(insidePanel);
	}

	public void addInsideContainer(DropPoint dp) {
		insidePanel.add(dp);
	}

	public void addInsideContainer(DropPoint child, DragContext context) {
		if (insidePanel.getWidgetCount() > 0) {
			ArrayList<DropPoint> children = new ArrayList<DropPoint>();
			ArrayList<DropPoint> sortedChildren = new ArrayList<DropPoint>();
			for (int i = 0; i < insidePanel.getWidgetCount(); i++) {
				if(!child.equals(insidePanel.getWidget(i)))
					children.add((DropPoint) insidePanel.getWidget(i));
			}
			while (children.size() > 0) {
				int maxHeight = children.get(0).getAbsoluteTop();
				int maxHeightIndex = 0;
				for (int i = 0; i < children.size(); i++) {
					if (children.get(i).getAbsoluteTop() < maxHeight) {
						maxHeight = children.get(i).getAbsoluteTop();
						maxHeightIndex = i;
					}
				}
				sortedChildren.add(children.get(maxHeightIndex));
				children.remove(maxHeightIndex);
			}
			boolean done = false;
			for (int i = 0; i < sortedChildren.size(); i++) {
				if (sortedChildren.get(i).getAbsoluteTop() > context.mouseY
						&& !done) {
					sortedChildren.add(i, child);
					done = true;
				}
			}
			for (DropPoint sc : sortedChildren) {
				insidePanel.add(sc);
			}
			if (!done) { // If panel is being added to bottom.
				insidePanel.add(child);
			}

		} else {
			insidePanel.add(child);
		}
	}

	public void updateContent() {

	}
	
	public boolean hasChild(String childID){
		for (int i = 0; i < insidePanel.getWidgetCount(); i++) {
			if(insidePanel.getWidget(i) instanceof DropPoint && ((DropPoint)insidePanel.getWidget(i)).getID().equals(childID)){
				return true;
			}
		}
		return false;
	}
	
	public void setEngaged(boolean engaged) {
		if (engaged) {
			if (isMain) {
				setStyleName("drop_point");
			} else {
				if(!stackable){
					setStyleName("drop_point");
				} else{
					setStyleName("drop_point");
				}
			}
		} else {
			if (isMain) {
				setStyleName("drop_point");
			} else {
				setStyleName("drop_point");
			}
		}
	}

	public boolean isStackable() {
		return stackable;
	}

	public AbsolutePanel getInsidePanel() {
		return insidePanel;
	}
	public String getContent(){
		return content;
	}
	
	public int getLeft() {
		return this.getAbsoluteLeft();
	}
	
	public int getWidth() {
		return this.getOffsetWidth();
	}
	
	public int getTop() {
		return this.getAbsoluteTop();
	}
	
	public int getHeight() {
		return this.getOffsetHeight();
	}
	public String getID(){
		return containerID;
	}
	public void setID(String id){
		containerID = id;
	}
	public void setMain(boolean main){
		this.isMain = main;
	}
	public void setStackable(boolean stack){
		stackable = stack;
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		dragController.registerDropController(dropController);
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		dragController.unregisterDropController(dropController);
	}

}
