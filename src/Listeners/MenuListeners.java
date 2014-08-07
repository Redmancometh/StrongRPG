package Listeners;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import Util.MerchantUtil;


public class MenuListeners implements Listener
{
	String[] names;
	JavaPlugin plugin = null;
	static ItemStack clicked = new ItemStack(Material.WOOD);
	boolean needsclear = false;
	public MenuListeners(JavaPlugin plugin)
	{
		this.plugin=plugin;
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClick(InventoryClickEvent event) throws UserDoesNotExistException 
	{
		Player pcoin = (Player) event.getWhoClicked();
		if(event.getCurrentItem()!=null)
		{
			if(event.getCurrentItem().hasItemMeta())
			{
				final Inventory inventory = event.getInventory();
				if(event.getCurrentItem().getItemMeta().getDisplayName()!=null)
				{
					if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD+"Coin Purse"))
					{
						event.setCancelled(true);
						pcoin.closeInventory();
						BigDecimal bal = Economy.getMoneyExact(pcoin.getName());
						pcoin.sendMessage(ChatColor.GOLD+"You currently have "+bal+" coins!");
						event.setCancelled(true);
					}
				}
			   		if(inventory.getName().contains("Shop"))
			   		{
			   			clicked = event.getCurrentItem();
			   			event.setCancelled(true);
			   			final Player p = (Player) event.getWhoClicked();
			   			if(clicked.hasItemMeta())
			   			{
			   				if(clicked.getItemMeta().hasDisplayName())
			   				{
			   					if(clicked.getItemMeta().getDisplayName().contains("Coins"))
			   					{
			   						MerchantUtil mutil = new MerchantUtil(clicked,p);
			   						try{mutil.Buy(mutil.getClicked(), mutil.getClicker());}
			   						catch (NoLoanPermittedException e){p.sendMessage(ChatColor.RED+"Sorry you don't have enough money bro!");}
			   						catch (UserDoesNotExistException e){e.printStackTrace();}
			   					}
			   				}
			   				if(clicked.hasItemMeta())
			   				{
			   					if(clicked.getItemMeta().hasDisplayName())
			   					{
			   						if(!clicked.getItemMeta().getDisplayName().contains("Coins"))
			   						{
			   							if(clicked.getItemMeta().getLore().size()>1)
			   							{
			   								if(clicked.getItemMeta().getLore().get(1).contains("Sell Value"))
			   								{
			   									MerchantUtil mutil = new MerchantUtil(clicked,p);
			   									try{mutil.sell(mutil.getClicked(), mutil.getClicker(), event.getSlot());}
			   									catch (NumberFormatException e){p.sendMessage(ChatColor.RED+"You cannot sell this item");}
			   									catch (NoLoanPermittedException e){p.sendMessage("Something weird happened");}
			   									catch (UserDoesNotExistException e){}
			   								}
			   								else
			   								{
			   									p.sendMessage(ChatColor.DARK_RED+"You cannot sell this item!");
			   								}
			   							}
			   							else
			   							{
			   								p.sendMessage(ChatColor.DARK_RED+"You cannot sell this item!");
			   							}
			   						}
			   					}
			   				}
			   				
								}
			   		else	
			   		{	
			   			clicked = event.getCursor(); 
			   			if(clicked.hasItemMeta())
			   			{
			   				if(clicked.getItemMeta().getDisplayName().contains("Coins"))
			   				{
			   					event.getWhoClicked().closeInventory();
			   					needsclear=true;
			   				}
			   				else{needsclear=false;}
			   			}
			   		}
			   }
			}
		}
	}
	
	@EventHandler
    public void onItemDrop(InventoryDragEvent e)
    {
		if(e.getCursor()!=null)
		{
			if(e.getCursor().hasItemMeta())
			{
				if(e.getCursor().getItemMeta().getDisplayName().equals(ChatColor.GOLD+"Coin Purse"))
        		{
        			e.setCancelled(true);
        		}
			}
		}
    }
}
			

