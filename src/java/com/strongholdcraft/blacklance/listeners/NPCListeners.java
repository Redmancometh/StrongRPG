package com.strongholdcraft.blacklance.listeners;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.strongholdcraft.blacklance.merchants.BlackridgeMerchant;
import com.strongholdcraft.blacklance.merchants.CrasmeerMerchant;
import com.strongholdcraft.blacklance.merchants.SpawnMerchant;
import com.strongholdcraft.blacklance.quests.*;
import net.citizensnpcs.api.event.NPCPushEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class NPCListeners implements Listener {
	HashMap<Player, Inventory> bank = new HashMap();
	private JavaPlugin blacklance;

	public NPCListeners(JavaPlugin blacklance) {
		this.blacklance = blacklance;
	}

	@EventHandler
	public void theNeutrals(NPCRightClickEvent event) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		Player p = event.getClicker();
		int id = event.getNPC().getId();
		//Spawn Area Merchant
		if (id == 20) {
			SpawnMerchant sm = new SpawnMerchant(event);
			sm.openInventory(event);
		}
		//Crasmeer Merchant
		if (id == 23 || id == 178) {
			CrasmeerMerchant cm = new CrasmeerMerchant(event);
			cm.openInventory(event);
		}
		//Falkirk and BlackRidge Merchants
		if (id == 194 || id == 399 || id == 411) {
			BlackridgeMerchant bm = new BlackridgeMerchant(event);
			bm.openInventory(event);
		}
		//Bankers
		if (id == 183 || id == 193) {
			Inventory inventory = Bukkit.createInventory(null, 27, "Bank");
			ItemStack[] inv = p.getEnderChest().getContents();
			inventory.setContents(inv);
			p.openInventory(inventory);
			bank.put(p, inventory);
		}
		//Innkeepers
		if (id == 176 || id == 192) {
			User u = ess.getUser(p);
			u.setHome("home", p.getLocation());
			p.sendMessage(ChatColor.GOLD + "Your home has been set here");
		}
		//BlackSmiths
		if (id == 177) {
			QuestProcessor qp = new QuestProcessor(event, blacklance);
			qp.blacksmithCrasmeer();
		}
		//Farmer Giles
		if (id == 436) {
			event.getClicker().sendMessage(ChatColor.DARK_GREEN + "Farmer Giles: " + ChatColor.GREEN + "Here's a hoe...we've got plenty of hoes around here");
			event.getClicker().sendMessage(ChatColor.GOLD + "Farmer Giles winks and nudges you");
			event.getClicker().getInventory().addItem(new ItemStack(Material.STONE_HOE));
		}
	}

	@EventHandler
	public void questGivers(NPCRightClickEvent event) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Player p = event.getClicker();
		int id = event.getNPC().getId();
		//Spawn Guide
		if (id == 133) {
			StarterQuests sq = new StarterQuests(event, blacklance);
			sq.starterQuest();
		}
		//Knight Quest
		if (id == 132) {
			KnightQuest kq = new KnightQuest(event, blacklance, p);
			kq.knightsQuest();
		}
		//Witch Quest
		if (id == 184) {
			WitchQuest wq = new WitchQuest(event, blacklance, p);
		}
		//Mayor's Quest (demon chain start)
		if (id == 195) {
			DemonStart ds = new DemonStart(event, blacklance, p);
		}
		//StableHand (Crasmeer)
		if (id == 204) {
			StableHand sh = new StableHand(event, blacklance, p);
		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void stopSunlight(EntityCombustEvent event) {
		if (event.getDuration() == 8) {
			event.setDuration(0);
		}
	}

	@EventHandler
	public void stopPush(NPCPushEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		if (!event.getEntity().getItemStack().hasItemMeta()) {
			event.setCancelled(true);
		} else {
			if (!event.getEntity().getItemStack().getItemMeta().hasLore()) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void bankClose(InventoryCloseEvent event) {
		Player p = (Player) event.getPlayer();
		if (event.getInventory().getName().equals("Bank")) {
			ItemStack[] inv = bank.get(p).getContents();
			p.getEnderChest().setContents(inv);
		}
	}
}
