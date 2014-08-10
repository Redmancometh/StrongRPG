package Quests;

import Storage.DataGetter;
import Storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ReinerQuest {
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;

	public ReinerQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}

	public void reinersQuest() {
		Player p = event.getClicker();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName().equals("Leather")) {
				int amount = p.getItemInHand().getAmount();
				if (p.getItemInHand().getAmount() >= 24 && !(dg.checkCompleted(16, p))) {
					ds.completeQuest(16, p);
					if (amount != 24) {
						p.getItemInHand().setAmount(amount - 24);
					} else {
						p.getInventory().remove(event.getClicker().getItemInHand());
					}
					p.sendMessage(ChatColor.DARK_GREEN + "Reiner: " + ChatColor.GREEN + " This will make me some fine armor! Thanks " + p.getName() + "!");
					p.sendMessage(ChatColor.DARK_GREEN + "Reiner: " + ChatColor.GREEN + " This is all I have left. Take it!");
					p.sendMessage(ChatColor.GOLD + "You have received some experience potions!");
					for (int x = 0; x < 10; x++) {
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					ItemStack is = new ItemStack(Material.IRON_SWORD);
					ItemMeta imIs = is.getItemMeta();
					imIs.setDisplayName("Trainee Sword");
					List<String> lore = new ArrayList<String>();
					lore.add("Damage: 12-18");
					lore.add(ChatColor.BLUE + "Sell Value: 30");
					imIs.setLore(lore);
					lore.clear();
					is.setItemMeta(imIs);
					p.getInventory().addItem(is);
					p.updateInventory();
				}
			} else {
				if (!dg.checkInProgress(16, p) && (!dg.checkCompleted(16, p))) {
					ds.addQIP(16, p);
					p.sendMessage(ChatColor.DARK_GREEN + "Reiner: " + ChatColor.GREEN + "I must find myself some armor! Or my captain won't take me in the team! Can you collect 24 Leather for me please?");
				}
				//if(dg.checkInProgress(16,p)&&!dg.checkCompleted(16,p)){p.sendMessage(ChatColor.DARK_GREEN+"Reiner: "+ChatColor.GREEN+"Do you have my Leather?");}
				else {
					if (dg.checkCompleted(16, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Reiner: " + ChatColor.GREEN + "I am in the team! Thank you so much " + p.getName() + "!");
					}
				}
			}
		} else {
			if (!dg.checkInProgress(16, p) && (!dg.checkCompleted(16, p))) {
				ds.addQIP(16, p);
				p.sendMessage(ChatColor.DARK_GREEN + "Reiner: " + ChatColor.GREEN + "I must find myself some armor! Or my captain won't take me in the team! Can you collect 24 Leather for me please?");
			} else {
				if (dg.checkInProgress(16, p) && !dg.checkCompleted(16, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Reiner: " + ChatColor.GREEN + "Do you have my Leather?");
				} else {
					if (dg.checkCompleted(16, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Reiner: " + ChatColor.GREEN + "I am in the team! Thank you so much " + p.getName() + "!");
					}
				}
			}
		}
	}
}