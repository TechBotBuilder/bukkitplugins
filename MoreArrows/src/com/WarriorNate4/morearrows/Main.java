package com.WarriorNate4.morearrows;

import java.util.Arrays;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Main extends JavaPlugin implements Listener {

	String[] arrowLore={"explosive_Arrow"};

	public void onEnable(){
		ItemStack arrow = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = arrow.getItemMeta();
		arrowMeta.setLore(Arrays.asList(arrowLore));
		arrow.setItemMeta(arrowMeta);
		ShapedRecipe arrowRecipe = new ShapedRecipe(arrow);
		arrowRecipe.shape(new String[] {"T","A","T"});
		arrowRecipe.setIngredient('A', Material.ARROW);
		arrowRecipe.setIngredient('T', Material.TNT);
		getServer().addRecipe(arrowRecipe);
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void listener(ProjectileHitEvent hit){
		Location loco;
		if (hit.getHitBlock() != null){
			loco=hit.getHitBlock().getLocation();
		}else{
			loco=hit.getHitEntity().getLocation();
		}
		loco.getWorld().createExplosion(loco,4);
	}
	@EventHandler
	public void login(PlayerJoinEvent e){
		e.getPlayer().sendMessage("PROJECTILES WILL ALL EXPLODE LIKE TNT\n" +
				"BE VERY CAREFUL!!!\n" +
				"THEY WILL KILL U IN 1-2 HITS\n" +
				"THIS INCLUDES SKELETON ARROWS YES SKELLIES ARE ARMED AND EXPLOSIVE\n" +
				"THEY WILL ALSO DAMAGE BLOCKS LIKE TNT SO DON'T GET SHOT NEAR UR BASE\n" +
				"\n" +
				"ALSO, FISHING ROD HOOKS ARE PROJECTILES SO THEY WILL EXPLODE LIKE UR ARROWS.\n" +
				"WE WILL HAVE TO FIX THIS SOON!!!\n" +
				"Right now it is on peaceful in the overworld so no worries unless\n" +
				"you do something silly in your own base.");
	}
}
