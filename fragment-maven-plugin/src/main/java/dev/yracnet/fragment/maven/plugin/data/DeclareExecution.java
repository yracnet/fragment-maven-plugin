/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.fragment.maven.plugin.data;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomUtils;

public class DeclareExecution {

	private final String[] goal;
	private final Xpp3Dom configuration;

	public DeclareExecution(Xpp3Dom config) {
		Xpp3Dom goals = config.getChild("goals");
		if (goals != null) {
			Xpp3Dom item[] = goals.getChildren();
			goal = new String[item.length];
			for (int i = 0; i < item.length; i++) {
				goal[i] = item[i].getValue();
			}
		} else {
			goal = new String[0];
		}
		Xpp3Dom configurationDom = config.getChild("configuration");
		configuration = configurationDom == null ? new Xpp3Dom("configuration") : configurationDom;
	}

	public String[] getGoal() {
		return goal;
	}

	public Xpp3Dom getConfiguration(Xpp3Dom config) {
		Xpp3Dom temporal = new Xpp3Dom("configuration");
		if (config != null) {
			temporal = new Xpp3Dom(config);
		}
		temporal = Xpp3DomUtils.mergeXpp3Dom(temporal, configuration);
		return temporal;
	}

	public Xpp3Dom getConfiguration() {
		return configuration;
	}

	public String getGoalString() {
		StringBuilder str = new StringBuilder();
		for (String item : goal) {
			str.append(item);
			str.append(",");
		}
		return str.toString();
	}

	public String getConfigurationString() {
		if (configuration == null || configuration.getChildCount() == 0) {
			return "[NONE]";
		}
		StringBuilder str = new StringBuilder();
		for (Xpp3Dom item : configuration.getChildren()) {
			str.append(item.getName());
			str.append("=");
			str.append(item.getValue());
			str.append(",");
		}
		return str.toString();
	}

}
