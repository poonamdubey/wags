package wags.views.interfaces;

import wags.Common.View;
import wags.admin.AssignedPanel;
import wags.admin.ButtonPanel;
import wags.admin.CheckBoxPanel;

public interface MagnetTabView extends View {

	public ButtonPanel getBtnPanelGroups();
	public CheckBoxPanel getChkPanelExercises();
	public AssignedPanel getSelected();
	public AssignedPanel getAssigned();
}
