package com.techbotbuilder.thelaw;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.plugin.java.JavaPlugin;

public class TheLaw extends JavaPlugin implements Listener {
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	public boolean closeToSpawn(Location l){
		int d = getServer().getSpawnRadius();
		return (l.distanceSquared(l.getWorld().getSpawnLocation())<=d*d);
	}
	@EventHandler(priority=EventPriority.LOW)
	public void entityExplode(ExplosionPrimeEvent e){
		if(closeToSpawn(e.getEntity().getLocation())){
			e.setCancelled(true);
		}
	}
	@EventHandler(priority=EventPriority.LOW)
	public void hangingExplode(HangingBreakEvent e){
		if(!e.isCancelled() && e.getCause()==RemoveCause.EXPLOSION &&
				closeToSpawn(e.getEntity().getLocation())){
			e.setCancelled(true);
		}
	}
}
