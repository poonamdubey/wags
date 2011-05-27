package edu.appstate.bostrt.WE.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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

import edu.appstate.bostrt.WE.client.view.Editor;
import edu.appstate.bostrt.WE.client.view.Login;
import edu.appstate.bostrt.WE.client.view.Registration;

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
		String hash = Location.getHash();
		// Put the contents of these if statements in functions
		if(hash.equals("#register")){
			register();
		}
		else if(hash.equals("#editor")){
			editor();
		}
		else if(hash.equals("#login")){
			login();
		}
		else{
			// DEFAULT
			// Attempt to get user's details.
			String isLoggedInURL = "http://student.cs.appstate.edu/~bostrt/wags/index.php?cmd=GetUserDetails";
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(isLoggedInURL));
			try{
				@SuppressWarnings("unused")
				Request req = builder.sendRequest(null, new RequestCallback(){
					@Override
					public void onResponseReceived(Request request,	Response response) {
						Window.alert(response.toString());
						WEStatus status = new WEStatus(response);
						if(status.getStat() == WEStatus.STATUS_ERROR){
							// No one is logged in.
							RootPanel root = RootPanel.get("main-content");
							root.add(new Login());
						}else if(status.getStat() == WEStatus.STATUS_SUCCESS){
							// Success status returned from server.
							// User is logged in.
							// TODO: Switch over HASH(#) in the URL.
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
