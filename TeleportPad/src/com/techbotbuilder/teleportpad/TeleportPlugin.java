package com.techbotbuilder.teleportpad;

import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;

import com.techbotbuilder.functionalstructures.StructurePlugin;

public class TeleportPlugin extends StructurePlugin<TeleportPad> {
	public static final int teleportPadSizeSquared = 25;
	public static final double teleporterReach = 2;
	public final static Material teleportMaterial = Material.COOKIE;

	private final TeleportInventory teleportInventory = new TeleportInventory(this);
	
	@Override
	public void onEnable(){
		super.onEnable();
		if (tracker != null) teleportInventory.update();
	}
	
	@Override
	public TeleportPad createStructure(DataInputStream data) throws IOException{
		return new TeleportPad(this, data);
	}

	@Override
	public TeleportPad createStructure(Location location) {
		return new TeleportPad(this, location);
	}

	@Override
	public TeleportListener getListener() {
		return new TeleportListener(this);
	}

	public TeleportInventory getTeleportInventory() {
		return teleportInventory;
	}
}
