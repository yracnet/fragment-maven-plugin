package com.github.yracnet.fragment.maven.plugin;

import com.github.yracnet.fragment.maven.plugin.data.DeclareFragment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;

public abstract class ContextMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject mavenProject;

	@Parameter(defaultValue = "${session}", readonly = true)
	private MavenSession mavenSession;

	@Component
	private BuildPluginManager pluginManager;

	public DeclareFragment getPluginFragment(String configFile) throws MojoExecutionException {
		File file = new File(configFile);
		if (file.exists()) {
			try {
				InputStream in = new FileInputStream(file);
				Xpp3Dom fragment = Xpp3DomBuilder.build(in, "UTF-8");
				return new DeclareFragment(fragment);
			} catch (IOException | XmlPullParserException e) {
				throw new MojoExecutionException("Can't not read file: " + configFile, e);
			}
		}
		return null;
	}

	public MojoExecutor.ExecutionEnvironment getCurrentExecutionEnvironment() {
		return executionEnvironment(mavenProject, mavenSession, pluginManager);
	}
}
