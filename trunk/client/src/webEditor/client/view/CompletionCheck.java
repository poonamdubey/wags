package webEditor.client.view;

import java.util.Stack;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RichTextArea;
import com.sun.xml.internal.fastinfoset.util.CharArray;

public class CompletionCheck {
	private ColorCounter curColor;
	final static String PURPLE = "#B300B3";
	final static String RED = "#CC0000";
	final static String GREEN = "#336633";
	final static String LIGHTBLUE = "#0066FF";
	final static String DARKBLUE = "#0000CC";
	final static String BLACK = "#000000";
	
	//Will probably want to move this to an external class later, for now
	//using in color completion checking.  Suffix 'S' means it needs
	//a shift as well
	private static final int QUOTE = 222;
	private static final int FSLASH = 191;
	private static final int SPLATS = 56;
	private static final int OCURLS = 219;
	private static final int CCURLS = 221;
	private static final int OPARENS = 57;
	private static final int CPARENS = 48;
	private static final int BACKSPACE = 8;
	private static final int SHIFT = 16;
	private boolean newSQuote;
	private boolean newDQuote;
	private boolean dQuoteFlag;

	private Stack<ColorCounter> stack = new Stack<ColorCounter>();
	
	
	
	public CompletionCheck(){
		//curColor starts black
		curColor = new ColorCounter(BLACK);
		newDQuote = true;
	}
	
	public String pushCheck(KeyDownEvent event){
		int key = event.getNativeKeyCode();
		boolean shift = event.isShiftKeyDown();
		
		//Checks to see if a block/quote was started
		if(key == OPARENS && shift){ 				// (
			stack.push(curColor);
			curColor = new ColorCounter(PURPLE);
		} 
		
		else if (key == OCURLS && shift){ 		// {
			stack.push(curColor);
			curColor = new ColorCounter(GREEN);
		}
		
		else if (key == OCURLS){ 					// [
			stack.push(curColor);
			curColor = new ColorCounter(RED);
		} 
		
		else if (key == QUOTE && shift){ 			// "
			if(curColor.COLOR != LIGHTBLUE){
				stack.push(curColor);
				curColor = new ColorCounter(LIGHTBLUE);
			}
		} 
		
		else if (key == QUOTE) { 					// '
			if(curColor.COLOR != DARKBLUE){
				stack.push(curColor);
				curColor = new ColorCounter(DARKBLUE);
				newSQuote = false;
			}
		}
		
		//special delete key logic
		if(key == BACKSPACE){
			int count = curColor.count - 1;
			curColor.setCount(count);
			
			//If it's black, you can't pop
			if(count == 0 && curColor.COLOR != BLACK){
				curColor = stack.pop();
			}
		} else {
			//Set right before return in case color changes
			if(key != SHIFT) curColor.setCount(curColor.count + 1);
		}
		
		return curColor.COLOR;
	}
	
	public String popCheck(KeyUpEvent event){
		int key = event.getNativeKeyCode();
		boolean shift = event.isShiftKeyDown();
		
		String checkColor = curColor.COLOR;
		/*-{ console.log(key); }-*/
		if(shift){
			switch (key){
				case CPARENS: 	// )
					if(checkColor == PURPLE)
						curColor = stack.pop();
					break;
				case CCURLS:  	// }
					if(checkColor == GREEN)
						curColor = stack.pop();
					break;
				case QUOTE:  	// "
					if(checkColor == LIGHTBLUE && curColor.count > 1){
						curColor = stack.pop();
					}
					break;
				default:
					break;
			}
		} else {
			switch (key){
				case CCURLS:  	// ]
					if(checkColor == RED)
						curColor = stack.pop();
					break;
				case QUOTE: 	// '
					if(checkColor == DARKBLUE && newSQuote)
						curColor = stack.pop();
					newSQuote = true;
					break;
				default:
					break;
			}
		}
		
		return curColor.COLOR;
	}
	
	private class ColorCounter{
		private final String COLOR;
		private int count;
		
		public ColorCounter(String hexColor){
			COLOR = hexColor;
			count = 0;
		}
		
		private void setCount(int count){
			this.count = count;
		}
	}
	
}
