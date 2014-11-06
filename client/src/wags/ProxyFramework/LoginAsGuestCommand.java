package wags.ProxyFramework;

import wags.Notification;
import wags.WEStatus;
import wags.views.concrete.DefaultPage;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class LoginAsGuestCommand extends AbstractServerCall {

	protected void handleResponse(Response response) {
		WEStatus status = new WEStatus(response);
		if(status.getStat() == WEStatus.STATUS_SUCCESS){
			
			Window.alert("Logged in as Guest\nAll progress is deleted upon logging out");
			DefaultPage d = new DefaultPage();
			RootPanel root = RootPanel.get();
			root.clear();
			root.add(d);
		}else{
			Notification.notify(WEStatus.STATUS_ERROR, status.getMessage());
		}

	}
	
	public LoginAsGuestCommand() {
		command = ProxyCommands.LoginAsGuest;
	}

}
