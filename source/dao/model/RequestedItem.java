package edu.gatech.cs6400.team81.model;

import java.sql.Timestamp;

public class RequestedItem extends BasePOJO {


	private int itemId;
	private int requesteeSiteId;
	private Timestamp reqDateTime;
	private Timestamp expireDate;
	private String userId;
	private String strStatus;
	private int numRequested;
	private int numAvailable;
	private int numFilled;
	private ItemCategory category;
	private String name;
	private ItemStorageType storageType;
	private int siteId;
	private Site site;    
    private String subCategory;
    private RequestedItemStatus status;
    
    public RequestedItem() {

    }
    
    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getRequesteeSiteId() {
		return requesteeSiteId;
	}

	public void setRequesteeSiteId(int requesteeSiteId) {
		this.requesteeSiteId = requesteeSiteId;
	}

	public Timestamp getReqDateTime() {
		return reqDateTime;
	}

	public void setReqDateTime(Timestamp reqDateTime) {
		this.reqDateTime = reqDateTime;
	}
	
	public Timestamp getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(Timestamp expDate){
		this.expireDate = expDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String newStatus) {
		this.strStatus = newStatus;
	}

	public int getNumRequested() {
		return numRequested;
	}

	public void setNumRequested(int numRequested) {
		this.numRequested = numRequested;
	}
	
	public int getNumAvailable(){
		return numAvailable;
	}
	
	public void setNumAvailable(int num){
		this.numAvailable = num;
	}

	public int getNumFilled() {
		return numFilled;
	}

	public void setNumFilled(int numFilled) {
		this.numFilled = numFilled;
	}
	
	public ItemCategory getCategory(){
		return category;
	}
	
	public void setCategory(ItemCategory cat){
		this.category = cat;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
	
	public ItemStorageType getStorageType(){
		return storageType;
	}
	
	public void setStorageType(ItemStorageType type){
		this.storageType = type;
	}
	
	public int getSiteId(){
		return siteId;
	}

	public void setSiteId(int id){
		this.siteId = id;
	}
	
    public RequestedItemStatus getStatus() {
        return status;
    }

    public void setStatus(RequestedItemStatus status) {
        this.status = status;
    }
	
    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }


}
