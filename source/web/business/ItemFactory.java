package edu.gatech.cs6400.team81.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVRecord;

import edu.gatech.cs6400.team81.dao.ItemDAO;
import edu.gatech.cs6400.team81.model.FoodItem;
import edu.gatech.cs6400.team81.model.FoodItemCategory;
import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.ItemStorageType;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.SupplyItem;
import edu.gatech.cs6400.team81.model.SupplyItemCategory;

public class ItemFactory {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	public static final Item createItem(Site site, ItemCategory category, HttpServletRequest request) {
		Item item = null;
		
		switch (category) {
		case FOOD:
			item = new FoodItem();
			((FoodItem)item).setFoodCategory(FoodItemCategory.byName(request.getParameter("foodItemType").toUpperCase()));
			break;
		case SUPPLY:
			item = new SupplyItem();
			((SupplyItem)item).setSupplyCategory(SupplyItemCategory.byName(request.getParameter("supplyItemType").toUpperCase()));
			break;
		}
		
		item.setCategory(category);
		try {
			item.setExpireDate(SDF.parse(request.getParameter("expireDate")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		item.setName(request.getParameter("name"));
		item.setNumberUnits(Integer.parseInt(request.getParameter("numberUnits")));
		item.setStorageType(ItemStorageType.valueOf(request.getParameter("storageType").toUpperCase()));
		item.setSite(site);
		
		return item;
	}
	
	public static final Item createItem(Site site, CSVRecord csvRecord) {
		Item item = null;
		
		ItemCategory category = ItemCategory.valueOf(csvRecord.get(ItemDAO.CATEGORY.toUpperCase()).toUpperCase());
		switch (category) {
		case FOOD:
			item = new FoodItem();
			((FoodItem)item).setFoodCategory(FoodItemCategory.valueOf(csvRecord.get("SUBCATEGORY").toUpperCase()));
			break;
		case SUPPLY:
			item = new SupplyItem();
			((SupplyItem)item).setSupplyCategory(SupplyItemCategory.valueOf(csvRecord.get("SUBCATEGORY").toUpperCase()));
			break;
		}
		
		item.setCategory(category);
		try {
			item.setExpireDate(SDF.parse(csvRecord.get(ItemDAO.EXPIREDATE.toUpperCase())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		item.setName(csvRecord.get(ItemDAO.NAME.toUpperCase()));
		item.setNumberUnits(Integer.parseInt(csvRecord.get(ItemDAO.NUMBERUNITS.toUpperCase())));
		item.setStorageType(ItemStorageType.valueOf(csvRecord.get(ItemDAO.STORAGETYPE.toUpperCase()).toUpperCase()));
		item.setSite(site);
		
		return item;
	}
}
