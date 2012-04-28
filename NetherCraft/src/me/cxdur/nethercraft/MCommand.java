package me.cxdur.nethercraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCommand implements CommandExecutor{

	public MCommand(NetherCraft instance) {
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (sender instanceof Player) {
			if (args.length == 0) {
				sender.sendMessage("/m spiller [tekst]");
				return true;
			}
			else if (args.length == 1) {
				sender.sendMessage("Du må huske tekst!");
				return true;
			} else if (args.length >= 2) {
				Player msender = (Player) sender;
				String[] message = args;
				Player reciver = Bukkit.getPlayer(args[0]);
				if (msender == reciver) {
					msender.sendMessage(ChatColor.RED + 
							"Kan ikke sende meldinger til deg selv!");
					return true;
				}if (reciver == null) {
					msender.sendMessage(ChatColor.RED + "Spilleren er ikke online!");
					return true;
				}
				sender.sendMessage(ChatColor.AQUA + "[" + msender.getName() + " --> " + reciver.getName() + "] " + ato(message));
				reciver.sendMessage(ChatColor.AQUA + "[" + reciver.getName() + " <-- " + msender.getName() + "] " + ato(message));
				NetherCraft.log.info(ChatColor.AQUA + "[" + msender.getName() + " --> " + reciver.getName() + "] " + ato(message));
				return true; 
			}	}
		return false;	}	

	public String ato(String[] array)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < array.length; i++) {
			sb.append(array[i]);
			sb.append(" ");
		}

		return sb.toString();
	}
}