package me.cxdur.nethercraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    public SpawnCommand(NetherCraft instance) {
    }

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) 	{    
		Player p = (Player) sender;
		Location loc = Bukkit.getWorld("world").getSpawnLocation();
		p.teleport(loc);
		p.sendMessage(ChatColor.GOLD + "Du ble sendt til spawn i " + p.getWorld().getName() + ".");
		p.setHealth(20);
		return true;
	}
}
