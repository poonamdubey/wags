package wags.ProxyFramework;

import wags.Notification;
import wags.WEStatus;

import com.google.gwt.http.client.Response;

public class SaveMagnetStateCommand extends AbstractServerCall {

	private boolean fromSuccess;
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		if (status.getStat() == WEStatus.STATUS_SUCCESS)
		{
			if (fromSuccess)
			{
				Notification.notify(WEStatus.STATUS_SUCCESS, status.getMessage());
			}
			else
			{
				Notification.notify(WEStatus.STATUS_WARNING, "Submission Processed Correctly - State not saved");
			}
		}
	}
	
	public SaveMagnetStateCommand(String state, int magnetID, int success, boolean fromSuccess)
	{
		command = ProxyCommands.SaveMagnetState;
		this.fromSuccess = fromSuccess;
		addArgument("state", state);
		addArgument("magnetId", "" + magnetID);
		addArgument("success", "" + success);
	}
}
