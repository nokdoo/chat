package chat.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	
	public static Properties properties = null;
	
	static {
		String fileName = "/home/nokdoo/workspace/chat/src/chat/server/properties";
		File propertyFile = new File(fileName);
		try(InputStream is  = new FileInputStream(propertyFile)){
			properties = new Properties();
			properties.load(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}