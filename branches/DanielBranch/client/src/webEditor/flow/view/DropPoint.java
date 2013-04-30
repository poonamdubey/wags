package webEditor.flow.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.DropController;

import webEditor.magnet.view.DragController;

import java.util.ArrayList;
import java.util.Stack;

public class DropPoint extends FocusPanel {
	
	private FlowUi flow;
	
	private AbsolutePanel primaryPanel = new AbsolutePanel();
	private AbsolutePanel insidePanel = new AbsolutePanel();
	
	private DropController dropController;

	private boolean stackable = false;
	private boolean isMain = false;
	private int containerID;
	private SegmentType type;
	private TextBox valueBox;
	private DropPoint insideDropPoint;
	private ArrayList<String> arrows = new ArrayList<String>();
	
	String content = "";
	
	ActionState action;
	

	public DropPoint(SegmentType segmentType, FlowUi flow) { // For mains, non draggable
		this.type = segmentType;
		this.flow = flow;
		add(primaryPanel);  // primaryPanel holds everything else, because the focusPanel can only hold one widget
		HorizontalPanel horPanel = new HorizontalPanel();
		switch(segmentType){
			case INSIDE_DROPPOINT:  setStyleName( "inside_droppoint");
									this.dropController = new InsideDropPointDropController(this,flow);
									stackable = true;
									break;
			case DROPPOINT:         setStyleName( "droppoint");
									this.dropController = new DropPointDropController(this,flow);
									stackable = true;
									break;
			case CONDITIONAL:		setStyleName( "conditional");
									this.dropController = new InsideDropPointDropController(this,flow);
									stackable = true;
									action = new ConditionalAction(this);
									break;
			case ADD:				setStyleName( "droppoint");
									this.dropController = new DropPointDropController(this,flow);
									DragController.INSTANCE.makeDraggable(this);
									stackable = false;
									horPanel.add(new Label("add"));
									valueBox = new TextBox();
									horPanel.add(valueBox);
									horPanel.add(new Label("to"));
									insideDropPoint = new DropPoint(SegmentType.INSIDE_DROPPOINT,flow);
									horPanel.add(insideDropPoint);
									action = new AddAction(this);
									break;
			case SUBTRACT:			this.dropController = new DropPointDropController(this,flow);
									setStyleName( "droppoint");
									DragController.INSTANCE.makeDraggable(this);
									stackable = false;
									horPanel.add(new Label("subtract"));
									valueBox = new TextBox();
									horPanel.add(valueBox);
									horPanel.add(new Label("to"));
									insideDropPoint = new DropPoint(SegmentType.INSIDE_DROPPOINT,flow);
									horPanel.add(insideDropPoint);
									action = new SubstractAction(this);
									break;
			case SET:       		this.dropController = new DropPointDropController(this,flow);
									setStyleName( "droppoint");
									DragController.INSTANCE.makeDraggable(this);
									stackable = false;
									horPanel.add(new Label("set"));
									insideDropPoint = new DropPoint(SegmentType.INSIDE_DROPPOINT,flow);
									horPanel.add(insideDropPoint);
									horPanel.add(new Label("to"));
									valueBox = new TextBox();
									horPanel.add(valueBox);
									action = new SetAction(this);
									break;
			case DIVIDE:       		this.dropController = new DropPointDropController(this,flow);
									setStyleName( "droppoint");
									DragController.INSTANCE.makeDraggable(this);
									stackable = false;
									horPanel.add(new Label("divide"));
									insideDropPoint = new DropPoint(SegmentType.INSIDE_DROPPOINT,flow);
									horPanel.add(insideDropPoint);
									horPanel.add(new Label("by"));
									valueBox = new TextBox();
									horPanel.add(valueBox);
									action = new DivideAction(this);
									break;
			case MULTIPLY:      	this.dropController = new DropPointDropController(this,flow);
									setStyleName( "droppoint");
									DragController.INSTANCE.makeDraggable(this);
									stackable = false;
									horPanel.add(new Label("multiply"));
									insideDropPoint = new DropPoint(SegmentType.INSIDE_DROPPOINT,flow);
									horPanel.add(insideDropPoint);
									horPanel.add(new Label("by"));
									valueBox = new TextBox();
									horPanel.add(valueBox);
									action = new MultiplyAction(this);
									break;	
			case MOD:      			this.dropController = new DropPointDropController(this,flow);
									setStyleName( "droppoint");
									DragController.INSTANCE.makeDraggable(this);
									stackable = false;
									horPanel.add(new Label("mod"));
									insideDropPoint = new DropPoint(SegmentType.INSIDE_DROPPOINT,flow);
									horPanel.add(insideDropPoint);
									horPanel.add(new Label("by"));
									valueBox = new TextBox();
									horPanel.add(valueBox);
									action = new ModAction(this);
									break;
			default:				setStyleName( "droppoint");
									this.dropController = new DropPointDropController(this,flow);
									stackable = true;
									break;
		}
		if(horPanel.getWidgetCount() !=0){
			insidePanel.add(horPanel);
		}
		primaryPanel.add(insidePanel);
	}
	
