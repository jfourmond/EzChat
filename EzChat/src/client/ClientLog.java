package client;

import java.util.logging.Logger;

public class ClientLog {
	private static Logger LOGGER = Logger.getLogger(Client.class.getName());
	
	public static void fine(String msg) { LOGGER.fine(msg); }
	
	public static void info(String msg) { LOGGER.info(msg); }
	
	public static void severe(String msg) { LOGGER.severe(msg); }
	
	public static void warning(String msg) { LOGGER.warning(msg); }
}
