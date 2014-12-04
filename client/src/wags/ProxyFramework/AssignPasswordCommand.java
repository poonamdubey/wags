package wags.ProxyFramework;

import wags.Notification;
import wags.WEStatus;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

/**
 * @author Dakota Murray
 * 
 * Server File Called: 
 *
 */
public class AssignPasswordCommand extends AbstractServerCall {

	@Override
	protected void handleResponse(Response response) 
	{
		WEStatus status = new WEStatus(response);  
		Notification.notify(status.getStat(), status.getMessage());
		Window.Location.reload(); //reload the page, will determine if password still == "password"
	}
	
	public AssignPasswordCommand(String password)
	{
		addArgument("pass", password);
		method = RequestBuilder.POST;
		command = ProxyCommands.AssignPassword;
	}

}
