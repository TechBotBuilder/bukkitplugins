package com.techbotbuilder.netherupgrade;

import static org.bukkit.Material.ANVIL;
import static org.bukkit.Material.BEACON;
import static org.bukkit.Material.BED_BLOCK;
import static org.bukkit.Material.BREWING_STAND;
import static org.bukkit.Material.CAKE_BLOCK;
import static org.bukkit.Material.CHEST;
import static org.bukkit.Material.DISPENSER;
import static org.bukkit.Material.DRAGON_EGG;
import static org.bukkit.Material.ENCHANTMENT_TABLE;
import static org.bukkit.Material.ENDER_CHEST;
import static org.bukkit.Material.FLOWER_POT;
import static org.bukkit.Material.FURNACE;
import static org.bukkit.Material.HOPPER;
import static org.bukkit.Material.JUKEBOX;
import static org.bukkit.Material.LEVER;
import static org.bukkit.Material.MINECART;
import static org.bukkit.Material.NOTE_BLOCK;
import static org.bukkit.Material.STORAGE_MINECART;
import static org.bukkit.Material.TRAPPED_CHEST;
import static org.bukkit.Material.TRAP_DOOR;
import static org.bukkit.Material.WORKBENCH;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.plugin.java.JavaPlugin;

public class NetherUpgrade extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new LightningSummoningListener(), this);
    }
}

final class LightningSummoningListener implements Listener {
    @EventHandler
    public void playerRightClick(PlayerInteractEvent event) {
        if (event.hasItem() && event.getItem().getType() == Material.BLAZE_ROD && isNiceClick(event)){
          Player player = event.getPlayer();
          Block target = player.getTargetBlock((Set<Material>)null,100);
          Location targetlocation = target.getLocation();
          World world = targetlocation.getWorld();
          world.strikeLightning(targetlocation);
        }
    }
    public static boolean isNiceClick(PlayerInteractEvent e){
		if(e.hasBlock() && !e.getPlayer().isSneaking()){
			List<Material> derps = Arrays.asList(
					ENCHANTMENT_TABLE, ANVIL, BREWING_STAND,
					NOTE_BLOCK, TRAPPED_CHEST, BED_BLOCK, JUKEBOX,
					CHEST, ENDER_CHEST, FURNACE, HOPPER, BEACON,
					DISPENSER, CAKE_BLOCK, DRAGON_EGG, FLOWER_POT,
					MINECART, STORAGE_MINECART, TRAP_DOOR,
					WORKBENCH, LEVER);
			List<String> superDerps = Arrays.asList("door", "gate", "boat", "diode",
					"comparator");
			Material m = e.getClickedBlock().getType();
			String ms = m.getData().getName();
			for(String s : superDerps){
				if(ms.equalsIgnoreCase("org.bukkit.material."+s)) return false;
			}
			if(derps.contains(m)) return false;
		}
		return true;
	}
}
