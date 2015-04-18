package me.magnet.microservice;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class ServiceHealthCheck extends InjectableHealthCheck {

	@Override
	protected Result check() throws Exception {
		//Nothing special yet
		return Result.healthy();
	}

	@Override
	public String getName() {
		// TODO: Rename this to "magnet-<your-service-name>"
		return "magnet-service";
	}
}
