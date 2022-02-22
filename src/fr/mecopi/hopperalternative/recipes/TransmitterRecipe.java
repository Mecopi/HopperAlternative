package fr.mecopi.hopperalternative.recipes;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.hopperalternative.Utils;
import net.md_5.bungee.api.ChatColor;

public class TransmitterRecipe 
{
	public static ItemStack Transmitter;
	
	public static void Init()
	{
		//Creating Transmitter item from existing item
		
		Transmitter = new ItemStack(Material.CHEST);
		ItemMeta transmitterMeta = Transmitter.getItemMeta();
		transmitterMeta.setDisplayName("Emetteur");
		transmitterMeta.setLocalizedName("transmitter");
		transmitterMeta.setLore(Arrays.asList(ChatColor.GREEN + "Cet �metteur permettra que lorsque", ChatColor.GREEN + "vous d�poserez des objets � l'int�rieur", ChatColor.GREEN + "ils soient t�l�port�s au r�cepteur", ChatColor.RED + "Si vous cassez un �metteur", ChatColor.RED + "et qu'un r�cepteur lui est li�, il sera d�li�", ChatColor.RED + "vous ne r�cupererez qu'un simple coffre"));
		Transmitter.setItemMeta(transmitterMeta);
		
		//Crafting code
		
		NamespacedKey recipeKey = new NamespacedKey(Utils.GetInstance(), "transmitter");
		ShapedRecipe Recipe = new ShapedRecipe(recipeKey, Transmitter);
		Recipe.shape("IDI", "RSR", "GCG");
		Recipe.setIngredient('I', Material.IRON_INGOT);
		Recipe.setIngredient('D', Material.DIAMOND);
		Recipe.setIngredient('R', Material.REDSTONE);
		Recipe.setIngredient('S', Material.DISPENSER);
		Recipe.setIngredient('G', Material.GOLD_BLOCK);
		Recipe.setIngredient('C', Material.CHEST);
		
		Bukkit.addRecipe(Recipe);

		
	}
}
