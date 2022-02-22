package fr.mecopi.hopperalternative.managers;

import org.bukkit.Bukkit;

import fr.mecopi.hopperalternative.commands.GlobalCommand;


public class CommandManager 
{
	public static void Init()
	{
		Bukkit.getPluginCommand("ha").setExecutor(new GlobalCommand());
	}
}
