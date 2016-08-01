package client;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import metier.Message;

public class Client {

	private static String host = "localhost";
	private static int port = 7030;
	
	public static void main(String[] args) {
		System.err.println("CLIENT");
		
		Message M = new Message("Jerome", "Hello World !");
		try {
			Socket s = new Socket(host, port);
			
			OutputStream os = s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			
			oos.writeObject(M);
			
			oos.close();
			os.close();
			s.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
