package edu.gatech.cs6400.team81.dao;

public interface UserSQL {
	public static final String GET_BY_USERNAME = "SELECT * FROM User u JOIN Account a ON u.USERNAME=a.USERNAME WHERE a.USERNAME = ?";
	public static final String CREATE_USER = "INSERT INTO User (USERNAME, EMAIL, FIRSTNAME, LASTNAME, ROLE, SITEID) VALUES(?, ?, ?, ?, ?, ?)";
}
