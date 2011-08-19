package webEditor.client.view;

import java.util.HashMap;

import webEditor.client.Proxy;
import webEditor.client.WEStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

public class Admin extends Composite{

	private HashMap<String, String> exerciseMap = new HashMap<String, String>();
	
	@UiField FileUpload solution;
	@UiField FileUpload skeleton;
	@UiField TextArea desc;
	@UiField SubmitButton addButton;
	@UiField FormPanel adminForm;
	@UiField TextBox fileName;
	@UiField CheckBox visible;
	@UiField ListBox exercises;
	@UiField Button btnAdminReview;
	@UiField Grid grdAdminReview;
	@UiField FileUpload testClass;
	@UiField FileUpload helperClass;
	@UiField FormPanel helperForm;
	
	private static AdminUiBinder uiBinder = GWT.create(AdminUiBinder.class);

	interface AdminUiBinder extends UiBinder<Widget, Admin> {
	}

	public Admin() {
		initWidget(uiBinder.createAndBindUi(this));
        
		Proxy.getVisibleExercises(exercises, exerciseMap); 
		
		adminForm.setAction(Proxy.getBaseURL() + "?cmd=AddExercise");
		adminForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		adminForm.setMethod(FormPanel.METHOD_POST);
		
		adminForm.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Notification.notify(1, event.getResults());
				exercises.clear();
				Proxy.getVisibleExercises(exercises, exerciseMap); 
			}
			
		});
		
		helperForm.setAction(Proxy.getBaseURL() + "?cmd=AddHelperClass");
		helperForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		helperForm.setMethod(FormPanel.METHOD_POST);
		
		helperForm.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			
			//didn't want to create a whole proxy call, so added this instead
			public void onSubmitComplete(SubmitCompleteEvent event) {
				int status = WEStatus.STATUS_SUCCESS;
				if(event.getResults() != "Class Uploaded") status = WEStatus.STATUS_ERROR; 
				Notification.notify(status, event.getResults());
			}
		});
				
	}
	
	@UiHandler("btnAdminReview")
	void onReviewClick(ClickEvent event)
	{
		String value = exercises.getValue(exercises.getSelectedIndex());
		Proxy.getSubmissionInfo(Integer.parseInt(exerciseMap.get(value)), grdAdminReview);		
	}

}
