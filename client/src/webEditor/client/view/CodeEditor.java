package webEditor.client.view;

import webEditor.client.PHP;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;


public class CodeEditor extends View implements HasHandlers
{
	//Will probably want to move this to an external class later, for now
	//using in color completion checking.  Suffix 'S' means it needs
	//a shift as well
	private static final int QUOTE = 222;
	private static final int FSLASH = 191;
	private static final int SPLATS = 56;
	private static final int OCURLS = 219;
	private static final int CCURLS = 221;
	private static final int OPARENS = 57;
	private static final int CPARENTS = 48;

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
				//codeArea.setHTML(php.parse(codeArea.getText()));
			}
		};
		
		codeArea.addKeyDownHandler(new KeyDownHandler(){
			public void onKeyDown(KeyDownEvent event)
			{
				colorCompletionCheck(event);
			}
		});
		
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
	
	public void colorCompletionCheck(KeyDownEvent event){
		Formatter formatter = codeArea.getFormatter();
		boolean cBlock, pBlock, comment, quote;
		cBlock = pBlock = comment = quote = false;
		
		//Block brace check
		if(event.getNativeKeyCode() == OCURLS && event.isShiftKeyDown()){
			formatter.setForeColor("#FF00FF");
			cBlock = true;
		}
		
		if(cBlock = true && event.getNativeKeyCode() == CCURLS &&
				event.isShiftKeyDown()){
			formatter.setForeColor("#000000");
		}
	}
	
	
	@Override
	public WEAnchor getLink()
	{
		return new WEAnchor("Editor", this, "codeEditor");
	}
}
