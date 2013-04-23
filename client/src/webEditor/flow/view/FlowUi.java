package webEditor.flow.view;

import java.util.ArrayList;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Path;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class FlowUi extends Composite {
	DrawingArea canvas;
	PickupDragController dc;
	
	@UiField LayoutPanel layout;
	@UiField AbsolutePanel canvasPanel;
	@UiField AbsolutePanel segmentsPanel;
	
	ArrayList<String> arrowOrder = new ArrayList<String>();
	ArrayList<DropPoint> dropPoints = new ArrayList<DropPoint>();
	ArrayList<Path> arrowList = new ArrayList<Path>();
	
	private static FlowUiUiBinder uiBinder = GWT
			.create(FlowUiUiBinder.class);

	interface FlowUiUiBinder extends UiBinder<Widget, FlowUi> {
	}
	
	public FlowUi(){
		initWidget(uiBinder.createAndBindUi(this));
		canvas = new DrawingArea(Window.getClientHeight(), (int)(Window.getClientWidth()*.6));
        canvasPanel.add(canvas);
		Window.alert(Window.getClientHeight()+" : "+canvasPanel.getOffsetWidth());
        
        this.arrowOrder.add("0:1");
        this.arrowOrder.add("1:2");
        this.arrowOrder.add("2:3");
        this.arrowOrder.add("3:2");
        this.arrowOrder.add("3:0");
        this.arrowOrder.add("3:4");
        this.arrowOrder.add("5:0");
        this.arrowOrder.add("5:4");
        this.arrowOrder.add("4:6");

        segmentsPanel.add(new DropPoint(SegmentType.SET, this));
        segmentsPanel.add(new DropPoint(SegmentType.MOD, this));
        segmentsPanel.add(new DropPoint(SegmentType.ADD, this));
        segmentsPanel.add(new DropPoint(SegmentType.DIVIDE, this));
        segmentsPanel.add(new DropPoint("Answer a",SegmentType.ANSWER_CHOICE, this));
        segmentsPanel.add(new DropPoint("var",SegmentType.VARIABLE, this));
        segmentsPanel.add(new DropPoint("A < B",SegmentType.CONDITION, this));
        segmentsPanel.add(new DropPoint(SegmentType.CONDITIONAL, this));
        segmentsPanel.add(new DropPoint("count",SegmentType.VARIABLE, this));
        
		addDropPoints("50:50,20:200,350:200,500:300,400:50,50:300,50:500"); // Last one is box for Answer
		addArrows(arrowOrder);
	}
	
	public void addDropPoints(final String locations){
   		String[] locs = locations.split(",");
   		for(int i=0; i < locs.length; i++){
   			String[] coords = locs[i].split(":");
            if(i != locs.length-1){
            	dropPoints.add(new DropPoint(SegmentType.DROPPOINT,this));
            } else{
            	dropPoints.add(new DropPoint("Answer",SegmentType.ANSWER,this));
            }
   			canvasPanel.add(dropPoints.get(i),Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
   		}
	}
	
	public void addArrows(final ArrayList<String> order){
        Timer timer = new Timer() {
            @Override
            public void run() {
        		for(int i = 0; i < order.size(); i++){
        			String[] items = order.get(i).split(":");
        			int src = Integer.parseInt(items[0]);
        			drawArrow(src, Integer.parseInt(items[1]));
        			dropPoints.get(src).addArrowToList(order.get(i));
        		} 	
            }
        };

        timer.schedule(100);
	}
	
	/**
	 * This will draw the entire arrow so that we can keep a list of them and only remove and redraw
	 * a single arrow when things are stacked in the DropPoints
	 * @param source Start DropPoint of the arrow
	 * @param dest End DropPoint of the arrow
	 */
	public void drawArrow(int source, int dest) {
		DropPoint sc1 = dropPoints.get(source);
		DropPoint sc2 = dropPoints.get(dest);
		Path arrow;
		if(sc1.getAbsoluteTop() < sc2.getAbsoluteTop()) { // item 1 above item 2
			arrow = new Path((int)(sc1.getAbsoluteLeft() + (.5*sc1.getOffsetWidth())), sc1.getAbsoluteTop());
			arrow.lineTo((int)(sc2.getAbsoluteLeft() + (.5*sc2.getOffsetWidth())), sc2.getAbsoluteTop()- 25);
			arrow.moveTo((int)(sc2.getAbsoluteLeft() + (.5*sc2.getOffsetWidth())), sc2.getAbsoluteTop()- 25);
			arrow = addArrowTip(Direction.SOUTH, arrow);
		} else if (sc1.getAbsoluteTop() == sc2.getAbsoluteTop()) { // same y position
			arrow = new Path((int)(sc1.getAbsoluteLeft()+(.5*sc1.getOffsetWidth())),sc1.getAbsoluteTop()+(int)(.5*sc1.getOffsetHeight())-15);
			arrow.lineTo((int)(sc2.getAbsoluteLeft()), sc2.getAbsoluteTop()+ (int)(.5*sc2.getOffsetHeight())-15);
			arrow.moveTo((int)(sc2.getAbsoluteLeft()), sc2.getAbsoluteTop()+ (int)(.5*sc2.getOffsetHeight())-15);
			arrow = addArrowTip(Direction.EAST, arrow);
		} else if (source == dest){  // same item, do a loop
			arrow = new Path(0,0);
		} else{ // item 1 below item 2
			arrow = drawBezierCurve(sc1,sc2);
		}

	//	Window.alert("added arrow from ("+sc1.getAbsoluteLeft()+","+sc1.getAbsoluteTop()+") to ("+sc2.getAbsoluteLeft()+","+sc2.getAbsoluteTop()+")");
		arrow.setFillOpacity(0.0);
		arrow.setStrokeWidth(2);	// Thicker arrows
		arrow.setStrokeColor("darkblue");
		arrowList.add(arrow);
		canvas.add(arrow);

	}
	
	private Path addArrowTip(Direction dir, Path arrow) {
		switch(dir) {
		case EAST:
			arrow.lineRelativelyTo(-17, -15);
			arrow.lineRelativelyTo(0, 30);
			arrow.close();
			break;
		case NORTH:
			arrow.lineRelativelyTo(15, 17);
			arrow.lineRelativelyTo(-30, 0);
			arrow.close();
			break;
		case WEST:
			arrow.lineRelativelyTo(17, 15);
			arrow.lineRelativelyTo(0, -25);
			arrow.close();
			break;
		case NORTHEAST:
			arrow.lineRelativelyTo(0, 25);
			arrow.lineRelativelyTo(-25, -25);
			arrow.close();
			break;
		case NORTHWEST:
			arrow.lineRelativelyTo(0, 25);
			arrow.lineRelativelyTo(25, -25);
			arrow.close();
			break;
		case SOUTHEAST:
			arrow.lineRelativelyTo(0, -25);
			arrow.lineRelativelyTo(-25, 25);
			arrow.close();
			break;
		case SOUTHWEST:
			arrow.lineRelativelyTo(0, -25);
			arrow.lineRelativelyTo(25, 25);
			arrow.close();
			break;
		case SOUTH: default:
			arrow.lineRelativelyTo(15, -17);
			arrow.lineRelativelyTo(-30, 0);
			arrow.close();
			break;
		}
		
		return arrow;
	}
	
	public Path drawBezierCurve(DropPoint start, DropPoint dest){
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
		if (sX >= dX) { // | or \ type relation, stay left side
			arrow = new Path(sX,sY);
			
			sCPX = sX - (30 + ((sX-dX)/4));
			sCPY = sY; 
			
			dCPX = dX - (30 + ((sX-dX)/4));
			dCPY = dY; 
			addArrowTip(Direction.SOUTHEAST, arrow);
		} else { // / type relation, go right side
			sX = start.getAbsoluteLeft() + start.getOffsetWidth(); // set start X-position to right side of start element
			dX = dest.getAbsoluteLeft() + dest.getOffsetWidth();   // set dest X-position to right side of dest element
			
			arrow = new Path(sX,sY);
			
			sCPX = sX + (30 + ((dX-sX)/4));
			sCPY = sY; 
			
			dCPX = dX + (30+ ((dX-sX)/4));
			dCPY = dY; 
			
			addArrowTip(Direction.SOUTHWEST, arrow);	
		}
		
		arrow.curveTo(sCPX, sCPY, dCPX, dCPY, dX, dY);
	return arrow;
	}
	
	/**
	 * Removes the arrow going from one DropPoint to another from both the list of drawn arrows
	 * and the list containing the strings specifying which DropPoints are currently being
	 * pointed to.
	 *
	public void updateArrows(ArrayList<String> srcDst) {
		for (String arrow : srcDst) {
			String[] tmp = arrow.split(":");
			int source = Integer.parseInt(tmp[0]);
			int dest = Integer.parseInt(tmp[1]);
			canvas.remove(arrowList.remove(arrowOrder.indexOf(srcDst)));
			drawArrow(source, dest);
		}
	}
	*/
	public void redrawArrows(){
		canvas.clear();
		addArrows(arrowOrder);
	}

}
