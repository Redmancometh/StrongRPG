package Alchemy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HerbloreListeners implements Listener
{
    List<String> lore0 = new ArrayList<String>();
    List<String> lore1 = new ArrayList<String>();
    List<String> lore2 = new ArrayList<String>();
    List<String> lore3 = new ArrayList<String>();
    ItemStack[] options = new ItemStack[20];
    String[] names = new String[20];
    ItemMeta[] meta = new ItemMeta[20];
    Inventory Herblore = (Inventory) Bukkit.createInventory(null, 27, "Herblore Menu");
    @EventHandler
    public void potionMenu(PlayerInteractEvent e)
    {
	if(e.getItem().getType()==Material.POTION&&!e.getItem().hasItemMeta())
	{
	    e.setCancelled(true);
	    setData();
	    applyData();
	    e.getPlayer().openInventory(Herblore);
	    lore0.clear();lore1.clear();lore2.clear();
	}
    }
    public void setData()
    {
	names[0]="Marigold Potion";
	names[1]="Silver Sumac Potion";
	names[2]="Hawkweed Potion";
	options[0] = new ItemStack(Material.POTION);
	options[1] = new ItemStack(Material.POTION);
	options[2] = new ItemStack(Material.POTION);
	lore0.add(0,ChatColor.BLUE+"Requires 3 Marigold");
	lore1.add(0,ChatColor.BLUE+"Requires 3 Silver Sumac");
	lore2.add(0,ChatColor.BLUE+"Requires 5 Hawkweed");
    }
    public void applyData()
    {
	for(int x = 0; x<3; x++)
	{
	    meta[x]=options[x].getItemMeta();
	}
	meta[0].setLore(lore0);
	meta[1].setLore(lore1);
	meta[2].setLore(lore2);
	for(int x = 0; x<3; x++)
	{
	    meta[x].setDisplayName(names[x]);
	    options[x].setItemMeta(meta[x]);
	}
	Herblore.setItem(0, options[0]);
	Herblore.setItem(1, options[1]);
	Herblore.setItem(2, options[2]);
    }
    
}
