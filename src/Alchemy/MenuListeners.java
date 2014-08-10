package Alchemy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListeners implements Listener
{
    @EventHandler
    public void mixtureClick(InventoryClickEvent e)
    {
	if (e.getInventory().getName().equals("Herblore Menu"))
	{
	    e.setCancelled(true);
	    Player p =(Player)e.getWhoClicked();
	    HerbloreProcessor.makeMixture(p,e.getCurrentItem(),p.getInventory());
	    e.getWhoClicked().closeInventory();
	}
    }
    @EventHandler
    public void alchemyClick(InventoryClickEvent e)
    {
	if (e.getInventory().getName().equals("Alchemy Menu"))
	{
	    e.setCancelled(true);
	    AlchemyProcessor.makePotion(e.getCurrentItem(), e.getWhoClicked().getInventory());
	    e.getWhoClicked().closeInventory();
	}
    }
}
