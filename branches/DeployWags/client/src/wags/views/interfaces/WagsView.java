package wags.views.interfaces;

import wags.Common.View;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;

public interface WagsView extends View {
	UIObject getHomeAnchor();
	UIObject getProblemsAnchor();
	UIObject getAdminAnchor();
	UIObject getLogoutAnchor();
	Panel getCenterPanel();
	UIObject getNavCollapse();
}
