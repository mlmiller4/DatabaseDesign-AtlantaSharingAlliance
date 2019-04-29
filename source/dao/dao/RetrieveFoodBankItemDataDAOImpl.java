package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.Item;

@Repository
public class RetrieveFoodBankItemDataDAOImpl extends BaseDAO<Item> implements RetrieveFoodBankItemDataDAO, RetrieveFoodBankItemDataSQL{

	@Override
	public void getFoodBankItems(String[] sites) throws SQLException {
		
		// If sites has a length of 0, then user would like to view items at all food banks
		// Else if site has length > 0, user has specified one or more food banks from which to view items
		if (sites.length == 0){
			execute(GET_FOODBANK_ITEMS_ALL_SITES);
			
		} else {			
			// Create a list to satisfy the SQL IN operator: "('siteId(1)','siteId(2)', ... 'siteId(n))"
			String listOfSites = "(";
			
			for (int i=0; i<sites.length; i++){
				
				if (i == 0){
					listOfSites += "'" + sites[i] + "'";
				} else {
					listOfSites += ",'" + sites[i] + "'";
				}				
			}
			
			listOfSites += ")";
			
			execute(GET_FOODBANK_ITEMS, new Object[]{listOfSites});
		}		
	}

	@Override
	protected Item mapResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
