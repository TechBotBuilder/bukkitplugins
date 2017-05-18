package com.WarriorNate4.sentries;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SentryRunner extends BukkitRunnable {

	//TODO private
	ArrayList<Sentry> sentries;//We need a List to keep track of all the sentries
	private JavaPlugin plugin;

	public SentryRunner(JavaPlugin plugin) throws IOException{
		this.plugin = plugin;
		sentries = new ArrayList<Sentry>();//initialize sentries to empty List

		File datafile = new File(plugin.getDataFolder(), "sentries.dat");
		datafile.getParentFile().mkdirs();//will create any folders we need, only if don't exist yet
		datafile.createNewFile();//will create new file, only if doesn't already exist

		DataInputStream data = new DataInputStream(new FileInputStream(datafile));

		while(data.available()>0){
			Sentry s = new Sentry(plugin, data);
			addSentry(s);
		}

		data.close();
	}

	public void run() {
		for(Sentry sentry : sentries){
			sentry.fire();
		}
	}

	public void writeSentries() throws IOException {
		File datafile = new File(plugin.getDataFolder(), "sentries.dat");
		datafile.getParentFile().mkdirs();
		datafile.createNewFile();
		DataOutputStream data = new DataOutputStream(new FileOutputStream(datafile));

		for(Sentry s: sentries){
			byte[] bytes = s.toBytes();
			data.write(bytes);
		}

		data.close();
	}

	public void addSentry(Location loc){
		sentries.add(new Sentry(plugin, loc));
	}

	public void addSentry(Sentry sentry){
		sentries.add(sentry);
	}

	public void removeSentry(Sentry sentry){
		sentries.remove(sentry);
	}

	public boolean removeSentry(Location loc){
		boolean found=false;
		ListIterator<Sentry> listIterator = sentries.listIterator();
		while( listIterator.hasNext() ){
			if (listIterator.next().getLocation().distance(loc)< 1){
				listIterator.remove();
				found=true;
			}
		}
		return found;
	}
}
