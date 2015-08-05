package me.magnet.jack.bauer.jaxrs;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import lombok.Data;
import me.magnet.jack.bauer.StateUpdater.Factory;

@RequestScoped
@Path("github-webhook")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WebHookResource {

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PullRequestEvent {

		@JsonProperty("pull_request")
		private PullRequest pullRequest;

		@JsonProperty("repository")
		private Repository repository;

	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PullRequest {

		@JsonProperty("number")
		private int number;

	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Repository {

		@JsonProperty("full_name")
		private String fullName;

	}

	private final ScheduledExecutorService executorService;
	private final Factory factory;

	@Inject
	WebHookResource(ScheduledExecutorService executorService, Factory factory) {
		this.executorService = executorService;
		this.factory = factory;
	}

	@POST
	public Response onEvent(@Context HttpServletRequest request, PullRequestEvent event) {
		String eventType = request.getHeader("X-GitHub-Event");
		if ("pull_request".equals(eventType)) {
			Repository repository = event.getRepository();
			String repoName = repository.getFullName();

			PullRequest pullRequest = event.getPullRequest();
			int number = pullRequest.getNumber();

			executorService.submit(factory.create(repoName, Optional.of(number)));
		}

		return Response.ok().build();
	}


}
