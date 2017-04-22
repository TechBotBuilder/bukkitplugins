package com.techbotbuilder.teleportpad;

import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Location;

import com.techbotbuilder.functionalstructures.StructurePlugin;

public class TeleportPlugin extends StructurePlugin<TeleportPad> {

	private final TeleportInventory teleportInventory = new TeleportInventory(this);
	
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
