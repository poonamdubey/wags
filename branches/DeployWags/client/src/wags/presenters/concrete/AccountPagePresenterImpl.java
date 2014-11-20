package wags.presenters.concrete;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;

import wags.presenters.interfaces.AccountPagePresenter;
import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.RequestAccountCommand;
import wags.views.interfaces.AccountPageView;

public class AccountPagePresenterImpl implements AccountPagePresenter, AcceptsOneWidget {

	private AccountPageView acc;
	private boolean bound = false;
	
	public AccountPagePresenterImpl(final AccountPageView view)
	{
		acc = view;
		bind();
	}
	
	@Override
	public void go(HasWidgets container)
	{
		container.clear();
		container.add(acc.asWidget());
	}
	
	@Override
	public void bind()
	{
		acc.setPresenter(this);
		bound = true;
	}
	
	@Override
	public boolean bound() {
		return bound;
	}
	
	@Override
	public void onSubmitClick()
	{
		String username = acc.getUsername().getText();
		String name = acc.getName().getText();
		String email = acc.getEmail().getText();
		String school = acc.getSchool().getText();
		String comments = acc.getComments().getText();
		AbstractServerCall cmd = new RequestAccountCommand(username, name, email, school, comments);
		cmd.sendRequest();
	}
	
	@Override
	public void update(List<String> data) {
		
	}
	
	@Override
	public void setWidget(IsWidget w)
	{
		
	}
	
}
