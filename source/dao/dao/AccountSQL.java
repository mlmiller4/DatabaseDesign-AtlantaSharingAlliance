package edu.gatech.cs6400.team81.dao;

public interface AccountSQL {
	public static final String CREATE_ACCOUNT = "INSERT INTO Account (USERNAME, PASSWORD) VALUES (?, ?)";
	public static final String GET_BY_USERNAME = "SELECT * FROM Account WHERE USERNAME = ?";
}
