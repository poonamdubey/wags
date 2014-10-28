package wags.ProxyFramework;

import wags.MagnetProblem;
import wags.WEStatus;
import wags.magnet.view.MagnetProblemCreator;
import wags.magnet.view.RefrigeratorMagnet;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GetMagnetProblemCommand extends AbstractServerCall {

	int id;
	AcceptsOneWidget page;
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		MagnetProblem magProblem = (MagnetProblem) status.getObject();
		MagnetProblemCreator creator = new MagnetProblemCreator();
		RefrigeratorMagnet problem = creator.makeProblem(magProblem);
		page.setWidget(problem);
	}
	
	public GetMagnetProblemCommand(final int id, final AcceptsOneWidget page)
	{
		command = ProxyCommands.GetMagnetProblem;
		addArgument("id", "" + id);
		this.id = id;
		this.page = page;
	}
}
