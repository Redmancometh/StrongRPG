package com.strongholdcraft.blacklance;

import com.strongholdcraft.blacklance.storage.DBUtil;
import com.strongholdcraft.blacklance.storage.RPGPlayers;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandParser implements CommandExecutor {
	private ItemStack saddle = new ItemStack(Material.SADDLE);
	private ItemMeta imeta = saddle.getItemMeta();
	private List<String> lore = new ArrayList<String>();
	Plugin pl = BlackLance.pl;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("blreload") && (sender instanceof Player)) {
			if (p.isOp()) {
				PluginManager pm = pl.getServer().getPluginManager();
				pl.getPluginLoader().disablePlugin(pl);
				pl.getPluginLoader().enablePlugin(pl);
				p.sendMessage(ChatColor.GOLD + "BlackLance has been reloaded");
				return true;
			} else {
				p.sendMessage("Stop Messing With Our Shit Please, thanks");
			}
		}
		if (cmd.getName().equalsIgnoreCase("blevel") && (sender instanceof Player) && p.isOp()) {
			NPC ThisNPC = ((Citizens) pl.getServer().getPluginManager().getPlugin("Citizens")).getNPCSelector().getSelected(sender);
			blm blminstance = ThisNPC.getTrait(blm.class);
			if (args[0].equalsIgnoreCase("set") && args.length == 2) {
				blminstance.setLevel(Integer.parseInt(args[1]));
				p.sendMessage(ChatColor.GOLD + "[BlackLanceMobs] " + ChatColor.AQUA + "Level set to " + args[1]);
			}
			if (args[0].equalsIgnoreCase("get")) {
				p.sendMessage(ChatColor.GOLD + "[BlackLanceMobs] " + ChatColor.AQUA + "This NPC is level " + blminstance.getLevel());
			}
			if (args[0].equalsIgnoreCase("health")) {
				p.sendMessage(ChatColor.GOLD + "[BlackLanceMobs] " + ChatColor.AQUA + "This NPC has " + blminstance.getHealth() + " Health");
			}
			if (args[0].equalsIgnoreCase("mhealth")) {
				p.sendMessage(ChatColor.GOLD + "[BlackLanceMobs] " + ChatColor.AQUA + "This NPC has " + blminstance.getmHealth() + " Health");
			}
		}
		if (cmd.getName().equalsIgnoreCase("horse") && (sender instanceof Player) && args.length == 2) {
			if (p.hasPermission("bl.dhorse")) {
				if (args[1].equalsIgnoreCase("skeleton")) {
					imeta.setDisplayName("Skeletal Reins");
					lore.add("Summons a skeletal charger");
					imeta.setLore(lore);
					lore.clear();
					saddle.setItemMeta(imeta);
					p.getInventory().addItem(saddle);
				}
				if (args[1].equalsIgnoreCase("zombie")) {
					imeta.setDisplayName("Deathcharger Reins");
					lore.add("Summons a Deathcharger");
					imeta.setLore(lore);
					lore.clear();
					saddle.setItemMeta(imeta);
					p.getInventory().addItem(saddle);
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("lookup")) {
			RPGPlayer rp = RPGPlayers.getRPGPlayer(p);
			Bukkit.broadcastMessage(rp.getHealth() + " HEALTH");
		}
		if (cmd.getName().equals("setup") && p.isOp()) {
			try {
				DBUtil.setup(BlackLance.c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (cmd.getName().equalsIgnoreCase("resource")) {
			if (args[0].equalsIgnoreCase("add")) {
				Bukkit.broadcastMessage("ADD");
				try {
					Block b = p.getLocation().getBlock();
					switch (args[1]) {
						case "iron":
							Bukkit.broadcastMessage("IRON");
							b.setType(Material.IRON_ORE);
							DBUtil.addResourceNode(args[1], p.getLocation());
							break;
						case "gold":
							b.setType(Material.GOLD_ORE);
							DBUtil.addResourceNode(args[1], p.getLocation());
							break;
						case "hay":
							b.setType(Material.HAY_BLOCK);
							DBUtil.addResourceNode(args[1], p.getLocation());
							break;
						case "sweetgum":
							b.setTypeIdAndData(175, (byte) 4, true);
							DBUtil.addResourceNode(args[1], p.getLocation());
							break;
						default:
							p.sendMessage(ChatColor.DARK_RED + "Invalid Node");
							break;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (args[1].equalsIgnoreCase("remove")) {
				try {
					DBUtil.removeResourceNode(p.getTargetBlock(null, 5).getLocation());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return true;

	}
}
