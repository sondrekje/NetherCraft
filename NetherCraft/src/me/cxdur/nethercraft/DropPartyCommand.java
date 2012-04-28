package me.cxdur.nethercraft;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.inventory.ItemStack;

public class DropPartyCommand implements CommandExecutor {

	public DropPartyCommand(NetherCraft instance) {
}
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
if (args.length == 0) {
	if (!(sender instanceof Player)) {
		sender.sendMessage("Denne kommandoen er bare for players.");
		return true;
	}
	Player p = (Player) sender;
	final Location loc = p.getLocation();
	final World world = loc.getWorld();
	if (p.isOp()) {
	for (int x = -15; x <= 15; x += 10)
	{
		for (int z = -15; z <= 15; z += 10)
		{
			final Location loc2 = new Location(world, loc.getBlockX() + x, p.getLocation() + 64, loc.getBlockZ() + z);
			world.spawn(loc2, ExperienceOrb.class);
			world.spawn(loc2, ThrownExpBottle.class);
			world.spawn(loc2, ThrownExpBottle.class);
			world.spawn(loc2, ThrownExpBottle.class);
			world.spawn(loc2, ThrownExpBottle.class);
			world.spawn(loc2, ThrownExpBottle.class);
			world.spawn(loc2, ThrownExpBottle.class);
			world.spawn(loc2, ThrownExpBottle.class);
			ItemStack item = new ItemStack(264, 5);
			ItemStack item2 = new ItemStack(278, 1);
			ItemStack item3 = new ItemStack(17, 30);
			world.dropItem(loc2, item);
			world.dropItem(loc2, item2);
			world.dropItem(loc2, item3);
		}
	}
sender.sendMessage("Drop party startet.");
	return true;
	} else {
	return false;
}
	}
return false;
} }
