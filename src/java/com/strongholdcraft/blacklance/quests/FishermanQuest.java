package com.strongholdcraft.blacklance.quests;

import com.strongholdcraft.blacklance.storage.DataGetter;
import com.strongholdcraft.blacklance.storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FishermanQuest {
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;

	public FishermanQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}

	public void fishermansQuest() {
		Player p = event.getClicker();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName().equals("Slaughterfish")) {
				int amount = p.getItemInHand().getAmount();
				if (p.getItemInHand().getAmount() >= 10 && !(dg.checkCompleted(11, p))) //change the 1 to your quest ID
				{
					ds.completeQuest(11, p);
					if (amount != 10) {
						p.getItemInHand().setAmount(amount - 10);
					} else {
						p.getInventory().remove(event.getClicker().getItemInHand());
					}
					p.sendMessage(ChatColor.DARK_GREEN + "Fisherman: " + ChatColor.GREEN + " Thank you so much for the fish, " + p.getName() + "!");
					p.sendMessage(ChatColor.DARK_GREEN + "Fisherman: " + ChatColor.GREEN + " This should be worth your while.");
					p.sendMessage(ChatColor.GOLD + "You have received some experience potions!");
					for (int x = 0; x < 10; x++) {
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					for (int i = 0; i < 5; i++) {
						p.getInventory().addItem(new ItemStack(Material.COOKED_FISH)); //I do not know if food is neccessary, but that's what a fisherman would usually give.
					}
					p.updateInventory();
				}
			} else {
				if (!dg.checkInProgress(11, p) && (!dg.checkCompleted(11, p))) {
					ds.addQIP(11, p);
					p.sendMessage(ChatColor.DARK_GREEN + "Fisherman: " + ChatColor.GREEN + "Hello there young one! Could you bring me some Slaughterfish please? I'll reward you handsomely.");
					if (!p.getInventory().contains(new ItemStack(Material.FISHING_ROD))) {
						p.sendMessage(ChatColor.DARK_GREEN + "Fisherman: " + ChatColor.GREEN + "Here is a fishing rod. If you lose it, talk to the other Fisherman...");
						p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
					}
				}
				//if(dg.checkInProgress(11,p)&&!dg.checkCompleted(11,p)){p.sendMessage(ChatColor.DARK_GREEN+"Fisherman: "+ChatColor.GREEN+"Still didn't catch them, eh?");}
				else {
					if (dg.checkCompleted(11, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Fisherman: " + ChatColor.GREEN + "Thank you for the fish.");
					}
				}
			}
		} else {
			if (!dg.checkInProgress(11, p) && (!dg.checkCompleted(11, p))) {
				ds.addQIP(11, p);
				p.sendMessage(ChatColor.DARK_GREEN + "Fisherman: " + ChatColor.GREEN + "Hello there young one! Could you bring me some Slaughterfish please? I'll reward you handsomely.");
			} else {
				if (dg.checkInProgress(11, p) && !dg.checkCompleted(11, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Fisherman: " + ChatColor.GREEN + "Still didn't catch them, eh?");
				} else {
					if (dg.checkCompleted(11, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Fisherman: " + ChatColor.GREEN + "Thank you for the fish.");
					}
				}
			}
		}
	}
}