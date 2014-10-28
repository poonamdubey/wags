package wags.ProxyFramework;

import wags.Notification;
import wags.WEStatus;

import com.google.gwt.http.client.Response;

public class LinkNewSectionCommand extends AbstractServerCall {
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		Notification.notify(status.getStat(), status.getMessage());
	}
	
	public LinkNewSectionCommand(String section, String admin, String guest)
	{
		command = ProxyCommands.LinkNewSection;
		addArgument("sect", section);
		addArgument("admin", admin);
		addArgument("guest", guest);
	}
}
