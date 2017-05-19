package cc.atspace.yoyofoe1.InfiniteGlider;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class InfiniteGliderTest extends JavaPlugin implements Listener {
	@EventHandler
	public void run(VehicleEnterEvent e) {
		e.getEntered().setGravity(false);
	}
	@EventHandler
	public void run(VehicleExitEvent e) {
		e.getExited().setGravity(true);
	}
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
}
