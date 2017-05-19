package cc.atspace.yoyofoe1.CraftableGlider;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;


public class CraftableGliderTest extends JavaPlugin {
	public void onEnable(){
		ItemStack elytra = new ItemStack(Material.ELYTRA);
		ShapedRecipe elytraRecipe = new ShapedRecipe(elytra);
		elytraRecipe.shape("ded","eoe","sss");
		elytraRecipe.setIngredient('e', Material.EMERALD_BLOCK);
		elytraRecipe.setIngredient('s', Material.SADDLE);
		elytraRecipe.setIngredient('d', Material.DIAMOND_BLOCK);
		elytraRecipe.setIngredient('o', Material.EYE_OF_ENDER);
		getServer().addRecipe(elytraRecipe);
	}
}