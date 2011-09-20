package dst.logic;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.IsSerializable;
import dst.client.DSTConstants;
import dst.client.EdgeParent;
import dst.client.Node;

public class AddEdgeRules implements IsSerializable
{
	public String checkFirstNode(Node node, ArrayList<Node> nodes, ArrayList<EdgeParent> edges)
	{
		return DSTConstants.CORRECT;
	}
	
	public String checkSecondNode(Node firstNode, Node secondNode, ArrayList<Node> nodes, ArrayList<EdgeParent> edges)
	{
		return DSTConstants.CORRECT;
	}
}
