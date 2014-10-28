package wags.ProxyFramework;

import wags.Notification;
import wags.ProxyFacilitator;
import wags.WEStatus;

import com.google.gwt.http.client.Response;

public class SetMMExercisesCommand extends AbstractServerCall {
	
	private String assignedMagnets;
	private ProxyFacilitator pf;
	
	@Override
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
