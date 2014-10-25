package wags.presenters.interfaces;

import com.google.gwt.event.dom.client.KeyPressEvent;

import wags.Common.Presenter;

public interface DefaultPagePresenter extends Presenter {
	public void onProblemsClick();
	void onLoginClick();
	void onKeyPressForUsername(KeyPressEvent event);
	void onKeyPressForPassword(KeyPressEvent event);
}
