package Util;

import BlackLance.BlackLance;
import Storage.RPGPlayers;
import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Harvest {
	List<String> lore = new ArrayList<String>();
	ItemMeta harvestmeta;
	String harvestname = "";
	Block b;

	public Harvest(Block b, Player p, BlackLance blacklance) {
		this.b = b;
		harvestProcessor(b, p, blacklance);
	}

	public void harvestProcessor(Block b, Player p, BlackLance blacklance) {
		if (b.getType() == Material.HAY_BLOCK) {
			harvestHay(b, p, blacklance);
		}
	}

	public void harvestHay(final Block b, final Player p, final BlackLance blacklance) {
		new BukkitRunnable() {
			int x = 0;
			Location loc1 = b.getLocation().add(new Location(p.getWorld(), .5, 1.6, 0));
			Hologram hologram = HolographicDisplaysAPI.createIndividualHologram(blacklance, loc1, p, "\u25A0");
			String loadingbar = "\u25A0";

			public void run() {
				loadingbar = loadingbar + loadingbar;
				hologram.setLine(0, ChatColor.YELLOW + loadingbar);
				hologram.update();
				x++;
				if (x == 5) {
					DropUtil du = new DropUtil(blacklance);
					ItemStack harvested = new ItemStack(Material.HAY_BLOCK);
					harvestmeta = harvested.getItemMeta();
					harvestmeta.setDisplayName("Hay");
					lore.add("Good horse food");
					lore.add(ChatColor.BLUE + "Sell Value: 1");
					lore.add("PlayerI" + p.getUniqueId());
					harvestmeta.setLore(lore);
					harvested.setItemMeta(harvestmeta);
					b.setType(Material.AIR);
					CraftItem ci = (CraftItem) p.getWorld().dropItem(b.getLocation(), harvested);
					du.hide(p, ci);
					BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
					RPGPlayers.addXP(p, 5);
					hologram.delete();
					this.cancel();
				}
			}
		}.runTaskTimer(blacklance, 10, 10);
		new BukkitRunnable() {
			public void run() {
				Collections.shuffle(ResourceNodes.hayLocations);
				boolean set = false;
				while (!set) {
					Block b = ResourceNodes.hayLocations.get(0).getBlock();
					if (b.getType() != Material.HAY_BLOCK) {
						b.setType(Material.HAY_BLOCK);
						set = true;
					} else {
						Collections.shuffle(ResourceNodes.hayLocations);
					}
				}
			}
		}.runTaskLater(blacklance, 600);

	}
}
