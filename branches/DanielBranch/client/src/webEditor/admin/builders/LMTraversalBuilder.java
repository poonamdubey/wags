package webEditor.admin.builders;

import webEditor.Genre;
import webEditor.InsertMethod;
import webEditor.NodeType;

public class LMTraversalBuilder extends LMBuilder {

	public LMTraversalBuilder(Genre genre, InsertMethod insertMethod,
			NodeType nodeType, boolean edgesRemovable, boolean nodesDraggable,
			int edgeRules, int groupId) {
		super(genre, insertMethod, nodeType, edgesRemovable, nodesDraggable, edgeRules,
				groupId);
	}

	@Override
	public void buildArgs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildPos() {
		// TODO Auto-generated method stub

	}

	@Override
	public String uploadString() {
		// TODO Auto-generated method stub
		return null;
	}

}
