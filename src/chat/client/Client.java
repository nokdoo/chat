package chat.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import chat.client.Configuration;

public class Client {

	public Socket socket;
	private BufferedInputStream console;
	private BufferedReader inStream;
	private PrintWriter outStream;
	Thread thread;
	Thread readThread;
	Thread writeThread;

	
	private Client(String host, int port) {
		try {
			socket = new Socket(host, port);
			openStream();
			initThread();
			runThread();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initThread() {
		readThread = new Thread(new Runnable() {           
	        public void run() { 
	        	String message = null;
	    		try {
	    			while ((message = inStream.readLine()) != null) {
	    				if(message.indexOf("/close") == 0) {
	    					closeStream();
	    				}
	    				System.out.println(message);
	    			}
	    			System.out.println("ended\n");
	    			closeStream();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        } 
	    }, "Read Thread : " + socket.getInetAddress().toString());
		
		writeThread = new Thread(new Runnable() {           
	        public void run() { 
	        	BufferedReader reader = new BufferedReader(new InputStreamReader(console, StandardCharsets.UTF_8));
	    		String message;
	    		while(true) {
	    			try {
						message = reader.readLine();
	        			sendMessage(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	        } 
	    }, "Write Thread : " + socket.getInetAddress().toString());
	}
	
	public static void mkInstance(String host, int port) {
		new Client(host, port);
	}
	
	private void runThread() {
		readThread.start();
		writeThread.start();
	}
	
	private void openStream() {
		try {
			console = new BufferedInputStream(System.in);
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			outStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private void closeStream() {
		try {
			console.close();
			inStream.close();
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendMessage(String message) {
		outStream.print(message + "\r\n");
		outStream.flush();
	}

	public static void main(String args[]) {
		String host = chat.client.Configuration.properties.getProperty("host_address");
		int port = Integer.parseInt(Configuration.properties.getProperty("port"));
		Client.mkInstance(host, port);
	}

}
