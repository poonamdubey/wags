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
	public String arrowString;
	
	@UiField LayoutPanel layout;
	@UiField AbsolutePanel canvasPanel;
	@UiField AbsolutePanel segmentsPanel;
	
	ArrayList<DropPoint> dropPoints = new ArrayList<DropPoint>();
	
	private static FlowUiUiBinder uiBinder = GWT
			.create(FlowUiUiBinder.class);

	interface FlowUiUiBinder extends UiBinder<Widget, FlowUi> {
	}
	
	public FlowUi(){
		initWidget(uiBinder.createAndBindUi(this));
		canvas = new DrawingArea(Window.getClientHeight(), (int)(Window.getClientWidth()*.6));
        canvasPanel.add(canvas);
		Window.alert(Window.getClientHeight()+" : "+canvasPanel.getOffsetWidth());
        
        this.arrowString = "0:1,1:2,2:3,3:2,3:0,3:4,5:0,5:4,4:6";
   
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
		addArrows(arrowString);
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
	
	public void addArrows(final String order){
        Timer timer = new Timer() {
            @Override
            public void run() {
        		String[] arrows = order.split(",");
        		for(String arrow: arrows){
        			String[] items = arrow.split(":");
        			drawArrow(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
        		} 	
            }
        };

        timer.schedule(100);
	}
	
	public void drawArrow(int item1, int item2){
		DropPoint sc1 = dropPoints.get(item1);
		DropPoint sc2 = dropPoints.get(item2);
		Path arrow;
		if(sc1.getAbsoluteTop() < sc2.getAbsoluteTop()){ // item 1 above item 2
			arrow = new Path((int)(sc1.getAbsoluteLeft()+(.5*sc1.getOffsetWidth())),sc1.getAbsoluteTop());
			arrow.lineTo((int)(sc2.getAbsoluteLeft()+(.5*sc2.getOffsetWidth())), sc2.getAbsoluteTop()- 25);
			drawArrowTip((int)(sc2.getAbsoluteLeft()+(.5*sc2.getOffsetWidth())), sc2.getAbsoluteTop()- 25,0);
		} else if (sc1.getAbsoluteTop() == sc2.getAbsoluteTop()) { // same y position
			arrow = new Path((int)(sc1.getAbsoluteLeft()+(.5*sc1.getOffsetWidth())),sc1.getAbsoluteTop()+(int)(.5*sc1.getOffsetHeight())-15);
			arrow.lineTo((int)(sc2.getAbsoluteLeft()), sc2.getAbsoluteTop()+ (int)(.5*sc2.getOffsetHeight())-15);
			drawArrowTip((int)(sc2.getAbsoluteLeft()), sc2.getAbsoluteTop()+ (int)(.5*sc2.getOffsetHeight())-15,1);
		} else if (item1 == item2){  // same item, do a loop
			arrow = new Path(0,0);
		} else{ // item 1 below item 2
			arrow = drawBezierCurve(sc1,sc2);
		}

	//	Window.alert("added arrow from ("+sc1.getAbsoluteLeft()+","+sc1.getAbsoluteTop()+") to ("+sc2.getAbsoluteLeft()+","+sc2.getAbsoluteTop()+")");
		arrow.setFillOpacity(0.0);
		canvas.add(arrow);
	}
	
	public void drawArrowTip(int x, int y, int dir){ // S = 0, E = 1, NE = 2, NW = 3 
		Path arrowTip;
		switch(dir){
			case 1: arrowTip = new Path(x-20,y-20);
					arrowTip.lineTo(x, y);
					arrowTip.lineTo(x-20,y+20);
			break;
			case 2: arrowTip = new Path(x-20,y);
					arrowTip.lineTo(x,y);
					arrowTip.lineTo(x, y+20);
			break;
			case 3: arrowTip = new Path(x+20,y);
					arrowTip.lineTo(x,y);
					arrowTip.lineTo(x, y+20);
			break;
			case 0: 
		    default: arrowTip = new Path(x-20,y-20);
					 arrowTip.lineTo(x, y);
					 arrowTip.lineTo(x+20, y-20);
			break;
		}
		arrowTip.setFillOpacity(0.0);
		canvas.add(arrowTip);
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
			drawArrowTip(dX,dY,2);
		} else { // / type relation, go right side
			sX = start.getAbsoluteLeft() + start.getOffsetWidth(); // set start X-position to right side of start element
			dX = dest.getAbsoluteLeft() + dest.getOffsetWidth();   // set dest X-position to right side of dest element
			
			arrow = new Path(sX,sY);
			
			sCPX = sX + (30 + ((dX-sX)/4));
			sCPY = sY; 
			
			dCPX = dX + (30+ ((dX-sX)/4));
			dCPY = dY; 
			
			drawArrowTip(dX,dY,3);	
		}
		
		arrow.curveTo(sCPX, sCPY, dCPX, dCPY, dX, dY);
	return arrow;
	}
	
	public void redrawArrows(){
		canvas.clear();
		addArrows(arrowString);
	}

}
