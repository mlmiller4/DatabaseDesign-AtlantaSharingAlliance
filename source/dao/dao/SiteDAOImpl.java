package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.Site;

@Repository
public class SiteDAOImpl extends BaseDAO<Site> implements SiteDAO, SiteSQL {

	@Override
	protected Site mapResult(ResultSet rs) throws SQLException {
		Site site = new Site();
		site.setCity(rs.getString(CITY));
		site.setId(rs.getInt(ID));
		site.setPhoneNumber(rs.getString(PHONENUMBER));
		site.setShortName(rs.getString(SHORTNAME));
		site.setState(rs.getString(STATE));
		site.setStreetAddress(rs.getString(STREETADDRESS));
		site.setZip(rs.getString(ZIP));

		return site;
	}

	@Override
	public Site getBySiteId(int siteId) throws SQLException{
		return getUnique(GET_BY_SITEID, new Object[]{siteId});
	}
	
	@Override
	public List<Site> getAll() throws SQLException{
		return getMultiple(GET_ALL, new Object[]{});
	}
}
