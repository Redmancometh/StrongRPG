package Listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import BlackLance.RPGPlayer;
import Storage.DataGetter;
import Storage.DataSetter;
import Storage.RPGPlayers;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.trc202.CombatTag.CombatTag;
import com.trc202.CombatTagApi.CombatTagApi;
public class PlayerListeners implements Listener 
{
	Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	private JavaPlugin pl;
	public ItemStack coinpurse = new ItemStack(Material.GHAST_TEAR);
	public ItemMeta cpmeta = coinpurse.getItemMeta();
	List<String> coinlore = new ArrayList<String>();
	private File configFile;
	private DataGetter dg;
	private DataSetter ds;
	public PlayerListeners(JavaPlugin pl, File configFile)
	{
		this.pl=pl;
		this.configFile=configFile;
		coinlore.add("Click this to check how many coins you have!");
		cpmeta.setDisplayName(ChatColor.GOLD+"Coin Purse");
		cpmeta.setLore(coinlore);
		coinpurse.setItemMeta(cpmeta);
		coinlore.clear();
		this.ds = new DataSetter(pl);
		this.dg = new DataGetter(pl);
	}
	@EventHandler
	public void createRPGPlayer(final PlayerLoginEvent event) throws Exception
	{
		final Player p = event.getPlayer();
		if(RPGPlayers.getRPGPlayer(p)==null)
		{
			final RPGPlayer rp = new RPGPlayer(pl);
			RPGPlayers.addRPGPlayer(p, rp);
			rp.setHealth(p);
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	        scheduler.scheduleSyncRepeatingTask(pl, new Runnable() 
	        {
	            public void run() 
	            {
	            	float health = (rp.getHealth()/rp.getMaxHealth());
	            	String healthdisplay = ChatColor.DARK_GREEN+"Health:  "+ChatColor.DARK_RED+rp.getHealth()+"/"+rp.getMaxHealth();
	        		if(!p.isDead())
	        		{
	        			rp.healPlayer(((p.getLevel())+2), p);
	        			p.setHealth(rp.getHealth()/rp.getMaxHealth()*20);
	        		}
	        	}
	        }, 10, 65);
		}
			if(event.getPlayer()!=null)
			{
				if(!dg.PlayerExists(event.getPlayer()))
				{
					ds.initialAdd(event.getPlayer());
				}
				User u = ess.getUser(event.getPlayer());
				if(!event.getPlayer().getInventory().contains(coinpurse))
				{
					Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
					{
						public void run() 
						{
								event.getPlayer().getInventory().setItem(8, coinpurse);
						}
				});
			}
			}
	}
	@EventHandler
	public void returnHome(PlayerRespawnEvent event) throws Exception
	{
		final User u = ess.getUser(event.getPlayer());
		final Player p = event.getPlayer();
		RPGPlayer rp = RPGPlayers.getRPGPlayer(p);
		rp.setHealth(p);
		if(u.getHome("home")!=null)
		{
			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
			{
		    	public void run() 
		    	{
		    		coinlore.add("Click this to check how many coins you have!");
		    		cpmeta.setDisplayName(ChatColor.GOLD+"Coin Purse");
		    		cpmeta.setLore(coinlore);
		    		coinpurse.setItemMeta(cpmeta);
		    		if(!p.getInventory().contains(coinpurse))
		    		{
		    			p.getInventory().setItem(8, coinpurse);
		    		}
		    		coinlore.clear();
		    		try 
		    		{
		    			Location loc1 = u.getHome("home");
		    			loc1.setY(loc1.getY()+2);
		    			p.teleport(loc1);
		    		} 
		    		catch (Exception e)
		    		{
		    			e.addSuppressed(e);
		    		}
		    	}},5);
		}
	}
	@EventHandler
	public void fuckAnvils(InventoryOpenEvent event) 
	{
		if(event.getInventory().getType()==InventoryType.ANVIL)
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void purseDrop(PlayerDropItemEvent e)
	{
		if(e.getItemDrop().getItemStack().getType()==Material.GHAST_TEAR)
		{
			e.getPlayer().getInventory().setItem(8, e.getItemDrop().getItemStack());
			e.setCancelled(true);
		}
	}
	@EventHandler 
	public void chatFormat(AsyncPlayerChatEvent event)
	{
		Player p = event.getPlayer();
		int level = p.getLevel();
		event.setFormat("["+ChatColor.BLUE+level+ChatColor.RESET+"] "+ChatColor.GRAY+"%s: %s ");
	}
	@EventHandler
	public void purseClick(InventoryClickEvent event)
	{
		if(event.getSlot()==8){event.setCancelled(true);}
	}
	@EventHandler
	public void stopFire(EntityCombustEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			event.setCancelled(true);
		}
	}
	
}
