package dao;

import java.util.List;

import metier.User;

public interface UserDAO {

	void create(User user) throws DAOException;
	
	void update(int id, User user) throws DAOException;
	
	User findByID(int id) throws DAOException;
	
	User find(String username, String password) throws DAOException;
	
	void incrementCountMessage(int id) throws DAOException;
	
	void updateLastConnexion(int id) throws DAOException;
	
	List<User> getAllUsers() throws DAOException;
	
	void deleteByID(int id) throws DAOException;
	
	int count() throws DAOException;
}
