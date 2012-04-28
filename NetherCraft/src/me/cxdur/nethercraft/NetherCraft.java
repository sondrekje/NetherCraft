package me.cxdur.nethercraft;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import me.cxdur.nethercraft.MCommand;
import me.cxdur.nethercraft.SetSpawnCommand;
import me.cxdur.nethercraft.SpawnCommand;
import me.cxdur.nethercraft.HomeCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NetherCraft extends JavaPlugin {
	HashMap<String, Object> pconfigDefaults = new HashMap<String, Object>();
	public static final Logger log = Logger.getLogger("Minecraft");
	public Map<String, Boolean> safezoned = new HashMap<String, Boolean>();

	public NetherCraftListener Listener = new NetherCraftListener(this);

	@Override
	public void onEnable() {
		NetherCraft plugin = this;
		log.info("Enabler NetherCraft v.01");
		getServer().getPluginManager().registerEvents(Listener, plugin);
		getCommand("m").setExecutor(new MCommand(this));
		getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		getCommand("dropparty").setExecutor(new DropPartyCommand(this));
		getCommand("home").setExecutor(new HomeCommand(this));				
		getDataFolder().mkdirs();
	}
	@Override
	public void onDisable() {
		log.info("Hade NetherCraft v.01");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (command.getName().equalsIgnoreCase("kick")) {     
			Player v = Bukkit.getServer().getPlayer(args[0]);
			Player p = (Player) sender;     
			if (p.isOp() || (p.isWhitelisted())) {
				if (args.length > 1) {
					String kickmsg = "";
					for (int msg = 1; msg <= args.length - 1; msg++) {
						kickmsg += args[msg] + " ";
					}
					kickmsg = kickmsg.substring(0, kickmsg.length() - 1);                       
					getServer().broadcastMessage(p.getDisplayName() + ChatColor.GOLD + " sparket ut " + ChatColor.GRAY +  v.getName());
					getServer().broadcastMessage(ChatColor.GREEN + "Grunn: " + kickmsg);
					v.kickPlayer("Du ble sparket ut av " + p.getName() + ". Grunn: " + kickmsg);
					v.getWorld().strikeLightningEffect(v.getLocation());
					return true;
				} else {                       
					getServer().broadcastMessage(p.getDisplayName() + ChatColor.GOLD + " sparket ut " + ChatColor.GRAY +  v.getName());
					getServer().broadcastMessage(ChatColor.GREEN + "Grunn: Ingen grunn oppgitt, det er ikke nødvendig");
					v.kickPlayer("Sparket ut: Ingen grunn oppgitt, det er ikke nødvendig");
					v.getWorld().strikeLightningEffect(v.getLocation());
					return true;
				}
			} else {
				p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED + "Noe gikk galt!");
			}
			return false;

		}
		return false;}
}