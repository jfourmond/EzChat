package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
	private static final String PROPERTIES_FILE		= "dao/dao.properties";
	private static final String PROPERTY_URL		= "url";
	private static final String PROPERTY_DRIVER		= "driver";
	private static final String PROPERTY_USER		= "user";
	private static final String PROPERTY_PW			= "pw";
	
	private String	url;
	private String	username;
	private String	password;
	
	DAOFactory( String url, String username, String password ) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/*
	* Méthode chargée de récupérer les informations de connexion à la base de
	* données, charger le driver JDBC et retourner une instance de la Factory
	*/
	public static DAOFactory getInstance() throws DAOConfigurationException {
		Properties properties = new Properties();
		String url;
		String driver;
		String user;
		String pw;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(PROPERTIES_FILE);

		if ( fichierProperties == null ) {
			throw new DAOConfigurationException("Le fichier properties " + PROPERTIES_FILE + " est introuvable.");
		}

		try {
			properties.load( fichierProperties );
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			user = properties.getProperty(PROPERTY_USER);
			pw = properties.getProperty(PROPERTY_PW);
		} catch (IOException e) {
			throw new DAOConfigurationException("Impossible de charger le fichier properties " + PROPERTIES_FILE, e);
		}

		try {
			Class.forName( driver );
		} catch ( ClassNotFoundException e ) {
			throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
		}

		DAOFactory instance = new DAOFactory(url, user, pw);
		return instance;
	}

	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password );
	}
	
	public UserDAO getEnregistrementDao() {
		return new UserDAOImpl(this);
	}
}
