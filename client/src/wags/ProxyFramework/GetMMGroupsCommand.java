package wags.ProxyFramework;

import wags.ProxyFacilitator;
import wags.WEStatus;

import com.google.gwt.http.client.Response;


public class GetMMGroupsCommand extends AbstractServerCall {

	private ProxyFacilitator pf;
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		pf.handleGroups(status.getMessageArray());
	}
	
	public GetMMGroupsCommand(final ProxyFacilitator pf)
	{
		command = ProxyCommands.GetMagnetGroups;
		this.pf = pf;
	}
}
