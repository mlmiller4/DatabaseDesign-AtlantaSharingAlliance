package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;

import edu.gatech.cs6400.team81.model.FoodPantry;

public interface FoodPantryDAO {
	public static final String TABLENAME = "FoodPantry";
	public static final String SITEID = "SiteId";
	public static final String SERVICEID = "ServiceId";


	FoodPantry getBySiteId(int siteId) throws SQLException;


	boolean delete(int serviceId) throws SQLException;


	boolean add(FoodPantry newService) throws SQLException;
}
