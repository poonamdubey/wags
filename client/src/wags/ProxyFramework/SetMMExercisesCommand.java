package wags.ProxyFramework;

import com.google.gwt.http.client.Response;

import wags.Notification;
import wags.ProxyFacilitator;
import wags.WEStatus;

public class SetMMExercisesCommand extends AbstractServerCall {
	
	private String assignedMagnets;
	private ProxyFacilitator pf;
	
	protected void handleResponse(Response response)
	{
		if(assignedMagnets.equals("")) assignedMagnets = "none";
		final String forCallback = assignedMagnets;
		
		WEStatus status = new WEStatus(response);
		Notification.notify(status.getStat(), status.getMessage());
		pf.setCallback(forCallback.split(","), status);
	}

	public SetMMExercisesCommand(String assignedMagnets, final ProxyFacilitator pf)
	{
		command = ProxyCommands.SetMagnetExercises;
		addArgument("list", assignedMagnets);
		this.assignedMagnets = assignedMagnets;
		this.pf = pf;
	}
}
