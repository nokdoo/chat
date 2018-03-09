package chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
		String fileName = "/home/nokdoo/workspace/chat/src/chat/chat.properties";
		InputStream is = null;
		File home = new File("src");
		File propDir = new File(home.getAbsoluteFile(), "chat");
		File propFile = new File(propDir, fileName);
		File propertyFile = new File(fileName);
		is = new FileInputStream(propertyFile);
		properties = new Properties();
		properties.load(is);
		is.close();
	}
}