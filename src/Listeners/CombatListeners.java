package Listeners;

import me.confuser.barapi.BarAPI;
import net.citizensnpcs.api.CitizensAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;

import BlackLance.RPGPlayer;
import Storage.RPGPlayers;
import Util.BLMUtil;
import Util.CombatUtil;

public class CombatListeners implements Listener
{
    private JavaPlugin pl;

    public CombatListeners(JavaPlugin pl)
    {
	this.pl = pl;
    }
    // This Event
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void mobHittingPlayer(EntityDamageByEntityEvent event) throws ClassNotFoundException, NoSuchMethodException, SecurityException
    {
	event.setDamage(0);
	// Entity hit is player
	if (event.getEntity() instanceof Player && !(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())))
	{
	    if (event.getDamager() instanceof Player && !(CitizensAPI.getNPCRegistry().isNPC(event.getDamager())))
	    {
		event.setCancelled(true);
	    }
	    else
	    {
		CombatUtil cu = new CombatUtil();
		final Player p = (Player) event.getEntity();
		LivingEntity paul = null;
		// Check if it's a mob
		if (event.getDamager() instanceof LivingEntity)
		{
		    paul = (LivingEntity) event.getDamager();
		}
		else
		{
		    // Not a mob, check for projectile
		    if (event.getDamager() instanceof Projectile)
		    {
			Projectile pj = (Projectile) event.getDamager();
			paul = pj.getShooter();
		    }
		    // Neither a mob nor projectile, cancel
		    else
		    {
			event.setCancelled(true);
		    }
		    if (paul == null)
		    {
			event.setCancelled(true);
		    }
		    // Calculate Damage
		}
		if (paul != null)
		{
		    if (paul instanceof LivingEntity)
		    {
			int hit = (int) (cu.getmobDamage(paul) - cu.getAbsorption(p));
			event.setDamage(0);
			RPGPlayer rp = RPGPlayers.getRPGPlayer(p);
			rp.damagePlayer((int) hit, p);
			float health = ((float) rp.getHealth() / (float) rp.getMaxHealth());
			String healthdisplay = ChatColor.DARK_GREEN + "Health:  " + ChatColor.DARK_RED + rp.getHealth() + "/" + rp.getMaxHealth();
			BarAPI.setMessage(p, healthdisplay, health * 100);
		    }
		}
	    }
	}
    }
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void playerHittingMob(EntityDamageByEntityEvent event)
    {
	event.setDamage(0);
	// Entity being hit is not a player, but is a living entity
	CombatUtil cu = new CombatUtil();
	// The entity being hit is not a player
	if (!(event.getEntity() instanceof Player))
	{
	    Player p = null;
	    LivingEntity paul = (LivingEntity) event.getEntity();
	    if (event.getDamager() instanceof Player)
	    {
		p = (Player) event.getDamager();
	    }
	    // Damager is not a player
	    else
	    {
		// Damager is not a player, check for projectile
		if (event.getDamager() instanceof Projectile)
		{
		    Projectile pj = (Projectile) event.getDamager();
		    // Damager is a projectile, find out if the shooter is a
		    // player
		    if (pj.getShooter() instanceof Player)
		    {
			p = (Player) pj.getShooter();
		    }
		}
		else
		{
		    event.setCancelled(true);
		}
		if (p == null)
		{
		    event.setCancelled(true);
		}
	    }
	    double hit = cu.generateHit(p);
	    if (p.isInsideVehicle())
	    {
		p.getVehicle().remove();
	    }
	    else
	    {
		if (hit > 0)
		{
		    BLMUtil blu = new BLMUtil();
		    blu.dealDamage(paul, (int) hit, p);
		    holoStuff((int) hit, paul, p);
		}
	    }
	}
	// Entity Being Damaged is a Player or Player-NPC
	else
	{
	    // Check for projectile
	    if (event.getDamager() instanceof Projectile)
	    {
		Projectile pj = (Projectile) event.getDamager();
		LivingEntity ps = (LivingEntity) pj.getShooter();
		if (ps instanceof Player && !(CitizensAPI.getNPCRegistry().isNPC(ps)))
		{
		    Player p = (Player) ps;
		    if (CitizensAPI.getNPCRegistry().isNPC(event.getEntity()))
		    {
			double hit = cu.generateHit(p);
			LivingEntity paul = (LivingEntity) event.getEntity();
			if (hit > 0)
			{
			    BLMUtil blu = new BLMUtil();
			    blu.dealDamage(paul, (int) hit, p);
			    holoStuff((int) hit, paul, p);
			}
		    }
		    if (!(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())))
		    {
			p.sendMessage(ChatColor.RED + "You can't PvP here!");
		    }
		}
	    }
	    if (event.getDamager() instanceof Player && !(CitizensAPI.getNPCRegistry().isNPC(event.getDamager())))
	    {
		Player p = (Player) event.getDamager();
		if (CitizensAPI.getNPCRegistry().isNPC(event.getEntity()))
		{
		    double hit = cu.generateHit(p);
		    if (hit > 0)
		    {
			LivingEntity paul = (LivingEntity) event.getEntity();
			BLMUtil blu = new BLMUtil();
			blu.dealDamage(paul, (int) hit, p);
			holoStuff((int) hit, paul, p);
		    }
		}
		if (!(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())))
		{
		    p.sendMessage(ChatColor.RED + "You can't PvP here!");
		}
	    }
	}
    }

    public void holoStuff(int hit, LivingEntity paul, Player p)
    {
	Location loc1 = paul.getLocation();
	loc1.setY(loc1.getY() + 2);
	String holohit = Integer.toString((int) hit);
	final Hologram hologram = HolographicDisplaysAPI.createIndividualHologram(pl, loc1, p, ChatColor.RED + Integer.toString((int) hit));
	hologram.setLocation(paul.getLocation());
	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	scheduler.scheduleSyncDelayedTask(pl, new Runnable()
	{
	    public void run()
	    {
		hologram.delete();
	    }
	}, 15);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(PlayerItemBreakEvent event)
    {
	event.getBrokenItem().setAmount(1);
    }
}