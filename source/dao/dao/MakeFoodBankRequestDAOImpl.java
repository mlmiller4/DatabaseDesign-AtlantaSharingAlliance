package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.Item;

@Repository
public class MakeFoodBankRequestDAOImpl extends BaseDAO<Item> implements MakeFoodBankRequestDAO, MakeFoodBankRequestSQL{
	
	@Override
	public void makeFoodBankRequest (int itemId, int requesteeSiteId, Timestamp currDate, String userId, int numRequested) throws SQLException{
		execute(MAKE_FOOD_BANK_REQUEST, new Object[]{itemId, requesteeSiteId, currDate, userId, numRequested});
	}

	@Override
	public void makeFoodBankRequest (int itemId, int requesteeSiteId, String userId, int numRequested) throws SQLException{
		makeFoodBankRequest(itemId, requesteeSiteId, new Timestamp(System.currentTimeMillis()), userId, numRequested);
	}

	@Override
	protected Item mapResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
