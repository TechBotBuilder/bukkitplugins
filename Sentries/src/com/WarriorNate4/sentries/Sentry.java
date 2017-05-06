/* Describes a single sentry.
 * Credit to Brice Roncace at http://stackoverflow.com/a/29836273
 * 	for uuid-bytes conversion.
*/
package com.WarriorNate4.sentries;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.Location;


public class Sentry {
	private final JavaPlugin plugin;//we might not be using this
	private final Location sentrylocation;
	//we'll be using the sentry's world alot, so add a variable for it

	public Sentry(JavaPlugin plugin, Location location){
		this.plugin = plugin;
		sentrylocation = location;
		//initialize world
	}

	public Sentry(JavaPlugin plugin, DataInputStream data) throws IOException{
		this.plugin = plugin;
	    long firstLong = data.readLong();
	    long secondLong = data.readLong();
	    World sentryWorld = plugin.getServer().getWorld(new UUID(firstLong, secondLong));
		long x = data.readLong();
		long y = data.readLong();
		long z = data.readLong();
		this.sentrylocation = new Location(sentryWorld, x,y,z);
	}

	public void fire() {
		for (Player other : plugin.getServer().getOnlinePlayers()) {
			if (other.getWorld() == sentrylocation.getWorld() && other.getLocation().distance(sentrylocation) <= 25) {
				Vector diff = other.getLocation().toVector().subtract(sentrylocation.toVector());
				sentrylocation.getWorld().spawnArrow(
						sentrylocation.clone().add(diff.clone().normalize()).add(0,1,0),
						diff,
						5,//100 was 1-hit.
						0);
			}
		}
	}

	public byte[] toBytes(){
		UUID uuid = sentrylocation.getWorld().getUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[40]);
		//2 longs for world, 3 for location; each long 8 bytes; need 5*8=40 bytes
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		bb.putLong((long) sentrylocation.getX());
		bb.putLong((long) sentrylocation.getY());
		bb.putLong((long) sentrylocation.getZ());
		return bb.array();
	}
}