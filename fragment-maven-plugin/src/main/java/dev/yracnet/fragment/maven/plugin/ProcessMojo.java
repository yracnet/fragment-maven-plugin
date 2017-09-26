package dev.yracnet.fragment.maven.plugin;

import dev.yracnet.fragment.maven.plugin.data.PluginExecution;
import dev.yracnet.fragment.maven.plugin.data.PluginFragment;
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

	public void execute(String name) throws MojoExecutionException {
		PluginFragment pluginFragment = getPluginFragment(name);
		if (pluginFragment != null && pluginFragment.isSkip() == false) {
			execute(pluginFragment);
		}
	}

	public void execute(PluginFragment pluginFragment) throws MojoExecutionException {
		Plugin plugin = pluginFragment.getPlugin();
		PluginExecution executionArray[] = pluginFragment.getExecution();
		for (PluginExecution execution : executionArray) {
			execute(plugin, execution, pluginFragment.getConfiguration());
		}
	}

	public void execute(Plugin plugin, PluginExecution execution, Xpp3Dom configuration) throws MojoExecutionException {
		String[] goalArray = execution.getGoal();
		MojoExecutor.ExecutionEnvironment env = getCurrentExecutionEnvironment();
		for (String goalName : goalArray) {
			getLog().info("PROCESS FRAGMENT " + plugin);
			getLog().info("GOAL NAME: " + goalName);
			getLog().debug("PROCESS FRAGMENT " + plugin + " - " + configuration);
			MojoExecutor.executeMojo(
											plugin,
											goalName,
											configuration,
											env
			);
		}
	}
}
