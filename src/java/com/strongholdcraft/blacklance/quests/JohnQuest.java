package com.strongholdcraft.blacklance.quests;

import com.strongholdcraft.blacklance.storage.DataGetter;
import com.strongholdcraft.blacklance.storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class JohnQuest {
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;

	public JohnQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}

	public void johnsQuest() {
		Player p = event.getClicker();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName().equals("Horse Meat")) {
				int amount = p.getItemInHand().getAmount();
				if (p.getItemInHand().getAmount() >= 15 && !(dg.checkCompleted(12, p))) {
					ds.completeQuest(12, p);
					if (amount != 15) {
						p.getItemInHand().setAmount(amount - 15);
					} else {
						p.getInventory().remove(event.getClicker().getItemInHand());
					}
					p.sendMessage(ChatColor.DARK_GREEN + "John: " + ChatColor.GREEN + " Thank you so much" + p.getName() + ", this should keep me fed!");
					p.sendMessage(ChatColor.DARK_GREEN + "John: " + ChatColor.GREEN + " Here is a little something for your work.");
					p.sendMessage(ChatColor.GOLD + "You have received some experience potions!");
					for (int x = 0; x < 10; x++) {
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					ItemStack is = new ItemStack(Material.COOKED_BEEF);
					ItemMeta isMeta = is.getItemMeta();
					isMeta.setDisplayName("Cooked Horse Meat");
					List<String> lore = new ArrayList<String>();
					lore.add("Delicious food");
					lore.add(ChatColor.BLUE + "Sell Value: 25");
					isMeta.setLore(lore);
					is.setItemMeta(isMeta);
					p.getInventory().addItem(is);
					p.updateInventory();
				}
			} else {
				if (!dg.checkInProgress(12, p) && (!dg.checkCompleted(12, p))) {
					ds.addQIP(12, p);
					p.sendMessage(ChatColor.DARK_GREEN + "John: " + ChatColor.GREEN + "I need some food, but these horses seem too dangerous for me. Can you get me 15 Horse Meat?");
				}
				//if(dg.checkInProgress(12,p)&&!dg.checkCompleted(12,p)){p.sendMessage(ChatColor.DARK_GREEN+"John: "+ChatColor.GREEN+"I am going to starve at this rate.");}
				else {
					if (dg.checkCompleted(12, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "John: " + ChatColor.GREEN + "You are a lifesaver! Thank you!");
					}
				}
			}
		} else {
			if (!dg.checkInProgress(12, p) && (!dg.checkCompleted(12, p))) {
				ds.addQIP(12, p);
				p.sendMessage(ChatColor.DARK_GREEN + "John: " + ChatColor.GREEN + "I need some food, but these horses seem to dangerous for me. Can you get me 15 Horse Meat?");
			} else {
				if (dg.checkInProgress(12, p) && !dg.checkCompleted(12, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "John: " + ChatColor.GREEN + "I am going to starve at this rate.");
				} else {
					if (dg.checkCompleted(12, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "John: " + ChatColor.GREEN + "You are a lifesaver! Thank you!");
					}
				}
			}
		}
	}
}