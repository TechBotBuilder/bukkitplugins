package cc.atspace.yoyofoe1.bridgebuilding;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;

public class BridgeBuilding extends JavaPlugin {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
    if (command.getName().equalsIgnoreCase("bridge")){
      if (sender instanceof Player){
        Player player = (Player) sender;
        Location playerbottomblock = player.getLocation().subtract(2, 1, 2);;
        if (player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 25)) {
          removeInventoryItems(player.getInventory(), Material.COBBLESTONE, 25);
          for (int z=0; z<5; z++){
            for (int x=0; x<5; x++){
              Block allblock = playerbottomblock.clone().add(x, 0, z).getBlock();
              allblock.setType(Material.COBBLESTONE);
            }
          }
        }else{
          sender.sendMessage("You need at least 25 Cobblestone in your inventory for this command to work!");
        }
      }else{
        return false;
      }
      return true;
    }
    return false;
  }
  public static void removeInventoryItems(PlayerInventory inv, Material type, int amount) {
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
  }
}