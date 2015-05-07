package me.magnet.microservice;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.magnet.microservice.StateUpdater.Factory;
import org.kohsuke.github.GitHub;

@Slf4j
public class ServiceModule extends AbstractModule {

	@Override
	@SneakyThrows(IOException.class)
	protected void configure() {
		install(new FactoryModuleBuilder().implement(StateUpdater.class, StateUpdater.class).build(Factory.class));

		bind(ScheduledExecutorService.class).toInstance(new ScheduledThreadPoolExecutor(1));

		String gitHubToken = System.getenv("GITHUB_TOKEN");
		GitHub gitHub = GitHub.connectUsingOAuth(gitHubToken);
		bind(GitHub.class).toInstance(gitHub);
	}

}
