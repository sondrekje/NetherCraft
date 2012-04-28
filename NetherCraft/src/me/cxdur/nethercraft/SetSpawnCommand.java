package me.cxdur.nethercraft;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetSpawnCommand implements CommandExecutor {

	public SetSpawnCommand(NetherCraft instance) {
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {    
		Player p = (Player) sender;
		if (p.isOp()) {
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();
			p.getWorld().setSpawnLocation(x, y, z);
			p.sendMessage(ChatColor.GOLD + "Du endret spawnet");
		} else {
			p.sendMessage(ChatColor.RED + "Du har ikke permission til dette.");
		}
		return false;
}	}
