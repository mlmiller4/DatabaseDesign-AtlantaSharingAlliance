package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;

import edu.gatech.cs6400.team81.model.User;

public interface UserDAO {
	public static final String TABLENAME = "User";
	public static final String USERNAME = "USERNAME";
	public static final String EMAIL = "EMAIL";
	public static final String FIRSTNAME = "FIRSTNAME";
	public static final String LASTNAME = "LASTNAME";
	public static final String ROLE = "ROLE";
	public static final String SITEID = "SITEID";

	
	User getByUsername(String username) throws SQLException;
	void createUser(User user) throws SQLException;

}
