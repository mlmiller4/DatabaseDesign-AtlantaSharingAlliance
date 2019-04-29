package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.Client;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.WaitList;

@Repository
public class WaitListDAOImpl extends BaseDAO<WaitList> implements WaitListDAO, WaitListSQL{

	
	@Override
	protected WaitList mapResult(ResultSet rs) throws SQLException {
		WaitList waitList = new WaitList();
		waitList.setPosition(Integer.parseInt(rs.getString(POSITION)));
		
		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));	
		
		waitList.setSite(site);

		Client client = new Client();
		client.setDescription(rs.getString(ClientDAO.DESCRIPTION));
		client.setIdentifier(Integer.parseInt(rs.getString(ClientDAO.IDENTIFIER)));
		client.setName(rs.getString(ClientDAO.NAME));
		client.setPhone(rs.getString(ClientDAO.PHONE));
		
		waitList.setClient(client);
		
		return waitList;
	}
	
	@Override
	public List<WaitList> getBySiteId(int siteId) throws SQLException{
		return getMultiple(GET_BY_SITEID, new Object[]{siteId});
	}
	
	@Override
	public WaitList getBySiteIdClientId(int siteId, int clientId) throws SQLException{
		return getUnique(GET_BY_SITEID_CLIENTID, new Object[]{siteId, clientId});
	}
	
	@Override
	public boolean removeFromList(int siteId, int clientId) throws SQLException{
		int rows = execute(REMOVE_FROM_LIST, new Object[]{siteId, clientId});
		
		return rows == 1;
	}
	
	@Override
	public int incrementPosition(int siteId, int beginPos, int endPos) throws SQLException{
		return execute(INCREMENT_BETWEEN, new Object[]{siteId, beginPos, endPos});
	}
	
	@Override
	public int decrementPosition(int siteId, int beginPos, int endPos) throws SQLException{
		return execute(DECREMENT_BETWEEN, new Object[]{siteId, beginPos, endPos});
	}
	
	@Override
	public int decrementPositionToEnd(int siteId, int beginPos) throws SQLException{
		return execute(DECREMENT_FROM, new Object[]{siteId, beginPos});
	}
	
	@Override
	public boolean addToList(int siteId, int clientId, Integer position) throws SQLException{
		int rows = 0;
		if(position == null){
			int lastPos = getIntResult(LAST_POSITION_BY_SITE, new Object[]{siteId})[0];//(CREATE_AT_END_OF_LIST, new Object[]{clientId, siteId, siteId});
			position = lastPos + 1;
		}
		rows = execute(CREATE, new Object[]{clientId, siteId, position});
		
		return rows == 1;
	}
}