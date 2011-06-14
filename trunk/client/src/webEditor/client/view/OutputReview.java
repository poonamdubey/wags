package webEditor.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class OutputReview extends Composite implements HasText {

	private static OutputReviewUiBinder uiBinder = GWT
			.create(OutputReviewUiBinder.class);

	interface OutputReviewUiBinder extends UiBinder<Widget, OutputReview> {
	}

	public OutputReview() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	

	public OutputReview(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}



	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}
}
