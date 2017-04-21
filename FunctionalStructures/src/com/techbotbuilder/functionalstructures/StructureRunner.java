package com.techbotbuilder.functionalstructures;

import org.bukkit.scheduler.BukkitRunnable;

public class StructureRunner extends BukkitRunnable {

	private StructurePlugin plugin;
	
	public StructureRunner(StructurePlugin plugin){
		this.plugin = plugin;
	}
	
	/*
	 * Assumes want all structures run at same time.
	 */
	public void run() {
		for (FunctionalStructure structure : plugin.getStructures()){
			structure.run();
		}
	}
	
	
}
