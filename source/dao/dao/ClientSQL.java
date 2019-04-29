package edu.gatech.cs6400.team81.dao;

public interface ClientSQL {

	public static final String GET_UNASSIGNED_FOR_SITEID = "SELECT * FROM Client WHERE Identifier NOT IN (SELECT ClientId From WaitList WHERE SiteId = ?)";
}
