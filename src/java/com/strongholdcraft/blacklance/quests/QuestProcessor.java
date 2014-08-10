package com.strongholdcraft.blacklance.quests;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class QuestProcessor {
	ItemStack noobsword = new ItemStack(Material.WOOD_SWORD);
	ItemMeta swordmeta = noobsword.getItemMeta();
	List<String> swordlore = new ArrayList<String>();
	private JavaPlugin blacklance;
	private NPCRightClickEvent event;
	private Player p;

	public QuestProcessor(NPCRightClickEvent event, JavaPlugin blacklance) {
		this.event = event;
		p = event.getClicker();
		this.blacklance = blacklance;
	}

	public void blacksmithCrasmeer() {
		if (p.getItemInHand().getType() == Material.IRON_ORE) {
			int amount = p.getItemInHand().getAmount();
			if (amount < 45) {
				p.sendMessage(ChatColor.DARK_GREEN + "Blacksmith: " + ChatColor.DARK_RED + "Sorry this isn't enough iron. I need 45 iron ore for a weapon!");
			} else {
				if (amount != 45) {
					p.getItemInHand().setAmount(amount - 45);
				} else {
					p.getInventory().remove(event.getClicker().getItemInHand());
				}
				p.sendMessage(ChatColor.DARK_GREEN + "Blacksmith: " + ChatColor.GREEN + " Here is your new sword!");
				p.sendMessage(ChatColor.GOLD + " You have received an Average Iron Sword!");
				swordlore.add("Damage: 5-8");
				swordlore.add(ChatColor.BLUE + "Sell Value: 25");
				swordmeta.setDisplayName("Average Iron Sword ");
				swordmeta.setLore(swordlore);
				swordlore.clear();
				noobsword.setItemMeta(swordmeta);
				noobsword.setType(Material.IRON_SWORD);
				p.getInventory().addItem(noobsword);
			}
		} else {
			p.sendMessage(ChatColor.DARK_GREEN + "Blacksmith: " + ChatColor.GREEN + "Bring me 45 Iron Ore if you want a weapon made!");
		}
	}

	public void blacksmithRiverwood() {
		if (p.getItemInHand().getType() == Material.GOLD_ORE) {
			int amount = p.getItemInHand().getAmount();
			if (amount < 64) {
				p.sendMessage(ChatColor.DARK_GREEN + "Blacksmith: " + ChatColor.DARK_RED + "Sorry this isn't enough gold. I need 64 gold ore for a weapon!");
			} else {
				if (amount != 64) {
					p.getItemInHand().setAmount(amount - 64);
				} else {
					p.getInventory().remove(event.getClicker().getItemInHand());
				}
				p.sendMessage(ChatColor.DARK_GREEN + "Blacksmith: " + ChatColor.GREEN + " Here is your new gold sword!");
				p.sendMessage(ChatColor.GOLD + " You have received a Powerful Gold Sword!");
				swordlore.add("Damage: 20-22");
				swordlore.add(ChatColor.BLUE + "Sell Value: 50");
				swordmeta.setDisplayName("Powerful Gold Sword ");
				swordmeta.setLore(swordlore);
				swordlore.clear();
				noobsword.setItemMeta(swordmeta);
				noobsword.setType(Material.GOLD_SWORD);
				p.getInventory().addItem(noobsword);
			}
		} else {
			p.sendMessage(ChatColor.DARK_GREEN + "Blacksmith: " + ChatColor.GREEN + "Bring me 64 Gold Ore if you want a weapon made!");
		}
	}

}
