package me.cxdur.nethercraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class NetherCraftListener implements Listener {
	private NetherCraft plugin;
	private YamlConfiguration config;
	private File configFile;
	
	public NetherCraftListener(final NetherCraft plugin) {
		this.plugin = plugin;
} 

	public void loadPlayerConfig() {
		plugin.pconfigDefaults.put("Banned:", " false.");		
		plugin.pconfigDefaults.put("Grunn:", " NoReason");	
		plugin.pconfigDefaults.put("Hjelper.Rank.Gul", "false");
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
public void onPlayerKick(final PlayerKickEvent e) {
	e.setLeaveMessage(null);
	}
@EventHandler(priority = EventPriority.NORMAL)
public void AntiBug(final PlayerTeleportEvent e) {
	Player p = e.getPlayer();
	if (plugin.safezoned.containsKey(p.getName())) {
		plugin.safezoned.remove(p.getName()); 
		} else {
			return;
		}	
}
@EventHandler(priority = EventPriority.HIGH)
public void onFoodLevelChange(final FoodLevelChangeEvent e) {
	e.setCancelled(true);
}
@EventHandler(priority = EventPriority.NORMAL)
public void onPlayerJoin(final PlayerJoinEvent e) {
	Player p = e.getPlayer();
	p.sendMessage(ChatColor.RED + "[----------------( " + ChatColor.GRAY + "Prosjekt-Redstone" + ChatColor.RED + " )----------------]");
	p.sendMessage(ChatColor.RED + "Velkommen til" + ChatColor.GRAY + " Prosjekt-Redstone midlertidig SMP server.");       
	if (p.isOp()) {	
		p.setDisplayName(ChatColor.DARK_GREEN + p.getName());
		p.setPlayerListName(p.getDisplayName());
		e.setJoinMessage(ChatColor.DARK_GREEN + "+ " + p.getDisplayName() + ChatColor.DARK_GRAY + " / logget inn.");
		// eid.getWorld().strikeLightning(eid);
		p.setFireTicks(0);
		p.setHealth(20);	
	} else if (p.isWhitelisted()) {
		e.setJoinMessage(ChatColor.DARK_GREEN + "+ " + ChatColor.BLUE +  p.getDisplayName() + ChatColor.DARK_GRAY + " logget inn.");
		p.setDisplayName(ChatColor.BLUE + p.getName());
		p.setPlayerListName(p.getDisplayName());
	} else {
		e.setJoinMessage(ChatColor.DARK_GREEN + "+ " + ChatColor.GRAY + p.getName() + ChatColor.DARK_GRAY + " / logget inn.");
	}
	e.setJoinMessage(ChatColor.DARK_GREEN + "+ " + ChatColor.GRAY + p.getDisplayName() + ChatColor.DARK_GRAY + " / logget inn.");
}


@EventHandler(priority = EventPriority.NORMAL)
public void onPlayerCreate(final PlayerJoinEvent e) throws FileNotFoundException, IOException, InvalidConfigurationException {
	Player p = e.getPlayer();
	if (new File(this.plugin.getDataFolder() + "/players/", p.getName()).exists()) {
		NetherCraft.log.info("Loadet character fil for spiller: " + p.getName() + ".");
	} else {
		File fp = (new File(this.plugin.getDataFolder() + "/players/" + p.getName()));
		boolean create = (new File(this.plugin.getDataFolder() + "/players/").mkdirs());
		if (!create) {
			NetherCraft.log.info("Loadet ikke character fil for spiller: " + p.getName() + ".");
			NetherCraft.log.info("Prøver å lage en character fil for spiller: " + p.getName() + ".");
			fp.mkdir();
			NetherCraft.log.info("Innfører nå en sjekk om playerconfig eksisterer.");
			File fpp = (new File(this.plugin.getDataFolder() + "/players/" + p.getName() + "playerconfig.yml"));
			if (!fpp.exists()) {
				NetherCraft.log.info("Playerfil eksisterer ikke, derfor lager jeg en nå.");
				loadPlayerConfig();
				this.config = new YamlConfiguration();
				this.configFile = new File(this.plugin.getDataFolder() + "/players/" + p.getName() + "/playerconfig.yml");
				for (String key : plugin.pconfigDefaults.keySet()) {
					this.config.set(key, plugin.pconfigDefaults.get(key)); 	}
			config.save(configFile);	
			} else {
					NetherCraft.log.info("Loadet config fil for spiller: " + p.getName() + ".");
					config.load(configFile);
				}
				
			
		
		
			}
		}
	}

@EventHandler(priority = EventPriority.HIGHEST )
public void onWeatherChange( WeatherChangeEvent event )
{
	if( !event.isCancelled( ) && event.toWeatherState()) {			
		event.setCancelled( true );
	}		}	


@EventHandler(priority = EventPriority.HIGHEST )
public void onThunderChange( ThunderChangeEvent event )
{
	if( !event.isCancelled( ) && event.toThunderState( ) )			{
		event.setCancelled( true );
	}	}
@EventHandler(priority = EventPriority.NORMAL)
public void onPlayerQuit(final PlayerQuitEvent e) {
	Player p = e.getPlayer();
	e.setQuitMessage(ChatColor.RED + "- " + ChatColor.GRAY + p.getDisplayName() + ChatColor.DARK_GRAY + " / logget ut.");
}
@EventHandler(priority = EventPriority.NORMAL)
public void onBlockPlace(final BlockPlaceEvent e) {
	Player p = e.getPlayer();
	Block b = e.getBlockPlaced();
	if (b.getType() == Material.DIAMOND_BLOCK && (p.getGameMode().equals(GameMode.SURVIVAL))) {
		b.setType(Material.AIR);
		p.sendMessage(ChatColor.RED + "Denne blokken er ikke plasserbar pga sikkerhetsgrunner.");
	}
	if (e.getBlockPlaced().getType() == Material.SPONGE&& (p.getGameMode().equals(GameMode.SURVIVAL))) {
		e.getBlockPlaced().setType(Material.AIR);
		p.sendMessage(ChatColor.RED +"Denne blokken er ansatt som farlig og kan ikke plasseres.");
	}
	
}

@EventHandler(priority = EventPriority.NORMAL)
public void BlockPlaceBlock(final BlockPlaceEvent e) {
	Player p = e.getPlayer();
	if (plugin.safezoned.containsKey(p.getName())) {
		if (p.getGameMode().equals(GameMode.SURVIVAL)) {
		e.setCancelled(true);
		p.sendMessage(ChatColor.RED + "Du kan ikke griefe i safe-zone.");
	}
	
	}
}
@EventHandler(priority = EventPriority.NORMAL)
public void BlockPlaceBlock(final BlockBreakEvent e) {
	Player p = e.getPlayer();
	if (plugin.safezoned.containsKey(p.getName())) {
		e.setCancelled(true);
		p.sendMessage(ChatColor.RED + "Du kan ikke griefe i safe-zone.");
	}
	if (e.getBlock().getType() == Material.SPONGE && (p.getGameMode().equals(GameMode.SURVIVAL))) {
		e.getBlock().setType(Material.AIR);
		p.sendMessage(ChatColor.RED +"Denne blokken er ansatt som farlig og kan ikke ødelegges.");
	}
	if (e.getBlock().getType() == Material.DIAMOND_BLOCK&& (p.getGameMode().equals(GameMode.SURVIVAL))) {
		e.getBlock().setType(Material.AIR);
		p.sendMessage(ChatColor.RED + "Denne blokken er ansatt som farlig og kan ikke ødelegges.");
	}
}
@EventHandler(priority = EventPriority.NORMAL)
public void onPlayerMove(final PlayerMoveEvent e) {
	Player p = e.getPlayer();
	if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
		p.setHealth(20);
		plugin.safezoned.put(p.getName(), true);
	} else if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DIAMOND_BLOCK) {
		plugin.safezoned.remove(p.getName());
	} }
