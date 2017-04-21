/* Describes a single sentry.
 * Credit to Brice Roncace at http://stackoverflow.com/a/29836273
 * 	for uuid-bytes conversion.
*/
package com.techbotbuilder.functionalstructures;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.Location;


public abstract class FunctionalStructure {
	private final StructurePlugin plugin;
	private final Location location;
	
	public FunctionalStructure(StructurePlugin plugin, Location location){
		this.plugin = plugin;
		this.location = location;
	}

	/*
	 * Read data about one structure from file.
	 * By default, this loads a long-type location.
	 */
	public FunctionalStructure(StructurePlugin plugin, DataInputStream data) throws IOException{
		this.plugin = plugin;
	    long firstLong = data.readLong();
	    long secondLong = data.readLong();
	    World world = plugin.getServer().getWorld(new UUID(firstLong, secondLong));
		long x = data.readLong();
		long y = data.readLong();
		long z = data.readLong();
		this.location = new Location(world, x,y,z);
	}
	
	public abstract void run();
	
	/*
	 * Return an array of byte data you want to save about this structure
	 * Should be same format as that readable by constructor from file.
	 */
	public byte[] toBytes(){
		UUID uuid = location.getWorld().getUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[40]);
		//2 longs for world, 3 for location; each long 8 bytes; need 5*8=40 bytes
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		bb.putLong((long) location.getX());
		bb.putLong((long) location.getY());
		bb.putLong((long) location.getZ());
		return bb.array();
	}
	
	public Location getLocation(){
		return location;
	}
}