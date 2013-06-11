package webEditor.admin.builders;

import com.google.gwt.user.client.Window;

public class NH_Traversal extends NodeHandler {
	int nodeX = 10, nodeY = 10;
	final int NODE_WIDTH = 40;
	
	public NH_Traversal(BasicCanvas canvas) {
		this.parent = canvas;
	}

	@Override
	/**
	 * addNode
	 * @param value - The value of the node that is displayed
	 * Adds a node to the BasicCanvas and registers it with the BasicCanvas.
	 * Relies on "positionNode(BasicNode)" to place node
	 * Calls "update()" in case parent LMDisplay must be notified of change
	 */
	public void addNode(String value) {
		// For traversals, we're going to force unique nodes
		for(BasicNode node: parent.nodes){
			if(node.value.equals(value)){
				Window.alert("Duplicate nodes not allowed!");
				return;
			}
		}
		
		BasicNode node = new BasicNode(value, parent);
		parent.dragger.makeDraggable(node);
		parent.nodes.add(node);
		
		positionNode(node);
		update();
	}

	/**
	 * positionNode
	 * @param node - Node to be added
	 * Determines where the new node will be placed on the canvas. 
	 */
	private void positionNode(BasicNode node){
		// May be modified in future
		// Sets position of current node
		node.setPosition(nodeX, nodeY);
		parent.canvasPanel.add(node, node.xPos, node.yPos);
		
		// Finds position for next node
		nodeX += NODE_WIDTH * 1.5;
		if(nodeX + NODE_WIDTH > parent.canvas.getWidth()){
			nodeX = 10;
		}
	}
	
	/**
	 * deleteNode
	 * @param value - The value of the node to be deleted
	 * Removes named node (and corresponding edges) from BasicCanvas, if node 
	 * exists.  Calls "update" in case parent LMDisplay needs to be notified
	 * of change.
	 */
	public void deleteNode(String value){
		for(BasicNode node: parent.nodes){
			if(node.value.equals(value)){
				node.deleteEdges();
				node.setVisible(false);
				parent.nodes.remove(node);
			}
		}
		update();
	}
}
