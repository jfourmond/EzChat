package dao;

import java.util.List;

import metier.User;

public interface UserDAO {

	void create(User user) throws DAOException;
	
	void update(String id, User user) throws DAOException;
	
	User findByID(String id) throws DAOException;
	
	User find(String username, String password);
	
	List<User> getAllUsers() throws DAOException;
	
	void deleteByID(String id) throws DAOException;
	
	int count() throws DAOException;
}
