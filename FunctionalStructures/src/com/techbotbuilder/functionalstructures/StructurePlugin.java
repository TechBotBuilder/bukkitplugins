package com.techbotbuilder.functionalstructures;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class StructurePlugin<T extends FunctionalStructure>  extends JavaPlugin {

	protected StructureTracker<T> tracker;
	
	/*
	 * return new StructureListenerSubclass(this);
	 */
	public abstract StructureListener<T> getListener();
	
	/*
	 * return new FunctionalStructureSubclass(this);
	 */
	public abstract T createStructure(DataInputStream data) throws IOException;
	public abstract T createStructure(Location location);
	
	@Override
	public void onEnable(){
		try {
			tracker = new StructureTracker<T>(this);
		} catch (IOException e) {
			getLogger().severe(e.getMessage());
			getLogger().severe("Structure loading had a problem T.T");
			getLogger().severe(getName() + " will not run.");
			return;
		}
		getServer().getPluginManager().registerEvents(getListener(), this);
	}
	
	@Override
	public void onDisable(){
		if (tracker == null) return;
		try {
			tracker.writeStructures();
		} catch (IOException e) {
			getLogger().severe("Structure saving had a problem!!!  D:");
		}
	}
	
	public void removeStructures(Location location){
		tracker.removeStructures(location);
	}
	public void addStructure(Location location){
		tracker.addStructure(location);
	}

	public ArrayList<T> getStructures() {
		return tracker.getStructures();
	}
	
	public static boolean areClose(Location loc1, Location loc2, double distance){
		return (loc1.getWorld()==loc2.getWorld() && loc1.distanceSquared(loc2) < distance);
	}
	
}
