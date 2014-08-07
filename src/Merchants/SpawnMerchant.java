package Merchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.citizensnpcs.api.event.NPCRightClickEvent;

public class SpawnMerchant 
{
	Inventory SpawnShop = (Inventory) Bukkit.createInventory(null, 27, "Spawn Shop");
	ItemStack[] options = new ItemStack[20];
	String[] names = new String[20];
	ItemMeta[] meta = new ItemMeta[20];
	//Must be a better way to do lore
	List<String> lore0 = new ArrayList<String>();
	List<String> lore1 = new ArrayList<String>();
	List<String> lore2 = new ArrayList<String>();
	List<String> lore3 = new ArrayList<String>();
	List<String> lore4 = new ArrayList<String>();
	List<String> lore5 = new ArrayList<String>();
	List<String> lore6 = new ArrayList<String>();
	List<String> lore7 = new ArrayList<String>();

	private NPCRightClickEvent event;
	public SpawnMerchant(NPCRightClickEvent event)
	{
		this.event=event;
		setData();
		applyData();
	}
	public void openInventory(NPCRightClickEvent event)
	{
		event.getClicker().openInventory(SpawnShop);
	}
	public void setData()
	{
		names[0]="Minor Healing Potion - 10 Steel Coins";
		names[1]="Crummy Iron Sword - 20 Steel Coins";
		names[2]="Basic Iron Sword - 80 Steel Coins";
		names[3]="Low Quality Leather Coif - 20 Steel Coins";
		names[4]="Drunk Made Leather Chaps - 20 Steel Coins";
		names[5]="Basic Leather Boots - 20 Steel Coins";
		names[6]="Cracked Leather Body Armor - 30 Steel Coins";
		names[7]="Wand - 30 Steel Coins";

		options[0] = new ItemStack(Material.POTION);
		options[1] = new ItemStack(Material.IRON_SWORD);
		options[2] = new ItemStack(Material.IRON_SWORD);
		options[3] = new ItemStack(Material.LEATHER_HELMET);
		options[4] = new ItemStack(Material.LEATHER_LEGGINGS);
		options[5] = new ItemStack(Material.LEATHER_BOOTS);
		options[6] = new ItemStack(Material.LEATHER_CHESTPLATE);
		options[7] = new ItemStack(Material.STICK);

		lore0.add("Heals Minor Damage");
		lore0.add(ChatColor.BLUE+"Sell Value: 3");
		
		lore1.add("Damage: 2-3");
		lore1.add(ChatColor.BLUE+"Sell Value: 5");
		
		lore2.add("Damage: 3-6");
		lore2.add(ChatColor.BLUE+"Sell Value: 17");
		
		lore3.add("Defense: 3");
		lore3.add(ChatColor.BLUE+"Sell Value: 5");
		
		lore4.add("Defense: 4");
		lore4.add(ChatColor.BLUE+"Sell Value: 5");
		
		lore5.add("Defense: 2");
		lore5.add(ChatColor.BLUE+"Sell Value: 5");
		
		lore6.add("Defense: 4");
		lore6.add(ChatColor.BLUE+"Sell Value: 8");
		
		lore7.add("Damage: 5-6");
		lore7.add(ChatColor.BLUE+"Sell Value: 8");
	}
	public void applyData()
	{
		for(int x = 0; x<=7; x++)
		{
			meta[x]=options[x].getItemMeta();
		}
		meta[0].setLore(lore0);
		meta[1].setLore(lore1);
		meta[2].setLore(lore2);
		meta[3].setLore(lore3);
		meta[4].setLore(lore4);
		meta[5].setLore(lore5);
		meta[6].setLore(lore6);
		meta[7].setLore(lore7);

		for(int x = 0; x<=7; x++)
		{
			meta[x].setDisplayName(names[x]);
			options[x].setItemMeta(meta[x]);
		}
		SpawnShop.setItem(10, options[1]);
		SpawnShop.setItem(9, options[2]);
		SpawnShop.setItem(0, options[3]);
		SpawnShop.setItem(1, options[4]);
		SpawnShop.setItem(2, options[5]);
		SpawnShop.setItem(3, options[6]);
		SpawnShop.setItem(4, options[7]);

		SpawnShop.setItem(18, options[0]);
		
		lore0.clear();
		lore1.clear();
		lore2.clear();
		lore3.clear();
		lore4.clear();
		lore5.clear();
		lore6.clear();
		lore7.clear();
	}
	public ItemStack returnCrummySword()
	{
		return options[1];
	}
	public ItemStack returnLeatherHelm()
	{
		return options[3];
	}

}
