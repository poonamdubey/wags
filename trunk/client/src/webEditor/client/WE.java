package webEditor.client;

import webEditor.client.view.Editor;
import webEditor.client.view.Login;
import webEditor.client.view.Registration;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * WE
 *
 * This is the entry point for this GWT application.
 * Check if user is currently logged in or not.
 * If they are logged in direct them to appropriate view
 * according to hash (#) in url.
 * If they are not logged in show them to the login view.
 *
 * @author Robert Bost <bostrt@appstate.edu>
 *
 */

public class WE implements EntryPoint 
{
	public void onModuleLoad() 
	{
		/** Get location name **/
		String hash = Location.getParameter("loc");

		// Put the contents of these if statements in functions
		if(hash.equals("register")){
			register();
		}
		else if(hash.equals("editor")){
			editor();
		}
		else if(hash.equals("login")){
			login();
		}
		else{
			/* 
			 * Default. No location given in URL. 
			 * Attempt to get user's details.
			 */
			String isLoggedInURL = Proxy.getBaseurl()+"?cmd=GetUserDetails";
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(isLoggedInURL));
			try{
				@SuppressWarnings("unused")
				Request req = builder.sendRequest(null, new RequestCallback(){
					@Override
					public void onResponseReceived(Request request,	Response response) {
						WEStatus status = new WEStatus(response);
						if(status.getStat() == WEStatus.STATUS_ERROR){
							/* No one is logged in. Redirect to login. */
							RootPanel root = RootPanel.get("main-content");
							root.add(new Login());
						}else if(status.getStat() == WEStatus.STATUS_SUCCESS){
							/* User is logged in. Show Editor. */
							RootLayoutPanel root = RootLayoutPanel.get();
							root.add(new Editor());
						}
					}
					@Override
					public void onError(Request request, Throwable exception) {
						// TODO: Show this message in a notification area.
						Window.alert("Could not connect to server.");
					}
				});
			}catch(RequestException e){
				Window.alert(e.getMessage());
				e.printStackTrace();
			}			
		}
	}
	
	private static void register(){
		RootPanel root = RootPanel.get("main-content");
		root.add(new Registration());		
	}
	
	private static void editor(){
		// TODO: Is user logged in?
		RootLayoutPanel root = RootLayoutPanel.get();
		root.add(new Editor());		
	}
	
	private static void login(){
		// Is user already logged in?
		RootPanel root = RootPanel.get("main-content");
		root.add(new Login());		
	}
}
