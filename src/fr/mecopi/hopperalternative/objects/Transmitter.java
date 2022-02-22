package fr.mecopi.hopperalternative.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.mecopi.hopperalternative.Main;
import fr.mecopi.hopperalternative.managers.HopperAlternativeManager;
import net.md_5.bungee.api.ChatColor;

public class Transmitter 
{
	private Block _Block;
	private UUID _Owner;
	private String _Name;
	private Receptor _Receptor;
	private int taskID;

	public Transmitter(Block Block, UUID Owner, String Name, Receptor Receptor)
	{
		_Block = Block;
		_Owner = Owner;
		_Name = Name;
		_Receptor = Receptor;
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.GetInstance(), () -> Task(), 0, 5);
	}
	
	//Getters
	
	public Block GetBlock() {
		return _Block;
	}
	public UUID GetOwner() {
		return _Owner;
	}
	public String GetName() {
		return _Name;
	}
	public Receptor GetReceptor() {
		return _Receptor;
	}
	
	//Setters
	
	public void SetName(String Name) {
		_Name = Name;
	}
	public void SetReceptor(Receptor Receptor)  {
		_Receptor = Receptor;
	}
	public void UnlinkReceptor(Player Player) 
	{
		_Receptor = null;
		Player.sendMessage(ChatColor.RED + "L'émetteur placé en X: " + _Block.getX() + "; Y: " + _Block.getY() + "; Z: " + _Block.getZ() + ", à été délié de son récepteur");
	}
	
	
	//Events
	
	public void onBreak(BlockBreakEvent e)
	{
		if(e.getBlock().equals(_Block))
		{
			e.setCancelled(true);
			if(e.getPlayer().getUniqueId().equals(_Owner) || e.getPlayer().isOp())
			{
				Bukkit.getScheduler().cancelTask(taskID);
				HopperAlternativeManager.Transmitters.remove(this);
				_Block.setType(Material.AIR);
				_Block.getWorld().dropItemNaturally(_Block.getLocation(), new ItemStack(Material.CHEST, 1));
				if(_Receptor != null)
				{
					if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(_Owner)))
						_Receptor.UnlinkTransmitter(Bukkit.getPlayer(_Owner));
					else
						_Receptor.UnlinkTransmitter(e.getPlayer());
				}
			}
		}
	}
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.getClickedBlock() != null && e.getClickedBlock().equals(_Block))
		{
			if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				if(_Name == null)
				{
					e.setCancelled(true);
					if(e.getPlayer().getUniqueId().equals(_Owner) || e.getPlayer().isOp()) //e.getPlayer().isOp() by e.getPlayer().hasPermission("hopperalternative.manageTransmittersReceptors")
					{
						if(e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getType().equals(Material.PAPER))
						{
							for(Transmitter Transmitter : HopperAlternativeManager.GetTransmitters(_Owner))
							{
								if(Transmitter._Name != null && Transmitter._Name.equals(e.getItem().getItemMeta().getDisplayName()))
								{
									e.getPlayer().sendMessage(ChatColor.RED + "Vous possédez déjà un émetteur portant ce nom, il se trouve en X: " + Transmitter._Block.getX() + "; Y: " + Transmitter._Block.getY() + "; Z: " + Transmitter._Block.getZ());
									return;
								}
							}
							_Name = e.getItem().getItemMeta().getDisplayName();
							e.getPlayer().sendMessage(ChatColor.GREEN + "Cet émetteur est desormais actif, vous pouvez taper /ha whois en pointant un émetteur ou un récepteur pour obtenir ses informations");
						}
						else
							e.getPlayer().sendMessage(ChatColor.RED + "Cet émetteur n'est pas actif, nommez le pour le rendre actif (/ha help)");
					}
					else
						e.getPlayer().sendMessage(ChatColor.RED + "Cet émetteur ne vous appartient pas");
				}
			}
		}
	}
	
	
	//Task
	
	private void Task()
	{
		if(_Receptor != null)
		{
			Inventory receptorInventory = ((Chest)_Receptor.GetBlock().getState()).getInventory();
			Inventory transmitterInventory = ((Chest)_Block.getState()).getInventory();
			for(ItemStack transmitterItem : transmitterInventory)
			{
				if(transmitterItem != null)
				{
					int realItemsInReceptor = HopperAlternativeManager.GetRealInventorySize(receptorInventory);
					receptorInventory.addItem(new ItemStack(transmitterItem.getType(), 1));
					if(HopperAlternativeManager.GetRealInventorySize(receptorInventory) == realItemsInReceptor+1)
						transmitterInventory.removeItem(new ItemStack(transmitterItem.getType(), 1));
					break;
				}
			}
			
			
		}
	}
	
}
