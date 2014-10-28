package wags.ProxyFramework;

import wags.Reviewer;
import wags.WEStatus;

import com.google.gwt.http.client.Response;

public class GetUsernamesReviewerCommand extends AbstractServerCall {
	
	private Reviewer studentReviewer;
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		String[] message = status.getMessageArray();
		
		for (int i = 0; i < message.length; i++)
		{
			message[i] = message[i].substring(1, message[i].length() - 1);
		}
		
		studentReviewer.getCallback(status.getMessageArray(), status, response.toString());
	}
	
	public GetUsernamesReviewerCommand(final Reviewer studentReviewer)
	{
		command = ProxyCommands.GetAllUsers;
		this.studentReviewer = studentReviewer;
	}

}
