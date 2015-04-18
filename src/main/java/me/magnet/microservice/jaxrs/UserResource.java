package me.magnet.microservice.jaxrs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import me.magnet.microservice.dao.UserDao;
import me.magnet.microservice.entity.User;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private final UserDao userDao;

	@Inject
	UserResource(UserDao userDao) {
		this.userDao = userDao;
	}

	@GET
	@Path("{id}")
	@UnitOfWork
	public User getUser(@PathParam("id") long id) {
		return userDao.findById(id)
				.orElseThrow(() -> new NotFoundException("Could not find user with id: " + id));
	}

	@POST
	@UnitOfWork
	public User createUser(User user) {
		return userDao.persist(user);
	}

}
