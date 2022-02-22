package fr.mecopi.hopperalternative;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mecopi.hopperalternative.managers.*;

public class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		RecipeManager.Init();
		try {
			FileManager.Init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommandManager.Init();
		getServer().getPluginManager().registerEvents(new EventsManager(), this);
	}
	public static Plugin GetInstance() {
		return Bukkit.getServer().getPluginManager().getPlugin("HopperAlternative");
	}
	
	@Override
	public void onDisable()
	{
		try {
			FileManager.Save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
