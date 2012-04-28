package me.cxdur.nethercraft;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    public HomeCommand(NetherCraft instance) {

    }

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {   
		Player p = (Player) sender;
		if (p.getBedSpawnLocation() != null) {		
			Location home = p.getBedSpawnLocation();
		p.teleport(home);
		p.sendMessage("Du ble sendt til hjemmet ditt!");
		return true;
	
		} else {
		p.sendMessage("Du har ingen seng du har sovet i, derfor kan vi ikke tpe deg!");
		return true;
	}	
	}	}