package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.UserBuilder;
import edu.gatech.cs6400.team81.model.User;

@Repository
public class UserDAOImpl extends BaseDAO<User> implements UserDAO, UserSQL{

	@Override
	protected User mapResult(ResultSet rs) throws SQLException {
		return new UserBuilder().withEmail(rs.getString(EMAIL))
		.withFirstName(rs.getString(FIRSTNAME))
		.withLastName(rs.getString(LASTNAME))
		.withRole(rs.getString(ROLE))
		.withSiteId(rs.getInt(SITEID))
		.withUserName(rs.getString(AccountDAO.USERNAME))
		.withPassword(rs.getString(AccountDAO.PASSWORD)).build();
	}

	@Override
	public User getByUsername(String username) throws SQLException {
		return getUnique(GET_BY_USERNAME, new Object[]{username});
	}
	
	@Override
	public void createUser(User user) throws SQLException {
		Object[] params = new Object[]{
				user.getAccount().getUserName(),
				user.getEmail(),
				user.getFirstName(),
				user.getLastName(),
				user.getRole().name(),
				user.getSiteId()
		};
		
		execute(CREATE_USER, params);
	}
}
