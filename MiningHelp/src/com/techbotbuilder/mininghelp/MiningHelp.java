package com.techbotbuilder.mininghelp;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class MiningHelp extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MiningHelpListener(), this);
    }
}

final class MiningHelpListener implements Listener {
    @EventHandler
    public void playerRightClick(PlayerInteractEvent event) {
        if (!(event.hasBlock() && event.getClickedBlock().getType() == Material.GOLD_BLOCK && event.hasItem())){
            return;
        }
        Material inHand = event.getItem().getType();
        Map<Material, ItemStack> lootOptions = new HashMap<Material, ItemStack>();
        lootOptions.put(Material.STONE_PICKAXE, new ItemStack(Material.COBBLESTONE, 64));
        ///add more loot options later
        ItemStack loot = lootOptions.get(inHand);
        if (inHand != null){
            Player player = event.getPlayer();
            PlayerInventory inv = player.getInventory();
            ItemStack IH = event.getItem();
            EquipmentSlot hand = event.getHand();
            if (hand == EquipmentSlot.HAND){
                inv.setItemInMainHand(loot);
            }///else don't do anything if they use second hand
        }
    }
}