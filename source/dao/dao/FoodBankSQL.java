package edu.gatech.cs6400.team81.dao;

public interface FoodBankSQL {
	public static final String GET_BY_SITEID = "SELECT * FROM FoodBank fb JOIN Site s ON fb.SITEID = s.ID WHERE fb.SITEID = ?";
	public static final String DELETE_BY_SITEID = "DELETE FROM FoodBank WHERE SITEID = ?";
	public static final String CREATE_FOODBANK = "INSERT INTO FoodBank (SITEID) VALUES (?)";
}
