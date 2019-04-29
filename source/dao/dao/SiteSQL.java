package edu.gatech.cs6400.team81.dao;

public interface SiteSQL {
	public static final String GET_ALL = "SELECT * FROM Site ";
	public static final String GET_BY_SITEID = GET_ALL + " WHERE Id = ?";

}