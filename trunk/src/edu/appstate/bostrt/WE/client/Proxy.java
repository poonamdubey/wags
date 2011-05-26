package edu.appstate.bostrt.WE.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;

import edu.appstate.bostrt.WE.client.view.CodeEditor;
import edu.appstate.bostrt.WE.client.view.Editor;
import edu.appstate.bostrt.WE.client.view.FileBrowser;
import edu.appstate.bostrt.WE.client.view.Login;
import edu.appstate.bostrt.WE.client.view.Notification;

public class Proxy
{
	private static final String baseURL = "http://localhost:80/~robert/we_server/index.php";
	private static final String getFileContents = baseURL+"?cmd=GetFileContents";
	private static final String saveFileContents = baseURL+"?cmd=SaveFileContents";
	private static final String deleteFile = baseURL+"?cmd=DeleteFile";
	private static final String getFileListing = baseURL+"?cmd=GetFileListing";
	private static final String logout = baseURL+"?cmd=Logout";
	private static final String login = baseURL+"?cmd=Login";
	
	private static Timer pleaseHold(String holdMessage)
	{
		Element parent = DOM.getElementById("notification-area");
		Notification.clear();
		final Label l = new Label(holdMessage);
		parent.appendChild(l.getElement());
		Timer t = new Timer() {
			@Override
			public void run()
			{
				l.removeFromParent();
			}
		};

		return t;
	}
	
	/**
	 * Get the contents of a file with the given name from server.
	 * Put those contents in the passed CodeEditor.
	 */
	public static void getFileContents(String fileName, final CodeEditor editor){
		String urlCompl = getFileContents+"&name="+fileName.trim();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, urlCompl);
		try {
			@SuppressWarnings("unused")
			Request r = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response)
				{
					editor.setContents(response.getText());
				}
				
				@Override
				public void onError(Request request, Throwable exception)
				{
					
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(final String fileName){
		String urlCompl = deleteFile+"&name="+fileName.trim();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, urlCompl);
		try {
			@SuppressWarnings("unused")
			Request r = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response)
				{
					
				}
				
				@Override
				public void onError(Request request, Throwable exception)
				{
					Window.alert(fileName);
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the file with the given name on server side.
	 * For now this is just going to take a string with all
	 * the file's contents. Later I will make this a diff of
	 * some sort.
	 * TODO: Read above.text
	 */
	public static void saveFile(String fileName, String contents)
	{
		final Timer t = pleaseHold("Saving...");
		String completeURL = saveFileContents+"&name="+fileName.trim()+"&contents="+contents;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(completeURL));
		try{
			@SuppressWarnings("unused")
			Request r = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response)
				{
					WEStatus status = new WEStatus(response);
					Notification.notify(WEStatus.STATUS_SUCCESS, status.getMessage());
					t.schedule(0);
				}
				
				@Override
				public void onError(Request request, Throwable exception)
				{
					Notification.notify(WEStatus.STATUS_ERROR, "Error saving file. "+exception.getMessage());
				}
			});
		}catch(RequestException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a file listing from server. Add each file to file browser tree.
	 * TODO: Change to get file listing from base path.
	 */
	public static void loadFileListing(final FileBrowser fileBrowser, final String path)
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, getFileListing);
		try {
			@SuppressWarnings("unused")
			Request r = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response)
				{
					WEStatus status = new WEStatus(response);
					if(status.getStat() == WEStatus.STATUS_SUCCESS){
						fileBrowser.loadTree(status.getMessageArray());
						fileBrowser.openPath(path);
					}else{
						Notification.notify(WEStatus.STATUS_ERROR, "Error fetching file listing.");
					}
				}
				@Override
				public void onError(Request request, Throwable exception)
				{
					
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tell server we want to logout of WE.
	 */
	public static void logout()
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(logout));
		try{
			@SuppressWarnings("unused")
			Request r = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response)
				{
					Login l = new Login();
					l.go();
				}
				
				@Override
				public void onError(Request request, Throwable exception)
				{
					Notification.notify(WEStatus.STATUS_ERROR, exception.getMessage());
				}
			});
		}catch(RequestException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Tell server we want to login.
	 */
	public static void login(String username, String password)
	{
		String completeURL = login+"&username="+username.trim()+"&password="+password;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(completeURL));
		try {
			@SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO: Do something....
					WEStatus status = new WEStatus(response);
					if(status.getStat() == WEStatus.STATUS_SUCCESS){
						// Login successful.
						Editor e = new Editor();
						e.go();
					}else{
						Notification.notify(WEStatus.STATUS_ERROR, status.getMessage());
					}
				}
				@Override
				public void onError(Request request, Throwable exception) {
					Notification.notify(WEStatus.STATUS_ERROR, "Error occurred while connecting to server.");
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the greeting label.
	 * If user did not submit a name when they registered just 
	 * use their email address. 
	 */
	public static void getUsersName(final Label label)
	{
		String completeURL = baseURL+"?cmd=GetUserDetails";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(completeURL));
		try {
			@SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					WEStatus stat = new WEStatus(response);
					String first = stat.getMessageMapVal("firstName");
					if(first == null){
						// Use email address in greeting.
						first = stat.getMessageMapVal("email");
					}
					label.setText("Hello, "+first+"!");
				}
				@Override
				public void onError(Request request, Throwable exception) {
					Notification.notify(WEStatus.STATUS_ERROR, "Error fetching user details.");
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Rename a file. Update TreeItem. 
	 */
	public static void renameFile(String oldName, final String newName, final FileBrowser browser)
	{
		String url = baseURL+"?cmd=RenameFile&old="+oldName+"&new="+newName;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
		try{
			@SuppressWarnings("unused")
			Request r = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response)
				{
					// Quietly rebuild the file browser on success.
					WEStatus stat = new WEStatus(response);
					if(stat.getStat() == WEStatus.STATUS_SUCCESS){
						// Rebuild browser
						// TODO: Only rebuild tree part that contains changed item
						Proxy.loadFileListing(browser, newName);
						
					}else{
						Notification.notify(stat.getStat(), stat.getMessage());
					}
				}
				@Override
				public void onError(Request request, Throwable exception)
				{}
			});
		}catch(RequestException e){
			e.printStackTrace();
		}
	}
}
