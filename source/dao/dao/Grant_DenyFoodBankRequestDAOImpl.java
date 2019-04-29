package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.RequestedItem;

@Repository
public class Grant_DenyFoodBankRequestDAOImpl extends BaseDAO<Item> implements Grant_DenyFoodBankRequestDAO, Grant_DenyFoodBankRequestSQL {	
	
	
	@Override
	public boolean isItemRequested(int itemId) throws SQLException {
		
		if (getCount(IS_ITEM_REQUESTED, new Object[]{itemId}) >= 1){
			return true;
		} else {
			return false;
		}	

	}
	
	@Override
	public List<Integer> viewItemIDs() throws SQLException{
		return getIntegers(VIEW_REQUESTED_ITEMIDS, new Object[]{});
	}

	
	@Override
	public List<RequestedItem> viewRequests() throws SQLException{		
		
		return getRequestedItems(VIEW_REQUESTS, new Object[]{});
	}
	
	@Override
	public void grantRequest(int numFilled, int itemId) throws SQLException{		
		execute(GRANT_REQUEST, new Object[]{numFilled, itemId});
		
	}
	
	@Override
	public void reduceItemCount(int numToReduce, int itemId) throws SQLException{
		execute(REDUCE_ITEMS, new Object[]{numToReduce, itemId});
	}

	@Override
	public void denyRequest(int itemId) throws SQLException{
		execute(DENY_REQUEST, new Object[]{itemId});
		
	}

	@Override
	protected Item mapResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



}
