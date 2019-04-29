package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;

import edu.gatech.cs6400.team81.model.Account;

public interface AccountDAO {
	public static final String USERNAME = "USERNAME";
	public static final String PASSWORD = "PASSWORD";
	
	Account getByUsername(String username) throws SQLException;
	void createAccount(Account account) throws SQLException;
}
