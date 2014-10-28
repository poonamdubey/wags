package wags.views.interfaces;

import wags.Reviewer;
import wags.Common.View;
import wags.admin.ReviewPanel;

import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.SubmitButton;

public interface ReviewTabView extends View {

	ReviewPanel getRVLogPanel();
	ReviewPanel getRVMagPanel();
	SubmitButton getBtnCompReview();
	FormPanel getFormCompReview();
	Reviewer getLogHandler();
	Reviewer getMagHandler();

}
