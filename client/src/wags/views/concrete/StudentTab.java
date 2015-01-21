package wags.views.concrete;


import wags.Notification;
import wags.Proxy;
import wags.Reviewer;
import wags.WEStatus;
import wags.Common.Presenter;
import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.GetUsernamesCommand;
import wags.ProxyFramework.GetUsernamesReviewerCommand;
import wags.ProxyFramework.RemoveUserFromSectionCommand;
import wags.ProxyFramework.ReviewStudentCommand;
import wags.admin.ReviewPanel;
import wags.admin.builders.BasicDisplay;
import wags.admin.builders.LMBuilder;
import wags.presenters.interfaces.StudentTabPresenter;
import wags.views.interfaces.StudentTabView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class StudentTab extends Composite implements StudentTabView {

	private static StudentTabUiBinder uiBinder = GWT
			.create(StudentTabUiBinder.class);

	interface StudentTabUiBinder extends UiBinder<Widget, StudentTab> {
	}
	
	@UiField SubmitButton sbtRegister;
	@UiField Button btnChgPassword;
	@UiField FormPanel registerForm, passwordForm;
	@UiField ListBox users, studentsListBox;
	@UiField ReviewPanel studentReviewPnl;
	@UiField Grid grdGrades;
	@UiField Button removeStudentButton;
	
	String studentClicked;
	Reviewer studentReviewer;
	private StudentTabPresenter presenter;
	
	public StudentTab() {
		initWidget(uiBinder.createAndBindUi(this));
		
		AbstractServerCall usernamesCmd = new GetUsernamesCommand(users);
		usernamesCmd.sendRequest();
		
		// Set up password form
		passwordForm.setAction(Proxy.getBaseURL()+"?cmd=ChangePassword");
		passwordForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		passwordForm.setMethod(FormPanel.METHOD_POST);
		passwordForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				WEStatus status = new WEStatus(event.getResults());
				
				Notification.notify(status.getStat(), status.getMessage());
				AbstractServerCall usernamesCmd = new GetUsernamesCommand(users);
				usernamesCmd.sendRequest();
			}
		});
		
		//Handle the registration form
		registerForm.setAction(Proxy.getBaseURL()+"?cmd=RegisterStudents");
		registerForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		registerForm.setMethod(FormPanel.METHOD_POST);
		
		registerForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				WEStatus status = new WEStatus(event.getResults());
				Notification.notify(status.getStat(), status.getMessage());
				
				users.clear();
				AbstractServerCall usernamesCmd = new GetUsernamesCommand(users);
				usernamesCmd.sendRequest();
			}
		});
		
		//the student review panel
		studentReviewer = new StudentReviewHandler();
		studentReviewPnl.setParent(studentReviewer);
		AbstractServerCall usernamesCmd1 = new GetUsernamesReviewerCommand(studentReviewer);
		usernamesCmd1.sendRequest();
		
		removeStudentButton.addClickHandler(new removeStudentClickHandler());
		
		studentsListBox.addChangeHandler(new ChangeHandler()
		 {
		  @Override
		public void onChange(ChangeEvent event)
		  {
			  int selectedIndex = studentsListBox.getSelectedIndex();
			  if (selectedIndex > -1) 
			  {
				  //Window.alert(studentsListBox.getItemText(selectedIndex));
				  studentClicked = studentsListBox.getItemText(selectedIndex);
				  studentReviewer.review(studentsListBox.getItemText(selectedIndex));
				  removeStudentButton.setVisible(true);
			  }
		  }
		 });
		
	}
	
	public void update(){
		AbstractServerCall usernamesCmd = new GetUsernamesCommand(users);
		usernamesCmd.sendRequest();
	}


	private class StudentReviewHandler implements Reviewer {
		@Override
		public void getCallback( String[] exercises, WEStatus status, String request )
		{
			//"" is success
			if (exercises != null) { 
				//studentReviewPnl.setStudents(exercises);
				for (int i = 0; i < exercises.length;i++)
				{
					studentsListBox.addItem(exercises[i]);
				}
			} else {
				Window.alert( "exercises is null" );
			}
		}

		@Override
		public void review( String name )
		{
			AbstractServerCall cmd = new ReviewStudentCommand(name, this);
			cmd.sendRequest();
			//Proxy.reviewStudent(name, this);
		}

		@Override
		//Generates review panel next to name
		public void reviewCallback( String[] list )
		{
			for (int i = 0; i < list.length; i++)
			{
				//Window.alert("reviewCallback" + gridInfo[i]);
			}
			
			//studentReviewPnl.fillGrid(list, true); //true because it is student review
			fillGrid(list, true);
		}
	}
	
	 /** Used by MyUiBinder to instantiate ReviewPanels */
	  @UiFactory ReviewPanel makeCricketStores() { // method name is insignificant
	    return new ReviewPanel(true);
	  }

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (StudentTabPresenter) presenter;
		
	}

	@Override
	public boolean hasPresenter() {
		return presenter != null;
	}

	@Override
	public Presenter getPresenter() {
		return presenter;
	}

	@Override
	public SubmitButton getSbtRegister() {
		return sbtRegister;
	}

	@Override
	public Button getBtnChgPassword() {
		return btnChgPassword;
	}

	@Override
	public FormPanel getRegisterForm() {
		return registerForm;
	}

	@Override
	public FormPanel getPasswordForm() {
		return passwordForm;
	}

	@Override
	public ListBox getUsers() {
		return users;
	}

	@Override
	public ReviewPanel getStudentReviewPnl() {
		return studentReviewPnl;
	}

	@Override
	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Called to populate the review grid. The method will use different
	 * titles and information for the grid depending on what context the
	 * review panel is being used in.
	 * 
	 *  
	 * @param data	The string data to populate the grid with
	 * @param studentReview if true, populates the grid with labels and 
	 * 						information meaningful to a student review. If
	 * 						False the same happens but for exercises. 
	 */
	public void fillGrid(String[] data, boolean studentReview) {
		if (studentReview) {
			fillGrid(data);
			
			//adjust the titles to reflect student based review
	  		grdGrades.setHTML(0, 0, "<b> Excercise </b>");
	  		grdGrades.setHTML(0, 1, "<b> NumAttempts </b>");
	  		grdGrades.setHTML(0, 2, "<b> Correct </b>");

		} else {
			fillGrid(data);
		}
	}

	/**
	 * Populates the reveiw grid with information and labels meaningful to 
	 * reviewing exercises. 
	 * 
	 * @param data The string data to populate the grid with
	 */
	public void fillGrid(String[] data){
      grdGrades.resize(data.length/3+1, 3);
  		grdGrades.setBorderWidth(1);
  		
  		//Sets the headers for the table
  		grdGrades.setHTML(0, 0, "<b> Username </b>");
  		grdGrades.setHTML(0, 1, "<b> Correct </b>");
  		grdGrades.setHTML(0, 2, "<b> NumAttempts </b>");

  		int k = 0, row = 1;
  		//Fills table with results - stops before last row (the summary)
  	    for (row = 1; row < data.length/3; ++row) {
  	      for (int col = 0; col < 3; ++col)
  	        grdGrades.setText(row, col, data[k++]);
  	    }
  	    // Last row
  	    grdGrades.setHTML(row, 0, "<b> " + data[data.length - 3] + " </b>");
  	    grdGrades.setHTML(row, 1, "<b> " + data[data.length - 2] + " </b>");
  	    grdGrades.setHTML(row, 2, "<b> " + data[data.length - 1] + " </b>");
  	    
	}
	
	/**
	 * Handles removing a student from the database with the remove student
	 * button. 
	 */
	private class removeStudentClickHandler implements ClickHandler{
		
		/**
		 * Once the "Remove Student" button is clicked, the user is presented with a dialog box with which
		 * they must verify the removal of the student form their section. If the admin chooses yet then
		 * the user will be removed from their section and their corresponding button will be removed from 
		 * the list on the left-hand side of the screen
		 */
        @Override
		public void onClick(ClickEvent event) {
			final DialogBox deleteBox = new DialogBox(false);
			Label deleteLbl = new Label("Delete User: " + studentClicked);
			Button yes = new Button("YES");
			Button no = new Button("NO");
			
			VerticalPanel base = new VerticalPanel();
			base.setWidth("100%");
			HorizontalPanel buttonPanel = new HorizontalPanel();
			buttonPanel.setWidth("100%");
			buttonPanel.setHeight("22px");
			
			buttonPanel.add(yes);
			buttonPanel.add(no);
			yes.setHeight("20px");
			yes.setWidth("100%");
			no.setHeight("20px");
			no.setWidth("100%");
			base.add(deleteLbl);
			base.add(buttonPanel);
			deleteBox.add(base);
			
			yes.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AbstractServerCall cmd = new RemoveUserFromSectionCommand(studentClicked);
					cmd.sendRequest();
					//Proxy.RemoveUserFromSection(selectedUser.getText());
					//selectedUser.removeFromParent();
					studentClicked = null;
					deleteBox.hide();
				}	
			});
			
			no.addClickHandler(new ClickHandler() {	
				@Override
				public void onClick(ClickEvent event) {
					deleteBox.hide();
				}
			});
			
			deleteBox.center();
		}
			
		
	}
	
}
