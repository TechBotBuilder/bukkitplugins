package com.techbotbuilder.functionalstructures;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.bukkit.Location;

public class StructureTracker <T extends FunctionalStructure>{

	private ArrayList<T> structures;
	protected final StructurePlugin<T> plugin;
	protected final String storageLocation;
	
	public ArrayList<T> getStructures(){
		return structures;
	}
	
	public StructureTracker(StructurePlugin<T> plugin) throws IOException{
		this.plugin = plugin;
		storageLocation = plugin.getName() + "structures.dat";
		
		structures = new ArrayList<T>();//initialize sentries to empty List
		
		File datafile = new File(plugin.getDataFolder(), storageLocation);
		datafile.createNewFile();//will create new file, only if doesn't already exist
		
		DataInputStream data = new DataInputStream(new FileInputStream(datafile));
		
		while(data.available()>0){
			T structure = plugin.createStructure(data);
			addStructure(structure);
		}
		
		data.close();
	}

	public void writeStructures() throws IOException {
		File datafile = new File(plugin.getDataFolder(), storageLocation);
		datafile.createNewFile();
		DataOutputStream data = new DataOutputStream(new FileOutputStream(datafile));
		
		for (T structure : structures){
			byte[] bytes = structure.toBytes();
			data.write(bytes);
		}
		
		data.close();
	}
	
	public void addStructure(T structure){
		structures.add(structure);
	}
	
	public void addStructure(Location location){
		structures.add(plugin.createStructure(location));
	}
	
	public void removeStructure(T structure){
		structures.remove(structure);
	}
	
	/*
	 * If any structures within one block of location, remove them & return true.
	 * If no structures found, return false.
	 */
	public boolean removeStructures(Location location){
		boolean foundsome = false;
		ListIterator<T> iter = structures.listIterator();
		while(iter.hasNext()){
			if (iter.next().getLocation().distanceSquared(location) < 1){
				iter.remove();
				foundsome = true;
			}
		}
		return foundsome;
	}
}
