package com.github.yracnet.fragment.maven.plugin.data;

import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class DeclareFragment {

	private final Xpp3Dom configuration;
	private final DeclareExecution execution[];
	private final String groupId;
	private final String artifactId;
	private final String version;

	public DeclareFragment(Xpp3Dom config) {
		groupId = config.getChild("groupId").getValue();
		artifactId = config.getChild("artifactId").getValue();
		version = config.getChild("version").getValue();
		Xpp3Dom executions = config.getChild("executions");
		if (executions != null) {
			Xpp3Dom item[] = executions.getChildren();
			execution = new DeclareExecution[item.length];
			for (int i = 0; i < item.length; i++) {
				execution[i] = new DeclareExecution(item[i]);
			}
		} else {
			execution = new DeclareExecution[0];
		}
		Xpp3Dom configurationDom = config.getChild("configuration");
		configuration = configurationDom == null ? new Xpp3Dom("configuration") : configurationDom;
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

	public Xpp3Dom getConfiguration() {
		return configuration;
	}

	public DeclareExecution[] getExecution() {
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

	public String getPluginString() {
		return groupId + " - " + artifactId + " @ " + version;
	}

	public String getConfigurationString() {
		StringBuilder str = new StringBuilder();
		for (Xpp3Dom item : configuration.getChildren()) {
			str.append(item.getName());
			str.append("=");
			str.append(item.getValue());
			str.append(",");
		}
		return str.toString();
	}

	public String getExecutionString() {
		StringBuilder str = new StringBuilder();
		for (DeclareExecution item : execution) {
			str.append(item.getGoalString());
			str.append(" for ");
			str.append(item.getConfigurationString());
			str.append(",");
		}
		return str.toString();
	}
}
