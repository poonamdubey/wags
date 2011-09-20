package dst.client;

import java.util.List;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ProblemResult implements IsSerializable
{
	private String problemName;
	private String problemText;
	private List<Node> nodes; //used to be serialnode and serial edge
	private List<EdgeUndirected> edges;
	private String currFeedback;
	private int attemptNum;
	
	private ProblemResult(){}

	public ProblemResult(String problemName, String problemText, List<Node> nodes, List<EdgeUndirected> edges, String currFeedback, int attemptNum)
	{
		this.problemName = problemName;
		this.problemText = problemText;
		this.nodes = nodes;
		this.edges = edges;
		this.currFeedback = currFeedback;
		this.attemptNum = attemptNum;
	}
	
	/**
	 * @return the problemName
	 */
	public String getProblemName() {
		return problemName;
	}

	/**
	 * @param problemName the problemName to set
	 */
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	/**
	 * @param problemText the problemText to set
	 */
	public void setProblemText(String problemText) {
		this.problemText = problemText;
	}

	/**
	 * @return the problem
	 */
	public String getProblemText() {
		return problemText;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public List<EdgeUndirected> getEdges() {
		return edges;
	}


	public void setEdges(List<EdgeUndirected> edges) {
		this.edges = edges;
	}

	/**
	 * @return the currFeedback
	 */
	public String getCurrFeedback() {
		return currFeedback;
	}

	/**
	 * @param currFeedback the currFeedback to set
	 */
	public void setCurrFeedback(String currFeedback) {
		this.currFeedback = currFeedback;
	}

	/**
	 * @return the attemptNum
	 */
	public int getAttemptNum() {
		return attemptNum;
	}

	/**
	 * @param attemptNum the attemptNum to set
	 */
	public void setAttemptNum(int attemptNum) {
		this.attemptNum = attemptNum;
	}
}
