package wags.ProxyFramework;

import wags.Notification;
import wags.WEStatus;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.ListBox;

public class DeleteExerciseCommand extends AbstractServerCall {

	private ListBox exercises;
	@Override
	
	protected void handleResponse(Response response) 
	{
		WEStatus status = new WEStatus(response);
		Notification.notify(status.getStat(), status.getMessage());
		AbstractServerCall cmd = new GetVisibleExercisesCommand(exercises);
		cmd.sendRequest();
	}
	
	public DeleteExerciseCommand(String ex, ListBox exercises)
	{
		command = ProxyCommands.DeleteExercise;
		this.exercises = exercises;
		addArgument("title", ex);
	}
}
