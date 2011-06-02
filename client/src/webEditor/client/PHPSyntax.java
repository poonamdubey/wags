package webEditor.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;

public class PHPSyntax
{
	public static String parse(String string)
	{
		return string;
	}

	public static String keyword(String s, In in){
		// Regx for PHP keywords
		String regex = "^[\\s]*(abstract|and|array\\(\\)|as|break|case|catch|class|clone|const|continue|declare|default|do|" +
		"else|elseif|enddeclare|endfor|endforeach|endif|endswitch|endwhile|extends|final|for|foreach|" +
		"function|global|goto|namespace|new|or|private|protected|public|static|switch|throw|try|use|var|while|xor)[\\s]*$";
		
		RegExp r = RegExp.compile(regex);
		MatchResult mr = r.exec(s);
		if(mr != null){
			// Match!
			in.setLastIndex(in.getIndex());
			return "<span style='color: purple; font-weight: bold;'>"+s+"</span>";
		}else{
			String peek = in.peek();
			if(peek == null)
				return null;
			return keyword(s+peek, in);
		}
	}
	
	public static String string(String s, In in){
		// Regex for PHP strings
		//String regex = "[\\s]*('|\").*('|\")[\\s]*";
		String regex = "[\\s]*(('.*')|(\".*\"))[\\s]*";
		Window.alert(s);
		RegExp r = RegExp.compile(regex);
		MatchResult mr = r.exec(s);
		if(mr != null){
			// Match!
			in.setLastIndex(in.getIndex());
			return "<span style='color: blue; font-style: italic;'>"+s+"</span>";
		}else{
			// Read in next token and try again.
			String peek = in.peek();
			if(peek == null)
				return null;
			return string(s+peek, in);
		}
	}
}

class In {
	private String theString;
	private int index;
	private int last;
	
	public In(String s){
		theString = s;
		index = 0;
		last = 0;
	}
	
	public int getLastIndex(){return last;}
	public int getIndex(){return index;}
	public void setIndex(int x){ index = x; }
	public void setLastIndex(int x){ last= x; }
	
	public String peek(){
		String s;
		if(index < theString.length())
			s = theString.charAt(index)+"";
		else
			s = null;
		index ++;
		return s;
	}
	
	public String next(){
		index = last;
		String s;
		if(index < theString.length())
			s = theString.charAt(index)+"";
		else
			s = null;
		index ++;
		last ++;
		return s;
	}
}