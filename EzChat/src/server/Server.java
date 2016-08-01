package server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import metier.Message;

public class Server {

	static String host;
	static int port = 7030;
	
	public static void main(String[] args) throws Exception {
		System.err.println("SERVER");
		
		ServerSocket server = new ServerSocket(port);
		Socket socket = server.accept();
		
		InputStream is = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		
		Message M = (Message)ois.readObject();
		if (M !=null )
			System.out.println(M);
		else
			System.out.println((String)ois.readObject());
		
		is.close();
		socket.close();
		server.close();
	}

}
