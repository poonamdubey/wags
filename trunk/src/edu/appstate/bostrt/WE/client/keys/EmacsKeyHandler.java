package edu.appstate.bostrt.WE.client.keys;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextArea;

/**
 * EmacsKeyListener
 *
 *
 * @author Robert Bost <bostrt@appstate.edu>
 */
public class EmacsKeyHandler implements KeyPressHandler, KeyUpHandler, KeyDownHandler  
{

	@Override
	public void onKeyPress(KeyPressEvent event)
	{
	}

	@Override
	public void onKeyUp(KeyUpEvent event)
	{
	}

	@Override
	public void onKeyDown(KeyDownEvent event)
	{
		TextArea t = (TextArea)event.getSource();
		// Must handle Ctrl, Alt, Meta here
		int key = event.getNativeKeyCode();
		if(event.isControlKeyDown()){
			if(key == 'P'){
				// Up
				event.stopPropagation();
				event.preventDefault();
			}
			else if(key == 'N'){
				// Down
				event.stopPropagation();
				event.preventDefault();
			}
			else if(key == 'B'){
				// Left
				t.setCursorPos(t.getCursorPos()-1);
				event.stopPropagation();
				event.preventDefault();
			}
			else if(key == 'F'){
				// Right
				t.setCursorPos(t.getCursorPos()+1);
				event.stopPropagation();
				event.preventDefault();
			}

		}
			
	}

}
