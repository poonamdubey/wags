package webEditor.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;



public class CodeEditor extends View implements HasHandlers
{
	

	private static CodeEditorUiBinder uiBinder = GWT
			.create(CodeEditorUiBinder.class);

	interface CodeEditorUiBinder extends UiBinder<Widget, CodeEditor>{}
	 
	@UiField RichTextArea codeArea;
	private CompletionCheck colorCheck = new CompletionCheck();

	public CodeEditor()
	{
		initWidget(uiBinder.createAndBindUi(this));

		codeArea.setFocus(true);
		codeArea.setEnabled(true);
		codeArea.getFormatter().setFontName("monospace");
		
		codeArea.addKeyDownHandler(new KeyDownHandler(){
			public void onKeyDown(KeyDownEvent event)
			{
				codeArea.getFormatter().setForeColor(colorCheck.pushCheck(event));
			}
		});
		
		codeArea.addKeyUpHandler(new KeyUpHandler(){
			public void onKeyUp(KeyUpEvent event){
				
				if(event.getNativeKeyCode() == 13){
					for(int i = 0; i < colorCheck.getTabCount(); i++)
						codeArea.getFormatter().insertHTML("&nbsp; &nbsp; &nbsp;");
					colorCheck.enterIncrement(colorCheck.getTabCount() * 5);
				}
				
				
				codeArea.getFormatter().setForeColor(colorCheck.popCheck(event));
			}
		});
		
		codeArea.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				//codeArea.getFormatter().setForeColor(colorCheck.pushCheck(event))
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
