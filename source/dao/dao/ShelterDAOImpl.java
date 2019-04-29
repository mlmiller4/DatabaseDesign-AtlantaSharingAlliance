package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Shelter;
import edu.gatech.cs6400.team81.model.Site;

@Repository
public class ShelterDAOImpl extends BaseDAO<Shelter> implements ShelterDAO, ShelterSQL {
	private static final String[] COLUMN_NAMES = new String[]{FAMILYROOMCOUNT.toUpperCase(), MALEBUNKCOUNT.toUpperCase(), FEMALEBUNKCOUNT.toUpperCase(), MIXEDBUNKCOUNT.toUpperCase()};
	private static final List<String> VALID_COLUMNS = Collections.unmodifiableList(Arrays.asList(COLUMN_NAMES)); 

	@Override
	protected Shelter mapResult(ResultSet rs) throws SQLException {
		Shelter shelter = new Shelter();

		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));
		shelter.setSite(site);
	
		shelter.setConditionUse(rs.getString(ClientServiceDAO.CONDITIONUSE));
		shelter.setDescription(rs.getString(ClientServiceDAO.DESCRIPTION));
		shelter.setHoursOperation(rs.getString(ClientServiceDAO.HOURSOPERATION));
		shelter.setServiceCategory(ServiceCategory.valueOf(rs.getString(ClientServiceDAO.SERVICECATEGORY).toUpperCase()));
		shelter.setServiceId(rs.getInt(SERVICEID));

		shelter.setFamilyRoomCount(rs.getInt(FAMILYROOMCOUNT));
		shelter.setFemaleBunkCount(rs.getInt(FEMALEBUNKCOUNT));
		shelter.setMaleBunkCount(rs.getInt(MALEBUNKCOUNT));
		shelter.setMixedBunkCount(rs.getInt(MIXEDBUNKCOUNT));
		shelter.setTotalBunkCount(rs.getInt(TOTALBUNKCOUNT));

		return shelter;
	}

	@Override
	public List<Shelter> getAll() throws SQLException{
		return getMultiple(GET_ALL, new Object[]{});
	}
	
	@Override
	public Shelter getBySiteId(int siteId) throws SQLException{
		return getUnique(GET_BY_SITEID, new Object[]{siteId});
	}
	
	@Override
	public boolean delete(int serviceId) throws SQLException {
		int rows = execute(DELETE_BY_SERVICEID, new Object[]{serviceId});
		return rows == 1;
	}
	
	@Override
	public boolean add(Shelter newService) throws SQLException {
		//SiteId, ServiceId, FamilyRoomCount, MaleBunkCount, FemaleBunkCount, MixedBunkCount, TotalBunkCount
		Object[] params = new Object[7];
		int index = 0;
		params[index] = newService.getSite().getId();
		params[++index] = newService.getServiceId();
		params[++index] = newService.getFamilyRoomCount();
		params[++index] = newService.getMaleBunkCount();
		params[++index] = newService.getFemaleBunkCount();
		params[++index] = newService.getMixedBunkCount();
		params[++index] = newService.getMaleBunkCount() + newService.getFemaleBunkCount() + newService.getMixedBunkCount();
		
		int rows = execute(CREATE_SHELTER, params);
		return rows == 1;
	}
	
	@Override
	public boolean update(Shelter updates) throws SQLException {
		int familyRoomCount = updates.getFamilyRoomCount();
		int femaleBunkCount = updates.getFemaleBunkCount();
		int maleBunkCount = updates.getMaleBunkCount();
		int mixedBunkCount = updates.getMixedBunkCount();
		int totalBunkCount = femaleBunkCount + maleBunkCount + mixedBunkCount;
		
		int rows = execute(UPDATE_BY_SERVICEID, new Object[]{familyRoomCount, femaleBunkCount, maleBunkCount, mixedBunkCount, totalBunkCount, updates.getServiceId()});
		return rows == 1;
	}
	
	@Override
	public List<Map<String, Object>> getSiteBunkRooms() throws SQLException{
		return getResult(GET_BUNK_COUNTS, new Object[]{});
	}
	
	@Override
	public int[] getBunkCountsBySite(int siteId) throws SQLException{
		return getIntResult(GET_BUNK_COUNTS_BY_SITE , new Object[]{siteId});
	}

	@Override
	public int getFamilyRoomCountBySite(int siteId) throws SQLException {
		int[] counts = getIntResult(GET_FAMILYROOM_COUNT_BY_SITE , new Object[]{siteId});
		if(counts == null){
			return 0;
		}
		return counts[0];
	}

	@Override
	public boolean updateFamilyRoomCount(int siteId, int value) throws SQLException {

		int rows = execute(UPDATE_FAMILYROOM_COUNT_BY_SITE, new Object[]{value}) ;

		return rows == 1;
	}

	@Override
	public boolean updateBunkCount(int siteId, String columnName, int value) throws SQLException{
		if(columnName == null || !VALID_COLUMNS.contains(columnName.toUpperCase())){
			throw new SQLException("Invalid Column[" + columnName + "]");
		}
		String sql = String.format(UPDATE_COL_BY_SITE, columnName);
		int rows = execute(sql, new Object[]{value, siteId});

		return rows == 1 && updateTotalBunkCount(siteId);
	}

	@Override
	public boolean incrementCount(int siteId, String columnName) throws SQLException{
		if(columnName == null || !VALID_COLUMNS.contains(columnName.toUpperCase())){
			throw new SQLException("Invalid Column[" + columnName + "]");
		}
		String sql = String.format(INCREMENT_COUNT_BY_SITE, columnName, columnName);
		int rows = execute(sql, new Object[]{siteId});

		return rows == 1 && updateTotalBunkCount(siteId);
	}

	@Override
	public boolean decrementCount(int siteId, String columnName) throws SQLException{
		if(columnName == null || !VALID_COLUMNS.contains(columnName.toUpperCase())){
			throw new SQLException("Invalid Column[" + columnName + "]");
		}
		String sql = String.format(DECREMENT_COUNT_BY_SITE, columnName, columnName, columnName);
		int rows = execute(sql, new Object[]{siteId});

		return rows == 1 && updateTotalBunkCount(siteId);
	}
	
	@Override
	public boolean updateTotalBunkCount(int siteId) throws SQLException{
		int[] counts = getBunkCountsBySite(siteId);
		
		int rows = execute(UPDATE_TOTAL_BUNK_BY_SITE, new Object[]{counts[0] + counts[1] + counts[2], siteId});
		
		return rows == 1;
	}
}
