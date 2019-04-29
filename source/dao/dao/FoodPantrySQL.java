package edu.gatech.cs6400.team81.dao;

public interface FoodPantrySQL {
	public static final String GET_BY_SITEID = "SELECT * FROM FoodPantry fp JOIN ClientService cs ON fp.serviceId = cs.serviceId JOIN Site s ON fp.siteId = s.Id WHERE fp.siteId = ?";

	public static final String DELETE_BY_SERVICEID = "DELETE FROM FoodPantry WHERE serviceId = ?";
	
	public static final String CREATE_FOODPANTRY = "INSERT INTO FoodPantry (SERVICEID, SITEID) VALUES (?,?)";
}