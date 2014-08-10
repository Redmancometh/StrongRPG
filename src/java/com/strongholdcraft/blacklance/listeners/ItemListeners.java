package com.strongholdcraft.blacklance.listeners;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.*;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.BigDecimal;
import java.util.HashMap;

public class ItemListeners implements Listener {
	//private boolean canmove=true;
	public HashMap<Player, Boolean> canmove = new HashMap();
	public HashMap<Player, Boolean> iscalling = new HashMap();

	private Location nomove;
	PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 9999, 1);
	PotionEffect speed2 = new PotionEffect(PotionEffectType.SPEED, 9999, 3);
	private JavaPlugin pl;

	public ItemListeners(JavaPlugin pl) {
		this.pl = pl;
	}

	@EventHandler
	public void checkCoins(PlayerInteractEvent event) throws UserDoesNotExistException {
		Player p = event.getPlayer();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName() != null) {
				if (p.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Coin Purse")) {
					BigDecimal bal = Economy.getMoneyExact(p.getName());
					p.sendMessage(ChatColor.GOLD + "You currently have " + bal + " steel coins!");
				}
			}
		}
	}

	/*@EventHandler
	public void wandEvent(PlayerInteractEvent event)
	{
		if(event.hasItem())
		{
			if(event.getItem().getType()==Material.STICK)
			{
				WandProcessor wp = new WandProcessor(event.getItem(), event, pl);
			}
			else{return;}
		}
	}*/
	@EventHandler
	public void callHorse(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (p.getItemInHand().getType() == Material.SADDLE) {
			if (!iscalling.containsKey(p)) {
				if (!p.isInsideVehicle()) {
					final Location loc1 = p.getLocation();
					final World w = p.getWorld();
					ItemMeta im = p.getItemInHand().getItemMeta();
					String saddlename = p.getItemInHand().getItemMeta().getDisplayName();
					if (saddlename.contains("Basic") && im.hasLore()) {
						p.sendMessage(ChatColor.GOLD + "Calling your horse..he'll be here in 5 seconds..you won't be able to move!");
						nomove = p.getLocation();
						canmove.put(p, false);
						iscalling.put(p, false);
						Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
							public void run() {
								iscalling.remove(p);
								Horse h = w.spawn(loc1, Horse.class);
								h.setStyle(Style.WHITE_DOTS);
								h.setColor(Color.CHESTNUT);
								h.setVariant(Variant.HORSE);
								h.setCustomName("Chestnut Horse");
								h.addPotionEffect(speed);
								Mount(h, p);
								canmove.remove(p);
							}
						}, 60);
					}
					if (saddlename.contains("Skeletal Stallion") && im.hasLore()) {
						if (p.hasPermission("sc.skeletalstallion")) {
							p.sendMessage(ChatColor.GOLD + "Calling your horse..he'll be here in 5 seconds..you won't be able to move!");
							nomove = p.getLocation();
							canmove.put(p, false);
							iscalling.put(p, false);
							Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
								public void run() {
									iscalling.remove(p);
									Horse h = w.spawn(loc1, Horse.class);
									h.setVariant(Variant.SKELETON_HORSE);
									h.setJumpStrength(1);
									h.setCustomName("Skeletal Charger");
									h.addPotionEffect(speed2);
									Mount(h, p);
									canmove.remove(p);
								}
							}, 60);
						} else {
							p.sendMessage("Donate to unlock this feature at strongholdcraft.com!");
						}
					}
					if (saddlename.contains("Deathcharger") && im.hasLore()) {
						if (p.hasPermission("sc.deathcharger")) {
							p.sendMessage(ChatColor.GOLD + "Calling your horse..he'll be here in 5 seconds..you won't be able to move!");
							nomove = p.getLocation();
							canmove.put(p, false);
							iscalling.put(p, false);
							Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
								public void run() {
									iscalling.remove(p);
									Horse h = w.spawn(loc1, Horse.class);
									h.setVariant(Variant.UNDEAD_HORSE);
									h.setCustomName("Deathcharger");
									h.setJumpStrength(1);
									h.addPotionEffect(speed2);
									Mount(h, p);
									canmove.remove(p);
								}
							}, 60);
						} else {
							p.sendMessage("Donate to unlock this feature at strongholdcraft.com!");
						}
					}
					if ((saddlename.contains("Riveted") || saddlename.contains("Sturdy")) && im.hasLore()) {
						if (p.hasPermission("sc.blackcharger")) {
							p.sendMessage(ChatColor.GOLD + "Calling your horse..he'll be here in 5 seconds..you won't be able to move!");
							canmove.put(p, false);
							iscalling.put(p, false);
							Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
								public void run() {
									iscalling.remove(p);
									Horse h = w.spawn(loc1, Horse.class);
									h.addPotionEffect(speed2);
									h.setStyle(Style.WHITE_DOTS);
									h.setColor(Color.BLACK);
									h.setVariant(Variant.HORSE);
									h.setCustomName("Black Charger");
									Mount(h, p);
									canmove.remove(p);
								}
							}, 60);
						} else {
							p.sendMessage("Donate to unlock this feature at strongholdcraft.com!");
						}
					}
				} else {
					p.sendMessage(ChatColor.DARK_RED + "You are already mounted, GG");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "You're already calling a horse!");
			}
		}
	}

	public void Mount(Horse h, Player p) {
		h.setTamed(true);
		h.setOwner(p);
		h.setAge(0);
		h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
		h.setPassenger(p);
	}

	@EventHandler
	public void stopMoving(PlayerMoveEvent event) {
		if (canmove.containsKey(event.getPlayer())) {
			if (!canmove.get(event.getPlayer())) {
				Location from = event.getFrom();
				Location to = event.getTo();
				double x = Math.floor(from.getX());
				double z = Math.floor(from.getZ());
				if (Math.floor(to.getX()) != x || Math.floor(to.getZ()) != z) {
					x += .5;
					z += .5;
					event.getPlayer().teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
				}
			}
		}
	}

	@EventHandler
	public void clearHorse(EntityDeathEvent event) {
		if (event.getEntity() instanceof Horse) {
			event.getDrops().clear();
		}
	}

	@EventHandler
	public void byeHorse(VehicleExitEvent event) {
		if (event.getVehicle() instanceof Horse) {
			Vehicle h = event.getVehicle();
			h.remove();
		}
	}

	@EventHandler
	public void stopHorseInv(InventoryOpenEvent event) {
		if (event.getInventory().getName().equals("Chestnut Horse")) {
			event.setCancelled(true);
		}
		if (event.getInventory().getName().equals("Black Charger")) {
			event.setCancelled(true);
		}
		if (event.getInventory().getName().equals("Deathcharger")) {
			event.setCancelled(true);
		}
		if (event.getInventory().getName().equals("Skeletal Charger")) {
			event.setCancelled(true);
		}
	}
}
