package com.WarriorNate4.sentries;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class SentryMain extends JavaPlugin {

	private SentryRunner runner;
	
	@Override
	public void onEnable(){
		try {
			runner = new SentryRunner(this);
		} catch (IOException e) {
			this.getServer().broadcastMessage("Sentry runner had a problem T.T");
			return; //stop trying to set stuff up, we failed
		}
		long delay=0;
		long period=1000L;
		//^^Fix these values
		runner.runTaskTimer(this, delay, period);
		getServer().getPluginManager().registerEvents(new SentryListener(this), this);
	}
	
	public void removeSentry(Location loc){
		runner.removeSentry(loc);
	}
	public void addSentry(Sentry sentry){
		runner.addSentry(sentry);
	}
}
