package com.techbotbuilder.potatomaster;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;

public class PotatoMaster extends JavaPlugin {
    public void onEnable(){}
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("potato")){
          if(args.length==1){
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
              sender.sendMessage("You dun did a thing to someone who isn't online or misspelled his/her name");
              return true;
            }
            Location dropLocation = player.getLocation().add(0,10,0);
            int numpotato = 10;
            ItemStack potato = new ItemStack(Material.POTATO_ITEM, 1);
            for(int i = 0; i<numpotato; i++){
              dropLocation.getWorld().dropItemNaturally(dropLocation, potato);
            }
            return true;
          }else{
           return false;
          }
        }
        return false;
    }
}