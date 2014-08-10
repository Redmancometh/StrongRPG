package com.strongholdcraft.blacklance.storage;

import com.earth2me.essentials.User;
import com.strongholdcraft.blacklance.RPGPlayer;
import me.confuser.barapi.BarAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RPGPlayers {
	public static HashMap<Player, RPGPlayer> RPGPlayers = new HashMap();

	public static HashMap getFullMap() {
		return RPGPlayers;
	}

	public static RPGPlayer getRPGPlayer(Player p) {
		RPGPlayer rp = RPGPlayers.get(p);
		return rp;
	}

	public static void addRPGPlayer(Player p, RPGPlayer rp) {
		RPGPlayers.put(p, rp);
	}

	public static void addXP(Player p, int xp) {
		RPGPlayer rp = RPGPlayers.get(p);
		User u = rp.getUser(p);
		rp.addXP(xp);
		if (rp.getXP() >= 100) {
			float cexp = (p.getExp() * p.getExpToLevel());
			while (rp.getXP() >= 100) {
				if (p.getExpToLevel() - cexp == 1) {
					p.sendMessage(ChatColor.GOLD + "Congratulations on level " + (p.getLevel() + 1) + "!");
					rp.setMaxHealth(p, false);
					String healthdisplay = ChatColor.DARK_GREEN + "Health:  " + ChatColor.DARK_RED + rp.getHealth() + "/" + rp.getMaxHealth();
					BarAPI.setMessage(p, healthdisplay, (Math.abs(rp.getHealth() / rp.getMaxHealth()) * 100));
				}
				rp.setXP((int) (rp.getXP() - 100));
				u.giveExp(1);
			}
		}
	}

	public static double getXP(Player p) {
		return RPGPlayers.get(p).getXP();
	}

}
