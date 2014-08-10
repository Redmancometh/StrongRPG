package Listeners;

import Storage.RPGPlayers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class ConsumableListener implements Listener {
	Random r = new Random();
	private JavaPlugin blacklance;

	public ConsumableListener(JavaPlugin blacklance) {
		this.blacklance = blacklance;
	}

	String itemname = " ";

	@EventHandler
	public void onDrink(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		final CraftPlayer cp = (CraftPlayer) event.getPlayer();
		if (event.getPlayer().getItemInHand() != null) {
			if (event.getPlayer().getItemInHand().hasItemMeta()) {
				ItemStack i = event.getPlayer().getItemInHand();
				String itemname = i.getItemMeta().getDisplayName();
				if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && itemname != null) {
					if (itemname.contains("Minor Healing Potion")) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(blacklance, new Runnable() {
							public void run() {
								p.setItemInHand(new ItemStack(Material.GLASS_BOTTLE));
								float healing = ((10) + r.nextInt(10));
								RPGPlayers.getRPGPlayer(p).healPlayer(healing, p);
								p.sendMessage(ChatColor.GREEN + "Your potion heals you for " + healing);
							}
						});
					}
					if (itemname.contains("Light Healing Potion")) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(blacklance, new Runnable() {
							public void run() {
								p.setItemInHand(new ItemStack(Material.GLASS_BOTTLE));
								float healing = ((15) + r.nextInt(15));
								RPGPlayers.getRPGPlayer(p).healPlayer(healing, p);
								p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Combat" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + "Your potion heals you for " + healing);
							}
						});
					}
				}

			}
		}
	}
}
