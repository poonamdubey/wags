package wags.ProxyFramework;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;

import wags.Common.Tokens;
import wags.Notification;
import wags.WEStatus;

public class RequestAccountCommand extends AbstractServerCall {

	String username;
	String name;
	String email;
	String school;
	String comments;
	
	@Override
	protected void handleResponse(Response response) 
	{
		WEStatus status = new WEStatus(response);
		switch (status.getMessage())
		{
			case "success": Notification.notify(WEStatus.STATUS_SUCCESS, "Thank you for registering for an account! "
				+ "Your submission will be processed and you should receive an email shortly!" );
				History.newItem(Tokens.DEFAULT);
				break;
			case "nullfield": Notification.notify(WEStatus.STATUS_ERROR, "Please fill in all of the fields.");
				break;	
			case "email": Notification.notify(WEStatus.STATUS_WARNING, "Please enter a valid email.");
				break;
			case "userexists": Notification.notify(WEStatus.STATUS_WARNING, "User already exists, please choose a different username.");
				break;
		}
	}
	
	public RequestAccountCommand(final String username, final String name, final String email,
			final String school, final String comments)
	{
		command = ProxyCommands.RequestAccount;
		addArgument("username", username);
		addArgument("name", name);
		addArgument("email", email);
		addArgument("school", school);
		addArgument("comments", comments);
		
		this.username = username;
		this.name = name;
		this.email = email;
		this.school = school;
		this.comments = comments;
	}
	
}
