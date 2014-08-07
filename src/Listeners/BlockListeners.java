package Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import BlackLance.BlackLance;
import Util.Harvest;
import Util.Mine;

public class BlockListeners implements Listener
{
	static BlackLance blacklance;
	
	public BlockListeners(BlackLance blacklance)
	{
		this.blacklance=blacklance;
	}
	@EventHandler
	public void onMineBlock(final BlockBreakEvent event) 
	{
		Block b = event.getBlock();
		Material m = b.getType();
		Player p = event.getPlayer();
		if(m==Material.IRON_ORE||m==Material.GOLD_ORE||m==Material.COAL_ORE||m==Material.EMERALD_ORE||m==Material.DIAMOND_ORE||m==Material.REDSTONE_ORE||m==Material.LAPIS_ORE||m==Material.STONE)
		{	
			event.setCancelled(true);
			Mine mine = new Mine(m, p, b, blacklance);
			mine.processEvent(m);
		}
	}
	@EventHandler 
	public void harvestHay(PlayerInteractEvent event)
	{
		Player p = event.getPlayer();
		Block b = event.getClickedBlock();
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if(event.getClickedBlock().getType()==Material.HAY_BLOCK)
			{
				if(p.getItemInHand()!=null&&p.getItemInHand().getType()==Material.STONE_HOE)
				{
					Harvest h = new Harvest(b,p, blacklance);
				}
				else{p.sendMessage(ChatColor.RED+"You need a hoe to harvest this...talk to the crasmeer stable hand!");}
			}
		}
	}
	@EventHandler
	public void stopRain(WeatherChangeEvent event)
	{
		event.setCancelled(true);
	}
}
