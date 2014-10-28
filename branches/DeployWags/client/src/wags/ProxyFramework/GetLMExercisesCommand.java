package wags.ProxyFramework;

import wags.ProxyFacilitator;
import wags.WEStatus;

import com.google.gwt.http.client.Response;

public class GetLMExercisesCommand extends AbstractServerCall {

	private ProxyFacilitator pf;
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		pf.handleExercises(status.getMessageArray());
	}
	
	public GetLMExercisesCommand (String group, ProxyFacilitator pf)
	{
		command = ProxyCommands.LogicalExercises;
		addArgument("request", "exercises");
		addArgument("group", group);
		this.pf = pf;
	}
}
