package webEditor.magnet.view;

import webEditor.MagnetProblem;
import webEditor.Proxy;
import webEditor.Wags;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * This is the "landing page" for magnets problems. It displays a list of 
 * buttons corresponding to the code magnet exercises that have been assigned 
 * to the user. If they have successfully completed the problem, the text on 
 * the button will be green. Otherwise, it will simply be black test displaying 
 * the name of the exercise.
 * 
 *
 */
public class Magnets extends AbsolutePanel {
	
	public static final String EMPTY_LABEL = "No Magnet Exercises Assigned!";
	public static int INCOMPLETE = 0;
	public static int SUCCESS = 1;
	public static int REVIEW = 2;

	private int[] ids;				//array of problem id numbers
	private String[] titles;		//array of problem names
	private int[] statuses;			//array of success values
	private int idAssignor = 0;
	private boolean initialLoad = true;
	private Wags wags;

	final VerticalPanel problemPane = new VerticalPanel();
	private HorizontalPanel topButtonPanel; // Hold "Switch to X" Buttons
	private Label banner;
	private Label selectLabel;
	private Button assigned;
	private Button review;
	private ArrayList<ProblemButton> attemptButtons;
	private ArrayList<ProblemButton> reviewButtons;
	
	/**
	 * This will generate a list of ProblemButtons corresponding to the ids and problems 
	 * passed in.
	 * 
	 * @param ids		id numbers for magnet problems
	 * @param titles	title text of magnet problems
	 * @param success	boolean for if magnet problem was completed successfully by the user
	 * @param wags		reference to Wags so that Wags can switch between different pages
	 * 					while retaining the top bar
	 */
	public Magnets(int[] ids, String[] titles, int[] statuses, Wags wags) {
		this.ids = ids;
		this.titles = titles;
		this.statuses = statuses;
		this.wags = wags;

		banner = new Label("Code Magnet Microlabs");
		attemptButtons = new ArrayList<ProblemButton>();
		reviewButtons = new ArrayList<ProblemButton>();
		createButtons();	
		topButtonPanel = buildButtonPanel();
		
		buildUI(attemptButtons);
		
		setStyleName("main_background");
		banner.setStyleName("banner");
		selectLabel.setStyleName("welcome");
	}
	
	private void createButtons() {
		// create an attempt button for each problem
		// buttons text is green if they have completed the problem successfully
		for (int i = 0; i < titles.length; i++) {
			int id = ids[i];
			String title = (statuses[i] == SUCCESS) ? "<font color=green>" + titles[i] + "</font>" : titles[i];
			ProblemButton b = new ProblemButton(title, id);
			
			if (statuses[i] == REVIEW) {
				reviewButtons.add(b);
			} else {
				attemptButtons.add(b);
			}
		}
	}
	
	/**
	 * Method used to build the user interface.
	 */
	private void buildUI(final ArrayList<ProblemButton> buttons) {	
		this.removeAllWidgets();
		add(problemPane);
		problemPane.clear();
		problemPane.setSpacing(5);
		problemPane.add(banner);
		problemPane.add(topButtonPanel);
		
		if (buttons.size() > 0) {
			selectLabel = new Label(""); 
		} else {
			selectLabel = new Label(EMPTY_LABEL);
		}
		
		problemPane.add(selectLabel);
		
		/*
		 * Add and format all the ProblemButtons
		 * 
		 * Wrap all this stuff in a timer so that getOffsetWidth() works correctly (I am ashamed)
		 */
		Timer timer = new Timer() {
			public void run() {
				int maxWidth = banner.getOffsetWidth(); //we want the buttons to be at least as wide as the banner

				// Add the style to all the buttons, add the buttons to problemPane,
				// and figure out the maximum width of all the buttons
				for (int i = 0; i < buttons.size(); i++) {
					ProblemButton b = buttons.get(i);
					addClickHandling(b, b.getID());
					b.setStyleName("problem");
					problemPane.add(b);

					// find the maximum width of buttons
					int width = b.getOffsetWidth();
					maxWidth = (width > maxWidth) ? width : maxWidth;
				}

				// Go back through and set all the buttons to the max width
				for (int i = 0; i < buttons.size(); i++) {
					ProblemButton b = buttons.get(i);
					b.setWidth(maxWidth + "px");
					b.setHeight("50px");
				}
				
				// Set assigned/review button to same width
				assigned.setWidth(maxWidth + "px");
				review.setWidth(maxWidth + "px");
				if(initialLoad){
					review.setVisible(true);
					initialLoad = false;
				}
			}
		};
		timer.schedule(1);
	}
	
