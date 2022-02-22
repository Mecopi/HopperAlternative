package fr.mecopi.hopperalternative.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.mecopi.hopperalternative.objects.Receptor;
import fr.mecopi.hopperalternative.objects.Transmitter;

public class HopperAlternativeManager
{
	public static List<Transmitter> Transmitters = new ArrayList<Transmitter>();
	public static List<Receptor> Receptors = new ArrayList<Receptor>();
	
	//Getters
	
	public static Transmitter GetTransmitter(String Name)
	{
		for(Transmitter Transmitter : Transmitters)
		{
			if(Transmitter.GetName().equals(Name))
				return Transmitter;
		}
		return null;
	}	
	public static Transmitter GetTransmitter(Block Block)
	{
		for(Transmitter Transmitter : Transmitters)
		{
			if(Transmitter.GetBlock().equals(Block))
				return Transmitter;
		}
		return null;
	}
	public static Transmitter GetPlayerTransmitter(String Name, UUID Owner)
	{
		for(Transmitter Transmitter : Transmitters)
		{
			if(Transmitter.GetName() != null && Transmitter.GetName().equals(Name) && Transmitter.GetOwner().equals(Owner))
				return Transmitter;
		}
		return null;
	}
	
	public static List<Transmitter> GetTransmitters(UUID Owner)
	{
		List<Transmitter> playerTransmitters = new ArrayList<Transmitter>();
		for(Transmitter Transmitter : Transmitters)
		{
			if(Transmitter.GetOwner().equals(Owner))
				playerTransmitters.add(Transmitter);
		}
		return playerTransmitters;
	}
	public static Receptor GetReceptor(Transmitter Transmitter)
	{
		for(Receptor Receptor : Receptors)
		{
			if(Receptor.GetTransmitter().equals(Transmitter))
				return Receptor;
		}
		return null;
	}
	public static Receptor GetReceptor(Block Block)
	{
		for(Receptor Receptor : Receptors)
		{
			if(Receptor.GetBlock().equals(Block))
				return Receptor;
		}
		return null;
	}
	public static List<Receptor> GetReceptors(UUID Owner)
	{
		List<Receptor> playerReceptors = new ArrayList<Receptor>();
		for(Receptor Receptor : Receptors)
		{
			if(Receptor.GetOwner().equals(Owner))
				playerReceptors.add(Receptor);
		}
		return playerReceptors;
	}
	
	public static int GetRealInventorySize(Inventory Inventory)
	{
		int itemsIn = 0;
		for(ItemStack Item : Inventory.getContents())
		{
			if(Item != null)
				itemsIn += Item.getAmount();
		}
		return itemsIn;
	}
	
}
