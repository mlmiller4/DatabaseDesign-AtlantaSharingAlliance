package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.Account;

@Repository
public class AccountDAOImpl extends BaseDAO<Account> implements AccountDAO, AccountSQL{

	@Override
	protected Account mapResult(ResultSet rs) throws SQLException {
		Account accountTO = new Account();
		accountTO.setUserName(rs.getString(USERNAME));
		accountTO.setPassword(rs.getString(PASSWORD));
		
		return accountTO;
	}

	@Override
	public Account getByUsername(String username) throws SQLException {
		return getUnique(GET_BY_USERNAME, new Object[]{username});
	}
	
	@Override
	public void createAccount(Account account) throws SQLException {
		execute(CREATE_ACCOUNT, new Object[]{account.getUserName(), account.getPassword()});
	}
}
