package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Server{

	private HashMap<String, Socket> socketHash = null;
	private ServerSocket serverSocket = null;
	private ServerThread serverThread = null;
	private Thread thread;
	
	private Server() {
		int port = Integer.parseInt(Configuration.properties.getProperty("port"));
		try {
			serverSocket = new ServerSocket(port);
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		socketHash = new HashMap<String, Socket>();
	}
	
	public static void main(String args[]) {
		try {
			Server.mkInstance().mkServerThread();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static Server mkInstance() {
		Server server = new Server();
		return server;
	}

	public void mkServerThread() {
		thread = new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						serverThread = new ServerThread(serverSocket.accept(), socketHash);
					} catch (IOException e) {
						System.out.println(e);
						e.printStackTrace();
					}
					String inetAddress = serverThread.socket.getInetAddress().toString();
					Integer port = serverThread.socket.getPort();
					socketHash.put(inetAddress+":"+port, serverThread.socket);
					serverThread.mkThread();
				}
			}
		}, "ServerThread");
		thread.start();
	}
	
	private class ServerThread extends Socket implements Runnable  {

		public Socket socket;
		public String name;
		private BufferedReader inStream =  null;
		private PrintWriter outStream = null;
		Thread thread;
		HashMap<String, Socket> socketHash = null;
		private String messege = null;
		
		private ServerThread(Socket socket, HashMap<String, Socket> socketHash) {
			this.socket = socket;
			this.name = socket.getInetAddress().toString()+":"+socket.getPort();
			this.socketHash = socketHash;
			openInStream();
		}
		
		private void mkThread() {
			thread = new Thread(this, name);
			thread.start();
		}
		
		private void receiveMessege() {
			try {
				while((messege = inStream.readLine()) != null) {
					sendMessege(messege);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("socket ended : " + socket);
		}
		
		
		private void openInStream() {
			try {
				this.inStream = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void sendMessege(String messege) {
			synchronized (socketHash) {
				for(Map.Entry<String, Socket> entry : socketHash.entrySet()) {
					try {
						outStream = new PrintWriter(
											new OutputStreamWriter(
													entry.getValue().getOutputStream(), StandardCharsets.UTF_8));
						outStream.print(messege+"\r\n");
						outStream.flush();
					} catch (IOException e) {
						System.out.println(e);
					e.printStackTrace();
					}
				}
			}
		}

		@Override
		public void run() {
			//openInStream();
			receiveMessege();
		}
	}
	
}