/*Create and destroy structures
 * when someone builds or breaks them.
 * For now, can't deal with pistons moving blocks.
 */
package com.techbotbuilder.functionalstructures;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

public abstract class StructureListener<T extends FunctionalStructure> implements Listener {
	
	protected final StructurePlugin<T> plugin;
	private Material keyBlock;
	public StructureListener(StructurePlugin<T> plugin){
		this.plugin = plugin;
		keyBlock = keyBlock();
	}
	
	
	/* Define a basic structure for the structure by locations of specific blocks
	 * relative to some base block that we check for the rest of the structure
	 * to be built around. Put key blocks that players are likely to not have at
	 * the beginning of the returned list for efficiency. Put central block at
	 * (0,0,0).
	 */
	public abstract List<TargetBlock> structureDesign();
	
	/*
	 * Return the key material in the structure, the material of the block that we
	 * want to build around. Default to Beacon if origin block material undefined.
	 */
	public Material keyBlock(){
		for (TargetBlock b : structureDesign())
			if (b.getDisplacement().dot(b.getDisplacement()) == 0)
				return b.getType();
		return Material.BEACON;
	}
	
	/*
	 * Iterate over the structureDesign defined structure and check that
	 * the real world matches up. Checks all four orientations.
	 */
	public boolean matchesStructure(Location initLocation){
		List<TargetBlock> design = structureDesign();
		for (int i=0; i<4; i++){
			boolean madeIt = true;
			for (TargetBlock b : design){
				if (initLocation.add(
						rotatedVector(b.getDisplacement(),i)).getBlock().getType()
						!= b.getType()){
					madeIt = false;
					break;
				}
			}
			if (madeIt)	return true;
		}
		return false;
	}
	
	/*
	 * Rotate vector by (90*times) degrees counterclockwise in xz-plane.
	 * One times Takes (1,y,0) to (0,y,1), and (0,y,1) to (-1,y,0)
	 */
	private Vector rotatedVector(Vector v, int times){
		//(0,1;-1,0) is 90 degree rotation matrix
		times = (times % 4 + 4) % 4;
		Vector r = new Vector();
		r.copy(v);
		for (int i=0; i<times; i++){
			double x = r.getX();
			r.setX(r.getZ());
			r.setZ(-x);
		}
		return r;
	}
	
	@EventHandler
	public void blockPlaceHandler(BlockPlaceEvent e){
		if (!isRelevant(e)) return;
		if (plugin.tracker.structureAlreadyAt(e.getBlock().getLocation())) return;
		if (matchesStructure(e.getBlock().getLocation())) {
			plugin.write("NEW STRUCTURE");
			plugin.addStructure(e.getBlock().getLocation());
		}
	}
	
	/*
	 * Right now, removes structure only when player breaks the central block
	 * of the structure.
	 */
	@EventHandler
	public void blockBreakHandler(BlockBreakEvent e){
		if(!isRelevant(e)) return;
		plugin.removeStructures(e.getBlock().getLocation());
	}
	
	protected boolean isRelevant(BlockEvent e){
		return ( !((Cancellable) e).isCancelled() && e.getBlock().getType()==keyBlock );
	}
	
}



