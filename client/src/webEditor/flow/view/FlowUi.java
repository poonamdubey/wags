package webEditor.flow.view;

import java.util.ArrayList;
import java.util.Stack;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Path;

import webEditor.magnet.view.StackableContainer;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FlowUi extends Composite {
	DrawingArea canvas;
	PickupDragController dc;
	private PopupPanel resetPopupPanel;
	static int dropPointID=0;
	int executeIndex = 0;

	@UiField LayoutPanel layout;
	@UiField AbsolutePanel canvasPanel;
	@UiField AbsolutePanel segmentsPanel;
	@UiField AbsolutePanel bottomPanel;
	@UiField Button restartButton;
	@UiField Button previousButton;
	@UiField Button nextButton;
	@UiField Button resetButton;
	
	// The arrowOrder list holds the strings specifying the source and destination DropPoints
	// for arrows. The arrowList holds the actual Path Vector Object in the same order.
	// Order is necessary so that arrows can be updated without redrawing the entire canvas.
	ArrayList<String> arrowOrder = new ArrayList<String>();
	ArrayList<Path> arrowList = new ArrayList<Path>();
	ArrayList<DropPoint> dropPoints = new ArrayList<DropPoint>();
	String dropPointCoords;
	
	// Limiting access to the stack so that it can reside in one place instead of having to
	// pass it around. Allowed actions are push, pop, clear, and size and must be called
	// using this class's methods.
	private Stack<ActionState> undoStack = new Stack<ActionState>();

	private static FlowUiUiBinder uiBinder = GWT
			.create(FlowUiUiBinder.class);

	interface FlowUiUiBinder extends UiBinder<Widget, FlowUi> {
	}
	
	public FlowUi(){
		initWidget(uiBinder.createAndBindUi(this));
		canvas = new DrawingArea(Window.getClientHeight(), (int)(Window.getClientWidth()*.6));
        canvasPanel.add(canvas);

        segmentsPanel.add(new DropPoint(SegmentType.SET, this));
        segmentsPanel.add(new DropPoint(SegmentType.MOD, this));
        segmentsPanel.add(new DropPoint(SegmentType.ADD, this));
        segmentsPanel.add(new DropPoint(SegmentType.DIVIDE, this));
        segmentsPanel.add(new DropPoint("Answer a",SegmentType.ANSWER_CHOICE, this));
        segmentsPanel.add(new DropPoint("var",SegmentType.VARIABLE, this));
        segmentsPanel.add(new DropPoint("A < B",SegmentType.CONDITION, this));
        segmentsPanel.add(new DropPoint("count",SegmentType.VARIABLE, this));
        
        // TODO figure out how to encode/where to keep which direction TRUE/FALSE leads to from a conditional box
        // A in from means Answer, C in front means Conditional.  Temporary for now...
		this.dropPointCoords = "200:10,200:120,200:300,200:500";
        initDropPoints(dropPointCoords);
		
        this.arrowOrder.add("0:1");
        this.arrowOrder.add("1:2");
        this.arrowOrder.add("1:3");
        this.arrowOrder.add("3:1");
        
		initArrows(arrowOrder);
		setupResetPopupPanel();
	}
	
	/*
	 * Handlers for the buttons 
	 * 
	 */
	@UiHandler("restartButton")
	void handleRestartClick(ClickEvent e) {
//		popupPanel.setPopupPosition(button.getAbsoluteLeft(),
//				button.getAbsoluteTop() - 80);
//		popupPanel.setVisible(true);
//		popupPanel.show();
	}
	
	@UiHandler("previousButton")
	void handlePreviousClick(ClickEvent e) {
		Window.alert("undoing?");
		popUndoStack().undo();
		executeIndex--;
	}
	
	@UiHandler("nextButton")
	void handleNextClick(ClickEvent e) {
		Window.alert("Next Hit");
		((DropPoint) dropPoints.get(this.executeIndex).getInsidePanel().getWidget(0)).doAction();
		pushUndoStack(((DropPoint) dropPoints.get(this.executeIndex++).getInsidePanel().getWidget(0)).getAction());
	}
	
	@UiHandler("resetButton")
	void handleResetClick(ClickEvent e) {
		resetPopupPanel.setPopupPosition(resetButton.getAbsoluteLeft() - 160,
				resetButton.getAbsoluteTop() - 80);
		resetPopupPanel.setVisible(true);
		resetPopupPanel.show();
	}
	
	private class yesResetHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			Window.alert("before resting");
			resetProblem();
			Window.alert("after resting");
			resetPopupPanel.setVisible(false);
		}
	}

	private class noResetHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			resetPopupPanel.setVisible(false);
		}
	}

	
	/**
	 * Adds DropPoints the list and draws them on the canvas
	 * @param locations A string of pixel positions in the format
	 * 					"x1:y1,x2:y2,x3:y3,..." where xN:yN represents the top left corner
	 */
	public void initDropPoints(final String locations) {
   		String[] locs = locations.split(",");
   		for(int i=0; i < locs.length; i++){
   			String[] coords = locs[i].split(":");
   			if(coords.length == 2){
   				dropPoints.add(new DropPoint(SegmentType.DROPPOINT,this));
   	   			canvasPanel.add(dropPoints.get(i),Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
   			}else if(coords[0].equals("C")){
            	dropPoints.add(new DropPoint(SegmentType.CONDITIONAL,this));
       			canvasPanel.add(dropPoints.get(i),Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
            }else if(coords[0].equals("A")){
            	dropPoints.add(new DropPoint("Answer: ",SegmentType.ANSWER,this));
       			canvasPanel.add(dropPoints.get(i),Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
            }
   			dropPoints.get(i).setID(getNextDropPointID());

   		}
	}
	
	/**
	 * Should only be called for initializing the arrows or redrawing all arrows.
	 * Called after all of the DropPoints have been added to their list.
	 * @param order List of strings formatted "source:dest"
	 */
	public void initArrows(final ArrayList<String> order) {
        Timer timer = new Timer() {
            @Override
            public void run() {
        		for(int i = 0; i < order.size(); i++) {
        			String[] items = order.get(i).split(":");
        			int src = Integer.parseInt(items[0]);
        			int dst = Integer.parseInt(items[1]);
        			drawArrow(src, dst);
        			dropPoints.get(dst).addArrowToList(order.get(i));	// each DropPoint holds a list of
        			dropPoints.get(src).addArrowToList(order.get(i));	// all arrows that point to and from it
        		}
            }
        };

        timer.schedule(100);
	}
	
	/**
	 * Draws a single arrow from one DropPoint to another.
	 * 
	 * TODO make the arrow shaft hit the base of the arrow head, not the tip
	 * 
	 * @param source Index of the source DropPoint of the arrow
	 * @param dest Index of the destination DropPoint of the arrow
	 */
	public void drawArrow(int source, int dest) {
		DropPoint sc1 = dropPoints.get(source);
		DropPoint sc2 = dropPoints.get(dest);
		Path arrow;
		if(sc1.getAbsoluteTop() < sc2.getAbsoluteTop()) { // item 1 above item 2
			arrow = new Path((int)(sc1.getAbsoluteLeft() + (.5*sc1.getOffsetWidth())), sc1.getAbsoluteTop());
			arrow.lineTo((int)(sc2.getAbsoluteLeft() + (.5*sc2.getOffsetWidth())), (int) (sc2.getAbsoluteTop())-45); // 45 just seems right for putting
			arrow.moveTo((int)(sc2.getAbsoluteLeft() + (.5*sc2.getOffsetWidth())), (int) (sc2.getAbsoluteTop())-45); // the arrow head in the right spot
			arrow.lineRelativelyTo(0, 17);
			arrow = addArrowHead(Direction.SOUTH, arrow);
		} else if (sc1.getAbsoluteTop() == sc2.getAbsoluteTop()) { // same y position
			arrow = new Path((int)(sc1.getAbsoluteLeft()+(.5*sc1.getOffsetWidth())),sc1.getAbsoluteTop()+(int)(.5*sc1.getOffsetHeight())-15);
			arrow.lineTo((int)(sc2.getAbsoluteLeft()), sc2.getAbsoluteTop()+ (int)(.5*sc2.getOffsetHeight())-15);
			arrow.moveTo((int)(sc2.getAbsoluteLeft()), sc2.getAbsoluteTop()+ (int)(.5*sc2.getOffsetHeight())-15);
			arrow = addArrowHead(Direction.EAST, arrow);
		} else if (source == dest){  // same item, do a loop, also this will never be true, == doesn't work for objects
			// TODO make the line go from the bottom to left side
			arrow = new Path(0,0);
		} else{ // item 1 below item 2
			arrow = drawUpAndOutArrow(sc1, sc2);
		}

		arrow.setFillOpacity(0.0);
		arrow.setStrokeWidth(2);	// Thicker arrows
		arrow.setStrokeColor("darkblue");
		arrowList.add(arrowOrder.indexOf(source + ":" + dest), arrow);
		canvas.add(arrow);

	}
	
	/**
	 * Takes a PATH and appends an arrow head to the end of it depending on
	 * which direction the user specifies that the arrow head should face
	 * @param dir The direction the arrow tip should face
	 * @param arrow The arrow Path to append the tip onto
	 * @return The entire arrow's Path
	 */
	private Path addArrowHead(Direction dir, Path arrow) {
		switch(dir) {
		case EAST:
			arrow.lineRelativelyTo(-17, -15);
			arrow.lineRelativelyTo(0, 30);
			arrow.lineRelativelyTo(17, -15);
			break;
		case NORTH:
			arrow.lineRelativelyTo(15, 17);
			arrow.lineRelativelyTo(-30, 0);
			arrow.lineRelativelyTo(15, -17);
			break;
		case WEST:
			arrow.lineRelativelyTo(17, 15);
			arrow.lineRelativelyTo(0, -30);
			arrow.lineRelativelyTo(-17, 15);
			break;
		case NORTHEAST:
			arrow.lineRelativelyTo(0, 25);
			arrow.lineRelativelyTo(-25, -25);
			arrow.lineRelativelyTo(25, 0);
			break;
		case NORTHWEST:
			arrow.lineRelativelyTo(0, 25);
			arrow.lineRelativelyTo(25, -25);
			arrow.lineRelativelyTo(-25, 0);
			break;
		case SOUTHEAST:
			arrow.lineRelativelyTo(0, -25);
			arrow.lineRelativelyTo(-25, 25);
			arrow.lineRelativelyTo(25, 0);
			break;
		case SOUTHWEST:
			arrow.lineRelativelyTo(0, -25);
			arrow.lineRelativelyTo(25, 25);
			arrow.lineRelativelyTo(-25, 0);
			break;
		case SOUTH: default:
			arrow.lineRelativelyTo(15, -17);
			arrow.lineRelativelyTo(-30, 0);
			arrow.lineRelativelyTo(15, 17);
			break;
		}
	
		return arrow;
	}
	
	private Path drawUpAndOutArrow(DropPoint source, DropPoint dest) {
		int X_OFFSET = 25;
		int sX = source.getAbsoluteLeft();
		int sY = source.getAbsoluteTop()+ (int)(.5*source.getOffsetHeight())-15;
		int dX = dest.getAbsoluteLeft();
		int dY = dest.getAbsoluteTop()+ (int)(.5*dest.getOffsetHeight())-30;
		
		int srcDstOffsetX = sX - dX;
		int srcDstOffsetY = sY - dY;
		
		Path arrow = new Path(sX, sY);
		
		if (srcDstOffsetX >= 0) { // destination is to the left of source
			arrow.lineRelativelyTo(-srcDstOffsetX - X_OFFSET, 0);
			arrow.lineRelativelyTo(0, -srcDstOffsetY); // destination is above source
			arrow.lineRelativelyTo(X_OFFSET, 0);
			addArrowHead(Direction.EAST, arrow);
		} else { // destination is to the right of source
			arrow.lineRelativelyTo((int)dest.getOffsetWidth() - srcDstOffsetX + X_OFFSET, 0);
			arrow.lineRelativelyTo(0, -srcDstOffsetY); // destination is above source
			arrow.lineRelativelyTo(-X_OFFSET, 0);
			addArrowHead(Direction.WEST, arrow);
		}

		return arrow;
	}
	
	/**
	 * Draws a curved arrow.
	 * @param start Source DropPoint
	 * @param dest Destination DropPoint
	 * @return The entire arrow
	 */
	private Path drawBezierCurve(DropPoint start, DropPoint dest){
		Path arrow;
		// the 6 arguments needed to make the bezier curve
		int sCPX; // start control point X-position
		int sCPY; // start control point Y-position
		int dCPX; // destination control point X-position
		int dCPY; // destination control point Y-position
		int dX = dest.getAbsoluteLeft(); // destination X-position
		int dY = dest.getAbsoluteTop() + dest.getOffsetHeight()-30; // destination Y-position
		
		int sX = start.getAbsoluteLeft();
		int sY = start.getAbsoluteTop()+ (int)(.5*start.getOffsetHeight())-15;
		if (sX <= dX) {	// dest is to the right of start
			arrow = new Path(sX,sY);
			
			sCPX = sX - (30 + ((sX-dX)/4));
			sCPY = sY; 
			
			dCPX = dX - (30 + ((sX-dX)/4));
			dCPY = dY; 
			
			arrow.curveTo(sCPX, sCPY, dCPX, dCPY, sX, sY);
			addArrowHead(Direction.NORTHEAST, arrow);
		} else { // dest is to the left of start
			sX = start.getAbsoluteLeft() + start.getOffsetWidth(); // set start X-position to right side of start element
			dX = dest.getAbsoluteLeft() + dest.getOffsetWidth();   // set dest X-position to right side of dest element
			
			arrow = new Path(sX,sY);
			
			sCPX = sX + (30 + ((dX-sX)/4));
			sCPY = sY; 
			
			dCPX = dX + (30+ ((dX-sX)/4));
			dCPY = dY; 
			
			arrow.curveTo(sCPX, sCPY, dCPX, dCPY, sX, sY);
			addArrowHead(Direction.NORTHWEST, arrow);	
		}
		
	return arrow;
	}

	/**
	 * Removes the specified arrows from the canvas and redraws them.
	 * arrowOrder and arrowList need to stay in sync so that the proper Path can
	 * be updated.
	 * @param srcDst The list of DropPoints
	 */
	public void updateArrows(ArrayList<String> srcDst) {
		String[] tmp;
		for (int i = 0; i < srcDst.size(); i++) {
			tmp = srcDst.get(i).split(":");
			int source = Integer.parseInt(tmp[0]);
			int dest = Integer.parseInt(tmp[1]);
			canvas.remove(arrowList.remove(arrowOrder.indexOf(srcDst.get(i))));
			drawArrow(source, dest);
		}
	}
	/*
	 * Building UI elements
	 * 
	 */
	public void setupResetPopupPanel() {
		resetPopupPanel = new PopupPanel(true);
		VerticalPanel vPanel = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		Label pLabel = new Label("Are you sure you wish to reset the problem?");
		Button yesButton = new Button("Yes", new yesResetHandler());
		yesButton.addStyleName("big_popup_button");
		Button noButton = new Button("No", new noResetHandler());
		noButton.addStyleName("big_popup_button");
		hPanel.add(yesButton);
		hPanel.add(noButton);
		hPanel.setCellWidth(yesButton, "128px");
		hPanel.setCellHeight(yesButton, "50px");
		hPanel.setCellWidth(noButton, "128px");
		hPanel.setCellHeight(noButton, "50px");
		vPanel.add(pLabel);
		vPanel.add(hPanel);
		resetPopupPanel.add(vPanel);
	}
	
	public void resetProblem(){
		for(DropPoint dp: dropPoints){
			dp.resetDropPoint();
		}
		redrawArrows();
	}
	/**
	 * Clears the canvas and redraws all arrows on the screen.
	 */
	public void redrawArrows(){
		canvas.clear();
		initArrows(arrowOrder);
	}

	public void addNewArrow(){}
	
	public void pushUndoStack(ActionState state) {
		undoStack.push(state);
	}
	public ActionState popUndoStack() {
		return undoStack.pop();
	}
	public void clearUndoStack() {
		undoStack.clear();
	}
	public int sizeUndoStack() {
		return undoStack.size();
	}
	public int getNextDropPointID(){
		return dropPointID++;
	}
	
}
