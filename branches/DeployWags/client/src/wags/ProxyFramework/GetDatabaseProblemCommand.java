package wags.ProxyFramework;

import wags.WEStatus;
import wags.database.DatabasePanel;
import wags.database.DatabaseProblem;

import com.google.gwt.http.client.Response;

/**
 * @author Dakota Murray
 * 
 * Server File: GetDatabaseProblem.php
 * Arguments:
 * 				id: an id of the database problem
 * 				dbPanel: the DatabasePanel to modify
 * Method: GET
 * 
 *
 */
public class GetDatabaseProblemCommand extends AbstractServerCall {

	private DatabasePanel dbPanel;
	
	@Override
	protected void handleResponse(Response response) {
		WEStatus status = new WEStatus(response);
		DatabaseProblem dbProblem = (DatabaseProblem) status.getObject();
		dbPanel.initialize(dbProblem);

	}

	public GetDatabaseProblemCommand(int id, final DatabasePanel dbPanel)
	{
		addArgument("id", "" + id);
		this.dbPanel = dbPanel;
		command = ProxyCommands.GetDatabaseProblem;
		
	}
}
