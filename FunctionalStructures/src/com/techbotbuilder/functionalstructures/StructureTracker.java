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

public class StructureTracker {

	private ArrayList<FunctionalStructure> structures;
	private StructurePlugin plugin;
	private final String storageLocation;
	
	public ArrayList<FunctionalStructure> getStructures(){
		return structures;
	}
	
	public StructureTracker(StructurePlugin plugin) throws IOException{
		this.plugin = plugin;
		storageLocation = plugin.getName() + "structures.dat";
		
		structures = new ArrayList<FunctionalStructure>();//initialize sentries to empty List
		
		File datafile = new File(plugin.getDataFolder(), storageLocation);
		datafile.createNewFile();//will create new file, only if doesn't already exist
		
		DataInputStream data = new DataInputStream(new FileInputStream(datafile));
		
		while(data.available()>0){
			FunctionalStructure structure = plugin.createStructure(data);
			addStructure(structure);
		}
		
		data.close();
	}

	public void writeStructures() throws IOException {
		File datafile = new File(plugin.getDataFolder(), storageLocation);
		datafile.createNewFile();
		DataOutputStream data = new DataOutputStream(new FileOutputStream(datafile));
		
		for (FunctionalStructure structure : structures){
			byte[] bytes = structure.toBytes();
			data.write(bytes);
		}
		
		data.close();
	}
	
	public void addStructure(FunctionalStructure structure){
		structures.add(structure);
	}
	
	public void addStructure(Location location){
		structures.add(plugin.createStructure(location));
	}
	
	public void removeStructure(FunctionalStructure structure){
		structures.remove(structure);
	}
	
	/*
	 * If any structures within one block of location, remove them & return true.
	 * If no structures found, return false.
	 */
	public boolean removeStructures(Location location){
		boolean foundsome = false;
		ListIterator<FunctionalStructure> iter = structures.listIterator();
		while(iter.hasNext()){
			if (iter.next().getLocation().distanceSquared(location) < 1){
				iter.remove();
				foundsome = true;
			}
		}
		return foundsome;
	}
}
