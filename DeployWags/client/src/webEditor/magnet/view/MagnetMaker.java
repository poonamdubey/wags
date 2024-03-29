package webEditor.magnet.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The magnet maker is a panel that consists of a series of dropdowns, and
 * from those dropdowns the students can select options to create their own
 * custom magnets.
 * 
 */

public class MagnetMaker extends VerticalPanel {
	public static final int FOR = 1;
	public static final int WHILE = 2;
	public static final int IF = 3;
	public static final int ELSE_IF = 4;
	public static final int ELSE = 5;
	private String[] structuresList = Consts.STRUCTURES_LIST;
	
	private ConstructUi constructPanel; // the left hand side of the magnets UI
	private Button createButton = new Button("Create");
	private MenuBar structures;
	private MenuBar structureOptions;
	private ListBox[] forConditions;
	private ListBox booleanConditions;

	/* different use cases are represented by panels, each for the type of decision structure */
	private HorizontalPanel forPanel = new HorizontalPanel();
	private HorizontalPanel booleanPanel = new HorizontalPanel();
	private HorizontalPanel topAlignPanel = new HorizontalPanel();

	private int nextID;
	private int[] limits;
	private int[] initialLimits;
	private int startOfCreatedIds;
	private int selectedStructureIndex = 0;

	public MagnetMaker(String[][] forLists, String[] booleanList, int[] limits,
			ConstructUi constructPanel, int nextID) {
		this.setStyleName("dropdown_panel");
		this.limits = limits;
		this.initialLimits = new int[limits.length];
		for (int i = 0; i < limits.length; i++) {
			this.initialLimits[i] = limits[i];
		}
			
		this.constructPanel = constructPanel;
		this.nextID = nextID;
		this.startOfCreatedIds = nextID;

		// set up Structures MenuBar(used as a ListBox but we can set html for the elements)
		structures = new MenuBar(true);
		setupStructures(structures, structuresList);
		topAlignPanel.add(structures);
		add(topAlignPanel);

		// do the leg work of turning string arrays into list boxes
		forConditions = new ListBox[3];
		forConditions[0] = setupListBox(forLists[0]);
		forConditions[1] = setupListBox(forLists[1]);
		forConditions[2] = setupListBox(forLists[2]);
		booleanConditions = setupListBox(booleanList);

		/* set up panels for each decision structure so they can be ready to swap to */
		forPanel.add(new HTML("&nbsp ( &nbsp"));
		forPanel.add(forConditions[0]);
		forPanel.add(new HTML("&nbsp ; &nbsp"));
		forPanel.add(forConditions[1]);
		forPanel.add(new HTML("&nbsp ; &nbsp"));
		forPanel.add(forConditions[2]);
		forPanel.add(new HTML("&nbsp ) &nbsp"));

		booleanPanel.addStyleName("boolean_conditions");
		booleanPanel.add(new HTML("&nbsp ( &nbsp"));
		booleanPanel.add(booleanConditions);
		booleanPanel.add(new HTML("&nbsp ) &nbsp"));

		createButton.addClickHandler(new CreateHandler());
		createButton.addStyleName("create_button");
		add(createButton);
		setCellHorizontalAlignment(createButton, HasHorizontalAlignment.ALIGN_RIGHT);
	}
	
	private void updateStructureOptions() {
		String menuItemHTML;
		structureOptions.clearItems();
		
		for (int i = 1; i < structuresList.length; i++) {
			String css = null;
			if (limits[i - 1] > 0) {
				css = "structureLimitAvailable";
			} else {
				css = "structureLimitUnvailable";
			}
			
			menuItemHTML = "<div><p style=\"margin:0px\">" + structuresList[i] + "<span style=\"float:right;\" class=\"" + css + "\">" + limits[i - 1] + "</span></div>";

			final int target = i;
			Command command = new Command() {
				public void execute() {
					showDropdowns(target);
					selectedStructureIndex = target;
					updateStructure();
				}
			};
			structureOptions.addItem(menuItemHTML, true, command);

			if (i != structuresList.length - 1) {
				structureOptions.addSeparator();
			}

			updateStructure();
		}
	}

