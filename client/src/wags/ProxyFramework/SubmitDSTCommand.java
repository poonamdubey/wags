package wags.ProxyFramework;

import wags.WEStatus;

import com.google.gwt.http.client.Response;

public class SubmitDSTCommand extends AbstractServerCall{
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
	}
	
	public SubmitDSTCommand(String title, int success)
	{
		command = ProxyCommands.SubmitDST;
		addArgument("title", title);
		addArgument("success", "" + success);
	}
}
