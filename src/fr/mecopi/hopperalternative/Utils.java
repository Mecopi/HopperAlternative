package fr.mecopi.hopperalternative;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Utils 
{
	public static Plugin GetInstance() {
		return Bukkit.getServer().getPluginManager().getPlugin("HopperAlternative");
	}
}
