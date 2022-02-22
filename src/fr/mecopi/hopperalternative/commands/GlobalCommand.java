package fr.mecopi.hopperalternative.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mecopi.hopperalternative.managers.HopperAlternativeManager;
import fr.mecopi.hopperalternative.objects.Receptor;
import fr.mecopi.hopperalternative.objects.Transmitter;

public class GlobalCommand implements CommandExecutor 
{

	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String arg2, String[] Args) 
	{
		if(Args.length >= 1)
		{
			switch(Args[0].toLowerCase())
			{
				case "help":
					Sender.sendMessage(ChatColor.GOLD + "Pour utiliser une r�cepteur, placez le puis prenez un bout de papier\nQue vous aurez renomm� avec le nom que vous souhaitez lui donner\nUne fois fait, effectuez un clic droit sur le r�cepteur concern�\n"
					+ "Pour utiliser un �metteur, prenez un bout de papier renomm� avec le nom d'un de vos r�cepteur, effectuez un clic droit sur le r�cepteur et voil�\n"
					+ "Pour connaitre les informations d'un �metteur ou d'un recepteur vous pouvez tapez la commande /ha whois en regardant un �metteur ou un r�cepteur");
					break;
				case "whois":
					if(Sender instanceof Player)
					{
						Player Player = (Player)Sender;
						Transmitter lookingTransmitter = HopperAlternativeManager.GetTransmitter(Player.getTargetBlockExact(10));
						Receptor lookingReceptor = HopperAlternativeManager.GetReceptor(Player.getTargetBlockExact(10));
						if(lookingTransmitter != null)
							Player.sendMessage(lookingTransmitter.GetReceptor() != null ? "--\nType: Emetteur\nNom: " + lookingTransmitter.GetName() + "\nR�cepteur: Avec" : "--\nType: Emetteur\nNom: " + lookingTransmitter.GetName() + "\nR�cepteur: Sans");
						else if(lookingReceptor != null)
							Player.sendMessage(lookingReceptor.GetTransmitter() != null ? "--\nType: Recepteur\nEmetteur: " + lookingReceptor.GetTransmitter().GetName() : "--\nType: Recepteur\nEmetteur: Sans");
						else
							Player.sendMessage(ChatColor.RED + "Vous ne regardez ni un �metteur ni un r�cepteur");
					}
					else
						Sender.sendMessage(ChatColor.RED + "Cette commande ne peut etre utilisee que part un joueur");
					break;
			}
		}
		else
		{
			Sender.sendMessage(ChatColor.RED + "Tapez : /ha help ou /ha whois");
		}
		
		
		return false;
	}

}
