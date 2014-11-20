package wags.views.interfaces;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.ValueBoxBase;

import java.util.List;

import wags.Common.View;

public interface AccountPageView extends View {
	
	public UIObject getAccountText();
	ValueBoxBase<String> getUsername();
	ValueBoxBase<String> getName();
	ValueBoxBase<String> getEmail();
	ValueBoxBase<String> getSchool();
	ValueBoxBase<String> getComments();
	public UIObject getButton();
	void setData(List<String> data);

}
