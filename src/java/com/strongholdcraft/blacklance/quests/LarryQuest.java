package com.strongholdcraft.blacklance.quests;

import com.strongholdcraft.blacklance.storage.DataGetter;
import com.strongholdcraft.blacklance.storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class LarryQuest {
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;

	public LarryQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}

	public void larrysQuest() {
		Player p = event.getClicker();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName().equals("Red Mushroom")) {
				int amount = p.getItemInHand().getAmount();
				if (p.getItemInHand().getAmount() >= 10 && !(dg.checkCompleted(13, p))) {
					ds.completeQuest(13, p);
					if (amount != 10) {
						p.getItemInHand().setAmount(amount - 10);
					} else {
						p.getInventory().remove(event.getClicker().getItemInHand());
					}
					p.sendMessage(ChatColor.DARK_GREEN + "Larry:" + ChatColor.GREEN + " Thank you so much" + p.getName() + ", this will help for the soup!");
					p.sendMessage(ChatColor.DARK_GREEN + "Larry:" + ChatColor.GREEN + " This is your reward. Take it.");
					p.sendMessage(ChatColor.GOLD + "You have received some experience potions and some soup!");
					for (int x = 0; x < 10; x++) {
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
					p.updateInventory();
				}
			} else {
				if (!dg.checkInProgress(13, p) && (!dg.checkCompleted(13, p))) {
					ds.addQIP(13, p);
					p.sendMessage(ChatColor.DARK_GREEN + "Larry: " + ChatColor.GREEN + "I need some mushrooms for my soup. You think you can get some for me?");
				}
				//if(dg.checkInProgress(13,p)&&!dg.checkCompleted(13,p)){p.sendMessage(ChatColor.DARK_GREEN+"Larry: "+ChatColor.GREEN+"Having trouble?");}
				else {
					if (dg.checkCompleted(13, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Larry: " + ChatColor.GREEN + "Thank you! Those mushrooms were perfect!");
					}
				}
			}
		} else {
			if (!dg.checkInProgress(13, p) && (!dg.checkCompleted(13, p))) {
				ds.addQIP(13, p);
				p.sendMessage(ChatColor.DARK_GREEN + "Larry: " + ChatColor.GREEN + "I need some mushrooms for my soup. You think you can get come for me?");
			} else {
				if (dg.checkInProgress(13, p) && !dg.checkCompleted(13, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Larry: " + ChatColor.GREEN + "Having trouble?");
				} else {
					if (dg.checkCompleted(13, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Larry: " + ChatColor.GREEN + "Thank you! Those mushrooms were perfect!");
					}
				}
			}
		}
	}
}