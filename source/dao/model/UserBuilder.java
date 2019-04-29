package edu.gatech.cs6400.team81.model;

import org.apache.commons.csv.CSVRecord;

import edu.gatech.cs6400.team81.dao.AccountDAO;
import edu.gatech.cs6400.team81.dao.UserDAO;
import edu.gatech.cs6400.team81.util.HashUtils;

public class UserBuilder {

	private User user;

	public UserBuilder() {
		user = new User();
	}

	public UserBuilder withCSVRecord(CSVRecord csvRecord) {
		if (csvRecord != null) {
			withEmail(csvRecord.get(UserDAO.EMAIL))
			.withFirstName(csvRecord.get(UserDAO.FIRSTNAME))
			.withLastName(csvRecord.get(UserDAO.LASTNAME))
			.withRole(csvRecord.get(UserDAO.ROLE))
			.withSiteId(Integer.parseInt(csvRecord.get(UserDAO.SITEID)))
			.withUserName(csvRecord.get(AccountDAO.USERNAME))
			.withRawPassword(csvRecord.get(AccountDAO.PASSWORD));
		}
		
		return this;
	}

	public UserBuilder withFirstName(String firstName) {
		user.setFirstName(firstName);

		return this;
	}

	public UserBuilder withLastName(String lastName) {
		user.setLastName(lastName);

		return this;
	}

	public UserBuilder withRole(String role) {
		if(role != null){
			user.setRole(UserRole.valueOf(role.toUpperCase()));
		} else {
			user.setRole(null);
		}
		return this;
	}

	public UserBuilder withSiteId(int siteId) {
		user.setSiteId(siteId);

		return this;
	}

	public UserBuilder withUserName(String username) {
		getAccount().setUserName(username);

		return this;
	}

	public UserBuilder withPassword(String password) {
		getAccount().setPassword(password);

		return this;
	}
	
	public UserBuilder withRawPassword(String password) {
		getAccount().setPassword(HashUtils.md5(password));

		return this;
	}
	
	
	public UserBuilder withEmail(String email) {
		user.setEmail(email);

		return this;
	}

	public User build() {
		return user;
	}
	
	private Account getAccount(){
		Account account = user.getAccount();
		if(account == null){
			account = new Account();
			user.setAccount(account);
		}
		
		return account;
	}
}
