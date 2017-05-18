/* Describes a single sentry.
 * Credit to Brice Roncace at http://stackoverflow.com/a/29836273
 * 	for uuid-bytes conversion.
 */
package com.WarriorNate4.sentries;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.Location;


public class Sentry {
	private final JavaPlugin plugin;
	private final Location sentrylocation;

	public Sentry(JavaPlugin plugin, Location location){
		this.plugin = plugin;
		sentrylocation = location;
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
		BlockState blockState = getLocation().add(0, 2, 0).getBlock().getState();
		List<String> players = new ArrayList<String>();
		if (blockState instanceof Sign) players = Arrays.asList(((Sign)blockState).getLines());
		for (Player other : plugin.getServer().getOnlinePlayers()) {
			if (!players.contains(other.getName()) && other.getWorld() == getLocation().getWorld() && other.getLocation().distance(getLocation()) <= 25) {
				Vector diff = other.getLocation().toVector().subtract(getLocation().toVector());
				getLocation().getWorld().spawnArrow(
						getLocation().add(diff.clone().normalize()).add(0,1,0),
						diff,
						5,//100 was 1-hit.
						0);
			}
		}
	}

	public byte[] toBytes(){
		UUID uuid = getLocation().getWorld().getUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[40]);
		//2 longs for world, 3 for location; each long 8 bytes; need 5*8=40 bytes
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		bb.putLong((long) getLocation().getX());
		bb.putLong((long) getLocation().getY());
		bb.putLong((long) getLocation().getZ());
		return bb.array();
	}
	public Location getLocation(){
		return sentrylocation.clone();
	}
}
