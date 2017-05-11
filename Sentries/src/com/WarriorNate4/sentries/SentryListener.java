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
import org.bukkit.event.block.BlockPlaceEvent;

public class SentryListener implements Listener {

	SentryMain plugin;
	public SentryListener(SentryMain plugin){
		this.plugin = plugin;
		plugin.log("Sentry listener created!");
	}

	/* We can define a basic structure for the sentry by locations of specific blocks
	 * relative to some base block that we check for the rest of the sentry
	 * to be built around.
	 */
	//ALL TODO BELOW
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
	//we might want to break up the above into two methods, one checking
	//block break, other checking block place.
 	@EventHandler
	public void checkBreak(BlockBreakEvent e){
		Block b = e.getBlock();
		if (b.getType() == Material.DIAMOND_BLOCK){
			Location block = b.getLocation();
			    plugin.removeSentry(block);
				e.getPlayer().sendMessage("Sentry Destroyed!");
		}
	}
}
