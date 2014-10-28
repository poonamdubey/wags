package wags.ProxyFramework;

import java.util.HashMap;

import wags.Notification;
import wags.WEStatus;
import wags.Common.AppController;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;

public class LoginCommand extends AbstractServerCall {

	@Override
	protected void handleResponse(Response response) 
	{	
		WEStatus status = new WEStatus(response);
		if(status.getStat() == WEStatus.STATUS_SUCCESS){
			HashMap<String, String> message = status.getMessageMap();
			History.newItem("default", false);
			
			AppController.setUserDetails(message);
		} else {
			Notification.notify(WEStatus.STATUS_ERROR, status.getMessage());
		}
		History.fireCurrentHistoryState();
	}
		
	public LoginCommand(String username, String password)
	{	
		addArgument("username", username);
		addArgument("password", password);
		command = ProxyCommands.Login;
	}


}
