package edu.appstate.bostrt.WE.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Anchor;

/**
 * WEAnchor
 * 
 * WEAnchor represents a <a> tag.
 * When the anchor text is clicked the View passed in
 * the constructor is shown.
 * 
 * @author Robert Bost <bostrt@appstate.edu>
 */
public class WEAnchor extends Anchor 
{
	private String url;

	public WEAnchor(String text, final View view, final String hash)
	{
		// Set text of link.
		this.setText(text);
		UrlBuilder builder = Location.createUrlBuilder();
		builder.setHash(hash);
		url = builder.buildString();
		// Set CSS class
		this.setStyleName("we-anchor");
		// On click, redirect to page with the hash in URL.
		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				go();
			}
		});
	}
	
	public void go()
	{
		Window.Location.assign(url);
		Window.Location.reload();
	}
}
