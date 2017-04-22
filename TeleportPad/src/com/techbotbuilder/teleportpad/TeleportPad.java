package com.techbotbuilder.teleportpad;

import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.techbotbuilder.functionalstructures.FunctionalStructure;

public class TeleportPad extends FunctionalStructure {
	
	private String name;
	
	public TeleportPad(TeleportPlugin plugin, Location location) {
		super(plugin, location);
		getName();
	}
	
	public TeleportPad(TeleportPlugin plugin, DataInputStream data) throws IOException{
		super(plugin, data);
		getName();
	}
	
	private void getName(){
		Sign sign = (Sign) this.getLocation().add(0, 1, 0).getBlock();
		name = sign.getLine(0);
	}
	
	/*
	 * Open up inventory for teleportation for players near teleport pads.
	 * (non-Javadoc)
	 * @see com.techbotbuilder.functionalstructures.FunctionalStructure#run()
	 */
	public void run() {
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if(p.getLocation().distanceSquared(getLocation()) < 4){
				((TeleportPlugin) plugin).getTeleportInventory().displayTo(p);
			}
		}
	}

	public String toString(){
		return name;
	}
	
}
