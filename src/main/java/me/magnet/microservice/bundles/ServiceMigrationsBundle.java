package me.magnet.microservice.bundles;

import com.google.inject.Singleton;
import io.dropwizard.Bundle;
import io.dropwizard.db.DataSourceFactory;
import me.magnet.microservice.ServiceConfiguration;

@Singleton
public class ServiceMigrationsBundle extends io.dropwizard.migrations.MigrationsBundle<ServiceConfiguration> implements Bundle {

	@Override
	public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
		return configuration.getDataSourceFactory();
	}

}
