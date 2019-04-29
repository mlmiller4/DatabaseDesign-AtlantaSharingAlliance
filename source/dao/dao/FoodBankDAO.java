package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;

import edu.gatech.cs6400.team81.model.FoodBank;

public interface FoodBankDAO {
	public static final String SERVICEID = "SERVICEID";
	public static final String SITEID = "SITEID";
	
	FoodBank getBySiteId(int siteId) throws SQLException;

	boolean delete(int siteId) throws SQLException;

	boolean add(FoodBank newService) throws SQLException;
}
