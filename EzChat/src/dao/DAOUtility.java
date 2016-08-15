package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtility {
private DAOUtility() {};
	
	public static void silentClose( ResultSet resultSet ) {
		if ( resultSet != null ) {
			try {
				resultSet.close();
			} catch ( SQLException e ) {
				System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
			}
		}
	}

	public static void silentClose( Statement statement ) {
		if ( statement != null ) {
			try {
				statement.close();
			} catch ( SQLException e ) {
				System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
			}
		}
	}

	public static void silentClose ( Connection connexion ) {
		if ( connexion != null ) {
			try {
				connexion.close();
			} catch ( SQLException e ) {
				System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
			}
		}
	}

	public static void silentCloses( Statement statement, Connection connexion ) {
		silentClose( statement );
		silentClose( connexion );
	}

	public static void silentCloses( ResultSet resultSet, Statement statement, Connection connexion ) {
		silentClose( resultSet );
		silentClose( statement );
		silentClose( connexion );
	}
	
	public static PreparedStatement initialisationPreparedRequest( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
		PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
		for ( int i = 0; i < objets.length; i++ ) {
			preparedStatement.setObject( i + 1, objets[i] );
		}
		return preparedStatement;
	}
}
