package cc.atspace.yoyofoe1.cuberemover;

import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.*;

public class CubeRemover extends JavaPlugin {
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new ShovelListener(), this);
	}
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
    if (command.getName().equalsIgnoreCase("cuberemove")){
      if (sender instanceof Player){
        Player player = (Player) sender;
        cubeRemove(player);
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
  public static Vector getSideDirection1(Vector v){
    if (Math.abs(v.getX()) == 1){ //1, 0, 0
      return new Vector(0, 1, 0);
    }else{
      return new Vector(1, 0, 0);
    }
  }
  public static Vector getSideDirection2(Vector v){
    if (Math.abs(v.getX()) == 1){
      return new Vector(0, 0, 1);
    }else{
      if (Math.abs(v.getY()) == 1){
        return new Vector(0, 0, 1);
      }else{
        return new Vector(0, 1, 0);
    }
    }
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
  public static void cubeRemove(Player player){
	  Location playerbottomblock = player.getLocation();
      Vector direction = player.getLocation().getDirection();
      Vector mainDirection = getPrincipleDirection(direction);
      Vector sideDirection1 = getSideDirection1(mainDirection);
      Vector sideDirection2 = getSideDirection2(mainDirection);
      if (removeInventoryItems(player.getInventory(), Material.COAL, 2)) {
        for (int mainDirectionStep=1; mainDirectionStep<=5; mainDirectionStep++){
          for (int sideDirection1Step=-2; sideDirection1Step<=2; sideDirection1Step++){
            for (int sideDirection2Step=-2; sideDirection2Step<=2; sideDirection2Step++){
              Vector mainDirectionJump = mainDirection.clone().multiply(mainDirectionStep);
              Vector sideDirection1Jump = sideDirection1.clone().multiply(sideDirection1Step);
              Vector sideDirection2Jump = sideDirection2.clone().multiply(sideDirection2Step);
              Vector jump = new Vector();
              jump.add(mainDirectionJump);
              jump.add(sideDirection1Jump);
              jump.add(sideDirection2Jump);
              Block allblock = playerbottomblock.clone().add(jump).getBlock();
              if (allblock.getType() == Material.STONE || allblock.getType() == Material.DIRT  || allblock.getType() == Material.GRASS){
                allblock.setType(Material.AIR);
              }
            }
          }
        }
        }else{
          player.sendMessage("You need at least 2 Coal in your inventory to use CubeRemover!");
        }
  }
}

class ShovelListener implements Listener {
	@EventHandler
	public void shovelRemove(PlayerInteractEvent e){
		if ( !e.isCancelled() && e.hasItem() && e.getItem().getType()==Material.GOLD_SPADE
				&& isNiceRightClick(e))
		{
			CubeRemover.cubeRemove(e.getPlayer());
		}
	}
	public static boolean isNiceRightClick(PlayerInteractEvent e){
		if( !(e.getAction()==Action.RIGHT_CLICK_AIR
				|| e.getAction()==Action.RIGHT_CLICK_BLOCK)) return false;
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