@EventHandler(priority = EventPriority.NORMAL)
public void onPlayerChat(final PlayerChatEvent e) {
	Player p = e.getPlayer();
	String msg = e.getMessage();
	e.setCancelled(true);
	if (p.isOp()) {
		plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + p.getName() + ": " + ChatColor.WHITE + msg);
		NetherCraft.log.info("[OP] " + p.getName() + ": " + msg);
	} else if (p.isWhitelisted()) {
		plugin.getServer().broadcastMessage(ChatColor.BLUE + p.getName() + ": " + ChatColor.WHITE + msg);
		NetherCraft.log.info("[Hjelper] " + p.getName() + ": " + msg);
	} else {
	plugin.getServer().broadcastMessage(ChatColor.GRAY + p.getName() + ": " + ChatColor.WHITE + msg);
	NetherCraft.log.info(p.getName() + ": " + msg);		}	}
@EventHandler(priority = EventPriority.NORMAL)
public void onPlayerInteractTest(final PlayerInteractEvent e) {
	Player p = e.getPlayer();
	if (plugin.safezoned.containsKey(p.getName())) {
		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			Location lol = p.getWorld().getSpawnLocation();
			p.teleport(lol);
			p.sendMessage(ChatColor.RED + "PvPing i spawn er forbudt.");
		} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Location lol = p.getWorld().getSpawnLocation();
			p.teleport(lol);
			p.sendMessage(ChatColor.RED + "PvPing i spawn er forbudt.");
		}	}
}
@EventHandler(priority = EventPriority.NORMAL)
public void onPlayerInteractEvent(final PlayerInteractEvent e) {
  	Player p = e.getPlayer();
ItemStack tool = p.getItemInHand();


 if (tool.getDurability() >= 16) {
         tool.setDurability((short) -1);
         p.setItemInHand(tool);



}}
}
