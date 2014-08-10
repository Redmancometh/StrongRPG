package Merchants;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BlackridgeMerchant {
	Inventory SpawnShop = (Inventory) Bukkit.createInventory(null, 27, "Crasmeer Shop");
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
	List<String> lore8 = new ArrayList<String>();
	List<String> lore9 = new ArrayList<String>();
	List<String> lore10 = new ArrayList<String>();
	List<String> lore11 = new ArrayList<String>();
	List<String> lore12 = new ArrayList<String>();
	List<String> lore13 = new ArrayList<String>();
	List<String> lore14 = new ArrayList<String>();
	List<String> lore15 = new ArrayList<String>();
	List<String> lore16 = new ArrayList<String>();
	List<String> lore17 = new ArrayList<String>();
	List<String> lore18 = new ArrayList<String>();

	private NPCRightClickEvent event;

	public BlackridgeMerchant(NPCRightClickEvent event) {
		this.event = event;
		setData();
		applyData();
	}

	public void openInventory(NPCRightClickEvent event) {
		event.getClicker().openInventory(SpawnShop);
	}

	public void setData() {
		names[0] = "Light Healing Potion - 35 Steel Coins";
		names[1] = "Average Stone Sword - 520 Steel Coins";
		names[2] = "Above Average Stone Sword - 620 Steel Coins";
		names[3] = "Rusty Chain Coif - 445 Steel Coins";
		names[4] = "Woven Chaps - 350 Steel Coins";
		names[5] = "Basic Chain Boots - 400 Steel Coins";
		names[6] = "Rusty Chain Mail - 550 Steel Coins";
		names[7] = "Reinforced Woven Coif - 495 Steel Coins";
		names[8] = "Above Average Woven Chaps - 450 Steel Coins";
		names[9] = "Reinforced Chain Boots - 500 Steel Coins";
		names[10] = "Average Chain Body Armor - 630 Steel Coins";
		names[11] = "Above Average Katar - 900 Steel Coins";
		names[12] = "Sharp Stone Sword - 750 Steel Coins";
		names[13] = "Wicked Sickle - 950 Steel Coins";
		names[14] = ChatColor.GREEN + "Basic Saddle - 550 Steel Coins";
		names[15] = ChatColor.AQUA + "Riveted Saddle - 750 Steel Coins";
		names[16] = ChatColor.GOLD + "Sturdy Saddle - 950 Steel Coins";
		names[17] = ChatColor.GOLD + "Spruce Bow - 950 Steel Coins";
		names[18] = ChatColor.GOLD + "Arrows - 15 Steel Coins";

		options[0] = new ItemStack(Material.POTION);
		options[1] = new ItemStack(Material.STONE_SWORD);
		options[2] = new ItemStack(Material.STONE_SWORD);
		options[3] = new ItemStack(Material.CHAINMAIL_HELMET);
		options[4] = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		options[5] = new ItemStack(Material.CHAINMAIL_BOOTS);
		options[6] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		options[7] = new ItemStack(Material.CHAINMAIL_HELMET);
		options[8] = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		options[9] = new ItemStack(Material.CHAINMAIL_BOOTS);
		options[10] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		options[11] = new ItemStack(Material.SHEARS);
		options[12] = new ItemStack(Material.STONE_SWORD);
		options[13] = new ItemStack(Material.IRON_HOE);
		options[14] = new ItemStack(Material.SADDLE);
		options[15] = new ItemStack(Material.SADDLE);
		options[16] = new ItemStack(Material.SADDLE);
		options[17] = new ItemStack(Material.BOW);
		options[18] = new ItemStack(Material.ARROW);
		options[18].setAmount(64);

		lore0.add("Heals Light Damage");
		lore0.add(ChatColor.BLUE + "Sell Value: 7");

		lore1.add("Damage: 4-13");
		lore1.add(ChatColor.BLUE + "Sell Value: 32");

		lore2.add("Damage: 4-15");
		lore2.add(ChatColor.BLUE + "Sell Value: 42");

		lore3.add("Defense: 13");
		lore3.add(ChatColor.BLUE + "Sell Value: 27");

		lore4.add("Defense: 12");
		lore4.add(ChatColor.BLUE + "Sell Value: 27");

		lore5.add("Defense: 10");
		lore5.add(ChatColor.BLUE + "Sell Value: 28");

		lore6.add("Defense: 14");
		lore6.add(ChatColor.BLUE + "Sell Value: 32");

		lore7.add("Defense: 11");
		lore7.add(ChatColor.BLUE + "Sell Value: 25");

		lore8.add("Defense: 14");
		lore8.add(ChatColor.BLUE + "Sell Value: 25");

		lore9.add("Defense: 13");
		lore9.add(ChatColor.BLUE + "Sell Value: 25");

		lore10.add("Defense: 15");
		lore10.add(ChatColor.BLUE + "Sell Value: 25");

		lore11.add("Damage: 3-19");
		lore11.add(ChatColor.BLUE + "Sell Value: 50");

		lore12.add("Damage: 5-16");
		lore12.add(ChatColor.BLUE + "Sell Value: 30");

		lore13.add("Damage: 8-16");
		lore13.add(ChatColor.BLUE + "Sell Value: 55");

		lore14.add("Summons a Chestnut Mare");

		lore15.add("Summons a Black Charger");

		lore16.add("Summons a Battlecharger");

		lore17.add("Damage: 10-13");
		lore17.add(ChatColor.BLUE + "Sell Value: 30");

		lore18.add("Shoot things with these");

	}

	public void applyData() {
		for (int x = 0; x <= 18; x++) {
			meta[x] = options[x].getItemMeta();
		}
		meta[0].setLore(lore0);
		meta[1].setLore(lore1);
		meta[2].setLore(lore2);
		meta[3].setLore(lore3);
		meta[4].setLore(lore4);
		meta[5].setLore(lore5);
		meta[6].setLore(lore6);
		meta[7].setLore(lore7);
		meta[8].setLore(lore8);
		meta[9].setLore(lore9);
		meta[10].setLore(lore10);
		meta[11].setLore(lore11);
		meta[12].setLore(lore12);
		meta[13].setLore(lore13);
		meta[14].setLore(lore14);
		meta[15].setLore(lore15);
		meta[16].setLore(lore16);
		meta[17].setLore(lore17);
		meta[18].setLore(lore18);

		for (int x = 0; x <= 18; x++) {
			meta[x].setDisplayName(names[x]);
			options[x].setItemMeta(meta[x]);
		}
		SpawnShop.setItem(9, options[1]);
		SpawnShop.setItem(10, options[2]);
		SpawnShop.setItem(11, options[12]);
		SpawnShop.setItem(12, options[13]);
		SpawnShop.setItem(0, options[3]);
		SpawnShop.setItem(1, options[4]);
		SpawnShop.setItem(2, options[5]);
		SpawnShop.setItem(3, options[6]);
		SpawnShop.setItem(4, options[7]);
		SpawnShop.setItem(5, options[8]);
		SpawnShop.setItem(6, options[9]);
		SpawnShop.setItem(7, options[10]);
		SpawnShop.setItem(8, options[11]);
		SpawnShop.setItem(18, options[0]);
		SpawnShop.setItem(18, options[14]);
		SpawnShop.setItem(19, options[15]);
		SpawnShop.setItem(20, options[16]);
		SpawnShop.setItem(25, options[17]);
		SpawnShop.setItem(26, options[18]);


		lore0.clear();
		lore1.clear();
		lore2.clear();
		lore3.clear();
		lore4.clear();
		lore5.clear();
		lore6.clear();
		lore7.clear();
		lore8.clear();
		lore9.clear();
		lore10.clear();
		lore11.clear();
		lore12.clear();
		lore13.clear();
		lore14.clear();
		lore15.clear();
		lore16.clear();
		lore17.clear();
		lore18.clear();
	}

	public ItemStack returnCrummySword() {
		return options[1];
	}

	public ItemStack returnLeatherHelm() {
		return options[3];
	}

}
