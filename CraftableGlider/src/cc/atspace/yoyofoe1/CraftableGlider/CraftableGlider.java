package cc.atspace.yoyofoe1.CraftableGlider;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;


public class CraftableGlider extends JavaPlugin implements Listener{
	public void onEnable(){
		ItemStack elytra = new ItemStack(Material.ELYTRA);
		ShapedRecipe elytraRecipe = new ShapedRecipe(elytra);
		elytraRecipe.shape("ded","eoe","sss");
		elytraRecipe.setIngredient('e', Material.EMERALD_BLOCK);
		elytraRecipe.setIngredient('s', Material.SADDLE);
		elytraRecipe.setIngredient('d', Material.DIAMOND_BLOCK);
		elytraRecipe.setIngredient('o', Material.EYE_OF_ENDER);
		getServer().addRecipe(elytraRecipe);

		getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void fireworkRestorer (PlayerInteractEvent e){
		Player p = e.getPlayer();

		if (p.isGliding() && e.hasItem() &&
				e.getMaterial() == Material.FIREWORK &&
				e.getAction()==Action.RIGHT_CLICK_AIR)
		{
			ItemStack i = e.getItem().clone();
			i.setAmount(1);
			p.getInventory().addItem(i);
		}
	}
}