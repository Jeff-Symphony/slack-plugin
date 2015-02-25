package jenkins.plugins.slack;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.inject.Inject;

/**
 * Integration with Workflow plugin
 */
public class SlackStep extends AbstractStepImpl {

	private final String message;

	@DataBoundConstructor
	public SlackStep(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Extension
	public static class DescriptorImpl extends AbstractStepDescriptorImpl {

		public DescriptorImpl() {
			super(SlackExecution.class);
		}

		@Override
		public String getFunctionName() {
			return "slack";
		}

		@Override
		public String getDisplayName() {
			return "Post Slack Message";
		}
	}

	public static class SlackExecution extends AbstractSynchronousStepExecution<Void> {

		@Inject private transient SlackStep step;
		@StepContextParameter private transient Run<?,?> run;
		@StepContextParameter private transient FilePath workspace;
		@StepContextParameter private transient Launcher launcher;
		@StepContextParameter private transient TaskListener listener;

		@Override protected Void run() throws Exception {
			SlackService testSlackService = new StandardSlackService("team", "token", "#jenkins");
			String message = "Slack/Jenkins plugin:cheating " + step.message;
			testSlackService.publish(message, "green");
			return null;
		}

		private static final long serialVersionUID = 1L;

	}

}
