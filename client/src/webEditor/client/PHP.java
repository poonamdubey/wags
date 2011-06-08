package webEditor.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;

public class PHP
{
	public String parse(String str){
		// Split on newline.
		String split[] = str.split("\n");
		
		String parsed = "";
		for(String line : split){
			// Scan line for keywords...
			parsed += keyword(variable(commentLine(string(line))))+"<br />";
		}

		return parsed;
	}
	
	public String keyword(String line){
		String regex = "[^\\$\"].*(abstract|and|array\\(\\)|as|break|case|catch|class|clone|const|continue|declare|default|do|" +
		"else|elseif|enddeclare|endfor|endforeach|endif|endswitch|endwhile|extends|final|for|foreach|" +
		"function|global|goto|namespace|new|private|protected|public|static|switch|throw|try|use|var|while|xor)+.*[^\"]";
		
		return line.replaceAll(regex, "<span style='color: blue;'>$&</span>");
	}
	
	public String string(String line){
		String regex = "(('([^\'<]|.*).*')|(\"([^\">]|.*)\"))+";
		return line.replaceAll(regex, "<span style='color: purple; font-style: italic;'>$&</span>");
	}
	
	public String variable(String line){
		String regex = "([\\$][\\w]+)";
		return line.replaceAll(regex, "<span style='color: red;'>$&</span>");
	}
	
	public String commentLine(String line){
		String regex = "^//.*$";

		return line.replaceAll(regex, "<span style='color: green;'>$&</span>");
	}
	
	@SuppressWarnings("unused")
	private void test(String regex, String line){
		RegExp reg = RegExp.compile(regex);
		MatchResult result = reg.exec(line);
		for(int i = 0; i < result.getGroupCount(); i++){
			Window.alert(result.getGroup(i));
		}
	}

}
