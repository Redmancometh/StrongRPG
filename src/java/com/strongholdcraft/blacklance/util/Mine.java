package com.strongholdcraft.blacklance.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mine {
	private Material m = Material.IRON_ORE;
	private Player p;
	private Block b;
	private JavaPlugin plugin;
	private Inventory pinv;
	private ItemStack ore = new ItemStack(Material.IRON_ORE);
	private ItemMeta oremeta = ore.getItemMeta();
	private List<String> lore = new ArrayList<String>();
	static HashMap<Location, Material> regen = new HashMap();

	public Mine(Material m, Player p, Block b, JavaPlugin plugin) {
		this.m = m;
		this.p = p;
		this.b = b;
		this.plugin = plugin;
		this.pinv = this.p.getInventory();
	}

	public void processEvent(Material m) {
		if (m == Material.IRON_ORE) {
			mineIron(p, b);
		}
		if (m == Material.GOLD_ORE) {
			mineGold(p, b);
		}
		if (m == Material.DIAMOND_ORE) {
			mineDiamond(p, b);
		}
		if (m == Material.REDSTONE_ORE) {
			mineRedstone(p, b);
		}
		if (m == Material.COAL_ORE) {
			mineCoal(p, b);
		}
		if (m == Material.EMERALD_ORE) {
			mineEmerald(p, b);
		}
		if (m == Material.LAPIS_ORE) {
			mineLapis(p, b);
		}
	}

	private void mineIron(Player p, final Block b) {
		oremeta.setDisplayName("Raw Iron");
		lore.add("Unrefined Iron Ore");
		oremeta.setLore(lore);
		lore.clear();
		ore.setItemMeta(oremeta);
		double result = Math.random();
		if (!regen.containsKey(b.getLocation())) {
			regen.put(b.getLocation(), b.getType());
		}
		b.setType(Material.STONE);
		if (result > .20) {
			pinv.addItem(ore);
			p.sendMessage(ChatColor.GOLD + "You mine some iron succesfully!");
		}
		if (result <= .20 && result > .1) {
			p.sendMessage(ChatColor.GOLD + "You fail to successfully get any iron ore!");
		}
		if (result <= .05) {
			p.sendMessage(ChatColor.RED + "Your mining attempt sparks off an explosion!");
			Location loc1 = p.getLocation();
			p.getWorld().createExplosion(loc1.getX(), loc1.getY(), loc1.getZ(), (float) 1, false, false);
		}
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				Location loc1;
				b.setType(Material.IRON_ORE);
			}
		}, 440L);
	}

	private void mineGold(Player p, final Block b) {
		if (!regen.containsKey(b.getLocation())) {
			regen.put(b.getLocation(), b.getType());
		}
		ore.setType(Material.GOLD_ORE);
		oremeta.setDisplayName("Raw Gold");
		lore.add("Unrefined Gold Ore");
		oremeta.setLore(lore);
		lore.clear();
		ore.setItemMeta(oremeta);
		b.setType(Material.STONE);
		pinv.addItem(ore);
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				b.setType(Material.GOLD_ORE);
			}
		}, 140L);
	}

	private void mineDiamond(Player p, final Block b) {
		if (!regen.containsKey(b.getLocation())) {
			regen.put(b.getLocation(), b.getType());
		}
		ore.setType(Material.DIAMOND_ORE);
		oremeta.setDisplayName("Raw Diamond");
		lore.add("Unrefined Diamonds");
		oremeta.setLore(lore);
		lore.clear();
		ore.setItemMeta(oremeta);
		b.setType(Material.STONE);
		pinv.addItem(new ItemStack(Material.DIAMOND_ORE));
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				b.setType(Material.DIAMOND_ORE);
			}
		}, 950L);
	}

	private void mineLapis(Player p, final Block b) {
		if (!regen.containsKey(b.getLocation())) {
			regen.put(b.getLocation(), b.getType());
		}
		ore.setType(Material.LAPIS_ORE);
		oremeta.setDisplayName("Unrefined Lapis");
		lore.add("Raw Lapis Lazuli");
		oremeta.setLore(lore);
		lore.clear();
		ore.setItemMeta(oremeta);
		b.setType(Material.STONE);
		pinv.addItem(new ItemStack(Material.LAPIS_ORE));
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				b.setType(Material.LAPIS_ORE);
			}
		}, 750L);
	}

	private void mineRedstone(Player p, final Block b) {
		if (!regen.containsKey(b.getLocation())) {
			regen.put(b.getLocation(), b.getType());
		}
		ore.setType(Material.REDSTONE);
		oremeta.setDisplayName("Eternal Essence");
		lore.add("Powder to Power Magical Things");
		oremeta.setLore(lore);
		lore.clear();
		ore.setItemMeta(oremeta);
		b.setType(Material.STONE);
		pinv.addItem(new ItemStack(Material.REDSTONE_ORE));
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				b.setType(Material.REDSTONE_ORE);
			}
		}, 860L);
	}

	private void mineCoal(Player p, final Block b) {
		if (!regen.containsKey(b.getLocation())) {
			regen.put(b.getLocation(), b.getType());
		}
		ore.setType(Material.COAL);
		oremeta.setDisplayName("Coal");
		lore.add("Common coal, used to smelt things");
		oremeta.setLore(lore);
		lore.clear();
		ore.setItemMeta(oremeta);
		b.setType(Material.STONE);
		pinv.addItem(new ItemStack(Material.COAL_ORE));
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				b.setType(Material.COAL_ORE);
			}
		}, 440L);
	}

	private void mineEmerald(Player p, final Block b) {
		if (!regen.containsKey(b.getLocation())) {
			regen.put(b.getLocation(), b.getType());
		}
		ore.setType(Material.EMERALD);
		oremeta.setDisplayName("Emerald");
		lore.add("Highly rare, Highly Valuable");
		oremeta.setLore(lore);
		lore.clear();
		ore.setItemMeta(oremeta);
		b.setType(Material.STONE);
		pinv.addItem(new ItemStack(Material.EMERALD_ORE));
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				b.setType(Material.EMERALD_ORE);
			}
		}, 950L);
	}

	public static HashMap<Location, Material> getRegen() {
		return regen;
	}
}
