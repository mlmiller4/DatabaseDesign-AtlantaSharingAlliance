package edu.gatech.cs6400.team81.dao;

import edu.gatech.cs6400.team81.model.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RequestedItemDAOImpl extends BaseDAO<RequestedItem> implements RequestedItemDAO, RequestedItemSQL{

    @Override
    protected RequestedItem mapResult(ResultSet rs) throws SQLException {
        RequestedItem requestedItem = new RequestedItem();

        ItemCategory category = ItemCategory.valueOf(rs.getString(ItemDAO.CATEGORY).toUpperCase());
        if(category == ItemCategory.FOOD){
            requestedItem.setSubCategory(rs.getString(FoodItemDAO.FOODCATEGORY).toUpperCase());
        } else {
            requestedItem.setSubCategory(rs.getString(SupplyItemDAO.SUPPLYCATEGORY).toUpperCase());
        }

        requestedItem.setItemId(rs.getInt(ITEMID));
        requestedItem.setCategory(category);
        requestedItem.setExpireDate(rs.getTimestamp(ItemDAO.EXPIREDATE));
        requestedItem.setName(rs.getString(ItemDAO.NAME));
        requestedItem.setNumAvailable(rs.getInt(ItemDAO.NUMBERUNITS));
        requestedItem.setStorageType(ItemStorageType.byName(rs.getString(ItemDAO.STORAGETYPE).toUpperCase()));

        Site site = new Site();
        site.setCity(rs.getString(SiteDAO.CITY));
        site.setId(rs.getInt(RequestedItemDAO.REQUESTEESITEID));
        site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
        site.setShortName(rs.getString(SiteDAO.SHORTNAME));
        site.setState(rs.getString(SiteDAO.STATE));
        site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
        site.setZip(rs.getString(SiteDAO.ZIP));

        requestedItem.setSite(site);

        requestedItem.setRequesteeSiteId(site.getId());
        requestedItem.setNumFilled(rs.getInt(RequestedItemDAO.NUMFILLED));
        requestedItem.setNumRequested(rs.getInt(RequestedItemDAO.NUMREQUESTED));
        requestedItem.setReqDateTime(rs.getTimestamp(RequestedItemDAO.REQDATETIME));
        requestedItem.setStatus(RequestedItemStatus.valueOf(rs.getString(STATUS).toUpperCase()));
        requestedItem.setUserId(rs.getString(RequestedItemDAO.USERID));

        return requestedItem;
    }

    @Override
    public boolean closeRequestsForItem(int itemId) throws SQLException {
	    int rows = execute(UPDATE_STATUS_FOR_ITEM_SITE, new Object[]{RequestedItemStatus.CLOSED.getValue(), itemId});
	    return rows == 1;
    }

    @Override
    public List<RequestedItem> getAllForSite(int siteId) throws SQLException {
        return getMultiple(GET_ALL_FOR_SITE, new Object[]{siteId});
    }

    @Override
    public boolean isShortFall(int itemId) throws SQLException {
        if (getResult(IS_SHORTFALL, new Object[]{itemId}).isEmpty()){
            return false;
        }
        return true;
    }
}
