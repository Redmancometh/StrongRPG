package Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil
{
    public static int getAmountInInventory(Player player, Material type)
    {
	int total = 0;
	for (ItemStack is : player.getInventory())
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
}
