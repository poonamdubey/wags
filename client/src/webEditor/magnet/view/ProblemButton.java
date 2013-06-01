package webEditor.magnet.view;

import com.google.gwt.user.client.ui.Button;

/**
 * 
 * A button that has an id associated with it.
 * 
 * This id corresponds to the id in the appropriate table in the database.
 * Used for MagnetProblems and FlowProblems.
 *
 */
public class ProblemButton extends Button {
	private int id;
	private String title;

	public ProblemButton(String title, int id) {
		super(title);
		this.id = id;
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public int getID() {
		return id;
	}

}
