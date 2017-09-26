package dev.yracnet.fragment.maven.plugin;

import dev.yracnet.fragment.maven.plugin.data.DeclareExecution;
import dev.yracnet.fragment.maven.plugin.data.DeclareFragment;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.twdata.maven.mojoexecutor.MojoExecutor;

@Mojo(name = "process", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class ProcessMojo extends ContextMojo {

	/**
	 * <b>XML Fragment of Plugin Maven</b>
	 * <pre>
	 *  &lt;configuration&gt;
	 *   &lt;fragments&gt;
	 *    &lt;fragment&gt;formatter.xml&lt;/fragment&gt;
	 *   &lt;/fragments&gt;
	 *  &lt;/configuration&gt;
	 * </pre>
	 *
	 * <b>File: formatter.xml</b>
	 * <pre>
	 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
	 * &lt;plugin&gt;
	 *  &lt;groupId&gt;net.revelc.code&lt;/groupId&gt;
	 *  &lt;artifactId&gt;formatter-maven-plugin&lt;/artifactId&gt;
	 *  &lt;version&gt;0.5.2&lt;/version&gt;
	 *  &lt;executions&gt;
	 *   &lt;execution&gt;
	 *    &lt;goals&gt;
	 *     &lt;goal&gt;format&lt;/goal&gt;
	 *    &lt;/goals&gt;
	 *   &lt;/execution&gt;
	 *  &lt;/executions&gt;
	 *  &lt;configuration&gt;
	 *   &lt;lineEnding&gt;CRLF&lt;/lineEnding&gt;
	 *   &lt;encoding&gt;UTF-8&lt;/encoding&gt;
	 *  &lt;/configuration&gt;
	 * &lt;/plugin&gt;
	 * </pre>
	 */
	@Parameter(alias = "fragments")
	private String[] fragment;

	public void space() {
		getLog().info("========================================================================");
	}

	public void goal() {
		getLog().info("------------------------------------------------------------------------");
	}

	@Override
	public void execute() throws MojoExecutionException {
		space();
		if (fragment != null && fragment.length > 0) {
			for (String name : fragment) {
				execute(name);
			}
		}
		space();
	}

	public void execute(final String name) throws MojoExecutionException {
		DeclareFragment pluginFragment = getPluginFragment(name);
		if (pluginFragment != null && pluginFragment.isSkip() == false) {
			execute(pluginFragment);
		}
	}

	public void execute(final DeclareFragment pluginFragment) throws MojoExecutionException {
		Plugin plugin = pluginFragment.getPlugin();
		DeclareExecution executionArray[] = pluginFragment.getExecution();
		for (DeclareExecution execution : executionArray) {
			execute(plugin, execution, pluginFragment.getConfiguration());
		}
	}

	public void execute(final Plugin plugin, final DeclareExecution execution, final Xpp3Dom configuration) throws MojoExecutionException {
		String[] goalArray = execution.getGoal();
		MojoExecutor.ExecutionEnvironment env = getCurrentExecutionEnvironment();
		for (String goalName : goalArray) {
			goal();
			getLog().info("PROCESS FRAGMENT " + plugin);
			getLog().info("GOAL NAME: " + goalName);
			getLog().debug("PROCESS FRAGMENT " + plugin + " - " + configuration);
			//Xpp3Dom temporal = new Xpp3Dom(configuration);
			Xpp3Dom config = execution.getConfiguration(configuration);
			execute(plugin, goalName, config, env);
		}

	}

	public void execute(final Plugin plugin, final String goalName, final Xpp3Dom configuration, final MojoExecutor.ExecutionEnvironment env) throws MojoExecutionException {
		MojoExecutor.executeMojo(
										plugin,
										goalName,
										configuration,
										env
		);
	}

}
