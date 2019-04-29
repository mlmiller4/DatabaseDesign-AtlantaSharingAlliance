package edu.gatech.cs6400.team81.model;

import java.util.Date;

  public class Item extends BasePOJO{
  	private int itemId;

 	private ItemCategory category;

  	private Date expireDate;

 	private String name;

 	private int numberUnits;

 	private ItemStorageType storageType;

 	private Site site;
 	
 	private int siteId;

	public Item() {
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public ItemCategory getCategory() {
		return this.category;
	}

	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberUnits() {
		return this.numberUnits;
	}

	public void setNumberUnits(int numberUnits) {
		this.numberUnits = numberUnits;
	}

	public ItemStorageType getStorageType() {
		return this.storageType;
	}

	public void setStorageType(ItemStorageType storageType) {
		this.storageType = storageType;
	}

	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public void setSiteId(int int1) {
		this.siteId = int1;		
	}
	
	public int getSiteId(){
		return siteId;
	}
}