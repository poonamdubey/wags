package wags.ProxyFramework;

import java.util.HashMap;

import wags.Notification;
import wags.WEStatus;
import wags.Common.AppController;
import wags.Common.Tokens;
import wags.views.concrete.DefaultPage;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class LoginAsGuestCommand extends AbstractServerCall {

	protected void handleResponse(Response response) {
		WEStatus status = new WEStatus(response);
		if(status.getStat() == WEStatus.STATUS_SUCCESS){
			
			Window.alert("Logged in as Guest\nAll progress is deleted upon logging out");
			HashMap<String, String> message = status.getMessageMap();
			History.newItem("default", false);
			
			AppController.setUserDetails(message);
		} 
		else {
			Notification.notify(WEStatus.STATUS_ERROR, status.getMessage());
		}
		History.fireCurrentHistoryState();
	}
	
	public LoginAsGuestCommand() {
		command = ProxyCommands.LoginAsGuest;
	}

}
