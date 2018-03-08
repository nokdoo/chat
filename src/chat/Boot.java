package chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Boot {
	
	private static Chat chatDaemon = null;
	private static Properties properties = null;
	private static Boot daemon = null;
	
	
	public static void main(String args[]) throws IOException {
		if(daemon == null) {
			Boot boot = new Boot();
			boot.init();
			daemon = boot;
		}
		
		chatDaemon.start();
	}
	
	private void init() throws IOException {
		loadProperties();
		chatDaemon = new Chat(properties);
	}
	
	private void loadProperties() throws IOException {
		String fileName = "chat.properties";
		InputStream is = null;
		File home = new File(System.getProperty("user.dir"));
		File propDir = new File(home, "src/chat");
		File propFile = new File(propDir, fileName);
		is = new FileInputStream(propFile);
		properties = new Properties();
		properties.load(is);
		is.close();
	}
}
