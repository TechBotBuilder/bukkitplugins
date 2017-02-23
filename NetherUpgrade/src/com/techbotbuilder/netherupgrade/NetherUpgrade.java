package com.techbotbuilder.netherupgrade;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
        if (event.hasItem() && event.getItem().getType() == Material.BLAZE_ROD){
          Player player = event.getPlayer();
          Block target = player.getTargetBlock((Set<Material>)null,100);
          Location targetlocation = target.getLocation();
          World world = targetlocation.getWorld();
          world.strikeLightning(targetlocation);
        }
    }
}
