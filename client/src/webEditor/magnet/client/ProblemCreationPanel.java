package webEditor.magnet.client;

import webEditor.client.Proxy;
import webEditor.client.WEStatus;
import webEditor.client.view.Notification;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.URL;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;


public class ProblemCreationPanel extends Composite{

	private static ProblemCreationPanelUiBinder uiBinder = GWT
			.create(ProblemCreationPanelUiBinder.class);

	interface ProblemCreationPanelUiBinder extends UiBinder<Widget, ProblemCreationPanel> {
	}
	
	@UiField FormPanel problemCreateFormPanel;
	@UiField TextBox titleTxtBox, topLabelTxtBox, topRealCodeTxtBox, topHiddenCodeTxtBox,
		commentsTxtBox, bottomLabelTxtBox, bottomRealCodeTxtBox, bottomHiddenCodeTxtBox;
	@UiField TextArea finalTitleTxtBox, descriptionTxtArea, finalDescriptionTxtArea,
		classDeclarationTxtArea, innerFunctionsTxtArea, statementsTxtArea, commentsStagingArea;
	//	@UiField MagnetCreation magnetCreator;
	@UiField SubmitButton createProblemSubmitButton;
	@UiField Button createCommentsButton, classDeclarationButton, innerFunctionsButton,
		statementsButton, clearDataButton;
	@UiField FileUpload solutionUpload, helperUpload;
	@UiField ListBox lstGroup;
	@UiField Label lblGroup;
		
	public ProblemCreationPanel(RefrigeratorMagnet magnet, boolean magnetAdmin){
		initWidget(uiBinder.createAndBindUi(this));
		Proxy.getMagnetGroups(lstGroup, null, null, null, null);
		
		/*if(magnetAdmin){
			lstGroup.setEnabled(true);
			Proxy.getMagnetGroups(lstGroup, null, null, null, null);
		} else {
			lblGroup.setVisible(false);
		}*/
		
		
		problemCreateFormPanel.setAction(Proxy.getBaseURL() + "?cmd=AddMagnetExercise");
		problemCreateFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		problemCreateFormPanel.setMethod(FormPanel.METHOD_POST);
		problemCreateFormPanel.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				WEStatus stat = new WEStatus(event.getResults());
				if(stat.getStat() == WEStatus.STATUS_SUCCESS){
					Proxy.addMagnetLinkage(stat.getMessage()); // The title of the problem
				} else {
					Notification.notify(stat.getStat(), stat.getMessage());
				}
			}
		});
		
		titleTxtBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				finalTitleTxtBox.setText(titleTxtBox.getText());
			}
		});
		
		descriptionTxtArea.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				finalDescriptionTxtArea.setText(descriptionTxtArea.getText());
			}
		});
	}	
	
	@UiHandler("createCommentsButton")
	void onCreateCommentClick(ClickEvent event)
	{
		commentsStagingArea.setText(commentsStagingArea.getText()+Consts.COMMENT_DELIMITER + "\\\\" + commentsTxtBox.getText());
    }
	
	@UiHandler("classDeclarationButton")
	void onClassDeclClick(ClickEvent event)
	{
		String newText = buildString();
		// The main class should only have one magnet, and no delimiter
		String realText = newText.substring(0, newText.length()-Consts.MAGNET_DELIMITER.length());
		classDeclarationTxtArea.setText(realText);
		clearLabels();
    }
	
	@UiHandler("innerFunctionsButton")
	void onInnerFunctionslClick(ClickEvent event)
	{
		String newMagnetString = buildString();
		innerFunctionsTxtArea.setText(innerFunctionsTxtArea.getText()+newMagnetString);
		clearLabels();
	}
	
	@UiHandler("statementsButton")
	void onStatementsClick(ClickEvent event)
	{
		String newMagnetString = buildString();
		statementsTxtArea.setText(statementsTxtArea.getText()+newMagnetString);
		clearLabels();
	}	
	
	@UiHandler("clearDataButton")
	void onClearDataClick(ClickEvent event)
	{
		clearLabels();
    }
	
	private String buildString(){
		boolean withPanel = false;
		
		String topLabel = "";
		if(topLabelTxtBox.getText()!=""){
			topLabel = topLabelTxtBox.getText();
		}
		
		String topRealCode = "";
		if(topRealCodeTxtBox.getText()!=""){
			topRealCode = Consts.CODE_START+topRealCodeTxtBox.getText()+Consts.CODE_SPLIT;
		}
		
		String topHiddenCode = "";
		if(topHiddenCodeTxtBox.getText()!=""){
			topHiddenCode = Consts.HIDE_START+topHiddenCodeTxtBox.getText()+Consts.HIDE_END;
		}
		

		String comments = "";
		if(commentsStagingArea.getText()!=""){
			comments = commentsStagingArea.getText();
			withPanel = true;
		}
		
		String bottomLabel = "";
		if(bottomLabelTxtBox.getText()!=""){
			withPanel = true;
			bottomLabel = bottomLabelTxtBox.getText();
		}
		
		String bottomRealCode = "";
		if(bottomRealCodeTxtBox.getText()!=""){
			withPanel = true;
			bottomRealCode = bottomRealCodeTxtBox.getText()+Consts.CODE_END;
		}
		
		String bottomHiddenCode = "";
		if(bottomHiddenCodeTxtBox.getText()!=""){
			withPanel=true;
			bottomHiddenCode = Consts.HIDE_START+bottomHiddenCodeTxtBox.getText()+Consts.HIDE_END;
		}
		
		if(withPanel){
			return topLabel+topRealCode+bottomRealCode+topHiddenCode+"<br/><!-- panel --><br/>"+bottomHiddenCode+bottomLabel+comments+Consts.MAGNET_DELIMITER;
		} else {
			return topLabel+topRealCode+bottomRealCode+topHiddenCode+Consts.MAGNET_DELIMITER;
		}
	
	}
	
	public void clearLabels(){
		topHiddenCodeTxtBox.setText("");
		topLabelTxtBox.setText("");
		topRealCodeTxtBox.setText("");
		
		bottomHiddenCodeTxtBox.setText("");
		bottomLabelTxtBox.setText("");
		bottomRealCodeTxtBox.setText("");
		commentsStagingArea.setText("");
		commentsTxtBox.setText("");
	}
}
