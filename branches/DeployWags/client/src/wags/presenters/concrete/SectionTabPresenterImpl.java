package wags.presenters.concrete;

import java.util.List;

import wags.presenters.interfaces.SectionTabPresenter;
import wags.views.concrete.SectionTab;

import com.google.gwt.user.client.ui.HasWidgets;

public class SectionTabPresenterImpl implements SectionTabPresenter {
	

	
	private SectionTab sectionTab;
	private boolean bound = false;

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(sectionTab.asWidget());
	}

	@Override
	public void bind() {
		sectionTab.setPresenter(this);
		bound = true;
	}

	@Override
	public boolean bound() {
		return bound;
	}

	@Override
	public void update(List<String> data) {
		// TODO Auto-generated method stub
		
	}

}
