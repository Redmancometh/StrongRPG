package Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Horses 
{
	private ItemStack saddle;
	private String saddlename;
	private PlayerInteractEvent event;
	private boolean canmove=true;
	private boolean iscalling=false;
	private Location nomove;
	private JavaPlugin pl;
	PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 9999, 1);
	PotionEffect speed2 = new PotionEffect(PotionEffectType.SPEED, 9999, 3);

	public Horses(JavaPlugin pl, String saddlename, PlayerInteractEvent event)
	{
		this.pl=pl;
		this.saddlename=saddlename;
		this.event=event;
		callHorse();
	}
	public void callHorse()
	{
		final Player p = event.getPlayer(); //final could be an issue
		Bukkit.broadcastMessage("Iscalling"+iscalling);
		if(!iscalling)
		{
			final Location loc1 = p.getLocation();
			final World w = p.getWorld();
			String saddlename = p.getItemInHand().getItemMeta().getDisplayName();
			if(saddlename.contains("Basic"))
			{
    			p.sendMessage(ChatColor.GOLD+"Calling your horse..he'll be here in 5 seconds");
				nomove=p.getLocation();
				canmove=false;
				iscalling=true;
    			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
    			{
		    		public void run() 
		    		{
		    			iscalling=false;
		    			Horse h = w.spawn(loc1, Horse.class);
		    			h.setTamed(true);
		    			h.setOwner(p);
		    			h.setStyle(Style.WHITE_DOTS);
		    			h.setColor(Color.CHESTNUT);
		    			h.setVariant(Variant.HORSE);
		    			h.setAge(0);
		    			h.setCustomName("Chestnut Horse");
		    			h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
		    			h.setPassenger(p);
		    			canmove=true;
		    		}
				},60);
			}
			if(saddlename.contains("Riveted"))
			{
    			p.sendMessage(ChatColor.GOLD+"Calling your horse..he'll be here in 5 seconds..you won't be able to move!");
				canmove=false;
				iscalling=true;
				nomove=p.getLocation();
    			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
				{
		    		public void run() 
		    		{
		    			iscalling=false;
		    			Horse h = w.spawn(loc1, Horse.class);
		    			h.addPotionEffect(speed);
		    			h.setTamed(true);
		    			h.setOwner(p);
		    			h.setStyle(Style.WHITE_DOTS);
		    			h.setColor(Color.BLACK);
		    			h.setVariant(Variant.HORSE);
		    			h.setAge(0);
		    			h.setCustomName("Black Charger");
		    			h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
		    			h.setPassenger(p);
		    			canmove=true;
		    		}
				},60);
			}
			if(saddlename.contains("Sturdy"))
			{
    			p.sendMessage(ChatColor.GOLD+"Calling your horse..he'll be here in 5 seconds..you won't be able to move!");
				canmove=false;
				iscalling=true;
				nomove=p.getLocation();
    			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
				{
		    		public void run() 
		    		{
		    			iscalling=false;
		    			Horse h = w.spawn(loc1, Horse.class);
		    			h.addPotionEffect(speed2);
		    			h.setTamed(true);
		    			h.setOwner(p);
		    			h.setStyle(Style.WHITE_DOTS);
		    			h.setColor(Color.WHITE);
		    			h.setVariant(Variant.HORSE);
		    			h.setAge(0);
		    			h.setCustomName("Black Charger");
		    			h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
		    			h.setPassenger(p);
		    			canmove=true;
		    		}
				},60);
			}
			if(saddlename.contains("Skeleton"))
			{
    			p.sendMessage(ChatColor.GOLD+"Calling your horse..he'll be here in 5 seconds..you won't be able to move!");
				canmove=false;
				iscalling=true;
				nomove=p.getLocation();
    			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
				{
		    		public void run() 
		    		{
		    			iscalling=false;
		    			Horse h = w.spawn(loc1, Horse.class);
		    			h.addPotionEffect(speed2);
		    			h.setTamed(true);
		    			h.setOwner(p);
		    			h.setVariant(Variant.SKELETON_HORSE);
		    			h.setAge(0);
		    			h.setJumpStrength(50);
		    			h.setCustomName("Skeleton Charger");
		    			h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
		    			h.setPassenger(p);
		    			canmove=true;
		    		}
				},60);
			}
			if(saddlename.contains("Deathcharger"))
			{
    			p.sendMessage(ChatColor.GOLD+"Calling your horse..he'll be here in 5 seconds..you won't be able to move!");
				canmove=false;
				iscalling=true;
				nomove=p.getLocation();
    			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
				{
		    		public void run() 
		    		{
		    			iscalling=false;
		    			Horse h = w.spawn(loc1, Horse.class);
		    			h.addPotionEffect(speed2);
		    			h.setTamed(true);
		    			h.setOwner(p);
		    			h.setVariant(Variant.UNDEAD_HORSE);
		    			h.setAge(0);
		    			h.setJumpStrength(50);
		    			h.setCustomName("Zombie Charger");
		    			h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
		    			h.setPassenger(p);
		    			canmove=true;
		    		}
				},60);
			}
		}
	}
	public boolean getCanmove()
	{
		return canmove;
	}
	public boolean getIsCalling()
	{
		return iscalling;
	}
	public Location getLocation()
	{
		return nomove;
	}
}
