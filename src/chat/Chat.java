package chat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import chat.client.Client;
import chat.server.Server;

public class Chat {
	private static Client client = null;
	private BufferedInputStream console = null;
	
	public static void main(String args[]) {
		String host = Configuration.properties.getProperty("host_address");
		int port = Integer.parseInt(Configuration.properties.getProperty("port"));
		Chat chat = new Chat(host, port);
		try {
			chat.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chat(String host, int port) {
		openStream();
	}

	private void openStream() {
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
			if(client == null) {
				System.out.println("명령어를 입력해주세요.");
				return;
			}
			client.sendMessage(message);
		}
	}

	public static void mkServerInstance() throws NumberFormatException, IOException {
		Server.mkInstance().mkThread();
	}
	
	public static void mkClientInstance(String host, int port) {
		client = Client.mkInstance(host, port);
		client.mkThread();
	}
	
	public static void getUserList() {
		System.out.println(client.requestUserList());
	}
	
	public static void getChatName() {
		System.out.println(client.socket.toString());
	}
}
