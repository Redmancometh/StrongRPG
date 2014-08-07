package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import BlackLance.BlackLance;
import Storage.RPGPlayers;

import com.comphenix.example.EntityHider;
import com.comphenix.example.EntityHider.Policy;

public class Harvest 
{
	List<String> lore = new ArrayList<String>();
	ItemMeta harvestmeta;
	String harvestname = "";
	static HashMap<Location, Material> regen = new HashMap();
	Block b;
	
	public Harvest(Block b, Player p, BlackLance blacklance)
	{
		this.b=b;
		harvestProcessor(b,p,blacklance);
	}
	public void harvestProcessor(Block b, Player p, BlackLance blacklance)
	{
		if(b.getType()==Material.HAY_BLOCK){harvestHay(b,p,blacklance);}
	}
	public void harvestHay(final Block b, Player p, BlackLance blacklance)
	{
		DropUtil du = new DropUtil(blacklance);
		ItemStack harvested = new ItemStack(Material.HAY_BLOCK);
		harvestmeta = harvested.getItemMeta();
		harvestmeta.setDisplayName("Hay");
		lore.add("Good horse food");
		lore.add(ChatColor.BLUE+"Sell Value: 1");
		lore.add("PlayerI"+p.getUniqueId());
		harvestmeta.setLore(lore);
		harvested.setItemMeta(harvestmeta);
		b.setType(Material.AIR);
		CraftItem ci = (CraftItem)p.getWorld().dropItem(b.getLocation(), harvested);
		du.hide(p, ci);
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		RPGPlayers.addXP(p, 5);
		final Block b2=b;
        scheduler.scheduleSyncDelayedTask(blacklance, new Runnable() 
        {
            public void run() 
            {
        		if(!regen.containsKey(b.getLocation())){regen.put(b.getLocation(), b.getType());}
            	b2.setType(Material.HAY_BLOCK);
            }
        }, 600L);

	}
	public static HashMap<Location, Material> getRegen()
    {
		return regen;
    	
    }

}
