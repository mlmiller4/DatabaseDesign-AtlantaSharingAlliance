package edu.gatech.cs6400.team81.business;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import edu.gatech.cs6400.team81.dao.FoodItemDAO;
import edu.gatech.cs6400.team81.model.FoodItemCategory;

@Service
public class MealsManager {

	@Autowired
	private FoodItemDAO foodItemDAO;
	
	public int getMealsAvailable(List<String> neededItems) throws ApplicationException{
		Integer possibleMeals = null;
		
		try {
			Map<String, Integer> categoryCounts = new HashMap<String,Integer>();
			List<Map<String, Object>> reportData = foodItemDAO.getAllMealCounts();
			if(!CollectionUtils.isEmpty(reportData)){
				for (Map<String, Object> rowData : reportData) {
					String foodCategory = rowData.get("FoodCategory").toString();
					Integer categoryCount = Integer.parseInt(rowData.get("TotalCount").toString());
					categoryCounts.put(foodCategory, categoryCount);
				}
				
				Integer vegCount = categoryCounts.get(FoodItemCategory.VEGETABLES.getValue()) == null ? 0 : categoryCounts.get(FoodItemCategory.VEGETABLES.getValue());
				Integer nutGrainCount = categoryCounts.get(FoodItemCategory.NUTS_GRAINS_BEANS.getValue()) == null ? 0 : categoryCounts.get(FoodItemCategory.NUTS_GRAINS_BEANS.getValue());
				Integer meatSeafoodCount = categoryCounts.get(FoodItemCategory.MEAT_SEAFOOD.getValue()) == null ? 0 : categoryCounts.get(FoodItemCategory.MEAT_SEAFOOD.getValue());
				Integer dairyEggCount = categoryCounts.get(FoodItemCategory.DAIRY_EGGS.getValue()) == null ? 0 : categoryCounts.get(FoodItemCategory.DAIRY_EGGS.getValue());
				
				possibleMeals = Math.min(vegCount, Math.min(nutGrainCount, meatSeafoodCount + dairyEggCount));
				
				if(vegCount.intValue() == possibleMeals.intValue()){
					neededItems.add(FoodItemCategory.VEGETABLES.getValue());
				}
				if(nutGrainCount.intValue() == possibleMeals.intValue()){
					neededItems.add(FoodItemCategory.NUTS_GRAINS_BEANS.getValue());
				}
				
				if((meatSeafoodCount.intValue() + dairyEggCount.intValue()) == possibleMeals.intValue()){
					if(meatSeafoodCount <= dairyEggCount){
						neededItems.add(FoodItemCategory.MEAT_SEAFOOD.getValue());
					}
					if(meatSeafoodCount >= dairyEggCount){
						neededItems.add(FoodItemCategory.DAIRY_EGGS.getValue());
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		
		return possibleMeals;
	}
}
