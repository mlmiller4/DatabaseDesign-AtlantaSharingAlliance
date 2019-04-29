package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.Client;

@Repository
public class ClientDAOImpl extends BaseDAO<Client> implements ClientDAO, ClientSQL{

	@Override
	protected Client mapResult(ResultSet rs) throws SQLException {
		Client client = new Client();
		client.setDescription(rs.getString(DESCRIPTION));
		client.setIdentifier(rs.getInt(IDENTIFIER));
		client.setName(rs.getString(NAME));
		client.setPhone(rs.getString(PHONE));
		
		return client;
	}
	
	@Override
	public List<Client> getUnassignedForSite(int siteId) throws SQLException {
		return getMultiple(GET_UNASSIGNED_FOR_SITEID, new Object[]{siteId});
	}
}
