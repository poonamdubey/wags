package wags.views.concrete;

import wags.Common.Presenter;
import wags.presenters.interfaces.WagsPresenter;
import wags.views.interfaces.WagsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class WagsViewImpl extends Composite implements WagsView
{

	private static WagsUiBinder uiBinder = GWT.create(WagsUiBinder.class);

	interface WagsUiBinder extends UiBinder<Widget, WagsViewImpl>{}
	
	@UiField Panel center;
	@UiField UIObject Home;
	@UiField UIObject navCollapse;
	//@UiField Label HomeOut;
	@UiField UIObject admin;
	@UiField UIObject problems;
	@UiField UIObject logout;
	@UiField UIObject logicalProblemManagement;
	@UiField UIObject logicalProblemCreation;
	@UiField UIObject magnetProblemManagement;
	@UiField UIObject magnetProblemCreation;
	@UiField UIObject progProblemManagement;
	@UiField UIObject studentManagement;
	@UiField UIObject review;

	private WagsPresenter presenter;
	
	/**
	 * Constructor
	 * 
	 * -Builds Wags interface once logged in
	 */
	public WagsViewImpl()
	{
		initWidget(uiBinder.createAndBindUi(this));
		//north.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
	}

	@UiHandler("Home")
	void onHomeClick(ClickEvent event) {
		presenter.onHomeClick();
	}

	@UiHandler("problems")
	void onMagnetsClick(ClickEvent event) {
		presenter.onProblemsClick();
	}

	@UiHandler("logout") 
	void onLogoutClick(ClickEvent event) {
		presenter.onLogoutClick();
	}

	@UiHandler("logicalProblemManagement")
	void onLogicalManagementClick(ClickEvent event) {
		presenter.onLogicalManagementClick();
	}
	
	@UiHandler("logicalProblemCreation")
	void onLogicalCreationClick(ClickEvent event) {
		presenter.onLogicalCreationClick();
	}
	
	@UiHandler("magnetProblemManagement")
	void onMagnetManagementClick(ClickEvent event) {
		presenter.onMagnetManagementClick();
	}
	
	@UiHandler("magnetProblemCreation")
	void onMagnetCreationClick(ClickEvent event) {
		presenter.onMagnetCreationClick();
	}
	
	@UiHandler("progProblemManagement")
	void onProgrammingProblemCreation(ClickEvent event) {
		presenter.onProgrammingManagementClick();
	}
	
	@UiHandler("studentManagement")
	void onStudentManagementClick(ClickEvent event) {
		presenter.onStudentManagementClick();
	}
	
	@UiHandler("review")
	void onReviewClick(ClickEvent event) {
		presenter.onReviewClick();
	}
	
	@Override
	public UIObject getHomeAnchor() {
		return Home;
	}

	@Override
	public UIObject getLogoutAnchor() {
		return logout;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (WagsPresenter) presenter;
	}
	
	@Override
	public boolean hasPresenter() {
		return presenter != null;
	}

	@Override
	public Presenter getPresenter() {
		return presenter;
	}

	@Override
	public Panel getCenterPanel() {
		return center;
	}

	@Override
	public UIObject getProblemsAnchor() {
		return problems;
	}

	@Override
	public UIObject getAdminAnchor() {
		return admin;
	}

	@Override
	public UIObject getNavCollapse() {
		return navCollapse;
	}
}
