package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client implements Runnable {

	public Socket socket = null;
	private BufferedReader inStream = null;
	private PrintWriter outStream = null;
	Thread thread;
	
	private Client(String host, int port) {
		try {
			socket = new Socket(host, port);
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
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Client mkInstance(String host, int port) {
		return new Client(host, port);
	}
	
	public void sendMessage(String message) {
		outStream.print(message+"\r\n");
		outStream.flush();
	}
	
	@Override
	public void run() {
		//읽기만 한다.
		String message = null;
		try {
			while((message = inStream.readLine()) != null) {
				System.out.println(message);
			}
			System.out.println("ended\n");
			closeStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void requestUserList() {
	
}
