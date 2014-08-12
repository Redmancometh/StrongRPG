package Alchemy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AlchemyListeners
{
    Inventory Herblore = (Inventory) Bukkit.createInventory(null, 27, "Herblore Menu");
    @EventHandler
    public void alchMenu(PlayerInteractEvent e)
    {
	if(e.getAction()==Action.RIGHT_CLICK_BLOCK&&e.getClickedBlock().getType()==Material.ENCHANTMENT_TABLE)
	{
	    e.setCancelled(true);
	}
    }
}
