package com.techbotbuilder.mininghelp;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MiningHelp extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MiningHelpListener(), this);
        getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH));
    }
}

final class MiningHelpListener implements Listener {
  Map<Material, ItemStack> lootOptions;
  public MiningHelpListener(){
	super();
    lootOptions = new HashMap<Material, ItemStack>();
    lootOptions.put(Material.STONE_PICKAXE, new ItemStack(Material.IRON_INGOT, 10));
    lootOptions.put(Material.WOOD_PICKAXE, new ItemStack(Material.COBBLESTONE, 64));
    lootOptions.put(Material.IRON_PICKAXE, new ItemStack(Material.GOLD_INGOT, 5));
    lootOptions.put(Material.GOLD_PICKAXE, new ItemStack(Material.DIAMOND, 1));
    lootOptions.put(Material.GOLD_INGOT, new ItemStack(Material.EXP_BOTTLE, 1));
    lootOptions.put(Material.DIAMOND_PICKAXE, new ItemStack(Material.OBSIDIAN, 16));
    lootOptions.put(Material.DIAMOND_SWORD, new ItemStack(Material.TNT, 16));
  }
    @EventHandler
    public void playerRightClick(PlayerInteractEvent event) {
        if ( !(event.hasBlock() &&
        		event.getClickedBlock().getType() == Material.GOLD_BLOCK &&
        		event.hasItem() &&
        		event.getAction()==Action.RIGHT_CLICK_BLOCK &&
        		event.getHand() == EquipmentSlot.HAND) ){
            return;
        }
        Material inHand = event.getItem().getType();
        ItemStack loot = lootOptions.get(inHand);
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (loot != null && removeInventoryItems(inv, inHand, 1)){
            inv.addItem(loot);
        }
    }
    public static boolean removeInventoryItems(PlayerInventory inv, Material type, int amount) {
    	if (!inv.containsAtLeast(new ItemStack(type), amount)){
    		return false;
    	}
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.removeItem(is);
                    amount = -newamount;
                    if (amount == 0) break;
                }
            }
        }
        return true;
      }
}
