package wags.views.concrete;

import wags.Proxy;
import wags.Reviewer;
import wags.WEStatus;
import wags.Common.Presenter;
import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.GetLMAssigned;
import wags.ProxyFramework.GetMMAssignedCommand;
import wags.ProxyFramework.ReviewExerciseCommand;
import wags.admin.ReviewPanel;
import wags.presenters.interfaces.ReviewTabPresenter;
import wags.views.interfaces.ReviewTabView;

import com.github.gwtbootstrap.client.ui.ListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.Widget;

public class ReviewTab extends Composite implements ReviewTabView{

	private static ReviewTabUiBinder uiBinder = GWT
			.create(ReviewTabUiBinder.class);

	interface ReviewTabUiBinder extends UiBinder<Widget, ReviewTab> {
	}
	
	@UiField ReviewPanel rvLogPanel, rvMagPanel;
	@UiField SubmitButton btnCompReview;
	@UiField FormPanel formCompReview;
	@UiField ListBox magReview, logReview, magReviewPast, logReviewPast;
	Reviewer logHandler, magHandler, magRev, logRev;
	
	
	private ReviewTabPresenter presenter;

	public ReviewTab() {
		initWidget(uiBinder.createAndBindUi(this));
		logRev = new LogicalReviewHandler();
		magRev = new MagnetReviewHandler();
		
		
		rvLogPanel.setParent(logHandler);
		rvMagPanel.setParent(magHandler);
		
		AbstractServerCall cmd1 = new GetLMAssigned(logRev, Reviewer.NONE);
		cmd1.sendRequest();
		
		AbstractServerCall cmd2 = new GetMMAssignedCommand(magRev, Reviewer.NONE);
		cmd2.sendRequest();
		
		AbstractServerCall cmd3 = new GetLMAssigned(logRev, Reviewer.GET_REVIEW);
		cmd3.sendRequest();
		
		AbstractServerCall cmd4 = new GetMMAssignedCommand(magRev, Reviewer.GET_REVIEW);
		cmd4.sendRequest();
		
		btnCompReview.addStyleName("button");
		
		formCompReview.setAction(Proxy.getBaseURL()+"?cmd=ComprehensiveReview");
		formCompReview.setEncoding(FormPanel.ENCODING_MULTIPART);
		formCompReview.setMethod(FormPanel.METHOD_POST);
		
		
		//Click handlers
		logReview.addChangeHandler(new ChangeHandler()
		 {
		  @Override
		public void onChange(ChangeEvent event)
		  {
			  int selectedIndex = logReview.getSelectedIndex();
			  if (selectedIndex > -1) 
			  {
				  logRev.review(logReview.getValue(selectedIndex));
		      }
		  }
		 });
		//Click handlers
		logReviewPast.addChangeHandler(new ChangeHandler()
		 {
		  @Override
		public void onChange(ChangeEvent event)
		  {
			  int selectedIndex = logReviewPast.getSelectedIndex();
			  if (selectedIndex > -1) 
			  {
				  logRev.review(logReviewPast.getValue(selectedIndex));
		      }
		  }
		 });
		//Click handlers
		magReview.addChangeHandler(new ChangeHandler()
		 {
		  @Override
		public void onChange(ChangeEvent event)
		  {
			  int selectedIndex = magReview.getSelectedIndex();
			  if (selectedIndex > -1) 
			  {
				  magRev.review(magReview.getValue(selectedIndex));
		      }
		  }
		 });
		//Click handlers
		magReviewPast.addChangeHandler(new ChangeHandler()
		 {
		  @Override
		public void onChange(ChangeEvent event)
		  {
			  int selectedIndex = magReviewPast.getSelectedIndex();
			  if (selectedIndex > -1) 
			  {
				  magRev.review(magReviewPast.getValue(selectedIndex));
		      }
		  }
		 });
	}
	
	//Fills drop down menus
	public class LogicalReviewHandler implements Reviewer{
		@Override
		public void getCallback(String[] exercises, WEStatus status,
				String request) {
			String[] assigned = new String[exercises.length];
			String[] prevAssigned = new String[exercises.length];
			
			for (int i =0; i < exercises.length;i++)
			{
				if(request.equals("")){
					assigned[i] = exercises[i];
				}
				if(request.equals(GET_REVIEW)){
					prevAssigned[i] = exercises[i];
				}
			}
			
			for (int i = 0; i < assigned.length; i++)
			{
				if (assigned[i] != null)
				{
					logReview.addItem(assigned[i]);
				}
			}
			
			for (int i = 0; i < prevAssigned.length; i++)
			{
				if (prevAssigned[i] != null)
				{
					logReviewPast.addItem(prevAssigned[i]);
				}			
			}
			logRev.review(logReview.getValue(0));
		}

		@Override
		public void review(String exercise) {
			AbstractServerCall cmd = new ReviewExerciseCommand(exercise, LOGICAL, this);
			cmd.sendRequest();
		}

		@Override
		public void reviewCallback(String[] data) {
			rvLogPanel.fillGrid(data);
		}
		
	}
	
	//Fills drop down menus
	public class MagnetReviewHandler implements Reviewer{
		@Override
		public void getCallback(String[] exercises, WEStatus status,
				String request) { 
			String[] magAssigned = new String[exercises.length];
			String[] magPrevAssigned = new String[exercises.length];
			for (int i =0; i < exercises.length;i++)
			{
				if(request.equals("")){
					magAssigned[i] = exercises[i];
				}
				
				if(request.equals(GET_REVIEW)){
					magPrevAssigned[i] = exercises[i];
				}
			}
			
			for (int i = 0; i < magAssigned.length; i++)
			{
				if (magAssigned[i] != null)
				{
					magReview.addItem(magAssigned[i]);
				}
			}
			
			for (int i = 0; i < magPrevAssigned.length; i++)
			{
				if (magPrevAssigned[i] != null)
				{
					magReviewPast.addItem(magPrevAssigned[i]);
				}
			}
			magRev.review(magReview.getValue(0));
		}

		@Override
		public void review(String exercise) {
			AbstractServerCall cmd = new ReviewExerciseCommand(exercise, MAGNET, this);
			cmd.sendRequest();
		}

		@Override
		public void reviewCallback(String[] data) {
			rvMagPanel.fillGrid(data);
		}
	}
	
	public void update()
	{
		presenter.update();
	}

	@Override
	public boolean hasPresenter(){
		return presenter != null;
	}
	
	@Override
	public Presenter getPresenter() {
		return presenter;
	}

	@Override
	public ReviewPanel getRVLogPanel() {
		return rvLogPanel;
	}

	@Override
	public ReviewPanel getRVMagPanel() {
		return rvMagPanel;
	}

	@Override
	public SubmitButton getBtnCompReview() {
		return btnCompReview;
	}

	@Override
	public FormPanel getFormCompReview() {
		return formCompReview;
	}

	@Override
	public Reviewer getLogHandler() {
		return logHandler;
	}

	@Override
	public Reviewer getMagHandler() {
		return magHandler;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (ReviewTabPresenter) presenter;
	}

	@Override
	public boolean isAdmin() {
		return true;
	}
}
