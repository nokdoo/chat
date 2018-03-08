package chat;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import chat.client.Client;
import chat.server.Server;

public class Chat {
	private static Client client = null;
	private static Server server = null;
	private static Properties properties = null;
	private static Commander commanderDaemon = null;
	private static Chat chatDaemon = null;
	
	public Chat(Properties properties) {
		Chat.properties = properties;
		commanderDaemon = new Commander();
	}

	public void start() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader((System.in)));
		String command = reader.readLine();
		Commander.commandCheck(command);
	}
	
	public static void mkServerInstance() throws NumberFormatException, IOException {
		server = Server.mkInstance(properties);
		server.mkThread();
	}
	
	public static void mkClientInstance() {
		client = new Client(properties);
	}
}
