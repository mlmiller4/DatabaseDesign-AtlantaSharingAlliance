package edu.gatech.cs6400.team81.dao;

import java.sql.Date;
import java.sql.SQLException;

import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.ItemStorageType;

public interface RetrieveFoodBankItemDataDAO {
	
	public static final String TABLENAME = "Item";
	public static final String NAME = "Name";
	public static final String NUMBERUNITS = "NumberUnits";
	public static final String EXPIREDATE = "ExpireDate";
	public static final String STORAGETYPE = "StorageType";
	
	void getFoodBankItems(String[] sites) throws SQLException;

}
