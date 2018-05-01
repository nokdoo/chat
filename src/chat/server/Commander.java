package chat.server;

import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commander {
	
	private enum Command{
		LIST("/list"), WHERE("/where");
		
		private String command;
		
		Command(String command){
			this.command = command;
		}
		
		public String getCommand() {
			return command;
		}
		
	}

	private static final Pattern startWithSpace = Pattern.compile("\\s+|$");
	
	public static Boolean isCommand(String messege) {
		String ltrim = messege.replaceAll("^\\s+", "");
		if(ltrim.charAt(0) == '/') {
			String firstWord = ltrim.substring(0, lastIndexOfFirstWord(ltrim));
			for(Command command : Command.values()) {
				if(command.getCommand().equals(firstWord)) {
					executeCommand(command, ltrim);
					return true;
				}
			}
		}
		return false;
	}
	
	private static void executeCommand(Command command, String str) {
		switch(command) {
		case LIST:
			break;
		case WHERE:
			break;
		default:
			break;
		}
	}
	
	private static int lastIndexOfFirstWord(String words) {
		Matcher matcher = startWithSpace.matcher(words);
		if(matcher.find()) {
			return matcher.start();
		}
		return 0;
	}
}
