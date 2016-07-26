/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.fragment.maven.plugin.data;

import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 *
 * @author wyujra
 */
public class PluginExecution {

	private final String[] goal;
	//private final Xpp3Dom configuration;

	public PluginExecution(Xpp3Dom config) {

		Xpp3Dom goals = config.getChild("goals");
		if (goals != null) {
			Xpp3Dom item[] = goals.getChildren();
			goal = new String[item.length];
			for (int i = 0; i < item.length; i++) {
				goal[i] = item[i].getValue();
				//configuration = 
			}
		} else {
			goal = new String[0];
		}
	}

	public String[] getGoal() {
		return goal;
	}

}
