package dev.yracnet.fragment.maven.plugin;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "process", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class ProcessMojo extends AbstractMojo {

	@Component
	private MavenProject mavenProject;

	@Component
	private MavenSession mavenSession;

	@Component
	private BuildPluginManager pluginManager;

	@Parameter(alias="fragments")
	private String[] fragment;

	public void space() {
		getLog().info("========================================================================");
	}

	@Override
	public void execute() throws MojoExecutionException {
		space();
		if (fragment != null && fragment.length > 0) {
			executeFragment();
		}
		space();
	}

	private void executeFragment() {
		for (String name : fragment) {
			getLog().info("PROCESS FRAGMENT " + name);
		}
	}
}
