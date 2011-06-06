package webEditor.client.view;

import webEditor.client.PHP;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;


public class CodeEditor extends View implements HasHandlers
{

	private static CodeEditorUiBinder uiBinder = GWT
			.create(CodeEditorUiBinder.class);

	interface CodeEditorUiBinder extends UiBinder<Widget, CodeEditor>{}
	 
	@UiField RichTextArea codeArea;
	private Timer timer;
	private PHP php;

	public CodeEditor()
	{
		initWidget(uiBinder.createAndBindUi(this));

		codeArea.setFocus(true);
		codeArea.setEnabled(true);
		php = new PHP();
	
		timer = new Timer() {
			@Override
			public void run()
			{
				codeArea.setHTML(php.parse(codeArea.getText()));
			}
		};
		
		codeArea.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				// Re-parse code area 1 second after user stops typing
				timer.schedule(1000);	
			}
		});
	}

	public void setContents(String contents){
		this.codeArea.setText(contents);
	}
	
	public String getContents(){
		return this.codeArea.getText();
	}
	
	@Override
	public WEAnchor getLink()
	{
		return new WEAnchor("Editor", this, "codeEditor");
	}
}
