package com.techbotbuilder.teleportpad;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeleportInventory {

	protected TeleportPlugin plugin;
	private Inventory inventory;
	private final static String title = "Teleports";
	
	TeleportInventory(TeleportPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void update(){
		ArrayList<TeleportPad> teleports = plugin.getStructures();
		int inventorySize = 9 * (teleports.size() / 9 + 1);
		inventory = plugin.getServer().createInventory(null, inventorySize, title);
		int i=0;
		for (TeleportPad teleport : teleports){
			ItemStack teleportItem = new ItemStack(TeleportPlugin.teleportMaterial);
			ItemMeta itemMeta = teleportItem.getItemMeta();
			itemMeta.setDisplayName(teleport.toString());
			Location loc = teleport.getLocation();
			itemMeta.setLore(Arrays.asList(
					loc.getWorld().getName(),
					""+(int)loc.getX(),
					""+(int)loc.getY(),
					""+(int)loc.getZ()));
			teleportItem.setItemMeta(itemMeta);
			inventory.setItem(i++, teleportItem);
		}
	}
	
	public void displayTo(Player player){
		update();
		player.openInventory(inventory);
	}
	
	public static String getTitle(){
		return title;
	}
}
