/*Create and destroy sentries
 * when someone builds or breaks them.
 * 
 * For now, we'll stick to creating.
 */
package com.WarriorNate4.sentries;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class SentryListener implements Listener {

	SentryMain plugin;
	public SentryListener(SentryMain plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void checkDiamond(BlockPlaceEvent e){
		Block b = e.getBlockPlaced();
		if (b.getType() == Material.DIAMOND_BLOCK){
			Location block = b.getLocation();
			if(block.clone().add(0,1,0).getBlock().getType() == Material.DISPENSER){
				plugin.addSentry(block);
				e.getPlayer().sendMessage("Sentry created!");
			}
		}
	}

	@EventHandler
	public void checkBreak(BlockBreakEvent e){
		Block b = e.getBlock();
		if (b.getType() == Material.DIAMOND_BLOCK){
			Location block = b.getLocation();
			if (plugin.removeSentry(block)){
				e.getPlayer().sendMessage("Sentry Destroyed!");
			}
		}
	}

}
