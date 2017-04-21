package com.techbotbuilder.functionalstructures;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class StructurePlugin extends JavaPlugin {

	private StructureRunner runner;
	private StructureTracker tracker;
	
	/*
	 * runner = new StructureRunnerSubclass(this);
	 * for periodic tasks you will want to add
	 * runner.runTaskTimer(this, delay, period);
	 * else add another listener to run task elsewhere.
	 */
	abstract StructureRunner startRunner();
	
	public StructureRunner getRunner(){
		return runner;
	}
	
	/*
	 * return new StructureListenerSubclass(this);
	 */
	abstract StructureListener getListener();
	
	/*
	 * return new FunctionalStructureSubclass(this);
	 */
	abstract FunctionalStructure createStructure(DataInputStream data);
	abstract FunctionalStructure createStructure(Location location);
	
	@Override
	public void onEnable(){
		try {
			tracker = new StructureTracker(this);
		} catch (IOException e) {
			this.getServer().broadcastMessage("Structure loading had a problem T.T");
			return;
		}
		startRunner();
		getServer().getPluginManager().registerEvents(getListener(), this);
	}
	
	@Override
	public void onDisable(){
		try {
			tracker.writeStructures();
		} catch (IOException e) {
			this.getServer().broadcastMessage("Structure saving had a problem!!!  D:");
		}
	}
	
	public void removeStructures(Location location){
		tracker.removeStructures(location);
	}
	public void addStructure(Location location){
		tracker.addStructure(location);
	}

	public ArrayList<FunctionalStructure> getStructures() {
		return tracker.getStructures();
	}
	
	
}
