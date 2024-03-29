package webEditor.client;

import dst.client.DataStructureTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.datanucleus.sco.backed.Collection;

import webEditor.client.view.Exercises;
import webEditor.client.view.FileBrowser;
import webEditor.client.view.Login;
import webEditor.client.view.Notification;
import webEditor.client.view.OutputReview;
import webEditor.client.view.Wags;

import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class Proxy
{
	private static DataStructureTool DST;
	private static final String baseURL = "http://localhost/public_html/WagsDSTServer/index.php";
	private static final String getFileContents = getBaseURL()+"?cmd=GetFileContents";
	private static final String saveFileContents = getBaseURL()+"?cmd=SaveFileContents";
	private static final String deleteFile = getBaseURL()+"?cmd=DeleteFile";
	private static final String getSections = getBaseURL() + "?cmd=GetSections";
	private static final String getFileListing = getBaseURL()+"?cmd=GetFileListing";
	private static final String submitFile = getBaseURL()+"?cmd=Review";
	private static final String logout = getBaseURL()+"?cmd=Logout";
	private static final String login = getBaseURL()+"?cmd=Login";
	private static final String registerURL = Proxy.getBaseURL()+"?cmd=RegisterUser";
	private static final String getExercises = getBaseURL()+"?cmd=GetExerciseList";
	private static final String getProblems = getBaseURL()+"?cmd=GetProblems";
	private static final String getProblemSkel = getBaseURL() + "?cmd=GetProblemSkel";
	private static final String getProblemNodes = getBaseURL() + "?cmd=GetProblemNodes.php";
	private static final String getProblemEdges = getBaseURL() + "?cmd=GetProblemEdges.php";
	private static final String getProblemResults = getBaseURL()+"?cmd=GetProblemResults";
	private static final String SaveProblemResult = getBaseURL()+"?cmd=SaveProblemResult";
		
	private static void holdMessage(String message){
		Element parent = DOM.getElementById("notification-area");
		Notification.clear();
		final Label l = new Label(message);
		parent.appendChild(l.getElement());
	}
	
	private static void clearMessage(){
		Notification.clear();
	}
	
	public static void setDST(DataStructureTool dst){
		DST = dst;
	}
	
	
	/**
	 * LOGIN
	 * The following methods all handling logging in the user
	 * and checking to see if the admin tab should be visible for them
	 */
	
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
					WEStatus status = new WEStatus(response);
					if(status.getStat() == WEStatus.STATUS_SUCCESS){
						// Login successful.
						Wags e = new Wags();
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

	public static void isAdmin(final TabLayoutPanel tabPanel){	
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getBaseURL()+"?cmd=IsAdmin");
		try {
		      @SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		          WEStatus status = new WEStatus(response);
	
		          if(status.getStat() != WEStatus.STATUS_SUCCESS){
		        	  tabPanel.remove(2);
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());
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
	 * FILES
	 * The following methods all deal with file access, saving,
	 * deleting, displaying, etc.
	 */
	
	/**
	 * Save the file with the given name on server side.
	 * For now this is just going to take a string with all
	 * the file's contents. Later I will make this a diff of
	 * some sort.
	 * TODO: Read above.text
	 */
	public static boolean saveFile(String fileName, String contents, final FileBrowser browser)
	{
		holdMessage("Saving...");
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
					loadFileListing(browser, "/");
				}
				
				@Override
				public void onError(Request request, Throwable exception)
				{
					Notification.notify(WEStatus.STATUS_ERROR, "Error saving file. "+exception.getMessage());
				}
			});
		}catch(RequestException e){
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}

	/**
	 * Get a file listing from server. Add each file to file browser tree.
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
	 * Rename a file. Update TreeItem. 
	 */
	public static void renameFile(String oldName, final String newName, final FileBrowser browser)
	{
		String url = getBaseURL()+"?cmd=RenameFile&old="+oldName+"&new="+newName;
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


	/**
	 * DST METHODS
	 * There will be some getters in here, but all of these methods
	 * directly relate to the datastructure tool functionality of Wags
	 */
	
	public static void getUsersNameDST(final Label label)
	{
		WagsCallback c = new WagsCallback() {
			@Override
			void warning(WEStatus status) {}
			
			@Override
			void success(WEStatus status) {
				String first = status.getMessageMapVal("firstName");
				if(first == null){
					// Use email address in greeting.
					first = status.getMessageMapVal("email");
				}
				label.setText("Hello, "+first+"!");
				
				DST.getProblems();
			}
			
			@Override
			void error(WEStatus status) {
				Notification.notify(WEStatus.STATUS_ERROR, "Error fetching user details.");
			}
		};
		
		Proxy.call("GetUserDetails", null, c);
	}
	
	public static void getProblems(final String[] problems){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getProblems);
		try {
		      @SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		          WEStatus status = new WEStatus(response);
		          if(status.getStat() == WEStatus.STATUS_SUCCESS){
		        	  
		        	  if(status.getObjectArray().length > 0){
		        		  Map[] problemsMap = status.getObjectArray();
		        		  
			        	  for(int i = 0; i < problemsMap.length; i++){
			        		  problems[i] = (String) problemsMap[i].get("name");
			        	  }
			        	  
		        	  } else {
		        		  problems[0] = (String) status.getMessageMapVal("name");
		        	  }
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());
		    }
	}
	
	public static void getProblemNodes(final ArrayList<Map> nodes){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getProblemNodes);
		try {
		      @SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		          WEStatus status = new WEStatus(response);
		          if(status.getStat() == WEStatus.STATUS_SUCCESS){
		        	  
		        	  if(status.getObjectArray().length > 0){
		        		  Map[] problemsMap = status.getObjectArray();
		        		  
		        		  for(int i = 0; i < problemsMap.length; i++){
		        			  nodes.add(problemsMap[i]);
		        		  }

			        	  
		        	  } else {

		        	  }
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());
		    }
	}
	
	public static void getProblemEdges(final ArrayList<Map> edges){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getProblemNodes);
		try {
		    builder.sendRequest(null, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		          WEStatus status = new WEStatus(response);
		          if(status.getStat() == WEStatus.STATUS_SUCCESS){
		        	  
		        	  if(status.getObjectArray().length > 0){
		        		  Map[] problemsMap = status.getObjectArray();
		        		  
		        		  for(int i = 0; i < problemsMap.length; i++){
		        			  edges.add(problemsMap[i]);
		        		  }

			        	  
		        	  } else {

		        	  }
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());
		    }
	}
	/**
	 * Hopefully, this method just returns the messagemap of the objec
	 * @param problem An empty map that will be filled in this method
	 */
	public static void getProblemSkel(final Map<String, String> problem){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getProblemSkel);
		try {
		      @SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		          WEStatus status = new WEStatus(response);
		          if(status.getStat() == WEStatus.STATUS_SUCCESS){
		        	   
		        	  Map<String, String> map = status.getMessageMap();
		        	  problem.putAll(map);
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());
		    }
	}
	
	
	
	/**
	 * MISCELLANEOUS
	 * Other methods...
	 */
	
	/**
	 * Register a user.
	 */
	public static void register(String email, final String username, final String password, String firstName, String lastName, String section)
	{
 		String completeURL = Proxy.registerURL+"&email="+email+
		 "&username="+username+
		 "&password="+password+
		 "&firstName="+firstName+
		 "&lastName="+lastName+
		 "&section="+section;
 		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, completeURL);
		try{
			@SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					RootPanel root = RootPanel.get();
					WEStatus status = new WEStatus(response);
					if(status.getStat() == WEStatus.STATUS_SUCCESS){
						// Log the user in automatically.
						Proxy.login(username, password);
						Notification.notify(status.getStat(), status.getMessage());
					}else{
						Notification.notify(status.getStat(), status.getMessage());
					}
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
				}
			});
		} catch (RequestException e){
			e.printStackTrace();
		}
	}
	
	//weird stuff with that timer, look at later
	public static void review(String code, final OutputReview review, String exerciseId, String fileName){
		holdMessage("Compiling...");
		review.setText("");
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, submitFile);
		try {
		      builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		      @SuppressWarnings("unused")
			Request req = builder.sendRequest("code="+code+"&id="+exerciseId+"&name="+fileName, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		          clearMessage();
		          
		          WEStatus status = new WEStatus(response);         
		          review.setHTML(status.getMessage());
		          
		          if(status.getStat() == WEStatus.STATUS_SUCCESS){
		        	  Notification.notify(WEStatus.STATUS_SUCCESS, "Correct!");
		          } else if (status.getStat() == WEStatus.STATUS_WARNING){
		        	  Notification.notify(WEStatus.STATUS_WARNING, "Incorrect, Try Again");
		          } else {
		        	  Notification.notify(WEStatus.STATUS_ERROR, "Failed to Compile");
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());	
		    }
	}
	
	public static void checkMultiUser(final Wags wags){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getBaseURL()+"?cmd=CheckMultiUser");
		try{
			Request req = builder.sendRequest(null, new RequestCallback(){

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Error in checkMultiUser request");
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					WEStatus status = new WEStatus(response);  
					
					if(status.getStat() == WEStatus.STATUS_ERROR){
						String title = status.getMessage();
						
						wags.assignPartner(title);
					}
				}
				
			});
		} catch (RequestException e) {
			Window.alert("Failed to send the request: " + e.getMessage());
		}
	}
	
	public static void checkTimedExercises(){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getBaseURL() + "?cmd=CheckOpenExercises");
		
		try{
			Request req = builder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
	
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
			});
		} catch (RequestException e){
			Window.alert("Failed");
		}
	}
	
	public static void assignPartner(String exercise, String partner){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getBaseURL()+"?cmd=SetPartner" +
				"&ex=" + exercise + "&partner=" + partner);
		try{
			builder.sendRequest(null, new RequestCallback(){

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Error in assignPartner request");
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					WEStatus status = new WEStatus(response);  
					
					//SetPartner will handle the correct messages
					Notification.notify(status.getStat(), status.getMessage());
				}
				
			});
		} catch (RequestException e) {
			Window.alert("Failed to send the request: " + e.getMessage());
		}
	}

	/**
	 * GETTERS
	 * The following methods all handle retrieving information from
	 * the database, and perhaps filling some past in component 
	 * with that information.
	 * 
	 */
	
	public static void getDesc(String exerciseId, final OutputReview review){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getBaseURL()+"?cmd=GetDesc&id="+exerciseId);
		try{
			builder.sendRequest(null, new RequestCallback(){
	
				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Error in getDesc request");
				}
	
				@Override
				public void onResponseReceived(Request request,
						Response response) {
					WEStatus status = new WEStatus(response);  
					
					review.setText(status.getMessage());
				}
				
			});
		} catch (RequestException e) {
			Window.alert("Failed to send the request: " + e.getMessage());
		}
	}

	/**
	 * Set the greeting label.
	 * If user did not submit a name when they registered just 
	 * use their email address. 
	 */
	public static void getUsersName(final Label label)
	{
		WagsCallback c = new WagsCallback() {
			@Override
			void warning(WEStatus status) {}
			
			@Override
			void success(WEStatus status) {
				String first = status.getMessageMapVal("firstName");
				if(first == null){
					// Use email address in greeting.
					first = status.getMessageMapVal("email");
				}
				label.setText("Hello, "+first+"!");
				
				//FOR PROXY VERSION UNCOMMENT
				//DST.getProblems();
			}
			
			@Override
			void error(WEStatus status) {
				Notification.notify(WEStatus.STATUS_ERROR, "Error fetching user details.");
			}
		};
		
		Proxy.call("GetUserDetails", null, c);
	}

	/**
	 * Get the contents of a file with the given name from server.
	 * Put those contents in the passed CodeEditor.
	 */
	public static void getFileContents(String fileName, final RichTextArea editor){
		//fileName.trim() leaves leading /, this is causing select errors
		String urlCompl = getFileContents+"&name="+fileName.trim().substring(1);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, urlCompl);
		try {
			@SuppressWarnings("unused")
			Request r = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response)
				{
					//Ugly. The current way I have helper classes visible
					//but not modifiable sends a flag character before
					//the actual text - using WEStatus requires using
					//JSON with kills the formatting...
					String html = response.getText();
					
					if(html.charAt(0) == '0'){
						editor.setEnabled(false);
					} else {
						editor.setEnabled(true);
					}
					
					editor.setHTML(html.substring(1));
					
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
	 * Get a list of exercises from the server and put them
	 * in the FlexTable.
	 * 
	 */
	public static void getExercises(Exercises ex){
		
		WagsCallback c = new WagsCallback() {
			@Override
			void warning(WEStatus status) {}
			
			@Override
			void success(WEStatus status) {
				Window.alert(status.getMessageArray().toString());
			}
			
			@Override
			void error(WEStatus status) {
				Window.alert(status.getMessage());
			}
		};
		HashMap<String, String> vars = new HashMap<String, String>();
		Proxy.call("GetExercises", vars, c);
	}

	public static void getVisibleExercises(final ListBox exercises, final HashMap<String, String> exerciseMap) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getBaseURL()+"?cmd=GetVisibleExercises");
		try {
		      @SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		          WEStatus status = new WEStatus(response);
		          
		          if(status.getStat() == WEStatus.STATUS_SUCCESS){
		        	  if(status.getMessageArray().length > 0){
		        		  String[] message = status.getMessageArray();
		        		  int n = message.length/2;
		        		  
			        	  for(int i = n; i < message.length; i++){
			        		  exercises.addItem(message[i]);
			        		  exerciseMap.put(message[i], message[i-n]);
			        	  }
			        	  
		        	  } else {
		        		  exercises.addItem(status.getMessage());
		        	  }
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());
		    }
	}

	public static void getUsernames(final ListBox users) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getBaseURL()+"?cmd=GetAllUsers");
		try {
		      @SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		          WEStatus status = new WEStatus(response);
		          
		          if(status.getStat() == WEStatus.STATUS_SUCCESS){
		        	  if(status.getMessageArray().length > 0){
		        		  String[] message = status.getMessageArray();
		        		  
			        	  for(int i = 0; i < message.length; i++){
			        		  users.addItem(message[i]);
			        	  }
			        	  
		        	  } else {
		        		  users.addItem(status.getMessage());
		        	  }
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());
		    }
	}

	public static void getSubmissionInfo(int exerciseId, final Grid grid){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Proxy.getBaseURL()+"?cmd=AdminReview&exerciseId="+exerciseId);
		
		try{
			Request req = builder.sendRequest(null, new RequestCallback(){

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("error");					
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					
					WEStatus status = new WEStatus(response);
			        String subInfo[] = new String[status.getMessageArray().length];
			        subInfo = status.getMessageArray();
			        
			        for (int i = 0; i < subInfo.length; i++){
			        	if(subInfo[i] == "1") subInfo[i] = "Yes";
			        	else if (subInfo[i] == "0") subInfo[i] = "No";
			        }
			        
			        grid.resize(subInfo.length/3+1, 3);
			  		grid.setBorderWidth(1);
			  		
			  		//Sets the headers for the table
			  		grid.setHTML(0, 0, "<b> Username </b>");
			  		grid.setHTML(0, 1, "<b> File </b>");
			  		grid.setHTML(0, 2, "<b> Correct </b>");
			  		
			  		int k = 0;
			  		//Fills table with results from AdminReview.php
			  	    for (int row = 1; row < subInfo.length/3+1; ++row) {
			  	      for (int col = 0; col < 3; ++col)
			  	        grid.setText(row, col, subInfo[k++]);
			  	    }
					
				}
				
			});
		}catch (RequestException e){
			Window.alert("Failed to send the request: " + e.getMessage());
		}
		
	}
	
	public static void getSections(final ListBox sections) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, getSections);
		try {
		      @SuppressWarnings("unused")
			Request req = builder.sendRequest(null, new RequestCallback() {
		        public void onResponseReceived(Request request, Response response) {
		        	
		          WEStatus status = new WEStatus(response);
		          
		          if(status.getStat() == WEStatus.STATUS_SUCCESS){
		        	  if(status.getMessageArray().length > 0){
		        		  String[] message = status.getMessageArray();
		        		 
		        		  for(String section: message){
		        			  sections.addItem(section.substring(1, section.length()-1)); //strip quotes
		        		  }
			        	  
		        	  } else {
		        		  sections.addItem(status.getMessage());
		        	  }
		          }
		        }
		        
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("error");
		        }
		      });
		    } catch (RequestException e) {
		      Window.alert("Failed to send the request: " + e.getMessage());
		    }
	}

	public static String getBaseURL() {
		return baseURL;
	}

	public static void call(String command, HashMap<String, String> request, WagsCallback callback){
		Proxy.call(command, request, callback, RequestBuilder.GET);
	}

	/*
	 * Makes a request to server.
	 * The string command will correspond to a commad on the server.
	 * The string[] will contain all other request variables.
	 * The callback will be called when a reponse is received from server.
	 */
	public static void call(String command, HashMap<String, String> request, WagsCallback callback, RequestBuilder.Method method)
	{
		String completeURL = getBaseURL()+"?cmd="+command;
	
		if(request != null){
			/* Loop over request variables appending each to request */
			Set<String> keys = request.keySet();
			Iterator<String> keysItr = keys.iterator();
			while(keysItr.hasNext()){
				String next = keysItr.next();
				completeURL += "&"+next+"="+request.get(next);
			}
		}
		
		RequestBuilder builder = new RequestBuilder(method, completeURL);
		
		/* Make request */
		try {
			builder.sendRequest(null, callback);
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
	
}
