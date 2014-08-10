package com.strongholdcraft.blacklance.quests;

import com.strongholdcraft.blacklance.storage.DataGetter;
import com.strongholdcraft.blacklance.storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FelixQuest {
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;

	public FelixQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}

	public void felixsQuest() {
		Player p = event.getClicker();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName().equals("Black Ink")) {
				int amount = p.getItemInHand().getAmount();
				if (p.getItemInHand().getAmount() >= 15 && !(dg.checkCompleted(14, p))) {
					ds.completeQuest(14, p);
					if (amount != 15) {
						p.getItemInHand().setAmount(amount - 15);
					} else {
						p.getInventory().remove(event.getClicker().getItemInHand());
					}
					p.sendMessage(ChatColor.DARK_GREEN + "Felix: " + ChatColor.GREEN + " Oh, you have my Ink! Thanks " + p.getName() + "! Now I can continue writting my book!");
					p.sendMessage(ChatColor.DARK_GREEN + "Felix: " + ChatColor.GREEN + " That Ink was helpful to me. Hope this is helpful to you!");
					p.sendMessage(ChatColor.GOLD + "You have received some experience potions, and a book!");
					for (int x = 0; x < 10; x++) {
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					p.getInventory().addItem(new ItemStack(Material.BOOK_AND_QUILL));
					p.updateInventory();
				}
			} else {
				if (!dg.checkInProgress(14, p) && (!dg.checkCompleted(14, p))) {
					ds.addQIP(14, p);
					p.sendMessage(ChatColor.DARK_GREEN + "Felix: " + ChatColor.GREEN + "I must write a book! But I ran out of Ink! And there are no squids here anymore... Those Hunters over there killed them all... Can you get some Ink from them please?");
				}
				//if(dg.checkInProgress(14,p)&&!dg.checkCompleted(14,p)){p.sendMessage(ChatColor.DARK_GREEN+"Felix: "+ChatColor.GREEN+"I need 15 Black Ink.");}
				else {
					if (dg.checkCompleted(14, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Felix: " + ChatColor.GREEN + "My book is almost done, thanks to you!");
					}
				}
			}
		} else {
			if (!dg.checkInProgress(14, p) && (!dg.checkCompleted(14, p))) {
				ds.addQIP(14, p);
				p.sendMessage(ChatColor.DARK_GREEN + "Felix: " + ChatColor.GREEN + "I must write a book! But I ran out of Ink! And there are no squids here anymore... Those Hunters over there killed them all... Can you get some Ink from them please?");
			} else {
				if (dg.checkInProgress(14, p) && !dg.checkCompleted(14, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Felix: " + ChatColor.GREEN + "I need 15 Black Ink.");
				} else {
					if (dg.checkCompleted(14, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Felix: " + ChatColor.GREEN + "My book is almost done, thanks to you!");
					}
				}
			}
		}
	}
}