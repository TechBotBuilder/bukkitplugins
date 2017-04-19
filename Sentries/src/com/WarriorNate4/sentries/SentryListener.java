/*Create and destroy sentries
 * when someone builds or breaks them.
 * 
 * For now, we'll stick to creating.
 */
package com.WarriorNate4.sentries;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SentryListener implements Listener {
	
	JavaPlugin plugin;
	public SentryListener(JavaPlugin plugin){
		this.plugin = plugin;
	}
	
	/* We can define a basic structure for the sentry by locations of specific blocks
	 * relative to some base block that we check for the rest of the sentry
	 * to be built around.
	 */
	//ALL TODO BELOW
	@EventHandler
	public void blockChangeHandler(BlockEvent event){//look at all ways blocks can change
		//first check that it's not cancelled - eg that it's not a false alarm.
		
		//second check if block's type is correct main block material.
		//Ideally, we'll want a block that can't be moved with pistons so someone
		//can't use a sticky piston to steal it out from underneath. We could
		//also deal with blocks moving from pistons, but that's more work.
		//recommend beacon
		
		/*Third check through the types of BlockEvents to see if event is one of the
		 * ones that has to deal with a block being placed, broken, or moved:
		 * check direct known subclasses of
		 * http://techbotbuilder.com/pc/javadoc/org/bukkit/event/block/BlockEvent.html
		 For destroying a sentry, we'll want to check:
		 	BlockBreakEvent. If it's that, we'll want the plugin to remove
		 		any sentry at this block's location.
		 For creating a sentry, we'll want to check:
		 	BlockPlaceEvent. If it's that, we'll want to see if the right structure
		 		is there around this block's location. Cycle through our relative
		 		positions and see if the blocks all match up. If so, have the plugin
		 		create a sentry at this location.
		*/
	}
	//we might want to break up the above into two methods, one checking
	//block break, other checking block place.
}
