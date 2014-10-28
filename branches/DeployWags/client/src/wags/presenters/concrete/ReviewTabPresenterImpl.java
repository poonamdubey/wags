package wags.presenters.concrete;

import java.util.List;

import wags.Reviewer;
import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.GetLMAssigned;
import wags.ProxyFramework.GetMMAssignedCommand;
import wags.presenters.interfaces.ReviewTabPresenter;
import wags.views.concrete.ReviewTab;
import wags.views.interfaces.ReviewTabView;

import com.google.gwt.user.client.ui.HasWidgets;

public class ReviewTabPresenterImpl implements ReviewTabPresenter {

	private ReviewTabView reviewTab;
	private boolean bound = false;

	public ReviewTabPresenterImpl(ReviewTab view) {
		this.reviewTab = view;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(reviewTab.asWidget());
	}

	@Override
	public void bind() {
		reviewTab.setPresenter(this);
		bound = true;
	}

	@Override
	public boolean bound() {
		return bound;
	}

	@Override
	public void update() {

		AbstractServerCall cmd1 = new GetLMAssigned(reviewTab.getLogHandler(), Reviewer.NONE);
		cmd1.sendRequest();

		AbstractServerCall cmd2 = new GetMMAssignedCommand(reviewTab.getLogHandler(), Reviewer.NONE);
		cmd2.sendRequest();

		AbstractServerCall cmd3 = new GetLMAssigned(reviewTab.getLogHandler(),	Reviewer.GET_REVIEW);
		cmd3.sendRequest();

		AbstractServerCall cmd4 = new GetMMAssignedCommand(reviewTab.getLogHandler(),	Reviewer.GET_REVIEW);
		cmd4.sendRequest();
	}

	@Override
	public void update(List<String> data) {
		update();		
	}

	
}
