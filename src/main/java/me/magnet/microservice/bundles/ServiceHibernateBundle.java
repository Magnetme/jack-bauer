package me.magnet.microservice.bundles;

import javax.inject.Singleton;
import javax.persistence.Entity;

import com.google.common.collect.ImmutableList;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.SessionFactoryFactory;
import me.magnet.microservice.ServiceApplication;
import me.magnet.microservice.ServiceConfiguration;
import org.reflections.Reflections;

@Singleton
public class ServiceHibernateBundle extends io.dropwizard.hibernate.HibernateBundle<ServiceConfiguration> implements
		ConfiguredBundle<ServiceConfiguration> {

	public ServiceHibernateBundle() {
		super(myDbEntities(), new SessionFactoryFactory());
	}

	private static ImmutableList<Class<?>> myDbEntities() {
		Reflections reflections = new Reflections(ServiceApplication.class.getPackage().getName());
		return ImmutableList.copyOf(reflections.getTypesAnnotatedWith(Entity.class));
	}

	@Override
	public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
		return configuration.getDataSourceFactory();
	}

}
