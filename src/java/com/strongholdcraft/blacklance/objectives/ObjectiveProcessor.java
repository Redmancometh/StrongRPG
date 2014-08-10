package com.strongholdcraft.blacklance.objectives;

import com.strongholdcraft.blacklance.storage.DataGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

public class ObjectiveProcessor implements Listener {
	ItemStack questitem = null;
	public int questID = 9999;
	JavaPlugin pl;
	HashMap<Player, Scoreboard> questobj = new HashMap();

	public ObjectiveProcessor(JavaPlugin pl) {
		this.pl = pl;
	}

	@EventHandler
	public void getQuestItem(PlayerPickupItemEvent event) {
		ItemStack item = event.getItem().getItemStack();
		if (item.hasItemMeta()) {
			Player p = event.getPlayer();
			String itemname = null;
			if (item.getType() == Material.BONE) {
				questitem = item;
				questID = 0;
				itemname = "Zombie Bones";
			}
			if (item.getType() == Material.POISONOUS_POTATO) {
				questitem = item;
				questID = 1;
				itemname = "Shrimp Meat";
			}
			if (item.getType() == Material.SPIDER_EYE) {
				questitem = item;
				questID = 3;
				itemname = "Spider Eyes";
			}
			if (item.getType() == Material.EYE_OF_ENDER) {
				questitem = item;
				questID = 4;
				itemname = "Servant's Eyes";
			}
			if (item.getType() == Material.HAY_BLOCK) {
				questitem = item;
				questID = 5;
				itemname = "Hay Bales";
			}
			if (questitem != null && questID != 9999 && itemname != null) {
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getNewScoreboard();
				Objective objective = board.registerNewObjective("test", "dummy");
				objective.setDisplaySlot(DisplaySlot.SIDEBAR);
				objective.setDisplayName(ChatColor.LIGHT_PURPLE + "Quest Objectives");
				Score score = objective.getScore(Bukkit.getOfflinePlayer(itemname + ":"));
				ItemStack[] contents = p.getInventory().getContents();
				int amount = 0;
				for (int x = 0; x < contents.length; x++) {
					if (contents[x] != null) {
						if (contents[x].getType() == event.getItem().getItemStack().getType()) {
							amount += contents[x].getAmount();
						}
					}
				}
				amount += item.getAmount();
				if (amount > 0) {
					DataGetter dg = new DataGetter(pl);
					if (dg.checkInProgress(questID, p) && !(dg.checkCompleted(questID, p))) {
						score.setScore(amount);
						p.setScoreboard(board);
					}
				}
			}

		}
	}
}
