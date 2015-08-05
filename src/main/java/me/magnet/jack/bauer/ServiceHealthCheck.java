package me.magnet.jack.bauer;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class ServiceHealthCheck extends InjectableHealthCheck {

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

	@Override
	public String getName() {
		return "jack-bauer";
	}
}
