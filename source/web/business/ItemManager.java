package edu.gatech.cs6400.team81.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs6400.team81.dao.RequestedItemDAO;
import edu.gatech.cs6400.team81.model.RequestedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import edu.gatech.cs6400.team81.dao.FoodItemDAO;
import edu.gatech.cs6400.team81.dao.ItemDAO;
import edu.gatech.cs6400.team81.dao.SupplyItemDAO;
import edu.gatech.cs6400.team81.model.FoodItem;
import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.SupplyItem;

@Service
public class ItemManager {
	
	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private FoodItemDAO foodItemDAO;
	
	@Autowired 
	private SupplyItemDAO supplyItemDAO;
	
	@Autowired 
	private RequestedItemDAO requestedItemDAO;
	
	public void saveItem(Item item) throws ApplicationException{
		try {
			Item insertedItem = itemDAO.add(item);
			
			switch(item.getCategory()){
			case FOOD:
				if(item instanceof FoodItem){
					foodItemDAO.add((FoodItem)insertedItem);
				}
				
				break;
			case SUPPLY:
				if(item instanceof SupplyItem){
					supplyItemDAO.add((SupplyItem)insertedItem);
				}
				
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
	
	public List<Item> getAllItems(int siteId) throws ApplicationException{
		List<Item> allItems;
		try {
			allItems = new ArrayList<Item>();
			List<FoodItem> foodItems = foodItemDAO.getAllForSite(siteId);
			if(!CollectionUtils.isEmpty(foodItems)){
				allItems.addAll(foodItems);
			}
			List<SupplyItem> supplyItems = supplyItemDAO.getAllForSite(siteId);
			if(!CollectionUtils.isEmpty(supplyItems)){
				allItems.addAll(supplyItems);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		
		return allItems;
	}
	
	public void removeItem(Item item) throws ApplicationException{
		try {
			ItemCategory category = item.getCategory();
			int itemId = item.getItemId();
			switch(category){
				case FOOD:
					foodItemDAO.delete(itemId);
					break;
				case SUPPLY:
					supplyItemDAO.delete(itemId);
					break;
			}
			
			itemDAO.delete(itemId);
			requestedItemDAO.closeRequestsForItem(itemId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

	public void updateQuantity(Item item, int newQuantity) throws ApplicationException {
		try {
			if(item != null && newQuantity >= 0){
				itemDAO.updateQuantity(item.getItemId(), newQuantity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
	
	public List<RequestedItem> getAllRequestedItems(int siteId) throws ApplicationException{
		List<RequestedItem> allItems;
		try {
			allItems = new ArrayList<RequestedItem>();
			List<RequestedItem> items = requestedItemDAO.getAllForSite(siteId);
			if(!CollectionUtils.isEmpty(items)){
				allItems.addAll(items);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}

		return allItems;
	}

	public String isShortFall(int itemId) throws ApplicationException, SQLException{
		if(requestedItemDAO.isShortFall(itemId)){
			return "True";
		}
		return "False";
	}
}
