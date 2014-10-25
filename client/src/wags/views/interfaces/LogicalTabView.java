package wags.views.interfaces;

import wags.Common.View;
import wags.admin.AssignedPanel;
import wags.admin.ButtonPanel;
import wags.admin.CheckBoxPanel;

public interface LogicalTabView extends View  {

	ButtonPanel btnPanelSubjects();
	ButtonPanel btnPanelGroups();
	CheckBoxPanel chkPanelExercises();
	AssignedPanel assigned(); 
	AssignedPanel selected();
}
