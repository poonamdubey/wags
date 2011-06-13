
package webEditor.client.view;

import webEditor.client.Proxy;
import webEditor.client.WEStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;


public class Editor extends View
{

	private static EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);

	interface EditorUiBinder extends UiBinder<Widget, Editor>{}

	@UiField DockLayoutPanel dock;
	@UiField Anchor logout;
	@UiField Anchor save;
	@UiField Anchor delete;
	
	
	@UiField TextBox fileName;
	@UiField Label hello;
	@UiField CodeEditor editor;
	@UiField FileBrowser browser;
	
	
	public Editor()
	{
		initWidget(uiBinder.createAndBindUi(this));
		// initialize to not visible.
		fileName.setVisible(false);
		save.setVisible(false);
		delete.setVisible(false);
		

		
		// Add selection handler to file browser
		browser.getTree().addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event)
			{
				// If clicked item is directory then just open it
				TreeItem i = event.getSelectedItem();
				// If clicked item is a leaf TreeItem then open it in editor
				Proxy.getFileContents(browser.getItemPath(i), editor);
				// Set filename, save, and delete stuff visible
				fileName.setVisible(true);
				save.setVisible(true);
				delete.setVisible(true);
				fileName.setText(browser.getItemPath(i).toString().substring(1));
			}
		});

		
		// Remove the link for renaming the file.
		fileName.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event)
			{
				String oldName = fileName.getElement().getAttribute("oldName");
				if(!oldName.equals(fileName.getText())){
					// Only update filename if user actually changed it derp.
					Proxy.renameFile(oldName, fileName.getText(), browser);
				}
			}
		});
		
		// Show text to rename the file.
		fileName.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event)
			{
				// Add an attribute to the filename textbox that stores the old file name. 
				// Do this onFocus because the user is probably about to edit the file name.
				fileName.getElement().setAttribute("oldName", fileName.getText());
			}
		});
		
		Proxy.getUsersName(hello);
	}
	
	/**
	 * Send contents of text area to server. 
	 */
	@UiHandler("save")
	void onSaveClick(ClickEvent event)
	{
		Proxy.saveFile(browser.getSelectedPath(), editor.getContents());
	}
	
	/**
	 * Delete file from server.
	 */
	@UiHandler("delete")
	void onDeleteClick(ClickEvent event)
	{
		TreeItem i = browser.getTree().getSelectedItem();
		TreeItem parent = i.getParentItem();
		
		deleteChildren(i);
		Notification.notify(WEStatus.STATUS_SUCCESS, i.getText()+" deleted");
		i.remove();
		
		String reloadPath;
		if(parent.getChildCount() > 0){
			reloadPath = getPath(parent.getChild(0));
		} else {
			reloadPath = getPath(parent);
		}

		editor.setContents("");
		Proxy.loadFileListing(browser, reloadPath);
	}
	
	/**
	 * Logout!	
	 */
	@UiHandler("logout")
	void onLogoutClick(ClickEvent event)
	{
		Proxy.logout();
	}
	

	
	@Override
	public WEAnchor getLink()
	{
		return new WEAnchor("Editor", this, "editor");
	}
	
	/**
	 * deleteChildren
	 * Description: recursively remove all children of a deleted directory
	 * @param i The directory
	 * @return none
	 */
	private void deleteChildren(TreeItem i){
		for(int childIndex = 0; childIndex < i.getChildCount(); childIndex++){
			TreeItem child = i.getChild(childIndex);
			
			if(child.getChildCount() > 0)
				deleteChildren(child); //recurses down to leaf
			
			Proxy.deleteFile(getPath(child)); //deletes leaf using path
			child.remove(); //remove from browser
		}
		
		Proxy.deleteFile(getPath(i));
		i.remove();
	}
	
	private String getPath(TreeItem i){
		String path = "";
		while(i != null && i.getParentItem() != null){
			path = "/"+i.getText()+path;
			i = i.getParentItem();
		}
		
		return path;
	}
	
}