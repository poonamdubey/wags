package webEditor.admin;

import webEditor.Proxy;
import webEditor.Reviewer;
import webEditor.WEStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.Widget;

public class ReviewTab extends Composite {

	private static ReviewTabUiBinder uiBinder = GWT
			.create(ReviewTabUiBinder.class);

	interface ReviewTabUiBinder extends UiBinder<Widget, ReviewTab> {
	}
	
	@UiField ReviewPanel rvLogPanel, rvMagPanel;
	@UiField SubmitButton btnCompReview;
	@UiField FormPanel formCompReview;
	Reviewer logHandler, magHandler;

	public ReviewTab() {
		initWidget(uiBinder.createAndBindUi(this));
		logHandler = new LogicalReviewHandler();
		magHandler = new MagnetReviewHandler();
		
		rvLogPanel.setParent(logHandler);
		rvLogPanel.setTitle("Logical Review");
		rvMagPanel.setParent(magHandler);
		rvMagPanel.setTitle("Magnet Review");
		Proxy.getLMAssigned(logHandler);
		Proxy.getMMAssigned(magHandler);
		Proxy.getLMAssigned(logHandler, Reviewer.GET_REVIEW);
		Proxy.getMMAssigned(magHandler, Reviewer.GET_REVIEW);
		
		btnCompReview.addStyleName("button");
		
		formCompReview.setAction(Proxy.getBaseURL()+"?cmd=ComprehensiveReview");
		formCompReview.setEncoding(FormPanel.ENCODING_MULTIPART);
		formCompReview.setMethod(FormPanel.METHOD_POST);
	}
	
	private class LogicalReviewHandler implements Reviewer{
		public void getCallback(String[] exercises, WEStatus status,
				String request) { 
			// Currently assigned
			if(request.equals("")){
				rvLogPanel.setCurrent(exercises);
			}
			
			// Review
			if(request.equals(GET_REVIEW)){
				rvLogPanel.setReview(exercises);
			}
		}

		@Override
		public void review(String exercise) {
			Proxy.reviewExercise(exercise, LOGICAL, this);
		}

		@Override
		public void reviewCallback(String[] data) {
			rvLogPanel.fillGrid(data);
		}
		
	}
	
	private class MagnetReviewHandler implements Reviewer{
		public void getCallback(String[] exercises, WEStatus status,
				String request) { 
			// Currently assigned
						if(request.equals("")){
							rvMagPanel.setCurrent(exercises);
						}
						
						// Review
						if(request.equals(GET_REVIEW)){
							rvMagPanel.setReview(exercises);
						}
		}

		@Override
		public void review(String exercise) {
			Proxy.reviewExercise(exercise, MAGNET, this);
		}

		@Override
		public void reviewCallback(String[] data) {
			rvMagPanel.fillGrid(data);
		}
	}
	
	public void update(){
		Proxy.getLMAssigned(logHandler);
		Proxy.getMMAssigned(magHandler);
		Proxy.getLMAssigned(logHandler, Reviewer.GET_REVIEW);
		Proxy.getMMAssigned(magHandler, Reviewer.GET_REVIEW);
	}

}