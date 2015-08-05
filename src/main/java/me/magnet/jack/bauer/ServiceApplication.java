package me.magnet.jack.bauer;

import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.inject.Injector;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.magnet.jack.bauer.StateUpdater.Factory;

public class ServiceApplication extends Application<ServiceConfiguration> {

	private static final String NAME = "jack-bauer";
	private static final String REPO_NAME = "Magnetme/magnet.me";

	public static void main(String[] args) throws Exception {
		new ServiceApplication().run(args);
	}

	private GuiceBundle<ServiceConfiguration> guiceBundle;

	@Override
	public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
		this.guiceBundle = GuiceBundle.<ServiceConfiguration>newBuilder()
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
		Injector injector = guiceBundle.getInjector();

		ScheduledExecutorService executorService = injector.getInstance(ScheduledExecutorService.class);
		Factory factory = injector.getInstance(Factory.class);

		executorService.scheduleAtFixedRate(factory.create(REPO_NAME, Optional.empty()), 0, 1, TimeUnit.HOURS);
	}
}
