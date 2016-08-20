package server;

import java.io.IOException;

import metier.Message;

public class MessageManagement extends Thread {
	private Message message;
	
	public MessageManagement(Message message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		for(DialogThread dt : Server.getSockets()) {
			try {
				dt.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
