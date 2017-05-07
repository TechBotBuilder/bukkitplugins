package com.techbotbuilder.moberizer;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Moberizer extends JavaPlugin implements Listener {
	public static final String toolLore = "Soul stealer";
	public static final Material toolType = Material.GOLD_SWORD;
	private static final double healthThreshold = 5.0;
	@Override
	public void onEnable(){
		ItemStack tool = new ItemStack(toolType);
		ItemMeta toolMeta = tool.getItemMeta();
		toolMeta.setLore(Arrays.asList(toolLore));
		tool.setItemMeta(toolMeta);
		ShapedRecipe toolRecipe = new ShapedRecipe(tool);
		toolRecipe.shape("rrr","rsr","rrr");
		toolRecipe.setIngredient('s', toolType);
		toolRecipe.setIngredient('r', Material.REDSTONE);
		getServer().addRecipe(toolRecipe);
		
		ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
		ShapedRecipe spawnerRecipe = new ShapedRecipe(spawner);
		spawnerRecipe.shape("bib","bdb","bib");
		spawnerRecipe.setIngredient('i', Material.IRON_FENCE);
		spawnerRecipe.setIngredient('b', Material.IRON_BLOCK);
		spawnerRecipe.setIngredient('d', Material.IRON_DOOR);
		getServer().addRecipe(spawnerRecipe);
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void onSword(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		
		ItemStack inHand = p.getInventory().getItemInMainHand();
		if (inHand.getType() != toolType || !inHand.hasItemMeta()) return;
		ItemMeta inHandMeta = inHand.getItemMeta();
		if (!(inHandMeta.hasLore()
				&&inHandMeta.getLore().get(0).equals(toolLore))) return;

		Entity ent = e.getRightClicked();
		if (!(ent instanceof Creature)) return;
		Creature cret = (Creature) ent;

		if (cret.getHealth() >= healthThreshold) return;
		cret.damage(healthThreshold, p);
		p.getInventory().setItemInMainHand(null);

		//create the egg
		ItemStack egg = new ItemStack(Material.MONSTER_EGG, 1);
		ItemMeta eggmeta = egg.getItemMeta();
		((SpawnEggMeta) eggmeta).setSpawnedType(cret.getType());
		egg.setItemMeta(eggmeta);
		
		p.getInventory().addItem(egg);
	}
}