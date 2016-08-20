package dao;

import static dao.DAOUtility.initialisationPreparedRequest;
import static dao.DAOUtility.silentCloses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.User;

public class UserDAOImpl implements UserDAO {

	private DAOFactory daoFactory;
	
	private static final String TABLE = "users";
	
	private static final String ID = "id";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String INSCRIPTION_DATE = "inscription_date";
	private static final String LAST_CONNEXION = "last_connexion";
	private static final String LAST_MESSAGE = "last_message";
	private static final String COUNT_MESSAGE = "count_message";
	
	private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE +
												" WHERE " +  ID + " = ? ";

	private static final String SELECT_BY_NAME_AND_PWD = "SELECT * FROM " + TABLE +
															" WHERE " + USERNAME + " = ? AND " +
															PASSWORD + " = ?";
	
	private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
	
	private static final String INSERT = "INSERT INTO " + TABLE + " ( " + 
											USERNAME + ", " +
											PASSWORD + ") VALUES (?, ?)";
	
	private static final String UPDATE = "UPDATE " + TABLE + " SET " +
											USERNAME + " = ?, " +
											PASSWORD + " = ? WHERE " + ID + " = ?";
	
	private static final String INCREMENT = "UPDATE " + TABLE + " SET " + 
											COUNT_MESSAGE + " = " + COUNT_MESSAGE + " + 1 ," +
											LAST_MESSAGE + " = NOW() " +
											"WHERE " + ID + " = ?";
	
	private static final String UPDATE_LAST_CONNEXION = "UPDATE " + TABLE + " SET " +
														LAST_CONNEXION + " = NOW() " +
														"WHERE " + ID + " = ?";
	
	private static final String DELETE = "DELETE FROM " + TABLE + " WHERE " + ID + " = ? ";
	
	private static final String COUNT = "SELECT COUNT(*) FROM " + TABLE;
	
	public UserDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void create(User user) throws IllegalArgumentException, DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet values = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, INSERT, true, user.getName(), user.getPassword());
			int status = preparedStatement.executeUpdate();
			if(status == 0)
				throw new DAOException("Échec de l'ajout de l'utilisateur, aucune ligne ajoutée dans la table.");
			values = preparedStatement.getGeneratedKeys();
			if(values.next())
				user.setId(values.getInt(1));
			else
				throw new DAOException("Échec de l'ajout de l'utilisateur en base, aucun ID auto-généré retourné.");
		} catch (SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(values, preparedStatement, connection);
		}
	}

	@Override
	public void update(int id, User user) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet values = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, UPDATE, false, user.getName(), user.getPassword(), id);
			int status = preparedStatement.executeUpdate();
			if(status == 0)
				throw new DAOException("Échec de l'édition de l'utilisateur, aucune ligne éditée dans la table.");
		} catch (SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(values, preparedStatement, connection);
		}
	}

	@Override
	public void incrementCountMessage(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet values = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, INCREMENT, false, id);
			int status = preparedStatement.executeUpdate();
			if(status == 0)
				throw new DAOException("Échec de l'incrémentation du compte de message de l'utilisateur, aucune ligne éditée dans la table.");
		} catch (SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(values, preparedStatement, connection);
		}
	}
	
	@Override
	public void updateLastConnexion(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet values = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, UPDATE_LAST_CONNEXION, false, id);
			int status = preparedStatement.executeUpdate();
			if(status == 0)
				throw new DAOException("Échec de la mise à jour de la date de dernière connexion de l'utilisateur, aucune ligne éditée dans la table.");
		} catch (SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(values, preparedStatement, connection);
		}
	}
	
	@Override
	public User findByID(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, SELECT_BY_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user = map(resultSet);
			}
		} catch(SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(resultSet, preparedStatement, connection);
		}
		return user;
	}

	@Override
	public User find(String username, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, SELECT_BY_NAME_AND_PWD, false, username, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user = map(resultSet);
			}
		} catch(SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(resultSet, preparedStatement, connection);
		}
		return user;
	}
	
	@Override
	public List<User> getAllUsers() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<User> users = new ArrayList<>();
		User user = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, SELECT_ALL, false);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				user = map(resultSet);
				users.add(user);
			}
		} catch(SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(resultSet, preparedStatement, connection);
		}
		return users;
	}

	@Override
	public void deleteByID(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet values = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, DELETE, false, id);
			int status = preparedStatement.executeUpdate();
			if(status == 0)
				throw new DAOException("Échec de la suppresion de l'enregistrement, aucune ligne supprimée dans la table.");
		} catch (SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(values, preparedStatement, connection);
		}
	}

	@Override
	public int count() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		int count = 0;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationPreparedRequest(connection, COUNT, true);
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			count = resultSet.getInt(1);
		} catch(SQLException E) {
			throw new DAOException(E);
		} finally {
			silentCloses(resultSet, preparedStatement, connection);
		}
		return count;
	}
	
	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt(ID));
		user.setName(resultSet.getString(USERNAME));
		user.setPassword(resultSet.getString(PASSWORD));
		user.setInscriptionDate(resultSet.getDate(INSCRIPTION_DATE));
		user.setLastConnexion(resultSet.getDate(LAST_CONNEXION));
		user.setLastMessage(resultSet.getDate(LAST_MESSAGE));
		user.setCountMessage(resultSet.getInt(COUNT_MESSAGE));
		return user;
	}
}
