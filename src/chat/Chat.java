package chat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import chat.client.Client;
import chat.server.Server;

public class Chat {
	private static Client client = null;
	private static Server server = null;
	private static Properties properties = null;
	private BufferedInputStream console = null;
	
	public Chat(Properties properties) {
		Chat.properties = properties;
		openStream();
	}
	
	public void openStream() {
		console = new BufferedInputStream(System.in);
	}
	
	public void start() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(console, StandardCharsets.UTF_8));
		String message;
		while(true) {
			message = reader.readLine();
			if(Commander.isCommand(message) == true) {
				continue;
			}
			sendStream(message);
		}
	}
	
	public static void mkServerInstance() throws NumberFormatException, IOException {
		server = Server.mkInstance(properties);
		server.mkThread();
	}
	public static void mkClientInstance() {
		client = Client.mkInstance(properties);
		client.mkThread();
	}
	
	private void sendStream(String message) {
		client.sendMessage(message);
	}
}
