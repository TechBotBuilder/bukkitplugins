package com.WarriorNate4.sentries;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SentryMain extends JavaPlugin {

	private SentryRunner runner;

	@Override
	public void onEnable(){
		try {
			runner = new SentryRunner(this);
		} catch (IOException e) {
			this.getLogger().severe("Sentry runner had a problem starting T.T");
			getLogger().severe(e.getMessage());
			return;
		}
		long delay=0;
		long period=2000/20;//2 seconds divided by 20 ticks per second
		runner.runTaskTimer(this, delay, period);
		getServer().getPluginManager().registerEvents(new SentryListener(this), this);
	}

	public void onDisable(){
		try {
			runner.writeSentries();
		} catch (IOException e) {
			this.getLogger().severe("Sentry runner couldn't save your data!");
			getLogger().severe(e.getMessage());
			return;
		}
	}
	
	public boolean removeSentry(Location loc){
		return runner.removeSentry(loc);
	}
	public void addSentry(Sentry sentry){
		runner.addSentry(sentry);
	}
	public void addSentry(Location loc){
		runner.addSentry(loc);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args){
		for(Sentry sn : runner.sentries){
			s.sendMessage(sn.getLocation().toString());
		}
		
		return true;
	}
}
