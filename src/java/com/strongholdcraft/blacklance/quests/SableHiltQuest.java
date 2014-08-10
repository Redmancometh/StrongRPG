package com.strongholdcraft.blacklance.quests;

import com.strongholdcraft.blacklance.storage.DataGetter;
import com.strongholdcraft.blacklance.storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SableHiltQuest {
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;

	public SableHiltQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}

	public void sableHiltsQuest() {
		Player p = event.getClicker();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName().equals("Slime ball")) {
				int amount = p.getItemInHand().getAmount();
				if (p.getItemInHand().getAmount() >= 15 && !(dg.checkCompleted(18, p))) {
					ds.completeQuest(18, p);
					if (amount != 15) {
						p.getItemInHand().setAmount(amount - 15);
					} else {
						p.getInventory().remove(event.getClicker().getItemInHand());
					}
					p.sendMessage(ChatColor.DARK_GREEN + "Sable-Hilt: " + ChatColor.GREEN + " Thanks for the Slime balls. They'll make fine leads!");
					p.sendMessage(ChatColor.DARK_GREEN + "Sable-Hilt: " + ChatColor.GREEN + " This is not much, but here. Take it.");
					p.sendMessage(ChatColor.GOLD + "You have received some ecperience points and Hay.");
					for (int x = 0; x < 10; x++) {
						event.getClicker().getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					for (int i = 0; i < 8; i++) {
						p.getInventory().addItem(new ItemStack(Material.HAY_BLOCK));
					}
					p.updateInventory();
				}
			} else {
				if (!dg.checkInProgress(18, p) && (!dg.checkCompleted(18, p))) {
					ds.addQIP(18, p);
					p.sendMessage(ChatColor.DARK_GREEN + "Sable-Hilt: " + ChatColor.GREEN + "I need some Slime balls for leads. Think you can pick some up for me?");
				}
				if (dg.checkCompleted(18, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Sable-Hilt: " + ChatColor.GREEN + "Hi again, " + p.getName() + "!");
				}
			}
		} else {
			if (!dg.checkInProgress(18, p) && (!dg.checkCompleted(18, p))) {
				ds.addQIP(18, p);
				p.sendMessage(ChatColor.DARK_GREEN + "Sable-Hilt: " + ChatColor.GREEN + "I need some Slime balls for leads. Think you can pick some up for me?");
			} else {
				if (dg.checkInProgress(18, p) && !dg.checkCompleted(18, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Sable-Hilt: " + ChatColor.GREEN + "I asked for 15... Go get some more please.");
				} else {
					if (dg.checkCompleted(18, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Sable-Hilt: " + ChatColor.GREEN + "Hi again, " + p.getName() + "!");
					}
				}
			}
		}
	}
}