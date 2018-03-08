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

public class Client {

	private Socket socket = null;
	public BufferedInputStream inStream = null;
	public BufferedReader inStream2 = null;
	private PrintWriter outStream = null;
	
	public Client(Properties properties) {
		// TODO Auto-generated constructor stub
	}
	
	
	public Client(String address, int port) throws IOException  {
		socket = new Socket(address, port);
		System.out.println(socket);
		inStream = new BufferedInputStream(System.in);
		outStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
	}

	public static void main(String argc[]) throws NumberFormatException, UnknownHostException, IOException {
		Client client = null;
		if(argc.length != 2) {
			client = new Client("localhost", 9999);
		}else {
			System.out.println(argc[0]);
			client = new Client(argc[0], Integer.parseInt(argc[1]));
			
		}
		BufferedReader r = new BufferedReader(new InputStreamReader(client.inStream, StandardCharsets.UTF_8));
		
		String a = r.readLine();
		client.outStream.print(a+"\r\n");
		client.outStream.flush();
		client.inStream2 = new BufferedReader(
								new InputStreamReader(client.socket.getInputStream(), StandardCharsets.UTF_8));
		
		a = client.inStream2.readLine();
		System.out.println(a);
	}
}
