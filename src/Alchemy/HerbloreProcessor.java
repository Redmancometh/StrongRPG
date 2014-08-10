package Alchemy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Util.ItemUtil;

public class HerbloreProcessor
{
    public static void makeMixture(Player p, ItemStack i, Inventory inv)
    {
	String itemname = i.getItemMeta().getDisplayName();
	switch (itemname)
	{
	case "Marigold Potion":
	    makeMarigold(p,inv);
	    break;
	case "Silver Sumac Potion":
	    makeSilverSumac(inv);
	    break;
	case "Hawkweed potion":
	    makeHawkWeed(inv);
	    break;
	}
    }
    public static void makeMarigold(Player p, Inventory inv)
    {
	if(ItemUtil.getAmountDisplayName("Marigold", inv)>=3)
	{
	    p.getInventory().removeItem(new ItemStack(Material.DOUBLE_PLANT));
	    Bukkit.broadcastMessage("TYPE : "+p.getInventory().getItem(0).getType());
	    p.updateInventory();
	}
    }
    public static void makeSilverSumac(Inventory inv)
    {
	if(ItemUtil.getAmountDisplayName("Silver Sumac", inv)>=3)
	{
	    
	}
    }
    public static void makeHawkWeed(Inventory inv)
    {
	if(ItemUtil.getAmountDisplayName("Hawkweed", inv)>=5)
	{
	    
	}
    }
}
