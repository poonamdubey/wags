package webEditor.magnet.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ResultsPanelUi extends Composite {

	private static ResultsPanelUiUiBinder uiBinder = GWT
			.create(ResultsPanelUiUiBinder.class);

	interface ResultsPanelUiUiBinder extends UiBinder<Widget, ResultsPanelUi> {
	}

	@UiField LayoutPanel layout;
	@UiField AbsolutePanel testResults;
	static String code = "";
	static String results = "";
	static TextArea codeToTest;
	static TextArea codeTestResults;
	
	/**
	 * Creates results panel where students can see their code that will be tested and 
	 * the results of the test run.
	 * 
	 * @param tabPanelHeight height variable used to ensure panel visibility
	 */
	public ResultsPanelUi(int tabPanelHeight) {
		initWidget(uiBinder.createAndBindUi(this));
		//set size of overall panel
		layout.setSize("100%", tabPanelHeight - 60 + "px");
		//set up left side of panel, the code to test from student
		codeToTest = new TextArea();
		codeToTest.setReadOnly(true);
		codeToTest.setVisibleLines(25);
		codeToTest.setSize("100%", "100%");
		
		codeTestResults = new TextArea();
		codeTestResults.setReadOnly(true);
		codeTestResults.setVisibleLines(25);
		codeTestResults.setSize("100%", "100%");
		
		String test = codeTestResults.getText() + codeToTest.getText();
		
		codeTestResults.setText(test);
		testResults.add(codeTestResults);
		//testResults.add(codeToTest);
		
	}

	public static void setResultsText(String s){
		results = s;
		
		codeTestResults.setText(results + "\n\n\n" + code);
	}
	
	/**
	 * This method takes a String of code, s, and formats 
	 * it so that it has line numbers to the left, then 
	 * stores that in the field code.
	 * 
	 * GWT can't do String.format, so I'm not sure how to 
	 * properly make the line numbers right-aligned. Thus, 
	 * I just have to if-statements to add spaces as necessary.
	 * @param s
	 */
	public static void setCodeText(String s){
		String[] linedCode = s.split("\n");
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < linedCode.length; i++) {
			String spaces = "";
			
			if ((i + 1) / 100 == 0) {
				spaces += " ";
			}
			
			if ((i + 1) / 10 == 0) {
				spaces += " ";
			}
			
			sb.append(spaces);
			sb.append(i + 1);
			sb.append("  ");
			sb.append(linedCode[i]);
			sb.append("\n");
		}
		
		code = sb.toString();
	}
}
