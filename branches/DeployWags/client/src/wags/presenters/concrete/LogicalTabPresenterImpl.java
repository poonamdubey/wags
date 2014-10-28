package wags.presenters.concrete;

import java.util.HashMap;
import java.util.List;

import wags.ProxyFacilitator;
import wags.WEStatus;
import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.GetLMExercisesCommand;
import wags.ProxyFramework.GetLMGroupsCommand;
import wags.ProxyFramework.SetLMExercisesCommand;
import wags.presenters.interfaces.LogicalTabPresenter;
import wags.views.concrete.LogicalTab;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;

public class LogicalTabPresenterImpl implements ProxyFacilitator,
		LogicalTabPresenter {

	private LogicalTab logicalTab;
	private boolean bound = false;

	public LogicalTabPresenterImpl(LogicalTab view) {
		this.logicalTab = view;
	}

	@Override
	public void addSubjectClickHandlers() {

		for (int i = 0; i < logicalTab.btnPanelSubjects().myButtons.size(); i++) {
			Button tmpBtn = logicalTab.btnPanelSubjects().myButtons.get(i);
			tmpBtn.addClickHandler(new subjectClickHandler(tmpBtn.getText(),
					this));
		}

		logicalTab.btnPanelSubjects().setClickHandlers();
	}

	// -------------------------------
	// Subject panel click handling
	// -------------------------------
	private class subjectClickHandler implements ClickHandler {
		private String title;
		private ProxyFacilitator pf;

		public subjectClickHandler(String title, ProxyFacilitator pf) {
			this.title = title;
		}

		@Override
		public void onClick(ClickEvent event) {
			AbstractServerCall command = new GetLMGroupsCommand(title, pf);
			command.sendRequest();
		}
	}

	// -------------------------------
	// Group panel click handling
	// -------------------------------
	@Override
	public void addGroupClickHandlers() {

		for (int i = 0; i < logicalTab.btnPanelGroups().myButtons.size(); i++) {
			Button tmpBtn = logicalTab.btnPanelGroups().myButtons.get(i);
			tmpBtn.addClickHandler(new groupClickHandler(tmpBtn.getText(), this));
		}

		logicalTab.btnPanelGroups().setClickHandlers();
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
			AbstractServerCall command = new GetLMExercisesCommand(title, pf);
			command.sendRequest();
		}

	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(logicalTab.asWidget());
	}

	@Override
	public void bind() {
		logicalTab.setPresenter(this);
		bound = true;
	}

	@Override
	public boolean bound() {
		return bound;
	}

	@Override
	public void update(List<String> data) {
		// TODO Auto-generated method stub
	}

	// -------------------------------
	// Group panel click handling
	// -------------------------------
	@Override
	public void handleGroups(String[] groups) {
		logicalTab.btnPanelGroups().addButtons(groups);
		addGroupClickHandlers();
	}

	// -----------------------------
	// Proxy facilitation
	// -----------------------------
	@Override
	public void handleSubjects(String[] subjects) {
		logicalTab.btnPanelSubjects().addButtons(subjects);
		addSubjectClickHandlers();
	}

	@Override
	public void handleExercises(String[] exercises) {
		logicalTab.chkPanelExercises().addCheckBoxes(exercises);
	}

	/**
	 * Assigning exercises
	 */
	@Override
	public void setExercises(String[] exercises) {
		String toAssign = "";
		for (int i = 0; i < exercises.length; i++) {
			toAssign += exercises[i] + "|";
		}
		AbstractServerCall command = new SetLMExercisesCommand(toAssign, this);
		command.sendRequest();
	}

	/**
	 * Initial callback to set up currently assigned problems
	 */
	@Override
	public void setCallback(String[] exercises, WEStatus status) {
		if (status.getStat() == WEStatus.STATUS_SUCCESS) {
			logicalTab.assigned().clear();

			for (int i = 0; i < exercises.length; i++) {
				logicalTab.assigned().add(exercises[i]);
			}
		}
	}

	@Override
	public void getCallback(String[] exercises, WEStatus status, String request) {
		// Currently assigned
		if (request.equals("")) {
			HashMap<String, CheckBox> chkBoxes = logicalTab.chkPanelExercises().getAssignments();
			for (int i = 0; i < exercises.length; i++) {
				logicalTab.assigned().add(exercises[i]);

				CheckBox tmpCheck = new CheckBox(exercises[i]);
				logicalTab.chkPanelExercises().addClickHandler(tmpCheck);
				tmpCheck.setValue(true);
				chkBoxes.put(exercises[i], tmpCheck);
			}
		}
	}

}
