package me.magnet.microservice;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ServiceApplication extends Application<ServiceConfiguration> {

	// TODO: Rename this to "magnet-<your-service-name>"
	private static final String NAME = "magnet-service";

	public static void main(String[] args) throws Exception {
		new ServiceApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
		GuiceBundle<ServiceConfiguration> guiceBundle = GuiceBundle.<ServiceConfiguration>newBuilder()
				.addModule(new ServiceModule())
				.enableAutoConfig(getClass().getPackage().getName())
				.setConfigClass(ServiceConfiguration.class)
				.build();

		bootstrap.addBundle(guiceBundle);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void run(ServiceConfiguration dispatcherConfiguration, Environment environment) throws Exception {
		// Do nothing...
	}
}
