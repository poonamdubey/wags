
package webEditor.client.view;

import webEditor.client.Proxy;
import webEditor.client.WEStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;


public class Wags extends View
{

	private static EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);

	interface EditorUiBinder extends UiBinder<Widget, Wags>{}

	@UiField DockLayoutPanel dock;
	@UiField Anchor logout;
	@UiField Anchor save;
	@UiField Anchor delete;
	@UiField Anchor submit;
	
	@UiField TextBox fileName;
	@UiField Label hello;
	@UiField CodeEditor editor;
	@UiField FileBrowser browser;
	@UiField OutputReview review;
	@UiField TabLayoutPanel tabPanel;
	
	public Wags()
	{
		initWidget(uiBinder.createAndBindUi(this));
		
		save.setVisible(false);
		delete.setVisible(false);
		submit.setVisible(false);
		

		// Add selection handler to file browser
		browser.getTree().addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event)
			{
				// If clicked item is directory then just open it
				TreeItem i = event.getSelectedItem();
				if(i.getChildCount() > 0)
					return;
				// If clicked item is a leaf TreeItem then open it in editor
				Proxy.getFileContents(browser.getItemPath(i), editor);
				// Set filename, save, and delete stuff visible
				save.setVisible(true);
				delete.setVisible(true);
				submit.setVisible(true);
				fileName.setText(browser.getItemPath(i).toString().substring(1));
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
		
		Proxy.isAdmin(tabPanel);
		
		Proxy.getUsersName(hello);
	}
	
	/**
	 * Send contents of text area to server. 
	 */
	@UiHandler("save")
	void onSaveClick(ClickEvent event)
	{
		if(Proxy.saveFile("/" + fileName.getText().toString(), editor.codeArea.getText(), browser));
	}
	
	@UiHandler("fileName")
	void onChange(ChangeEvent event)
	{
		save.setVisible(true);
		delete.setVisible(true);
		submit.setVisible(true);
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
	
	@UiHandler("submit")
	void onSubmitClick(ClickEvent event)
	{
		//Apparently spaces in an RTA get passed as \160, or &nbsp, which is
		//not an acceptable character for java compilation
		String codeHTML, codeText;
		
		codeHTML = editor.codeArea.getHTML();
		editor.codeArea.setHTML("&nbsp;" + codeHTML);
		
		codeText = editor.codeArea.getText();
		codeText = codeText.replace(codeText.charAt(0), ' ');
		codeText = codeText.substring(1);
		
		editor.codeArea.setHTML(codeHTML);
		
		if(Proxy.submit(codeText, review)){
			tabPanel.selectTab(1);
		}
	}
	

	
	@Override
	public WEAnchor getLink()
	{
		return new WEAnchor("Wags", this, "editor");
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
