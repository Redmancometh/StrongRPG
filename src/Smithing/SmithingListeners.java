package Smithing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SmithingListeners implements Listener
{
    List<String> lore0 = new ArrayList<String>();
    List<String> lore1 = new ArrayList<String>();
    List<String> lore2 = new ArrayList<String>();
    List<String> lore3 = new ArrayList<String>();
    ItemStack[] options = new ItemStack[20];
    String[] names = new String[20];
    ItemMeta[] meta = new ItemMeta[20];
    Inventory Smithing = (Inventory) Bukkit.createInventory(null, 27, "Smithing Menu");
    @EventHandler
    public void openSmithingMenu(PlayerInteractEvent e)
    {
	if(e.getAction()==Action.RIGHT_CLICK_BLOCK&&e.getClickedBlock().getType()==Material.ANVIL)
	{
	    setData();
	    applyData();
	    e.getPlayer().openInventory(Smithing);
	}
    }
    public void setData()
    {
	names[0]="Rough Iron Sword";
	names[1]="Low-Quality Tempered Iron Sword";
	names[2]="Low-Quality Plated Iron Sword";
	options[0] = new ItemStack(Material.IRON_SWORD);
	options[1] = new ItemStack(Material.IRON_SWORD);
	options[2] = new ItemStack(Material.IRON_SWORD);
	
	lore0.add("Damage: 4-7");
	lore0.add(ChatColor.BLUE+"Sell Value: 15");
	lore0.add(ChatColor.BLUE+"15 Iron Ingots");//Level added to lore after
	
	lore1.add("Damage: 5-9");
	lore1.add(ChatColor.BLUE+"Sell Value: 25");
	lore1.add(ChatColor.BLUE+"20 Iron Ingots 5 Gold Ingots");

	lore2.add("Damage: 7-12");
	lore2.add(ChatColor.BLUE+"Sell Value: 40");
	lore2.add(ChatColor.BLUE+"10 Steel Ingots 10 Gold Ingots");
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
	Smithing.setItem(0, options[0]);
	Smithing.setItem(1, options[1]);
	Smithing.setItem(2, options[2]);
    }
}
