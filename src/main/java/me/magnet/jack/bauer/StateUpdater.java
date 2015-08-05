package me.magnet.jack.bauer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHCommitState;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

@Slf4j
public class StateUpdater implements Runnable {

	public interface Factory {
		StateUpdater create(String repoName, Optional<Integer> pullRequestId);
	}

	private final GitHub gitHub;
	private final String repoName;
	private final Optional<Integer> pullRequestId;

	@Inject
	StateUpdater(GitHub gitHub, @Assisted String repoName, @Assisted Optional<Integer> pullRequestId) {
		this.gitHub = gitHub;
		this.repoName = repoName;
		this.pullRequestId = pullRequestId;
	}

	@Override
	public void run() {
		try {
			GHRepository repository = gitHub.getRepository(repoName);

			if (pullRequestId.isPresent()) {
				GHPullRequest pullRequest = repository.getPullRequest(pullRequestId.get());
				updatePullRequestState(repository, pullRequest);
			}
			else {
				List<GHPullRequest> pullRequests = repository.getPullRequests(GHIssueState.OPEN);
				pullRequests.forEach(pullRequest -> updatePullRequestState(repository, pullRequest));
			}
		}
		catch (Throwable e) {
			log.error("Error while updating GitHub states: " + e.getMessage(), e);
		}
	}

	private void updatePullRequestState(GHRepository repository, GHPullRequest pullRequest) {
		try {
			Date createdAt = pullRequest.getCreatedAt();
			Date yesterday = Date.from(Instant.now().minus(1, ChronoUnit.DAYS));

			GHCommitState state = GHCommitState.PENDING;
			String description = "This PR may not be merged yet due to the 24-hour rule.";

			if (createdAt.before(yesterday)) {
				state = GHCommitState.SUCCESS;
				description = "This PR may be merged according to the 24-hour rule.";
			}

			String sha = pullRequest.getHead().getSha();
			repository.createCommitStatus(sha, state, null, description, "Jack Bauer says");
		}
		catch (Throwable e) {
			// Catch everything, so we don't break the loop.
			log.error("Error while updating GitHub PR state: " + e.getMessage(), e);
		}
	}

}
