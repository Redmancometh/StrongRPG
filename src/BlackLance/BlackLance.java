package BlackLance;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Listeners.*;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.earth2me.essentials.Essentials;

import Objectives.ObjectiveProcessor;
import Storage.DataGetter;
import Storage.DataSetter;
import Storage.RPGPlayers;
import Util.DropUtil;
import Util.Harvest;
import Util.Horses;
import Util.MerchantUtil;
import Util.Mine;

public class BlackLance extends JavaPlugin
{
	private Plugin plugin = this;
    private FileConfiguration config;
	private Configuration configs;
	private JavaPlugin jplugin = this;
	private ItemStack saddle = new ItemStack(Material.SADDLE);
	private ItemMeta imeta = saddle.getItemMeta();
	private List<String> lore = new ArrayList<String>();
	public void onEnable()
	{
		String[] header = {"TestHeader!"};
		File configFile = new File(this.getDataFolder(), "config.yml");
		PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents(new CombatListeners(this), this);
	    pm.registerEvents(new PlayerListeners(this, configFile), this);
	    pm.registerEvents(new ExperienceListeners(), this);
	    pm.registerEvents(new ObjectiveProcessor(this), this);
	    pm.registerEvents(new DropListeners(this), this);
	    pm.registerEvents(new NPCListeners(this), this);
	    pm.registerEvents(new BlockListeners(this), this);
	    pm.registerEvents(new MenuListeners(this), this);
	    pm.registerEvents(new ItemListeners(this), this);
	    pm.registerEvents(new ConsumableListener(this), this);
        pm.registerEvents(new TradeListener(), this);
		net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(blm.class).withName("blm"));	
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() 
        {
            public void run() 
            {
            	Bukkit.broadcastMessage(ChatColor.GOLD+"The RPG plugin is reloading in 10 seconds, expect a moment of lag!");
        		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(plugin, new Runnable() 
                {
                    public void run() 
                    {
                    	getServer().getPluginManager().disablePlugin(plugin);
                    	getServer().getPluginManager().enablePlugin(plugin);
                    }
                }, 200L);
            }
        }, 36000, 36000);
        HashMap<Location,Material> toRegen = Mine.getRegen();
		Iterable<Location> locar = toRegen.keySet();
		for(locar.iterator(); locar.iterator().hasNext();)
		{
			Location l1 = locar.iterator().next();
			Bukkit.broadcastMessage("Loc: "+l1.getX());
			l1.getBlock().setType(toRegen.get(l1));
		}
	    plugin = this;
	    if(!configFile.exists())
		{
			this.saveDefaultConfig();
		}
	}
	public void onReload()
	{
		net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(blm.class).withName("blm"));	
		saveIt();

	}
	public void onDisable()
	{
		HashMap<Location,Material> toRegen = Mine.getRegen();
		Iterator iter = toRegen.keySet().iterator();
		for(Map.Entry<Location, Material> entry : toRegen.entrySet()){entry.getKey().getBlock().setType(entry.getValue());}
		saveIt();
		
        HashMap<Location,Material> toRegen2 = Harvest.getRegen();
		Iterable<Location> locar2 = toRegen2.keySet();
		for(locar2.iterator(); locar2.iterator().hasNext();)
		{
			Location l1 = locar2.iterator().next();
			Bukkit.broadcastMessage("Loc: "+l1.getX());
			l1.getBlock().setType(toRegen.get(l1));
		}
	}
	public void saveIt()
	{
		this.saveConfig();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p = (Player)sender;
    	if(cmd.getName().equalsIgnoreCase("blreload")&&(sender instanceof Player))
    	{
    		if(p.isOp())
    		{
    			PluginManager pm = getServer().getPluginManager();
    			saveIt();
    			pm.disablePlugin(this);
    			pm.enablePlugin(this);
    			p.sendMessage(ChatColor.GOLD+"BlackLance has been reloaded");
    		}
    		else
    		{
    			p.sendMessage("Stop Messing With Our Shit Please, thanks");
    		}
    	}
    	if(cmd.getName().equalsIgnoreCase("blevel")&&(sender instanceof Player)&&p.isOp())
    	{
			NPC ThisNPC = ((Citizens)this.getServer().getPluginManager().getPlugin("Citizens")).getNPCSelector().getSelected(sender);
			blm blminstance = ThisNPC.getTrait(blm.class);
			if(args[0].equalsIgnoreCase("set")&&args.length==2)
			{
				blminstance.setLevel(Integer.parseInt(args[1]));
				p.sendMessage(ChatColor.GOLD+"[BlackLanceMobs] "+ChatColor.AQUA+"Level set to "+args[1]);
			}
			if(args[0].equalsIgnoreCase("get"))
			{
				p.sendMessage(ChatColor.GOLD+"[BlackLanceMobs] "+ChatColor.AQUA+"This NPC is level "+blminstance.getLevel());
			}
			if(args[0].equalsIgnoreCase("health"))
			{
				p.sendMessage(ChatColor.GOLD+"[BlackLanceMobs] "+ChatColor.AQUA+"This NPC has "+blminstance.getHealth()+" Health");
			}
			if(args[0].equalsIgnoreCase("mhealth"))
			{
				p.sendMessage(ChatColor.GOLD+"[BlackLanceMobs] "+ChatColor.AQUA+"This NPC has "+blminstance.getmHealth()+" Health");
			}	
    	}
    	if(cmd.getName().equalsIgnoreCase("horse")&&(sender instanceof Player)&&args.length==2)
    	{
    		if(p.hasPermission("bl.dhorse"))
    		{
    			if(args[1].equalsIgnoreCase("skeleton"))
    			{
    				imeta.setDisplayName("Skeletal Reins");
    				lore.add("Summons a skeletal charger");
    				imeta.setLore(lore);
    				lore.clear();
    				saddle.setItemMeta(imeta);
    				p.getInventory().addItem(saddle);
    			}
    			if(args[1].equalsIgnoreCase("zombie"))
    			{
    				imeta.setDisplayName("Deathcharger Reins");
    				lore.add("Summons a Deathcharger");
    				imeta.setLore(lore);
    				lore.clear();
    				saddle.setItemMeta(imeta);
    				p.getInventory().addItem(saddle);
    			}
    		}
    	}
		return true;
		
	}
}