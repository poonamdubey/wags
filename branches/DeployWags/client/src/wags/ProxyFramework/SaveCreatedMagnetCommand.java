package wags.ProxyFramework;

import wags.Notification;
import wags.WEStatus;
import wags.magnet.view.StackableContainer;

import com.google.gwt.http.client.Response;

public class SaveCreatedMagnetCommand extends AbstractServerCall {

	private StackableContainer magnet;
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		if (status.getStat() == WEStatus.STATUS_SUCCESS)
		{
			
		}
		else
		{
			Notification.notify(WEStatus.STATUS_WARNING, "Submission Processed Correctly - Magnet could not be saved.");
		}
	}
	
	public SaveCreatedMagnetCommand(StackableContainer magnet, int magnetProblemID)
	{
		command = ProxyCommands.SaveCreatedMagnet;
		addArgument("magnetcontent", magnet.getContent());
		addArgument("magnetID", "" + magnet.getID());
		addArgument("magnetProblemID", "" + magnetProblemID);
		this.magnet = magnet;
	}
}
