package wags.views.concrete;

import java.util.HashMap;

import wags.ProxyFacilitator;
import wags.Common.Presenter;
import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.GetLMAssigned;
import wags.ProxyFramework.GetLMExercisesCommand;
import wags.ProxyFramework.GetLMGroupsCommand;
import wags.ProxyFramework.GetLMSubjectsCommand;
import wags.ProxyFramework.GetMMAssignedCommand;
import wags.ProxyFramework.GetMMGroupsCommand;
import wags.ProxyFramework.ProxyCommands;
import wags.ProxyFramework.SetLMExercisesCommand;
import wags.ProxyFramework.SetMMExercisesCommand;
import wags.admin.AssignedPanel;
import wags.admin.ButtonPanel;
import wags.admin.CheckBoxPanel;
import wags.admin.builders.LMBuilderFactory;
import wags.admin.builders.LMTraversalDisplay;
import wags.presenters.interfaces.LogicalTabPresenter;
import wags.views.interfaces.LogicalTabView;
import wags.WEStatus;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LogicalTab extends Composite implements ProxyFacilitator,
		LogicalTabView {

	private static LogicalTabUiBinder uiBinder = GWT
			.create(LogicalTabUiBinder.class);

	private LogicalTabPresenter presenter;

	interface LogicalTabUiBinder extends UiBinder<Widget, LogicalTab> {
	}

	@UiField
	ButtonPanel btnPanelSubjects;
	@UiField
	ButtonPanel btnPanelGroups;
	@UiField
	CheckBoxPanel chkPanelExercises;
	@UiField
	AssignedPanel assigned, selected;
	@UiField ListBox subjectListBox, groupsListBox;

	public LogicalTab() {
		initWidget(uiBinder.createAndBindUi(this));

		// Calls the server to get subjects
		AbstractServerCall cmd = new GetLMSubjectsCommand(this);
		cmd.sendRequest();
		
		//Calls the server to get already assigned exercises 
		AbstractServerCall getCmd = new GetLMAssigned(this,"");
		getCmd.sendRequest();
		// Proxy.getLMAssigned(this);

		// Initial set up
		// set up button panels
		// set up checkbox panel
		chkPanelExercises.setAssignedPanel(selected);

		// setup assigned panels
		selected.setAssigned(false);
		selected.setPartner(assigned);
		selected.setParent(this);
		selected.setExercises(chkPanelExercises);
		assigned.setAssigned(true);
		assigned.setPartner(selected);
		assigned.setParent(this);

		addSubjectClickHandlers();
		addGroupClickHandlers();
		
		subjectListBox.addChangeHandler(new ChangeHandler()
		 {
		  public void onChange(ChangeEvent event)
		  {
			  int selectedIndex = subjectListBox.getSelectedIndex();
			  if (selectedIndex > -1) 
			  {
				  String title = subjectListBox.getValue(selectedIndex);
				  groupsListBox.clear();
				  //This shouldn't be done. Please help. 
				  switch( title) {
				  case "Binary Trees":
					  btnPanelSubjects.myButtons.get(0).click();
				  	  break;
				  case "Created":
					  btnPanelSubjects.myButtons.get(6).click();
				  	  break;
				  case "Graphs":
					  btnPanelSubjects.myButtons.get(1).click();
				  	  break;
				  case "Hashing":
					  btnPanelSubjects.myButtons.get(2).click();
				  	  break;
				  case "Heaps":
					  btnPanelSubjects.myButtons.get(3).click();
				  	  break;
				  case "Quicksort":
					  btnPanelSubjects.myButtons.get(4).click();
				  	  break;
				  case "RadixSort":
					  btnPanelSubjects.myButtons.get(5).click();
				  	  break;
				  default:
				  	Window.alert("Not yet implemented");
				  	btnPanelSubjects.myButtons.get(0).click();
				  	
				  }
				  chkPanelExercises.clearCheckBoxes();
		      }
		  }
		 });
		
		groupsListBox.addChangeHandler(new ChangeHandler()
		 {
		  public void onChange(ChangeEvent event)
		  {
			  int selectedIndex = groupsListBox.getSelectedIndex();
			  if (selectedIndex > -1) 
			  {
				  btnPanelGroups.myButtons.get(selectedIndex).click();
		      }
		  }
		 });
		
	}

	// -------------------------------
	// Subject panel click handling
	// -------------------------------
	private void addSubjectClickHandlers() {
		for (int i = 0; i < btnPanelSubjects.myButtons.size(); i++) {
			Button tmpBtn = btnPanelSubjects.myButtons.get(i);
			tmpBtn.addClickHandler(new subjectClickHandler(tmpBtn.getText(),
					this));
		}

		btnPanelSubjects.setClickHandlers();
	}

	private class subjectClickHandler implements ClickHandler {
		private String title;
		private ProxyFacilitator pf;

		public subjectClickHandler(String title, ProxyFacilitator pf) {
			this.title = title;
			this.pf = pf;
		}

		@Override
		public void onClick(ClickEvent event) {
			AbstractServerCall cmd = new GetLMGroupsCommand(title, pf);
			cmd.sendRequest();
		}
	}

	// -------------------------------
	// Group panel click handling
	// -------------------------------
	private void addGroupClickHandlers() {
		for (int i = 0; i < btnPanelGroups.myButtons.size(); i++) {
			Button tmpBtn = btnPanelGroups.myButtons.get(i);
			tmpBtn.addClickHandler(new groupClickHandler(tmpBtn.getText(), this));
		}

		btnPanelGroups.setClickHandlers();
	}

	private class groupClickHandler implements ClickHandler {
		String title;
		ProxyFacilitator pf;

		public groupClickHandler(String title, ProxyFacilitator pf) {
			this.title = title;
			this.pf = pf;
		}

		@Override
		public void onClick(ClickEvent event) {
			AbstractServerCall cmd = new GetLMExercisesCommand(title, pf);
			cmd.sendRequest();
		}

	}

	// -----------------------------
	// Proxy facilitation
	// -----------------------------
	@Override
	public void handleSubjects(String[] subjects) {
		btnPanelSubjects.addButtons(subjects);
		subjectListBox.addItem("Subjects");
		for (int i = 0; i < subjects.length; i++)
		{
			subjectListBox.addItem("" + subjects[i]);
		}
		addSubjectClickHandlers();
	}

	@Override
	public void handleGroups(String[] groups) {
		btnPanelGroups.addButtons(groups);
		for (int i = 0; i < groups.length; i++)
		{
			groupsListBox.addItem("" + groups[i]);
		}
		addGroupClickHandlers();
	}

	@Override
	public void handleExercises(String[] exercises) {
		chkPanelExercises.addCheckBoxes(exercises);
	}

	/**
	 * Assigning exercises
	 */
	public void setExercises(String[] exercises){
		String exerciseList = "";
		for(int i = 0; i < exercises.length; i++){
			if(exercises.length>1 && !exercises[i].equals("none")){
				exerciseList += exercises[i] + "|";
			}
		}
		
		if(exerciseList.length() > 0)  // a comma was added
			exerciseList = exerciseList.substring(0, exerciseList.length()-1);
		
		AbstractServerCall cmd = new SetLMExercisesCommand(exerciseList, this);
		cmd.sendRequest();
	}

	/**
	 * Initial callback to set up currently assigned problems
	 */
	public void setCallback(String[] exercises, WEStatus status) {
		if (status.getStat() == WEStatus.STATUS_SUCCESS) {
			assigned.clear();

			for (int i = 0; i < exercises.length; i++) {
				assigned.add(exercises[i]);
			}
		}
	}

	@Override
	public void getCallback(String[] exercises, WEStatus status, String request) {
		// Currently assigned
		if (request.equals("")) {
			HashMap<String, CheckBox> chkBoxes = chkPanelExercises
					.getAssignments();
			for (int i = 0; i < exercises.length; i++) {
				assigned.add(exercises[i]);
				CheckBox tmpCheck = new CheckBox(exercises[i]);
				chkPanelExercises.addClickHandler(tmpCheck);
				tmpCheck.setValue(true);
				chkBoxes.put(exercises[i], tmpCheck);
			}
		}
	}
	
	public void update(){
		AbstractServerCall groupsCmd = new GetLMSubjectsCommand(this);
		groupsCmd.sendRequest();
		AbstractServerCall assignedCmd = new GetLMAssigned(this, "");
		assignedCmd.sendRequest();
		addGroupClickHandlers();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (LogicalTabPresenter) presenter;
	}

	@Override
	public boolean hasPresenter() {
		System.out.println(presenter != null);
		return presenter != null;
	}

	@Override
	public Presenter getPresenter() {
		return presenter;
	}

	@Override
	public ButtonPanel btnPanelSubjects() {
		return btnPanelSubjects;
	}

	@Override
	public ButtonPanel btnPanelGroups() {
		return btnPanelGroups;
	}

	@Override
	public CheckBoxPanel chkPanelExercises() {
		return chkPanelExercises;
	}

	@Override
	public AssignedPanel assigned() {
		return assigned;
	}

	@Override
	public AssignedPanel selected() {
		return selected;
	}
}
