package com.techbotbuilder.diskexchange;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.Server;

public class DiskExchange extends JavaPlugin{
	public void onEnable(){
		Server server = getServer();
		Material[] records = {
				Material.GOLD_RECORD,
				Material.GREEN_RECORD,
				Material.RECORD_3,
				Material.RECORD_4,
				Material.RECORD_5,
				Material.RECORD_6,
				Material.RECORD_7,
				Material.RECORD_8,
				Material.RECORD_9,
				Material.RECORD_10,
				Material.RECORD_11,
				Material.RECORD_12};
		for(int i=0; i<records.length; i++){
			ShapelessRecipe recipe = new ShapelessRecipe(
					new ItemStack(records[(i+1)%records.length]));
			recipe.addIngredient(records[i]);
			server.addRecipe(recipe);
		}
		
	}
}
