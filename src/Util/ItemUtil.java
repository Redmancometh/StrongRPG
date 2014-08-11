package Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil
{
    public static int getAmountTypeInInventory(Inventory inv, Material type)
    {
	int total = 0;
	for (ItemStack is : inv)
	{
	    if (is != null)
	    {
		if (is.getType() == type&&is.getEnchantments().size()<1&&is.getDurability()==0)
		{
		    total += is.getAmount();
		}
	    }
	}
	return total;
    }
    public static int getAmountDisplayName(String displayname, Inventory inv)
    {
	int total = 0;
	for (ItemStack is : inv)
	{
	    if(is!=null)
	    {
		if(is.hasItemMeta())
		{
		    ItemMeta imeta = is.getItemMeta();
		    if(imeta.hasDisplayName())
		    {
			if(displayname.equals(imeta.getDisplayName()))
			{
			    Bukkit.broadcastMessage("Get amount "+is.getAmount());
			    total+=is.getAmount();
			}
		    }
		}
	    }
	}
	return total;
    }
    public static void removeByName(Inventory inv, String name, int quantity)
    {
	for(ItemStack i : inv)
	{
	    if(i!=null)
	    {
		if(i.hasItemMeta())
		{
		    ItemMeta imeta = i.getItemMeta();
		    if(imeta.hasDisplayName())
		    {
			if(name.equals(imeta.getDisplayName()))
			{
			    if(i.getAmount()>quantity)
			    {
				i.setAmount(i.getAmount()-quantity);
			    }
			    else{inv.remove(i);}
			}
		    }
		}
	    }
	}
    }
}
