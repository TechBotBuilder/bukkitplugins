package com.WarriorNate4.sentries;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SentryRunner extends BukkitRunnable {

	private ArrayList<Sentry> sentries;//We need a List to keep track of all the sentries
	private JavaPlugin plugin;

	public SentryRunner(JavaPlugin plugin) throws IOException{
		this.plugin = plugin;
		sentries = new ArrayList<Sentry>();//initialize sentries to empty List

		//File datafile = new File(plugin.getDataFolder(), "sentries.dat");
		//datafile.createNewFile();//will create new file, only if doesn't already exist

		/*the data will be stored as:
		* (w11 w12 x1 y1 z1 w21 w22 x2 y2 z2 ... wN1 wN2 xN yN zN),
		* each element will be a long (big whole number, eight bytes).
		*/
		//DataInputStream data = new DataInputStream(new FileInputStream(datafile));

		/*while(data.available()>0){
			//TODO: create sentry from stream and addSentry(...) to sentries
		}*/

		//data.close();
	}
	
	public void run() {
		//Info: the bukkit scheduler system will call this every little while
		for(Sentry sentry : sentries){
		  sentry.fire();
		}
	}

	public void writeSentries() throws IOException {
		File datafile = new File(plugin.getDataFolder(), "sentries.dat");
		datafile.createNewFile();
		DataOutputStream data = new DataOutputStream(new FileOutputStream(datafile));

		/*
		//TODO: iterate over the sentries
			byte[] bytes=null;//TODO: get the byte[] representation
			data.write(bytes);
		*/
		
		data.close();
	}

	public void addSentry(Location loc){
		sentries.add(new Sentry(plugin, loc));
	}
	
	public void addSentry(Sentry sentry){
		sentries.add(sentry);
	}

	public void removeSentry(Sentry sentry){
		//remove sentry from our sentries
	}

	public boolean removeSentry(Location loc){
		//iterate over our sentries
			//if sentry is close enough to loc
				//removeSentry(that sentry)
				//return true, there was a sentry near that location and we removed it
		return false;//if we get to this point, we didn't find a sentry nearby
	}
}