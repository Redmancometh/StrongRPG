package Alchemy;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
public class HealingPotion
{
    public static final HealingPotion minor = new HealingPotion(35, "Minor Healing Potion",5);
    public static final HealingPotion Light = new HealingPotion(35, "Light Healing Potion",5);
    public static final HealingPotion Standard = new HealingPotion(35, "Minor Healing Potion",5);
    public static final HealingPotion Major = new HealingPotion(35, "Minor Healing Potion",5);
    public static final HealingPotion Superior = new HealingPotion(35, "Minor Healing Potion",5);
    public static final HealingPotion Super = new HealingPotion(35, "Minor Healing Potion",5);
    public ItemStack potionItem;
    public ItemMeta potionMeta;
    public int Healing;
    public String itemname;
    public int value;
    public List<String> lore = new ArrayList<String>();
    HealingPotion(int Healing, String itemname, int value)
    {
	potionItem = new ItemStack(Material.POTION);
	potionMeta = potionItem.getItemMeta();
	potionMeta.setDisplayName(itemname);
	lore.add("Heals "+itemname+" damage");
	lore.add(ChatColor.BLUE+"Sell Value: "+value);
	potionMeta.setLore(lore);
	this.Healing=Healing;
	this.itemname=itemname;
	this.value=value;
	potionItem.setItemMeta(potionMeta);
    }
}

