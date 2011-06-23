package webEditor.client.view;

import webEditor.client.Proxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Admin extends Composite{

	@UiField TextBox ID;
	@UiField FileUpload solution;
	@UiField FileUpload skeleton;
	@UiField TextBox desc;
	@UiField SubmitButton addButton;
	@UiField FormPanel form;
	@UiField TextBox fileName;
	
	private static AdminUiBinder uiBinder = GWT.create(AdminUiBinder.class);

	interface AdminUiBinder extends UiBinder<Widget, Admin> {
	}

	public Admin() {
		initWidget(uiBinder.createAndBindUi(this));
		
		form.setAction(Proxy.getBaseURL() + "?cmd=AddExercise&desc="+desc.getText().toString()+"&fileName="+fileName.getText());
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
	}

	@UiHandler("addButton")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}


}
