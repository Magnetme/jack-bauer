package me.magnet.microservice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import me.magnet.microservice.bundles.ServiceHibernateBundle;
import org.hibernate.SessionFactory;

@Slf4j
public class ServiceModule extends AbstractModule {

	@Provides
	public SessionFactory provideSessionFactory(ServiceHibernateBundle hibernateBundle) {
		return hibernateBundle.getSessionFactory();
	}

	@Override
	protected void configure() {
		// Do nothing...
	}
}
