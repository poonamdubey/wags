package wags.ProxyFramework;

import wags.Notification;
import wags.ProxyFacilitator;
import wags.WEStatus;

import com.google.gwt.http.client.Response;

public class SetLMExercisesCommand extends AbstractServerCall {

	private ProxyFacilitator pf;
	private String toAssign;
	
	@Override
	protected void handleResponse(Response response)
	{
		if(toAssign.equals("")) toAssign = "none";
		final String forCallback = toAssign;
		
		WEStatus status = new WEStatus(response);
		Notification.notify(status.getStat(), status.getMessage());
		pf.setCallback(forCallback.split("\\|"), status);
	}
	
	public SetLMExercisesCommand(String toAssign, final ProxyFacilitator pf)
	{
		command = ProxyCommands.SetLogicalExercises;
		addArgument("list", toAssign);
		this.pf = pf;
		this.toAssign = toAssign;
	}
}
