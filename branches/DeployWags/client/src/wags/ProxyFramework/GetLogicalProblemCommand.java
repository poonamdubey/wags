package wags.ProxyFramework;

import wags.LogicalMicrolab;
import wags.Notification;
import wags.WEStatus;
import wags.logical.DataStructureTool;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GetLogicalProblemCommand extends AbstractServerCall {

	AcceptsOneWidget page;
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		
		if(status.getStat() == WEStatus.STATUS_ERROR){
			Notification.notify(status.getStat(), status.getMessage());
			return;
		}
		
		//DataStructureTool dst = new DataStructureTool();
		LogicalMicrolab logMicro = (LogicalMicrolab) status.getObject();
		DataStructureTool.initialize(logMicro.getProblem(), page);
		/*Notification.notify(status.getStat(), "Loaded from server");*/
	}

	public GetLogicalProblemCommand(String title, AcceptsOneWidget page)
	{
		addArgument("title", title);
		this.page = page;
		command = ProxyCommands.GetLogicalMicrolab;
		
	}
}
