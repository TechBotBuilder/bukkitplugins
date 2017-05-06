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
			this.getLogger().severe("Sentry runner had a problem T.T");
			getLogger().severe(e.getMessage());
			return;
		}
		long delay=0;
		//long period=1500/20;//1.5 seconds divided by 20 ticks per second
		long period=3000/20;//3 seconds divided by 20 ticks per second
		runner.runTaskTimer(this, delay, period);
		getServer().getPluginManager().registerEvents(new SentryListener(this), this);
	}

	public void removeSentry(Location loc){
		runner.removeSentry(loc);
	}
	public void addSentry(Sentry sentry){
		runner.addSentry(sentry);
	}
	public void addSentry(Location loc){
		runner.addSentry(loc);
	}
	public void log(String m){
		this.getLogger().info(m);
	}
}