package Listeners;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.citizensnpcs.api.event.NPCDeathEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.example.EntityHider;
import com.comphenix.example.EntityHider.Policy;

import BlackLance.BlackLance;
import BlackLance.RPGPlayer;
import BlackLance.RPGWeapon;
import Storage.RPGPlayers;
import Util.CombatUtil;
import Util.DropUtil;

public class DropListeners implements Listener
{
    private ItemStack drop = new ItemStack(Material.WOOD);
    private ItemMeta dropmeta = drop.getItemMeta();
    private int level;
    private BlackLance blacklance;

    public DropListeners(BlackLance blacklance)
    {
	this.blacklance = blacklance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void stopPickup(PlayerPickupItemEvent e) throws SQLException
    {
	final RPGPlayer rp = RPGPlayers.getRPGPlayer(e.getPlayer());
	String name = e.getItem().getItemStack().getItemMeta().getDisplayName();
	List<String> lorecompare = new ArrayList<String>();
	lorecompare = e.getItem().getItemStack().getItemMeta().getLore();
	if (e.getItem().getItemStack().hasItemMeta())
	{
	    if (lorecompare.size() >= 3)
	    {
		if (!lorecompare.get(2).equals("PlayerI" + e.getPlayer().getUniqueId()))
		{
		    e.setCancelled(true);
		}
		else
		{
		    lorecompare.remove(2);
		    dropmeta.setLore(lorecompare);
		    dropmeta.setDisplayName(name);
		    e.getItem().getItemStack().setItemMeta(dropmeta);
		}
	    }
	    else
	    {
		if (lorecompare.size() == 2)
		{
		    if (!lorecompare.get(1).contains("Value"))
		    {
			if (!lorecompare.get(1).equals("PlayerI" + e.getPlayer().getUniqueId()))
			{
			    e.setCancelled(true);
			}
			else
			{
			    lorecompare.remove(1);
			    dropmeta.setLore(lorecompare);
			    dropmeta.setDisplayName(name);
			    e.getItem().getItemStack().setItemMeta(dropmeta);
			}
		    }
		}
	    }
	    new BukkitRunnable()
	    {
		public void run()
		{
		    try{RPGWeapon.makeWeapons(rp);}
		    catch (SQLException e){e.printStackTrace();}
		}
	    }.runTaskLater(blacklance, 5);
	}
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void stopXP(PlayerDeathEvent event)
    {
	event.setDroppedExp(0);
    }

    @EventHandler
    public void stopXP2(NPCDeathEvent event)
    {
	if (event.getDroppedExp() > 0)
	{
	    Bukkit.broadcastMessage("HIGH XP!");
	    event.setDroppedExp(0);
	}
    }
}