	private void updateStructure() {
		structures.clearItems();
		if (selectedStructureIndex == 0) {
			structures.addItem("<div style=\"display:inline-block;min-width:110px;\" >" + structuresList[selectedStructureIndex] + "</div>", true, structureOptions);
			return;
		}
		
		String css = null;
		if (limits[selectedStructureIndex - 1] > 0) {
			css = "structureLimitAvailable";
		} else {
			css = "structureLimitUnvailable";
		}
		
		String html = "<div style=\"display:inline-block;min-width:110px;\" >"
					+ structuresList[selectedStructureIndex]
					+ "<span style=\"float:right;\" class=\"" + css + "\">"
					+ limits[selectedStructureIndex - 1]
					+ "</span></div>";
		structures.addItem(html, true, structureOptions);

		createButton.setHTML("Create: " + limits[selectedStructureIndex - 1] + " left");
	}

	// takes the string array and turns it into a usable listbox for the decision structures
	private void setupStructures(MenuBar structures, String[] structuresList) {
		structureOptions = new MenuBar(true);
		structures.setAnimationEnabled(true);
		updateStructureOptions();
	}

	// takes the string array and turns it into a usable listbox for the
	// arguments used in decision structures
	private ListBox setupListBox(String[] listOptions) {
		if (listOptions == null) {
			return null;
		}

		ListBox listBox = new ListBox();
		for (String option : listOptions) {
			listBox.addItem(option);
		}
		return listBox;
	}

	private void showDropdowns(int structure) {
		topAlignPanel.clear();
		topAlignPanel.add(structures);
		
		switch (structure) {
		case FOR:		topAlignPanel.add(forPanel); break;
		case WHILE:		
		case IF:		
		case ELSE_IF:	topAlignPanel.add(booleanPanel); break;
		}
	}
 
	/**
	 * Create a stackable container and add it to the Construct Panel
	 */
	private class CreateHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			if (selectedStructureIndex == 0) {
				return;
			} else if (limits[selectedStructureIndex - 1] <= 0) {
				Window.alert("Error: You have reached your limit of " + structuresList[selectedStructureIndex] + "'s ");
				return;
			}
			
			StackableContainer createdContainer = new StackableContainer(Consts.STRUCTURE_CODE[selectedStructureIndex], Consts.STATEMENT);

			switch (selectedStructureIndex) {
			case FOR:
				String left =  forConditions[0].getItemText(forConditions[0].getSelectedIndex());
				String mid =   forConditions[1].getItemText(forConditions[1].getSelectedIndex());
				String right = forConditions[2].getItemText(forConditions[2].getSelectedIndex());
				String forLoop = left + "; " + mid + "; " + right;
				createdContainer.addConditionContent(forLoop);
				break;

			case WHILE:
			case IF:
			case ELSE_IF:
				createdContainer.addConditionContent(booleanConditions.getItemText(booleanConditions.getSelectedIndex()));
				break;
			}

			createdContainer.setID("" + nextID++);
			createdContainer.setCreated(true);
			constructPanel.addSegment(createdContainer);
			limits[selectedStructureIndex - 1]--;
			updateStructureOptions();
		}
	}
	
	public void incrementLimitCounter(int i) {
		limits[i - 1]++;
		updateStructureOptions();
	}
	public void decrementLimitCounter(int i) {
		limits[i - 1]--;
		updateStructureOptions();
	}
	
	public int getStartOfCreatedIds() {
		return startOfCreatedIds;
	}
	
	public void resetLimits(){
		for (int i = 0; i < limits.length; i++) {
			this.limits[i] = initialLimits[i];
		}
		updateStructureOptions();
	}
}