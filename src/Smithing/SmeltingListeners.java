package Smithing;

import Util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SmeltingListeners implements Listener {
	@EventHandler
	public void smithOre(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.FURNACE) {
			Bukkit.broadcastMessage("TEST");
			Player p = e.getPlayer();
			int id = p.getItemInHand().getTypeId();
			Inventory inv = p.getInventory();
			if (id == 15) {
				if (ItemUtil.getAmountInInventory(p, Material.COAL_ORE) >= 2) {
					inv.removeItem(new ItemStack(Material.COAL_ORE, 2));
					inv.removeItem(new ItemStack(Material.IRON_ORE));
					p.sendMessage(ChatColor.GOLD + "You place an iron ore and 2 coal ore in the furnace and get an iron ingot");
					inv.addItem(new ItemStack(Material.IRON_INGOT));
					p.updateInventory();
				} else {
					p.sendMessage(ChatColor.DARK_RED + "You need 2 coal ore to smelt iron!");
				}
			}
			if (id == 14) {
				if (ItemUtil.getAmountInInventory(p, Material.COAL_ORE) >= 4) {
					inv.removeItem(new ItemStack(Material.COAL_ORE, 4));
					inv.removeItem(new ItemStack(Material.IRON_ORE));
					p.sendMessage(ChatColor.GOLD + "You place an iron ore and 4 coal ore in the furnace and get an iron ingot");
					inv.addItem(new ItemStack(Material.IRON_INGOT));
					p.updateInventory();
				} else {
					p.sendMessage(ChatColor.DARK_RED + "You need 4 coal ore to smelt gold!");
				}
			}
			if (id == 265) {
				if (!p.getItemInHand().hasItemMeta()) {
					if (ItemUtil.getAmountInInventory(p, Material.COAL_ORE) >= 6) {
						inv.removeItem(new ItemStack(Material.COAL_ORE, 6));
						inv.removeItem(new ItemStack(Material.IRON_INGOT));
						p.sendMessage(ChatColor.GOLD + "You place an iron ingot and 6 coal ore in the furnace and refine your ingot!");
						ItemStack steelbar = new ItemStack(Material.IRON_INGOT);
						ItemMeta imeta = steelbar.getItemMeta();
						imeta.setDisplayName("Steel Ingot");
						steelbar.setItemMeta(imeta);
						inv.addItem(steelbar);
						p.updateInventory();
					} else {
						p.sendMessage(ChatColor.DARK_RED + "You need 6 coal ore to refine this iron!");
					}
				}
			}
		}
	}

	@EventHandler
	public void cancelFurnace(InventoryOpenEvent e) {
		if (e.getInventory().getType() == InventoryType.FURNACE) {
			e.setCancelled(true);
		}
	}

}
