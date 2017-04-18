package cc.atspace.yoyofoe1.cuberemover;

import java.util.HashSet;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.Material;

public class CubeRemover extends JavaPlugin {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
    if (command.getName().equalsIgnoreCase("cuberemove")){
      if (sender instanceof Player){
        Player player = (Player) sender;
        Location playerbottomblock = player.getLocation();
        //Block targetBlock = player.getTargetBlock((HashSet<Material>) null, 2);
        Vector direction = player.getLocation().getDirection();
        if (removeInventoryItems(player.getInventory(), Material.COAL, 2)) {
          for (int z=0; z<5; z++){
            for (int x=0; x<5; x++){
              for (int y=0; y<5; y++){
                Block allblock = playerbottomblock.clone().add(x, y, z).getBlock();
                if (allblock.getType() == Material.COBBLESTONE || allblock.getType() == Material.STONE || allblock.getType() == Material.DIRT) {
                  allblock.setType(Material.AIR);
                }
              }
            }
          }
          }else{
            sender.sendMessage("You need at least 2 Coal in your inventory for this command to work!");
          }
      }else{
        return false;
      }
      return true;
    }
    return false;
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
                inv.remove(is);
                amount = -newamount;
                if (amount == 0) break;
            }
        }
    }
    return true;
  }
  public static Vector getPrincipleDirection(Vector v){
	  double x = Math.abs(v.getX()), y=Math.abs(v.getY()), z=Math.abs(v.getZ());
	  if(x >= y && x > z){
		  return new Vector(x/v.getX(), 0, 0);
	  }else if (y > z){
		  return new Vector(0, y/v.getY(), 0);
	  }else{
		  return new Vector(0, 0, z/v.getZ());
	  }
  }
}