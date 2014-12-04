package wags.views.concrete;


import wags.Notification;
import wags.Proxy;
import wags.Reviewer;
import wags.WEStatus;
import wags.Common.Presenter;
import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.GetUsernamesCommand;
import wags.ProxyFramework.GetUsernamesReviewerCommand;
import wags.ProxyFramework.ReviewStudentCommand;
import wags.admin.ReviewPanel;
import wags.presenters.interfaces.StudentTabPresenter;
import wags.views.interfaces.StudentTabView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.Widget;

public class StudentTab extends Composite implements StudentTabView {

	private static StudentTabUiBinder uiBinder = GWT
			.create(StudentTabUiBinder.class);

	interface StudentTabUiBinder extends UiBinder<Widget, StudentTab> {
	}
	
	@UiField SubmitButton sbtRegister;
	@UiField Button btnChgPassword;
	@UiField FormPanel registerForm, passwordForm;
	@UiField ListBox users;
	@UiField ReviewPanel studentReviewPnl;
	
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
		studentReviewPnl.setTitle( "Student Review" );
		AbstractServerCall usernamesCmd1 = new GetUsernamesReviewerCommand(studentReviewer);
		usernamesCmd1.sendRequest();
		
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
				studentReviewPnl.setStudents(exercises);
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
		public void reviewCallback( String[] list )
		{
			studentReviewPnl.fillGrid(list, true); //true because it is student review
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
		return true;
	}
}
