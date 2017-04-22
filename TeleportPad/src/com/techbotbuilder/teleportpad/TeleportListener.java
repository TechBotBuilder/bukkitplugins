package com.techbotbuilder.teleportpad;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.util.Vector;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.techbotbuilder.functionalstructures.StructureListener;
import com.techbotbuilder.functionalstructures.TargetBlock;

public class TeleportListener extends StructureListener<TeleportPad> {

	final static List<TargetBlock> design = Arrays.asList(
			new TargetBlock(new Vector(0,0,0), Material.EMERALD_BLOCK),
			new TargetBlock(new Vector(0,1,0), Material.SIGN),
			new TargetBlock(new Vector(0,4,0), Material.OBSIDIAN)
			);
	
	public TeleportListener(TeleportPlugin plugin) {
		super(plugin);
	}

	@Override
	public List<TargetBlock> structureDesign() {
		return design;
	}
	
	@EventHandler
	public void inventoryClickHandler(InventoryClickEvent e){
		if (e.getInventory().getTitle().equals(TeleportInventory.getTitle())){
			ItemStack i = e.getCurrentItem();
			if (i.getType() == Material.AIR) return;
			e.setCancelled(true);
			List<String> lore = i.getItemMeta().getLore();
			Location to = new Location(
					plugin.getServer().getWorld(lore.get(0)),
					Integer.parseInt(lore.get(1)),
					Integer.parseInt(lore.get(2)) + 5,
					Integer.parseInt(lore.get(3)));
			e.getWhoClicked().teleport(to);
		}
	}

	@EventHandler
	public void summonTeleportHandler(BlockPlaceEvent e){
		Block b = e.getBlockPlaced();
		if (b.getType() == Material.FIRE
				&& e.getBlockAgainst().getType() == Material.OBSIDIAN){
			for(TeleportPad tp : plugin.getStructures()){
				if(b.getLocation().distanceSquared(tp.getLocation()) < 25){
					e.setCancelled(true);
					tp.run();
				}
			}
			
		}
	}
	
}
