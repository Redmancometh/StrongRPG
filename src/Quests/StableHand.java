package Quests;

import Storage.DataGetter;
import Storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class StableHand {
	private NPCRightClickEvent event;
	private JavaPlugin blacklance;
	private Player p;
	private List<String> lore = new ArrayList<String>();
	private DataGetter dg;
	private DataSetter ds;

	public StableHand(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.blacklance = blacklance;
		this.event = event;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
		HayCollection();
	}

	public void HayCollection() {
		Player p = event.getClicker();
		ItemStack i = event.getClicker().getItemInHand();
		Material m = i.getType();
		if (m != Material.HAY_BLOCK) {
			if (!dg.checkInProgress(5, p) && (!dg.checkCompleted(5, p))) {
				ds.addQIP(5, p);
				p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand: " + ChatColor.GREEN + p.getName() + " I need to feed these 2 horses, but it is too dangerous to go out and get hay... If I give you a hoe can you do it?");
				p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand: " + ChatColor.GREEN + "There is hay ahead on path, across from the witches hut, but there are bandits.");
				p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand: " + ChatColor.GREEN + "Take this hoe to harvest the hay with... about 8 bales should do, good luck " + p.getName());
				p.getInventory().addItem(new ItemStack(Material.STONE_HOE));
			} else {
				if (dg.checkInProgress(5, p) && !dg.checkCompleted(5, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand: " + ChatColor.GREEN + "The horses are still hungry adventurer.");
					p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand: " + ChatColor.GREEN + "If you need another hoe, speak to Farmer Giles. His house is nearby.");
				}
				if (dg.checkCompleted(5, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand: " + ChatColor.GREEN + " The horses are well fed now, thanks! Talk to farmer Ashton if you need another hoe.");
				}
			}
		} else {
			if (!dg.checkCompleted(5, p)) {
				int amount = p.getItemInHand().getAmount();
				if (amount >= 8) {
					if (amount > 8) {
						p.getItemInHand().setAmount(amount - 8);
						ds.completeQuest(5, p);
					}
					if (amount == 8) {
						p.getItemInHand().setType(Material.AIR);
						ds.completeQuest(5, p);
					}
					p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand:" + ChatColor.GREEN + " Thank you for your help " + p.getName() + "! I'll feed the horses now!");
					p.sendMessage(ChatColor.GOLD + "The farm hand has given you some experience potions!");
					for (int x = 0; x < 5; x++) {
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					p.updateInventory();
				} else {
					p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand: " + "I need 8 bales to feed the horses!");
				}
			} else {
				p.sendMessage(ChatColor.DARK_GREEN + "Stable Hand: " + ChatColor.GREEN + " The horses are well fed now, thanks! Talk to farmer Ashton if you need another hoe.");
			}
		}
	}

}

