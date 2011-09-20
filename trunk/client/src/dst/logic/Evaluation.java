package dst.logic;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

import dst.client.EdgeParent;
import dst.client.Node;

public abstract class Evaluation implements IsSerializable
{
	protected String errorMessage;
	
	public Evaluation()
	{
		errorMessage = "";
	}
	
	public abstract String evaluate(String[] arguments, ArrayList<Node> nodes, ArrayList<EdgeParent> edges);
}
