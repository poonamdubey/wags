package edu.appstate.bostrt.WE.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import edu.appstate.bostrt.WE.client.WEStatus;

/**
 * Registration
 *
 * Show the form for a prospective user to register.
 * They give their user name, password, real name (display name).... 
 *
 * @author Robert Bost <bostrt@appstate.edu>
 *
 */
public class Registration extends View
{

	private static RegistrationUiBinder uiBinder = GWT.create(RegistrationUiBinder.class);

	interface RegistrationUiBinder extends UiBinder<Widget, Registration>{}

	public static final String registerURL = "http://student.cs.appstate.edu/~bostrt/wags/index.php?cmd=RegisterUser";
	
	@UiField Button registerButton;
	@UiField TextBox email;
	@UiField TextBox username;
	@UiField TextBox firstName;
	@UiField TextBox lastName;
	@UiField PasswordTextBox password;
	@UiField PasswordTextBox passwordConfirm;
	@UiField Label passwordStatus;

	public Registration()
	{
		initWidget(uiBinder.createAndBindUi(this));
		registerButton.setText("Register");
	}

	/**
	 * This is the click handler for the registration submit button.
	 * If passwords match and the username is not taken then 
	 * the user is good to go. Register them! 
	 */
	@UiHandler("registerButton")
	void onRegisterClick(ClickEvent event)
	{
		// Passwords must match.
		if(!passwordsMatch(password, passwordConfirm) || !arePasswordsGood(password, passwordConfirm)){
			Notification.notify(WEStatus.STATUS_ERROR, "Please check your password.");
			return;
		}
		// Passwords match...move along.
		String completeURL = Registration.registerURL+"&email="+email.getText()+
							 "&username="+username.getText()+
							 "&password="+password.getText()+
							 "&firstName="+firstName.getText()+
							 "&lastName="+lastName.getText();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, completeURL);
		try{
			@SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO: Do something...
					RootPanel root = RootPanel.get();
					WEStatus status = new WEStatus(response);
					Notification.notify(status.getStat(), status.getMessage());
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
				}
			});
		} catch (RequestException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This is the handler for the password field.
	 * The status label changes appropriately.  
	 */
	@UiHandler("password")
	void passwordChange(KeyUpEvent event)
	{
		Element e = passwordStatus.getElement();
		if(arePasswordsGood(password, passwordConfirm)){
			if(passwordsMatch(password, passwordConfirm)){
				e.removeClassName("passwords-not-match");
				e.addClassName("passwords-match");
				passwordStatus.setText("Passwords match");
			}else{
				e.removeClassName("passwords-match");
				e.addClassName("passwords-not-match");
				passwordStatus.setText("Passwords do not match");
			}
		}else{
			e.removeClassName("passwords-match");
			e.addClassName("passwords-not-match");
			passwordStatus.setText("Password is too short!");
		}
	}
	
	/**
	 * This is the handler for the passwordConfirm field.
	 * The status label changes appropriately.
	 */
	@UiHandler("passwordConfirm")
	void passwordConfirmChange(KeyUpEvent event)
	{
		Element e = passwordStatus.getElement();
		if(arePasswordsGood(password, passwordConfirm)){
			if(passwordsMatch(password, passwordConfirm)){
				e.removeClassName("passwords-not-match");
				e.addClassName("passwords-match");
				passwordStatus.setText("Passwords match");
			}else{
				e.removeClassName("passwords-match");
				e.addClassName("passwords-not-match");
				passwordStatus.setText("Passwords do not match");
			}
		}
		else{
			e.removeClassName("passwords-match");
			e.addClassName("passwords-not-match");
			passwordStatus.setText("Password is too short!");
		}	
	}
	
	/**
	 * Check if both password fields have any characters in them.
	 * Password needs to be longer than NOTHING.
	 * TODO: Run wordlist on password. 
	 * 
	 * @return boolean
	 */
	private boolean arePasswordsGood(PasswordTextBox p1, PasswordTextBox p2)
	{
		if(p1.getText().length() > 0 || p2.getText().length() > 0){
			return true;
		}	
		
		return false;
	}
	
	/**
	 * Check if passwords match.
	 * 
	 * @return boolean 
	 */
	private boolean passwordsMatch(PasswordTextBox p1, PasswordTextBox p2)
	{
		if(p1.getText().equals(p2.getText()))
			return true;
		return false;
	}
	

	@Override
	public WEAnchor getLink()
	{
		return new WEAnchor("Register", this, "register");
	}
}