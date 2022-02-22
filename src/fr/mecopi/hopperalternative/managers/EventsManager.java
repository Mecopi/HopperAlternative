package fr.mecopi.hopperalternative.managers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;

import fr.mecopi.hopperalternative.Main;
import fr.mecopi.hopperalternative.objects.Receptor;
import fr.mecopi.hopperalternative.objects.Transmitter;
import fr.mecopi.hopperalternative.recipes.ReceptorRecipe;
import fr.mecopi.hopperalternative.recipes.TransmitterRecipe;
import net.md_5.bungee.api.ChatColor;

public class EventsManager implements Listener
{

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		if(e.getItemInHand().getItemMeta().getLocalizedName().equals("transmitter"))
		{
			Bukkit.getScheduler().runTaskLater(Main.GetInstance(), new Runnable() {
				
				@Override
				public void run() 
				{
					if(((Chest)e.getBlockPlaced().getState()).getInventory() instanceof DoubleChestInventory)
					{
						Chest leftChest = ((Chest)((DoubleChest)((Chest)e.getBlockPlaced().getState()).getInventory().getHolder()).getLeftSide());
						Chest rightChest = ((Chest)((DoubleChest)((Chest)e.getBlockPlaced().getState()).getInventory().getHolder()).getRightSide());
						if(leftChest.getLocation().equals(((Chest)e.getBlockPlaced().getState()).getLocation())) //If transmitter just place to left of another chest
						{
							for(Transmitter Transmitter : HopperAlternativeManager.Transmitters)
							{
								if(Transmitter.GetBlock().equals(rightChest.getBlock()))
								{
									e.getBlockPlaced().setType(Material.AIR);
									if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
										e.getPlayer().getInventory().addItem(TransmitterRecipe.Transmitter);
									e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas coller deux émetteurs, utiliser un simple un coffre");
									return;
								}
							}
							for(Receptor Receptor : HopperAlternativeManager.Receptors)
							{
								if(Receptor.GetBlock().equals(rightChest.getBlock()))
								{
									e.getBlockPlaced().setType(Material.AIR);
									if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
										e.getPlayer().getInventory().addItem(TransmitterRecipe.Transmitter);
									e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas coller un émetteur et un récepteur");
									return;
								}
							}
						}
						else
						{
							for(Transmitter Transmitter : HopperAlternativeManager.Transmitters)
							{
								if(Transmitter.GetBlock().equals(leftChest.getBlock()))
								{
									e.getBlockPlaced().setType(Material.AIR);
									if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
										e.getPlayer().getInventory().addItem(TransmitterRecipe.Transmitter);
									e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas coller deux émetteurs, utiliser un simple un coffre");
									return;
								}
							}
							for(Receptor Receptor : HopperAlternativeManager.Receptors)
							{
								if(Receptor.GetBlock().equals(leftChest.getBlock()))
								{
									e.getBlockPlaced().setType(Material.AIR);
									if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
										e.getPlayer().getInventory().addItem(TransmitterRecipe.Transmitter);
									e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas coller un émetteur et un récepteur");
									return;
								}
							}
						}
					}
					HopperAlternativeManager.Transmitters.add(new Transmitter(e.getBlockPlaced(), e.getPlayer().getUniqueId(), null, null));
					e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Pour utiliser cet émetteur, equipez-vous d'un papier\nrenommé avec le nom souhaité");
				}
			}, 1);
			
			
			
			/*
			for(Transmitter Transmitter : HopperAlternativeManager.Transmitters)
			{
				if(e.getBlockPlaced().getWorld().getBlockAt(new Location(e.getBlockPlaced().getWorld(), e.getBlockPlaced().getX()+1, e.getBlockPlaced().getY(), e.getBlockPlaced().getZ())).equals(Transmitter.GetBlock()) )
				{
					
				}
			}
			*/
			
		}
		else if(e.getItemInHand().getItemMeta().getLocalizedName().equals("receptor"))
		{
				Bukkit.getScheduler().runTaskLater(Main.GetInstance(), new Runnable() {
				
				@Override
				public void run() 
				{
					if(((Chest)e.getBlockPlaced().getState()).getInventory() instanceof DoubleChestInventory)
					{
						Chest leftChest = ((Chest)((DoubleChest)((Chest)e.getBlockPlaced().getState()).getInventory().getHolder()).getLeftSide());
						Chest rightChest = ((Chest)((DoubleChest)((Chest)e.getBlockPlaced().getState()).getInventory().getHolder()).getRightSide());
						if(leftChest.getLocation().equals(((Chest)e.getBlockPlaced().getState()).getLocation())) //If transmitter just place to left of another chest
						{
							for(Transmitter Transmitter : HopperAlternativeManager.Transmitters)
							{
								if(Transmitter.GetBlock().equals(rightChest.getBlock()))
								{
									e.getBlockPlaced().setType(Material.AIR);
									if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
										e.getPlayer().getInventory().addItem(TransmitterRecipe.Transmitter);
									e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas coller un émetteur et un récepteur");
									return;
								}
							}
							for(Receptor Receptor : HopperAlternativeManager.Receptors)
							{
								if(Receptor.GetBlock().equals(rightChest.getBlock()))
								{
									e.getBlockPlaced().setType(Material.AIR);
									if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
										e.getPlayer().getInventory().addItem(ReceptorRecipe.Receptor);
									e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas coller deux récepteur, utiliser un simple un coffre");
									return;
								}
							}
						}
						else
						{
							for(Transmitter Transmitter : HopperAlternativeManager.Transmitters)
							{
								if(Transmitter.GetBlock().equals(leftChest.getBlock()))
								{
									e.getBlockPlaced().setType(Material.AIR);
									if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
										e.getPlayer().getInventory().addItem(TransmitterRecipe.Transmitter);
									e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas coller un émetteur et un récepteur");
									return;
								}
							}
							for(Receptor Receptor : HopperAlternativeManager.Receptors)
							{
								if(Receptor.GetBlock().equals(leftChest.getBlock()))
								{
									e.getBlockPlaced().setType(Material.AIR);
									if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
										e.getPlayer().getInventory().addItem(ReceptorRecipe.Receptor);
									e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas coller deux récepteur, utiliser un simple un coffre");
									return;
								}
							}
						}
					}			
					HopperAlternativeManager.Receptors.add(new Receptor(e.getBlockPlaced(), e.getPlayer().getUniqueId(), null));
					e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Pour utiliser ce récepteur, equipez-vous d'un papier portant le nom d'un de vos émetteurs");
				}
			}, 1);
		
		}
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory().getType().equals(InventoryType.ANVIL) && e.getCurrentItem() != null && (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLocalizedName() && e.getCurrentItem().getItemMeta().getLocalizedName().equals("transmitter")))
			e.setCancelled(true);
		else if(e.getInventory().getType().equals(InventoryType.ANVIL) && e.getCurrentItem() != null && (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLocalizedName() && e.getCurrentItem().getItemMeta().getLocalizedName().equals("receptor")))
			e.setCancelled(true);
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		for(int i = 0; i < HopperAlternativeManager.Transmitters.size(); i++)
			HopperAlternativeManager.Transmitters.get(i).onBreak(e);
		for(int i = 0; i < HopperAlternativeManager.Receptors.size(); i++)
			HopperAlternativeManager.Receptors.get(i).onBreak(e);
	}
	@EventHandler
	public void onBlockExplode(EntityExplodeEvent e)
	{
		for(Block ExplodedBlock : e.blockList())
		{
			if(ExplodedBlock.getType().equals(Material.CHEST))
			{
				for(Transmitter Transmitter : HopperAlternativeManager.Transmitters)
				{
					if(ExplodedBlock.equals(Transmitter.GetBlock()))
						e.blockList().remove(ExplodedBlock);
				}
				for(Receptor Receptor : HopperAlternativeManager.Receptors)
				{
					if(ExplodedBlock.equals(Receptor.GetBlock()))
						e.blockList().remove(ExplodedBlock);
				}
			}
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{	
		for(Transmitter Transmitter : HopperAlternativeManager.Transmitters)
			Transmitter.onInteract(e);
		for(Receptor Receptor : HopperAlternativeManager.Receptors)
			Receptor.onInteract(e);
	}
}
