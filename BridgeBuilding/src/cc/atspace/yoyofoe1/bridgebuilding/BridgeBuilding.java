package cc.atspace.yoyofoe1.bridgebuilding;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
 
public class BridgeBuilding extends JavaPlugin {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
    if (command.getName().equalsIgnoreCase("bridge")){
      if (sender instanceof Player){
        Player player = (Player) sender;
        Location playerbottomblock = player.getLocation();
        for (int z=0; z<3; z++){
          for (int x=0; x<5; x++){
            Block allblock = playerbottomblock.clone().add(x, -1, z).getBlock();
            allblock.setType(Material.COBBLESTONE);
          }
        }
      }else{
        return false;
      }
      return true;
    }
    return false;
  }
}