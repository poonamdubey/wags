package wags.presenters.interfaces;

import wags.Common.Presenter;

import com.google.gwt.event.dom.client.ClickEvent;

public interface ProgrammingTabPresenter extends Presenter {

	void deleteExerciseClick(ClickEvent event);

	void onVisClick(ClickEvent event);

	void onReviewClick(ClickEvent event);

	void onSkelClick(ClickEvent event);

}
