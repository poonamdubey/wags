package microlabs.dst.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

import microlabs.dst.client.EdgeParent;
import microlabs.dst.client.Node;

public abstract class Evaluation implements IsSerializable
{
	protected String errorMessage;
	
	public Evaluation()
	{
		errorMessage = "";
	}
	
	public abstract String evaluate(String problemName, String[] arguments, ArrayList<Node> nodes, ArrayList<EdgeParent> edges);
	
	public void emailResult(String problemName)
	{
		EmailService emailService = new EmailService();
		emailService.email(problemName);
		
	}
}
