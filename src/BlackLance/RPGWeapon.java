package BlackLance;

import Storage.DBUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RPGWeapon {
	public int uid;
	public int mindmg;
	public int maxdmg;

	public RPGWeapon(int mindmg, int maxdmg) {
		this.mindmg = mindmg;
		this.maxdmg = maxdmg;
	}

	public static void makeWeapons(final RPGPlayer rp) throws SQLException {
		new BukkitRunnable() {
			@Override
			public void run() {
				Inventory i = rp.getPlayer().getInventory();
				for (ItemStack item : i) {
					if (item != null && item.getTypeId() != 0 && item.getType() != Material.POTION) {
						if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
							ItemMeta imeta = item.getItemMeta();
							if (imeta.getLore().toString().contains("Damage")) {
								if (imeta.getLore().size() < 3) {
									String name = imeta.getDisplayName();
									int[] damages = getDamages(item);
									try {
										DBUtil.setWeaponData(name, damages[0], damages[1], rp.getUID());
									} catch (SQLException e) {
										e.printStackTrace();
									}
									List<String> lore = new ArrayList<String>();
									lore = imeta.getLore();
									try {
										Bukkit.broadcastMessage("ADDING");
										String itemid = Integer.toString(DBUtil.getWeaponData(name, damages[0], damages[1], rp.getUID()));
										rp.weapons.put(Integer.parseInt(itemid), new RPGWeapon(damages[0], damages[1]));
										String hiddenid = convertToInvisibleString(itemid);
										lore.add(hiddenid);
									} catch (SQLException e) {
										e.printStackTrace();
									}
									imeta.setLore(lore);
									item.setItemMeta(imeta);
								} else {
									int[] damages = getDamages(item);
									rp.weapons.put(getWeaponID(item), new RPGWeapon(damages[0], damages[1]));
								}
							}
						}
					}
				}
			}
		}.runTaskLater(BlackLance.pl, 10);
	}

	public static String convertToInvisibleString(String s) {
		String hidden = "";
		for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR + "" + c;
		return hidden;
	}

	public static int[] getDamages(ItemStack weapon) {
		int damages[] = new int[2];
		String damage = weapon.getItemMeta().getLore().toString();
		if (damage.charAt(10) == '-') {
			damages[0] = Integer.parseInt(damage.substring(9, 10));
		} else {
			damages[0] = Integer.parseInt(damage.substring(9, 11));
		}
		String[] hcheck = damage.split("-");
		if (hcheck.length == 1) {
			damages[1] = Integer.parseInt(hcheck[1].substring(0, 1));
		}
		if (hcheck.length == 2) {
			if (hcheck[1].charAt(1) == ',' || hcheck[1].charAt(1) == ']') {
				damages[1] = Integer.parseInt(hcheck[1].substring(0, 1));
			} else {
				damages[1] = Integer.parseInt(hcheck[1].substring(0, 2));
			}
		}
		if (hcheck.length == 3) {
			if (hcheck[1].charAt(2) == ',' || hcheck[1].charAt(2) == ']') {
				damages[1] = Integer.parseInt(hcheck[1].substring(0, 2));
			} else {
				damages[1] = Integer.parseInt(hcheck[1].substring(0, 3));
			}
		}
		return damages;
	}

	public static int getWeaponID(ItemStack i) {
		return Integer.parseInt(i.getItemMeta().getLore().get(2).replaceAll("�", ""));
	}
}
