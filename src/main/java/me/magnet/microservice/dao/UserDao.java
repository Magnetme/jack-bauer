package me.magnet.microservice.dao;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import me.magnet.microservice.entity.User;
import org.hibernate.SessionFactory;

public class UserDao extends AbstractDAO<User> {

	@Inject
	UserDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<User> findById(long id) {
		return Optional.ofNullable(get(id));
	}

	public User persist(User user) {
		checkNotNull(user);

		currentSession().saveOrUpdate(user);
		return user;
	}

}