	public DropPoint(String content, SegmentType segmentType,  FlowUi flow) { 
		this.content = content;
		this.type = segmentType;
		add(primaryPanel);  // primaryPanel holds everything else, because the focusPanel can only hold one widget
		primaryPanel.add(insidePanel);
		HorizontalPanel horPanel = new HorizontalPanel();;
		switch(segmentType){
		case VARIABLE:     	    setStyleName( "inside_droppoint");
								this.dropController = new DropPointDropController(this,flow);
								DragController.INSTANCE.makeDraggable(this);
						 		stackable = false;
						 		horPanel.add(new Label(this.content));
						 		break;
		case CONDITION:			setStyleName( "inside_droppoint");
		 						DragController.INSTANCE.makeDraggable(this);
		 						this.dropController = new DropPointDropController(this,flow);
		 						stackable = false;
		 						horPanel.add(new Label(this.content));
		 						break;
		case ANSWER:   			setStyleName( "answer");
								this.dropController = new DropPointDropController(this,flow);
								stackable = false;
								horPanel.add(new Label(content));
								insideDropPoint = new DropPoint(SegmentType.INSIDE_DROPPOINT,flow);
								horPanel.add(insideDropPoint);
							    break;
		case ANSWER_CHOICE:     setStyleName( "inside_droppoint");
							  	stackable = false;
							  	DragController.INSTANCE.makeDraggable(this);
							  	this.dropController = new DropPointDropController(this,flow);
							  	horPanel.add(new Label(this.content));
							  	break;
		default: 				setStyleName( "inside_droppoint");
								DragController.INSTANCE.makeDraggable(this);
								this.dropController = new DropPointDropController(this,flow);
								stackable = false;
								horPanel.add(new Label(this.content));
								break;
		}
		if(horPanel.getWidgetCount() != 0){
			insidePanel.add(horPanel);
		}
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
	
	public boolean hasChild(int childID){
		for (int i = 0; i < insidePanel.getWidgetCount(); i++) {
			if(insidePanel.getWidget(i) instanceof DropPoint && ((DropPoint)insidePanel.getWidget(i)).getID() == (childID)){
				return true;
			}
		}
		return false;
	}
	
	public void setEngaged(boolean engaged) {
		if (engaged) {
			if (isMain) {
				setStyleName("droppoint");
			} else {
				if(!stackable){
					setStyleName("droppoint");
				} else{
					setStyleName("droppoint");
				}
			}
		} else {
			if (isMain) {
				setStyleName("droppoint");
			} else {
				setStyleName("droppoint");
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
	public int getID(){
		return containerID;
	}
	public void setID(int id){
		containerID = id;
	}
	public void setMain(boolean main){
		this.isMain = main;
	}
	public void setStackable(boolean stack){
		stackable = stack;
	}
	public SegmentType getType(){
		return type;
	}
	public void addArrowToList(String arrow) {
		arrows.add(arrow);
	}
	public ArrayList<String> getArrowList() {
		return arrows;
	}
	
	public void resetDropPoint(){
		if(this.type == SegmentType.CONDITIONAL){
			if(insidePanel.getWidgetCount() > 0){
				flow.segmentsPanel.add(insidePanel.getWidget(0));
			}
		} else if(this.type == SegmentType.DROPPOINT){
			if(insidePanel.getWidgetCount() > 0){
				flow.segmentsPanel.add(insidePanel.getWidget(0));
			}	
		} else if(this.type == SegmentType.ANSWER){ // insidePanel->horPanel(content,inside_droppoint)
			if(((DropPoint)((HorizontalPanel)insidePanel.getWidget(0)).getWidget(1)).getInsidePanel().getWidgetCount() >0){
				flow.segmentsPanel.add(((DropPoint)((HorizontalPanel)insidePanel.getWidget(0)).getWidget(1)).getInsidePanel().getWidget(0));
			}
		}
	}
	@Override
	protected void onLoad() {
		super.onLoad();
		DragController.INSTANCE.registerDropController(dropController);
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		DragController.INSTANCE.unregisterDropController(dropController);
	}
	public DropPoint getCopy(){
		return new DropPoint(this.content,this.type,this.flow);
	}
	
	public void doAction(){
		Window.alert("Executing Action");
		action.execute();
	}

	public ActionState getAction(){
		return action;
	}
	public int getBoxValue() {
		int value = 0;
		try{
			value = Integer.parseInt(valueBox.getText());
		}
		catch(NumberFormatException ex){
			Window.alert("Contents of TextBox wasn't a number!");
		}
		return value;
	}

	public String getInsideContent() {
		return ((DropPoint)insideDropPoint.getInsidePanel().getWidget(0)).getContent();
	}
}
