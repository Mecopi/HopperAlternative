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

public class ReceptorRecipe 
{
	public static ItemStack Receptor;
	
	public static void Init()
	{
		//Creating Transmitter item from existing item
		
		Receptor = new ItemStack(Material.CHEST);
		ItemMeta receptorMeta = Receptor.getItemMeta();
		receptorMeta.setDisplayName("Récepteur");
		receptorMeta.setLocalizedName("receptor");
		receptorMeta.setLore(Arrays.asList(ChatColor.GREEN + "Ce récepteur recevra des objets", ChatColor.GREEN + "de la part de l'émetteur qui lui est lié", ChatColor.RED + "Si vous cassez ce récepteur", ChatColor.RED + "vous ne récupererez qu'un simple coffre"));
		Receptor.setItemMeta(receptorMeta);
		
		//Crafting code
		
		NamespacedKey recipeKey = new NamespacedKey(Utils.GetInstance(), "receptor");
		ShapedRecipe Recipe = new ShapedRecipe(recipeKey, Receptor);
		Recipe.shape("IDI", "RTR", "GCG");
		Recipe.setIngredient('I', Material.IRON_INGOT);
		Recipe.setIngredient('D', Material.DIAMOND);
		Recipe.setIngredient('R', Material.REDSTONE);
		Recipe.setIngredient('T', Material.TARGET);
		Recipe.setIngredient('G', Material.GOLD_BLOCK);
		Recipe.setIngredient('C', Material.CHEST);
		
		Bukkit.addRecipe(Recipe);

		
	}
}
