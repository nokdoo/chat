package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Properties;

public class Server implements Runnable {

	private HashMap<String, Socket> socketHash = null;
	private ServerSocket serverSocket = null;
	private ThreadSocket threadSocket = null;

	private static Server serverDaemon = null;

	private Server(Properties properties) throws NumberFormatException, IOException {
		serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("port")));
		socketHash = new HashMap<String, Socket>();
	}

	public static Server mkInstance(Properties properties) {
		try {
			if (serverDaemon == null) {
				serverDaemon = new Server(properties);
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("mkServerInstance completed");
		return serverDaemon;
	}

	public void mkThread() {
		Thread thread = new Thread(this, "ServerThread");
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				threadSocket = new ThreadSocket(serverSocket.accept(), socketHash);
			} catch (IOException e) {
				System.out.println(e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String inetAddress = threadSocket.socket.getInetAddress().toString();
			Integer port = threadSocket.socket.getPort();
			socketHash.put(inetAddress+":"+port, threadSocket.socket);
			threadSocket.mkThread();
		}
	}
}