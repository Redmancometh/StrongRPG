package Wands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class WandProcessor
{
    JavaPlugin plugin;
    ItemStack wand;
    PlayerInteractEvent event;
    static HashMap<Player, Boolean> casters = new HashMap();

    public WandProcessor(ItemStack wand, PlayerInteractEvent event, JavaPlugin plugin)
    {
	this.event = event;
	this.wand = wand;
	this.plugin = plugin;
	if (wand.getItemMeta().getDisplayName().contains("Wand"))
	{
	    cast();
	}
    }

    public void cast()
    {
	final Player p = this.event.getPlayer();
	if (!casters.containsKey(p) || casters.get(p) == false)
	{
	    casters.put(p, true);
	    Location loc1 = p.getEyeLocation();
	    p.sendMessage(ChatColor.GOLD + "Casting..");
	    new BukkitRunnable()
	    {
		public void run()
		{
		    p.getLocation().getDirection().normalize().multiply(2);
		    Fireball f = p.launchProjectile(Fireball.class);
		    f.setShooter(p);
		    f.setYield(0);
		    casters.put(p, false);
		}
	    }.runTaskLater(plugin, 27);
	}
	else
	{
	    p.sendMessage(ChatColor.RED + "You're already casting that spell");
	    return;
	}
    }
}
