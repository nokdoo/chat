package chat.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ThreadSocket extends Socket implements Runnable  {

	public Socket socket;
	public String name;
	private BufferedReader inStream =  null;
	private PrintWriter outStream = null;
	Thread thread;
	HashMap<String, Socket> socketHash = null;
	private String messege = null;
	
	public ThreadSocket(Socket socket, HashMap<String, Socket> socketHash) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.name = socket.getInetAddress().toString()+":"+socket.getPort();
		this.socketHash = socketHash;
		openInStream();
	}
	
	public void mkThread() {
		thread = new Thread(this, name);
		thread.start();
	}
	
	private void receiveMessege() {
		try {
			while((messege = inStream.readLine()) != null) {
				System.err.println("Read : " + messege);
				sendMessege(messege);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("socket ended : " + socket);
	}
	
	
	private void openInStream() {
		try {
			this.inStream = new BufferedReader(
					new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendMessege(String messege) {
		synchronized (socketHash) {
			for(Map.Entry<String, Socket> entry : socketHash.entrySet()) {
				try {
					PrintWriter pw = new PrintWriter(
										new OutputStreamWriter(
												entry.getValue().getOutputStream(), StandardCharsets.UTF_8));
					pw.print(messege+"\r\n");
					pw.flush();
				} catch (IOException e) {
					System.out.println(e);
					// TODO Auto-generated catch block
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
