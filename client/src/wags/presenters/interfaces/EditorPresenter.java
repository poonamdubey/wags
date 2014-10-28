package wags.presenters.interfaces;

import wags.Common.Presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TreeItem;

public interface EditorPresenter extends Presenter {

	void addLineNumbers();
	String createUserViewableCode();
	void saveCurrentCode();
	void onSubmitClick();
	void commandBarVisible(boolean visible);
	String getPath(TreeItem i);
	void initializeAutosaving();
	void handleInvisibility(int vis);
	void onDescClick(ClickEvent event);
}
