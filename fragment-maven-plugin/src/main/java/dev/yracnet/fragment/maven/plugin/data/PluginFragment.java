package dev.yracnet.fragment.maven.plugin.data;

import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class PluginFragment {

	private final Xpp3Dom configuration;
	private final PluginExecution execution[];
	private final String groupId;
	private final String artifactId;
	private final String version;
	private final boolean skip;

	public PluginFragment(Xpp3Dom config) {
		groupId = config.getChild("groupId").getValue();
		artifactId = config.getChild("artifactId").getValue();
		version = config.getChild("version").getValue();
		Xpp3Dom executions = config.getChild("executions");
		if (executions != null) {
			Xpp3Dom item[] = executions.getChildren();
			execution = new PluginExecution[item.length];
			for (int i = 0; i < item.length; i++) {
				execution[i] = new PluginExecution(item[i]);
			}
		} else {
			execution = new PluginExecution[0];
		}
		configuration = config.getChild("configuration");
		skip = configuration == null ? true : configuration.getChild("skip") == null ? false : "true".equalsIgnoreCase(configuration.getChild("skip").getValue());
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}

	public boolean isSkip() {
		return skip;
	}

	public Xpp3Dom getConfiguration() {
		return configuration;
	}

	public PluginExecution[] getExecution() {
		return execution;
	}

	public Plugin getPlugin() {
		Plugin plugin = new Plugin();
		plugin.setGroupId(groupId);
		plugin.setArtifactId(artifactId);
		plugin.setVersion(version);
		return plugin;
	}

	@Override
	public String toString() {
		return "PluginFragment{"
										+ " groupId=" + groupId
										+ ", artifactId=" + artifactId
										+ ", version=" + version
										+ ", configuration=" + configuration
										+ ", execution=" + execution
										+ "}";
	}
}
