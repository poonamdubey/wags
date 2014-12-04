package wags.views.concrete;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

import wags.Common.Presenter;
import wags.presenters.interfaces.AccountPagePresenter;
import wags.views.interfaces.AccountPageView;


public class AccountPage extends Composite implements AccountPageView {

	
	private static AccountPageUiBinder uiBinder = GWT.create(AccountPageUiBinder.class);
	
	interface AccountPageUiBinder extends UiBinder<Widget, AccountPage> {
	}

	@UiField Paragraph accountText;
	@UiField ValueBoxBase<String> username;
	@UiField ValueBoxBase<String> name;
	@UiField ValueBoxBase<String> email;
	@UiField ValueBoxBase<String> school;
	@UiField ValueBoxBase<String> comments;
	@UiField Button submit;
	
	private AccountPagePresenter presenter;
	
	public AccountPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter)
	{
		this.presenter = (AccountPagePresenter) presenter;
	}
	
	@Override
	public boolean hasPresenter()
	{
		return this.presenter != null;
	}
	
	@Override
	public Presenter getPresenter()
	{
		return this.presenter;
	}
	
	@Override
	public UIObject getAccountText()
	{
		return accountText;
	}
	
	@Override
	public ValueBoxBase<String> getUsername()
	{
		return username;
	}
	
	@Override
	public ValueBoxBase<String> getName()
	{
		return name;
	}
	
	@Override
	public ValueBoxBase<String> getEmail()
	{
		return email;
	}
	
	@Override
	public ValueBoxBase<String> getSchool()
	{
		return school;
	}
	
	@Override
	public ValueBoxBase<String> getComments()
	{
		return comments;
	}
	
	@Override
	public UIObject getButton()
	{
		return submit;
	}
	
	@UiHandler("submit")
	void onSubmitClick(ClickEvent event)
	{
		presenter.onSubmitClick();
	}
	
	@Override
	public void setData(List<String> data)
	{
		
	}

	@Override
	public boolean isAdmin() {
		return false;
	}
}
