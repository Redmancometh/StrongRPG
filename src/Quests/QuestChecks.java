package Quests;

import Util.DropUtil;
import com.gmail.filoghost.healthbar.api.HealthBarAPI;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class QuestChecks {
	private EntityDeathEvent event;
	private ItemStack drop = new ItemStack(Material.WOOD);
	private ItemMeta dropmeta = drop.getItemMeta();
	private JavaPlugin blacklance;
	private boolean dodrop;

	public QuestChecks(JavaPlugin blacklance) {
		this.blacklance = blacklance;
	}

	public List<String> lore = new ArrayList<String>();

	public void checkQuestMob(Entity Paul, Player killer) {
		String mobname;
		if (Paul instanceof Player && CitizensAPI.getNPCRegistry().isNPC(Paul)) {
			Player p = (Player) Paul;
			mobname = p.getName();
		} else {
			mobname = HealthBarAPI.getMobName((LivingEntity) Paul);
		}
		double isdropped = Math.random();
		DropUtil du = new DropUtil(blacklance);
		if (mobname.contains("Shrimp")) {
			drop.setType(Material.POISONOUS_POTATO);
			dropmeta.setDisplayName("Raw Shrimp Meat");
			lore.add("Probably Poisonous Without Cooking...");
			dodrop = true;
		}
		if (mobname.contains("Zombie")) {
			drop.setType(Material.BONE);
			dropmeta.setDisplayName("Zombie Bones");
			lore.add("Very slimy...");
			lore.add(ChatColor.BLUE + "Sell Value: 1");
			dodrop = true;
		}
		if (mobname.contains("Spider")) {
			drop.setType(Material.SPIDER_EYE);
			dropmeta.setDisplayName("Gooey Spider Eye ");
			lore.add("Gross...");
			lore.add(ChatColor.BLUE + "Sell Value: 2");
			dodrop = true;
		}
		if (mobname.contains("Servant")) {
			drop.setType(Material.EYE_OF_ENDER);
			dropmeta.setDisplayName("Dark Servant's Eye ");
			lore.add("Sends a Brutal Message...");
			lore.add(ChatColor.BLUE + "Sell Value: 3");
			dodrop = true;
		}

		//============================================================================================================
		if (mobname.contains("Charger")) {
			drop.setType(Material.PORK);
			dropmeta.setDisplayName("Horse Meat");
			lore.add("Looks tasty...");
			lore.add(ChatColor.BLUE + "Sell Value: 10");
			dodrop = true;
		}

		if (mobname.contains("Crawler")) {
			drop.setType(Material.STRING);
			dropmeta.setDisplayName("String");
			lore.add("Good for making bows");
			lore.add(ChatColor.BLUE + "Sell Value: 3");
			dodrop = true;
		}

		if (mobname.contains("Chicken")) {
			drop.setType(Material.EGG);
			dropmeta.setDisplayName("Chicken Egg");
			lore.add("You can cook these");
			lore.add(ChatColor.BLUE + "Sell Value: 1");
			dodrop = true;
		}

		if (mobname.contains("Hunter")) {
			drop.setType(Material.INK_SACK);
			dropmeta.setDisplayName("Black Ink");
			lore.add("Good for writting books");
			lore.add(ChatColor.BLUE + "Sell Value: 4");
			dodrop = true;
		}

		if (mobname.contains("Crawler")) {
			drop.setType(Material.STRING);
			dropmeta.setDisplayName("Strings");
			lore.add("Good for making bows");
			lore.add(ChatColor.BLUE + "Sell Value: 3");
			dodrop = true;
		}

		if (mobname.contains("Red Cow")) {
			drop.setType(Material.RED_MUSHROOM);
			dropmeta.setDisplayName("Red Mushroom");
			lore.add("Looks delicious.");
			lore.add(ChatColor.BLUE + "Sell Value: 2");
			dodrop = true;
		}

		if (mobname.contains("Dark Archer")) {
			drop.setType(Material.BONE);
			dropmeta.setDisplayName("Dark Bone");
			lore.add("Slimy...");
			lore.add(ChatColor.BLUE + "Sell Value: 1");
			dodrop = true;
		}

		if (mobname.contains("[19] Cow")) {
			drop.setType(Material.LEATHER);
			dropmeta.setDisplayName("Leather");
			lore.add("Good for making armour.");
			lore.add(ChatColor.BLUE + "Sell Value: 13");
			dodrop = true;
		}

		if (mobname.contains("Red Cow")) {
			drop.setType(Material.RED_MUSHROOM);
			dropmeta.setDisplayName("Red Mushroom");
			lore.add("Looks delicious.");
			lore.add(ChatColor.BLUE + "Sell Value: 2");
			dodrop = true;
		}

		if (mobname.contains("Slime")) {
			drop.setType(Material.SLIME_BALL);
			dropmeta.setDisplayName("Slime ball");
			lore.add("Sticky...");
			lore.add(ChatColor.BLUE + "Sell Value: 1");
			dodrop = true;
		}

		if (mobname.contains("Dire")) {
			drop.setType(Material.LEATHER);
			dropmeta.setDisplayName("Wolf Pelt");
			lore.add("Looks delicious.");
			lore.add(ChatColor.BLUE + "Sell Value: 2");
			dodrop = true;
		}

		if (mobname.contains("Red Cow")) {
			drop.setType(Material.RED_MUSHROOM);
			dropmeta.setDisplayName("Red Mushroom");
			lore.add("Looks delicious.");
			lore.add(ChatColor.BLUE + "Sell Value: 2");
			dodrop = true;
		}

		if (mobname.contains("Magma")) {
			drop.setType(Material.MAGMA_CREAM);
			dropmeta.setDisplayName("Magma ball");
			lore.add("Seems to have magical properties in it...");
			lore.add(ChatColor.BLUE + "Sell Value: 11");
			dodrop = true;
		}
		//============================================================================================================
		if (dodrop && isdropped <= .48) {
			lore.add("PlayerI" + killer.getUniqueId());
			dropmeta.setLore(lore);
			lore.clear();
			drop.setItemMeta(dropmeta);
			CraftItem ci = (CraftItem) Paul.getWorld().dropItem(Paul.getLocation(), drop);
			du.hide(killer, ci);
		}
	}

}
