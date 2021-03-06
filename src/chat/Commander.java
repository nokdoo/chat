package chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commander {
	
	private enum Command{
		START("/start"), JOIN("/join"), PUB("/pub"), PRIV("/priv"), OUT("/out"),
		LIST("/list"), WHERE("/where"), MAKESERVER("/mks");
		
		private String command;
		
		Command(String command){
			this.command = command;
		}
		
		public String getCommand() {
			return command;
		}
		
	}

	private static final Pattern firstSpace = Pattern.compile("\\s+|$");
	private static final Pattern ip = Pattern.compile("\\W(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\W");
	private static final Pattern number = Pattern.compile("\\d+");
	
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
	}
	
	private static int lastIndexOfFirstWord(String words) {
		Matcher matcher = firstSpace.matcher(words);
		if(matcher.find()) {
			return matcher.start();
		}
		return 0;
	}
}
