package wags.views.interfaces;

import wags.Common.View;
import wags.programming.view.CodeEditor;
import wags.programming.view.FileBrowser;
import wags.programming.view.OutputReview;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public interface EditorView extends View {
	
	public TextBox fileName();
	public Anchor getCode();
	public FileBrowser browser();
	public CodeEditor editor();
	public com.google.gwt.user.client.ui.Image description();
	public OutputReview review();
	public Anchor save();
	public Button submit();
	public TabLayoutPanel tabPanel();
	public FormPanel wrapperForm();
	public TextArea lines();
}
