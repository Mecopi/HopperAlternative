package fr.mecopi.hopperalternative.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.mecopi.hopperalternative.managers.HopperAlternativeManager;
import net.md_5.bungee.api.ChatColor;

public class Receptor 
{
	private Block _Block;
	private UUID _Owner;
	private Transmitter _Transmitter;
	
	public Receptor(Block Block, UUID Owner, Transmitter Transmitter)
	{
		_Block = Block;
		_Owner = Owner;
		_Transmitter = Transmitter;
		if(_Transmitter != null)
			_Transmitter.SetReceptor(this);
	}
	
	//Getters
	
	public Block GetBlock() {
		return _Block;
	}
	public UUID GetOwner() {
		return _Owner;
	}
	public Transmitter GetTransmitter() {
		return _Transmitter;
	}
	
	//Static methods
	
	public void UnlinkTransmitter(Player Player) 
	{
		_Transmitter = null;
		Player.sendMessage(ChatColor.RED + "Le r�cepteur plac� en X: " + _Block.getX() + "; Y: " + _Block.getY() + "; Z: " + _Block.getZ() + ", � �t� d�li� de son transmetteur");
	}
	
	//Events

	public void onBreak(BlockBreakEvent e) 
	{
		if(e.getBlock().equals(_Block))
		{
			e.setCancelled(true);
			if(e.getPlayer().getUniqueId().equals(_Owner) || e.getPlayer().isOp())
			{
				HopperAlternativeManager.Receptors.remove(this);
				_Block.setType(Material.AIR);
				_Block.getWorld().dropItemNaturally(_Block.getLocation(), new ItemStack(Material.CHEST, 1));
				if(_Transmitter != null)
				{
					if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(_Owner)))
						_Transmitter.UnlinkReceptor(Bukkit.getPlayer(_Owner));
					else
						_Transmitter.UnlinkReceptor(e.getPlayer());
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
				if(_Transmitter == null)
				{
					e.setCancelled(true);
					if(e.getPlayer().getUniqueId().equals(_Owner) || e.getPlayer().isOp()) //e.getPlayer().isOp() by e.getPlayer().hasPermission("hopperalternative.manageTransmittersReceptors")
					{
						if(e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getType().equals(Material.PAPER))
						{
							Transmitter tryingLinkTransmitter = HopperAlternativeManager.GetPlayerTransmitter(e.getItem().getItemMeta().getDisplayName(), _Owner);
							if(tryingLinkTransmitter != null)
							{
								if(tryingLinkTransmitter.GetReceptor() == null)
								{
									_Transmitter = tryingLinkTransmitter;
									tryingLinkTransmitter.SetReceptor(this);
									e.getPlayer().sendMessage(ChatColor.GREEN + "Ce r�cepteur � bien �t� li� � l'�meteur \"" + tryingLinkTransmitter.GetName() + "\"");
								}
								else
									e.getPlayer().sendMessage(ChatColor.RED + "L'�metteur que vous tentez de li� poss�de d�j� un r�cepteur");
							}
							else
								e.getPlayer().sendMessage(ChatColor.RED + "Vous ne poss�dez aucun �metteur portant le nom \"" + e.getItem().getItemMeta().getDisplayName() + "\"");
						}
						else
							e.getPlayer().sendMessage(ChatColor.RED + "Ce r�cepteur n'est pas actif, liez le pour le rendre actif (/ha help)");
					}
					else
						e.getPlayer().sendMessage(ChatColor.RED + "Ce r�cepteur ne vous appartient pas");
				}
			}
		}
	}
	
	
}
