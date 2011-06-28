package webEditor.client.view;

import webEditor.client.Proxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class Admin extends Composite{

	@UiField FileUpload solution;
	@UiField FileUpload skeleton;
	@UiField TextArea desc;
	@UiField SubmitButton addButton;
	@UiField FormPanel adminForm;
	@UiField TextBox fileName;
	@UiField CheckBox visible;
	
	private static AdminUiBinder uiBinder = GWT.create(AdminUiBinder.class);

	interface AdminUiBinder extends UiBinder<Widget, Admin> {
	}

	public Admin() {
		initWidget(uiBinder.createAndBindUi(this));
		
		final int isVisible;
		if (visible.isEnabled()) isVisible = 1;
		else isVisible = 0;
		
		adminForm.setAction(Proxy.getBaseURL() + "?cmd=AddExercise");
		adminForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		adminForm.setMethod(FormPanel.METHOD_POST);
		
		adminForm.addSubmitHandler(new SubmitHandler(){

			@Override
			public void onSubmit(SubmitEvent event) {
				Window.alert(desc.getText().toString() + "|||" + fileName.getText() + "|||" + isVisible);
				
			}
			
		});
		
		adminForm.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Window.alert(event.getResults());
			}
			
		});
	}

}
