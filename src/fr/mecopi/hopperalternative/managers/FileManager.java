package fr.mecopi.hopperalternative.managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.mecopi.hopperalternative.Main;
import fr.mecopi.hopperalternative.objects.Receptor;
import fr.mecopi.hopperalternative.objects.Transmitter;

public class FileManager 
{
	private static File globalFolder = new File(Main.GetInstance().getDataFolder().getPath().replace("\\", "/"));
	private static File transmittersReceptors = new File(globalFolder.getPath().concat("/informations.yml"));
	
	
	public static void Init() throws IOException
	{
		if(!globalFolder.exists())
			globalFolder.mkdir();
		if(!transmittersReceptors.exists())
			transmittersReceptors.createNewFile();
		
		YamlConfiguration Configuration = new YamlConfiguration();
		
		try
		{
			Configuration.load(transmittersReceptors);
			for(String Key : Configuration.getKeys(false))
			{
				if(Key.contains("tr"))
				{
					UUID Owner = UUID.fromString(Configuration.getString(Key.concat(".owner")));
					String[] Location = Configuration.getString(Key.concat(".location")).split(";");
					Block Block = Bukkit.getWorld(Location[0]).getBlockAt(new Location(Bukkit.getWorld(Location[0]), Integer.parseInt(Location[1]), Integer.parseInt(Location[2]), Integer.parseInt(Location[3])));
					String Name = Configuration.getString(Key.concat(".name"));
					HopperAlternativeManager.Transmitters.add(new Transmitter(Block, Owner, Name, null));
				}
				else
				{
					UUID Owner = UUID.fromString(Configuration.getString(Key.concat(".owner")));
					String[] Location = Configuration.getString(Key.concat(".location")).split(";");
					Block Block = Bukkit.getWorld(Location[0]).getBlockAt(new Location(Bukkit.getWorld(Location[0]), Integer.parseInt(Location[1]), Integer.parseInt(Location[2]), Integer.parseInt(Location[3])));
					HopperAlternativeManager.Receptors.add(new Receptor(Block, Owner, HopperAlternativeManager.GetPlayerTransmitter(Configuration.getString(Key.concat(".transmitter")), Owner)));
				}
			}
			
		}
		catch(Exception ex) { ex.printStackTrace(); }
	}
	
	public static void Save() throws IOException
	{
		FileWriter Writer = new FileWriter(transmittersReceptors);
		for(Transmitter Transmitter : HopperAlternativeManager.Transmitters)
		{
			Writer.write("tr_".concat(String.valueOf(HopperAlternativeManager.Transmitters.indexOf(Transmitter))).concat(":\n"));
			Writer.write("  owner: " + Transmitter.GetOwner().toString() + "\n");
			Writer.write("  location: " + Transmitter.GetBlock().getLocation().getWorld().getName() + ";" + Transmitter.GetBlock().getLocation().getBlockX() + ";" + Transmitter.GetBlock().getLocation().getBlockY() + ";" + Transmitter.GetBlock().getLocation().getBlockZ() + "\n");
			Writer.write("  name: " + Transmitter.GetName() + "\n");
		}
		for(Receptor Receptor : HopperAlternativeManager.Receptors)
		{
			Writer.write("rc_".concat(String.valueOf(HopperAlternativeManager.Receptors.indexOf(Receptor))).concat(":\n"));
			Writer.write("  owner: " + Receptor.GetOwner().toString() + "\n");
			Writer.write("  location: " + Receptor.GetBlock().getLocation().getWorld().getName() + ";" + Receptor.GetBlock().getLocation().getBlockX() + ";" + Receptor.GetBlock().getLocation().getBlockY() + ";" + Receptor.GetBlock().getLocation().getBlockZ() + "\n");
			Writer.write(Receptor.GetTransmitter() != null ? "  transmitter: " + Receptor.GetTransmitter().GetName() +"\n" : "  transmitter: null\n");
		}
		Writer.close();
	}
}
