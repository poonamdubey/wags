package webEditor.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class OutputReview extends View{

	private static OutputReviewUiBinder uiBinder = GWT
			.create(OutputReviewUiBinder.class);
	
	@UiField TextArea review;

	interface OutputReviewUiBinder extends UiBinder<Widget, OutputReview> {
	}

	public OutputReview() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text){
		review.setText(text);
	}

	public OutputReview(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public WEAnchor getLink() {
		// TODO Auto-generated method stub
		return null;
	}
}