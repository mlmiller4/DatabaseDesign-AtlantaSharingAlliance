package edu.gatech.cs6400.team81.model;

  public class Site extends BasePOJO{
  	private int id;

 	private String city;

 	private String phoneNumber;

 	private String shortName;

 	private String state;

 	private String streetAddress;

 	private String zip;
 	
 	private int siteId;

	public Site() {
	}

	public int getId() {
		return this.id;
	}
	
	public int getSiteId(){
		return siteId;
	}
	
	public void setSiteId(int sid){
		this.siteId = sid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}