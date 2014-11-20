package wags.Common;

/**
 * @author  : Dakota Murray
 * @version : 21 July 2014
 * 
 * Controls all page transitions of the web by means of GWT's native History class. Whenever a page transition 
 * takes place, the class triggering the transition adds a new history token to the url. The AppController notices
 * the change in the url and responds by executing the page transition. 
 * 
 * Page transitions are not specific to each presenter and thus the AppController decouples the specific presenter logic 
 * from the more general page transition logic.
 * 
 * In order to add a new view the following steps must be carried out in the AppController:
 *     -Ensure that there is a token in the Tokens class corresponding to the new page
 *     -Add any special rules for the page transition inside the onValueChange method (redirects, privilege checks, etc)
 *     -Create a new method that when called will load the new page (view and presenter) and set it as the current page,
 *      use the other such method as a guide.
 *     -Create a new case corresponding to the new page for the switch statement inside the laodPage method which when trigered
 *      will call the method to load the new page.
 *     -Congratulations! A new page transition has just been created!
 *  
 * Send any questions, comments, or concerns to murrayds@email.appstate.edu
 *  
 */
import java.util.HashMap;

import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.BuildDatabaseCommand;
import wags.ProxyFramework.CheckPasswordCommand;
import wags.ProxyFramework.GetMagnetProblemCommand;
import wags.admin.ProblemCreationPanel;
import wags.presenters.concrete.AccountPagePresenterImpl;
import wags.presenters.concrete.DefaultPagePresenterImpl;
import wags.presenters.concrete.EditorPresenterImpl;
import wags.presenters.concrete.LMEditTabPresenterImpl;
import wags.presenters.concrete.LogicalTabPresenterImpl;
import wags.presenters.concrete.LoginPresenterImpl;
import wags.presenters.concrete.MagnetTabPresenterImpl;
import wags.presenters.concrete.ProblemCreationPanelPresenterImpl;
import wags.presenters.concrete.ProblemPagePresenterImpl;
import wags.presenters.concrete.ProgrammingTabPresenterImpl;
import wags.presenters.concrete.ReviewTabPresenterImpl;
import wags.presenters.concrete.StudentTabPresenterImpl;
import wags.presenters.concrete.WagsPresenterImpl;
import wags.presenters.interfaces.AccountPagePresenter;
import wags.presenters.interfaces.DefaultPagePresenter;
import wags.presenters.interfaces.EditorPresenter;
import wags.presenters.interfaces.LMEditTabPresenter;
import wags.presenters.interfaces.LogicalTabPresenter;
import wags.presenters.interfaces.LoginPresenter;
import wags.presenters.interfaces.MagnetTabPresenter;
import wags.presenters.interfaces.ProblemCreationPanelPresenter;
import wags.presenters.interfaces.ProblemPagePresenter;
import wags.presenters.interfaces.ProgrammingTabPresenter;
import wags.presenters.interfaces.ReviewTabPresenter;
import wags.presenters.interfaces.StudentTabPresenter;
import wags.views.concrete.AccountPage;
import wags.views.concrete.DefaultPage;
import wags.views.concrete.Editor;
import wags.views.concrete.LMEditTab;
import wags.views.concrete.LogicalTab;
import wags.views.concrete.Login;
import wags.views.concrete.MagnetTab;
import wags.views.concrete.ProgrammingTab;
import wags.views.concrete.ReviewTab;
import wags.views.concrete.StudentTab;
import wags.views.concrete.WagsViewImpl;
import wags.views.interfaces.ProblemPageView;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class AppController implements ValueChangeHandler<String> {

	public AppController()
	{
		bind();
	}
	
	/**
	 * Binds this class to the History change handler thus allowing the AppController's onValueChanged
	 * method to be called when the underlying History class notices a change. 
	 */
	private void bind()
	{
		History.addValueChangeHandler(this);
	}
	
	/**
	 * Called whenever GWT's History class notices a change (ie: the url is changed). Is the entry point
	 * for all page transitions. Special page transition rules (privilege checks, redirects, etc) go in here.
	 * 
	 * @param event An event which is triggered when the History value changes. The event object contains the 
	 * 				updated url (the new token) which is used to carry out page transitions. 
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String url = event.getValue();
		boolean isLoggedIn = ClientFactory.getAppModel().isLoggedIn();
		boolean isAdmin = ClientFactory.getAppModel().isAdmin();
		
		String[] args = url.split("&");
		String token = args[0];
		String arg = args[1];
		if (token == null) {
			token = Tokens.DEFAULT;
		}
		if (! isLoggedIn && token != Tokens.ACCOUNT) {
			token = Tokens.DEFAULT;
		}
		if ( token.startsWith(Tokens.DEFAULT)) {
			if ( isLoggedIn ) {
				token = Tokens.DEFAULT;
			}
			else {
				token = Tokens.DEFAULT;
			}
		}
		else if(token.startsWith(Tokens.ADMIN) && !isAdmin) {
			Window.alert("You do not have admin privileges");
			token = Tokens.DEFAULT;
		}
		
		WagsViewImpl main = ClientFactory.getWagsView();
		AcceptsOneWidget pres = new WagsPresenterImpl(main);
		loadPage(token, pres, arg);
		RootLayoutPanel root = RootLayoutPanel.get();
		root.clear();
		root.add(main);
	}
	
	/**
	 * Simply delegates the page transition logic to the appropriate method based on the token passed in
	 * 
	 * @param token A History token added to the url
	 * @param page  The main application page (Wags.java and corresponding files)
	 * @param arg   An optional String argument used for transitions where more specific information is needed
	 */
	public void loadPage(String token, AcceptsOneWidget page, String arg) 
	{
		switch(token)
		{
		case Tokens.PROBLEMS:
			loadProblems(page);
			break;
		case Tokens.LOGICALCREATION:
			loadLogicalProblemCreation(page);
			break;
		case Tokens.LOGICALMANAGEMENT:
			loadLogicalProblemManagement(page);
			break;
		case Tokens.MAGNETMANAGEMENT:
			loadMagnetProblemManagement(page);
			break;
		case Tokens.MAGNETCREATION:
			loadMagnetProblemCreation(page);
			break;
		case Tokens.MANAGESECTION:
			loadSectionManagement(page);
			break;
		case Tokens.MANAGESTUDENT:
			loadStudentManagement(page);
			break;
		case Tokens.PROGRAMMINGMANAGEMENT:
			loadProgrammingManagement(page);
			break;
		case Tokens.REVIEW:
			loadReviewTab(page);
			break;
		case Tokens.MAGNETPROBLEM:
			loadMagnetProblem(page, arg);
			break;
		case Tokens.ACCOUNT:
			loadAccountCreate(page);
			break;
		default:
			loadDefaultPage(page);
		}	
	}
	
	/**
	 * A method called upon login and logout which will set the necessary details of the application
	 * wide model with updated information about user status. 
	 * 
	 * @param map A HashMap where the keys are made up of the names of the arguments and the values are
	 * 		  	  the values of those arguments specific to the current user. 
	 */
	public static void setUserDetails(HashMap<String, String> map)
	{
		AppModel model = ClientFactory.getAppModel();
		model.setId( new Integer(map.get("id")), false);
		model.setUsername( map.get("username"), false);
		model.setIsLoggedIn(true, false);
		model.setIsAdmin(map.get("admin").equals("1"), false);
		model.notifyObservers();
	}
	
	
	/**
	 * Below are all method which handle specific page transition logic. There is no point in commenting each one as 
	 * most follow similar logic. The basic idea is to either instantiate or retrieve an existing view, give it a presenter
	 * if it does not already have one, and make it the current page. 
	 */
	//As of right now not all of these transitions have been implemented using the new MVP pattern
	
	
	private void loadMagnetProblem(AcceptsOneWidget page, String arg) {
		String[] arg_array = arg.split("=");
		int id = Integer.parseInt(arg_array[1]);
		AbstractServerCall cmd = new GetMagnetProblemCommand(id, page);
		cmd.sendRequest();
	}

	public void loadReviewTab(AcceptsOneWidget page) 
	{
		ReviewTab view = ClientFactory.getReviewTab();
		if (!view.hasPresenter()) {
			ReviewTabPresenter pres = new ReviewTabPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	public void loadLogicalProblemManagement(AcceptsOneWidget page)
	{
		LogicalTab view = ClientFactory.getLogicalManagementTab();
		if (!view.hasPresenter()) {
			LogicalTabPresenter pres = new LogicalTabPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	public void loadProblems(AcceptsOneWidget page) {
		ProblemPageView view = ClientFactory.getMagnetPageView();
		if (!view.hasPresenter()) {
			ProblemPagePresenter pres = new ProblemPagePresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	public void loadLogicalProblemCreation(AcceptsOneWidget page)
	{
		LMEditTab view = ClientFactory.getLogicalCreationTab();
		if (!view.hasPresenter()) {
			LMEditTabPresenter pres = new LMEditTabPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	
	public void loadMagnetProblemManagement(AcceptsOneWidget page)
	{		
		MagnetTab view = ClientFactory.getMagnetManagementTab();
		if (!view.hasPresenter()) {
			MagnetTabPresenter pres = new MagnetTabPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	// TODO implement in MVP (save this one, we are make a new design after all)
	public void loadMagnetProblemCreation(AcceptsOneWidget page)
	{
		ProblemCreationPanel view = ClientFactory.getMagnetProblemCreationTab();
		if (!view.hasPresenter()) {
			ProblemCreationPanelPresenter pres = new ProblemCreationPanelPresenterImpl(view);
			pres.bind();

		}
		page.setWidget(view);
	}
	
	public void loadSectionManagement(AcceptsOneWidget page)
	{
		ProblemCreationPanel view = ClientFactory.getMagnetProblemCreationTab();
		if (!view.hasPresenter()) {
			ProblemCreationPanelPresenter pres = new ProblemCreationPanelPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	public void loadProgrammingManagement(AcceptsOneWidget page)
	{
		ProgrammingTab view = ClientFactory.getProgrammingTab();
		if (!view.hasPresenter()) {
			ProgrammingTabPresenter pres = new ProgrammingTabPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	public void loadStudentManagement(AcceptsOneWidget page)
	{
		StudentTab view = ClientFactory.getStudentManagementTab();
		if (!view.hasPresenter()) {
			StudentTabPresenter pres = new StudentTabPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);

	}
	
	public void loadLoginPage(AcceptsOneWidget page)
	{
		Login view = ClientFactory.getLoginView();
		if ( !view.hasPresenter()) {
			LoginPresenter pres = new LoginPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	public void loadAccountCreate(AcceptsOneWidget page)
	{
		AccountPage view = ClientFactory.getAccountView();
		if (!view.hasPresenter()) {
			AccountPagePresenter pres = new AccountPagePresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}
	
	public void loadDefaultPage(AcceptsOneWidget page) {
		DefaultPage view = ClientFactory.getDefaultView();
		if ( !view.hasPresenter()) {
			DefaultPagePresenter pres = new DefaultPagePresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
	}

	public void loadDST(AcceptsOneWidget page) {
		Editor view = ClientFactory.getEditorView();
		if ( !view.hasPresenter()) {
			EditorPresenter pres = new EditorPresenterImpl(view);
			pres.bind();
		}
		page.setWidget(view);
		
		//AbstractServerCall cmd = new BuildDSTCommand(page);
		//cmd.sendRequest();
	}
	

	
	//TODO implement MVP (look at the magnet page as a guide)
	public void loadDatabasePage(AcceptsOneWidget page) {
		AbstractServerCall cmd = new BuildDatabaseCommand(page);
		cmd.sendRequest();
	}

}
