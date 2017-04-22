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
			this.getServer().broadcastMessage("Structure loading had a problem T.T");
			return;
		}
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

	public ArrayList<T> getStructures() {
		return tracker.getStructures();
	}
	
}
