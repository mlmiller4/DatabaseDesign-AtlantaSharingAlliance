package edu.gatech.cs6400.team81.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.gatech.cs6400.team81.dao.UserDAO;
import edu.gatech.cs6400.team81.model.User;



@Component
@Path("user")
public class UserService {
	@Autowired
	private UserDAO userDAO;
	
	@GET
	@Path("byUsername")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByLicenseNo(@QueryParam("username") String username){
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			User user = userDAO.getByUsername(username);
			json = mapper.writeValueAsString(user);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return Response.ok(json).build();
	}
	
}
