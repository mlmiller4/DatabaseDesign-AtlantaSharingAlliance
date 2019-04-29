package edu.gatech.cs6400.team81.model;

public class ClientService extends BasePOJO implements ASACSService{
  	private int serviceId;

 	private String conditionUse;

 	private String description;

 	private String hoursOperation;

 	private ServiceCategory serviceCategory;

  	private Site site;

	public ClientService() {
	}

	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getConditionUse() {
		return this.conditionUse;
	}

	public void setConditionUse(String conditionUse) {
		this.conditionUse = conditionUse;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHoursOperation() {
		return this.hoursOperation;
	}

	public void setHoursOperation(String hoursOperation) {
		this.hoursOperation = hoursOperation;
	}

	public ServiceCategory getServiceCategory() {
		return this.serviceCategory;
	}

	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
}