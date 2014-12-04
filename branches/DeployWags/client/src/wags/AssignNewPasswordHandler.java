package wags;

import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.AssignPasswordCommand;
import wags.ProxyFramework.LogoutCommand;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class AssignNewPasswordHandler {

	public static void handleAssignNewPassword() 
	{
		final DialogBox setPassword = new DialogBox(false);
		final PasswordTextBox password = new PasswordTextBox();
		final PasswordTextBox passwordCheck = new PasswordTextBox();
		Label lbl1 = new Label("Enter password: ");
		Label lbl2 = new Label("Re-enter password: ");
		
		Button close = new Button("Set Password");
		Button cancel = new Button("Cancel");
		
		VerticalPanel base = new VerticalPanel();
		HorizontalPanel line1 = new HorizontalPanel();
		HorizontalPanel line2 = new HorizontalPanel();
		HorizontalPanel line3 = new HorizontalPanel();
		
		setPassword.setText("Please Enter a New Password");
		
		close.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(!password.getText().equals(passwordCheck.getText())){
					Notification.notify(WEStatus.STATUS_ERROR, "Passwords don't match");
					return;
				}
				
				if(password.getText().length() < 8){
					Notification.notify(WEStatus.STATUS_ERROR, "Password must be at least 8 characters");
					return;
				}
				
				setPassword.hide();
				AbstractServerCall cmd = new AssignPasswordCommand(password.getText());
				cmd.sendRequest();
				//Proxy.assignPassword(password.getText());
			}
		});
		
		cancel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					//If the user selects cancel, then they will be logged out. 
					AbstractServerCall cmd = new LogoutCommand();
					cmd.sendRequest();
					setPassword.hide();
				}
		});
		
		line1.add(lbl1);
		line1.add(password);
		line2.add(lbl2);
		line2.add(passwordCheck);
		line3.add(close);
		line3.add(cancel);
		base.add(line1);
		base.add(line2);
		base.add(line3);
		setPassword.add(base);
		setPassword.center();
		password.setFocus(true);
	}
	
}
