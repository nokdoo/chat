package chat.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Client implements Runnable {

	private Socket socket = null;
	private BufferedReader inStream = null;
	private PrintWriter outStream = null;
	Thread thread;
	
	private Client(Properties properties) {
		try {
			socket = new Socket(
					properties.getProperty("host_address"),
					Integer.parseInt(properties.getProperty("port")
							));
			inStream = new BufferedReader(
								new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			outStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mkThread() {
		thread = new Thread(this, socket.getInetAddress().toString());
		thread.start();
	}
	
	private void closeStream() {
		try {
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Client(String address, int port) throws IOException  {
		socket = new Socket(address, port);
	}

	public static Client mkInstance(Properties properties) {
		return new Client(properties);
	}
	
	public void sendMessage(String message) {
		outStream.print(message+"\r\n");
		outStream.flush();
	}

	@Override
	public void run() {
		//읽기만 한다.
		while(true) {
			String a = null;
			try {
				a = inStream.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(a);
		}
	}
}