	private HorizontalPanel buildButtonPanel() {
		assigned = new Button("Switch to Assigned");
		review = new Button("Switch to Review");
		
		assigned.setVisible(false);
		review.setVisible(false);
		assigned.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				buildUI(attemptButtons);
				assigned.setVisible(false);
				review.setVisible(true);
			}
		});
		
		review.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				buildUI(reviewButtons);
				review.setVisible(false);
				assigned.setVisible(true);
			}
		});
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(assigned);
		hp.add(review);
		
		return hp;
	}

	/**
	 * Removes all widgets currently on the screen.
	 */
	public void removeAllWidgets() {
		for (int i = 0; i < this.getWidgetCount(); i++) {
			this.remove(this.getWidget(i));
		}
	}
	
	/**
	 * 
	 * Add a click handler to the button so that it calls 
	 * Proxy.getMagnetProblem() when clicked.
	 * 
	 */
	public void addClickHandling(ProblemButton b, final int id) {
		b.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	Timer timer = new Timer() {
            		public void run() {
            			Proxy.getMagnetProblem(id, wags);
            			History.newItem("?loc=magnetproblem");
            		}
            	};
            	timer.schedule(1);
            }
		});
	}
	
	/**
	 * @return a RefrigeratorMagnet object, created from the MagnetProblem 
	 * that was grabbed from the server using Proxy.getMagnetProblem()
	 */
	public RefrigeratorMagnet makeProblem(MagnetProblem magnet) {
		idAssignor = 0;
		return new RefrigeratorMagnet(
				magnet,
				getMainContainer(magnet.mainFunction),
				buildFunctions(magnet.innerFunctions),
				decodePremade(magnet.statements, magnet.createdIDs, magnet.numStatements), 
				new String[][] { magnet.forLeft, magnet.forMid, magnet.forRight }
		);
	}
	
	/**
	 * Creates an array of StackableContainers from an array of 
	 * StackableContainers with the text from an array of Strings.
	 * 
	 * @param segments Array of Strings representing each code segment.
	 * 
	 * @return An array of StackableContainers. Will return null 
	 * if segments is null.
	 */
	private StackableContainer[] decodePremade(String[] segments, String[] createdIDs, int numStatements) {
		if (segments == null) {
			return null;
		}
		
		StackableContainer[] preMadeList = new StackableContainer[segments.length]; //should never need this many
		
		for (int i = 0; i < segments.length; i++) {
			StackableContainer sc = new StackableContainer(segments[i], Consts.STATEMENT);
			if (idAssignor > numStatements) {
				sc.setID(createdIDs[idAssignor - numStatements - 1]);
				sc.setCreated(true);
				idAssignor++;
			} else {
				sc.setID(getID());
			}
			preMadeList[i] = sc;
		}
		return preMadeList;
	}
	
	
	/**
	 * Creates the main StackableContainer that holds all of 
	 * the other StackableContainers.
	 * 
	 * @param str The text for the main StackableContainer
	 * 
	 * @return The main StackableContainer
	 */
	private StackableContainer getMainContainer(String str) {
		StackableContainer sc = new StackableContainer(str, Consts.MAIN);
		sc.setID(getID());
		return sc;
	}
	
	/**
	 * Creates an array of StackableContainers from a String array
	 * 
	 * @param insideFunctions array of text for inner functions
	 * 
	 * @return An array of StackableContainers. Will return null if 
	 * insideFunctions is null.
	 */
	private StackableContainer[] buildFunctions(String[] insideFunctions) {
		if (insideFunctions == null) {
			return null;
		}
		
		StackableContainer[] insideFunctionsList = new StackableContainer[insideFunctions.length]; //should never need this many
		
		for (int i = 0; i < insideFunctions.length; i++) {
			StackableContainer sc = new StackableContainer(insideFunctions[i], Consts.INNER);
			sc.setID(getID());
			insideFunctionsList[i] = sc;
		}
		
		return insideFunctionsList;
	}
	
	/**
	 * Returns the next unused id and increments the global varaible idAssignor
	 * @return String of the next id number
	 */
	private String getID() {
		idAssignor++;
		return "" + (idAssignor - 1);
	}
}