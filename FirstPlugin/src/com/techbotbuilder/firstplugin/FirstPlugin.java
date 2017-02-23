package com.techbotbuilder.firstplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstPlugin extends JavaPlugin {
    public void onEnable(){
        getLogger().info("The first plugin is alive!!!");
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("first")){
            sender.sendMessage("Yes, yes, you can do that. It just does this though.");
            if (sender instanceof Player){
                sender.sendMessage("Don't forget, I'm watching you, "+ ((Player)sender).getName());
            }
            return true;
        }
        return false;
    }
}