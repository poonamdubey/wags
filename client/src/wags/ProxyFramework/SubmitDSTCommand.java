package wags.ProxyFramework;

import com.google.gwt.http.client.Response;

import wags.WEStatus;

public class SubmitDSTCommand extends AbstractServerCall{
	
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
