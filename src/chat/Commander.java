package chat;

import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commander {
	
	private enum Command{
		START("-start"), JOIN("-join"), PUB("-pub"), PRIV("-priv"), OUT("-out");
		
		private String command;
		
		Command(String command){
			this.command = command;
		}
		
		public String getCommand() {
			return command;
		}
		
	}

	private static Pattern pattern = null;
	
	{
		pattern = Pattern.compile("\\s+|$");
	}
	
	public static String commandCheck(String messege) {
		String ltrim = messege.replaceAll("^\\s+", "");
		if(ltrim.charAt(0) == '-') {
			String firstWord = ltrim.substring(0, lastIndexOfFirstWord(ltrim));
			for(Command command : Command.values()) {
				if(command.getCommand().equals(firstWord)) {
					executeCommand(command);
					break;
				}
			}
			return null;
		}
		return messege;
	}
	
	private static void executeCommand(Command command) {
		switch(command) {
		case START:
			try {
				Chat.mkServerInstance();
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	
	private static int lastIndexOfFirstWord(String words) {
		Matcher matcher = pattern.matcher(words);
		if(matcher.find()) {
			return matcher.start();
		}
		return 0;
	}
}
