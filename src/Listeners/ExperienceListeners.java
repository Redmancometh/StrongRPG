package Listeners;

import BlackLance.RPGPlayer;
import Storage.RPGPlayers;
import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class ExperienceListeners implements Listener {
	Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		event.setKeepLevel(true);
	}

	@EventHandler
	public void onLevel(PlayerExpChangeEvent event) {
		Player p = event.getPlayer();
		if (event.getAmount() >= (1 - p.getExp()) * p.getExpToLevel()) {
			p.sendMessage(ChatColor.GOLD + "You have gained a level! Gratz on getting level " + (p.getLevel() + 1) + "!");
			if (p.getLevel() > 21) {
				RPGPlayer rp = RPGPlayers.getRPGPlayer(p);

				p.sendMessage("You have gained some maximum health!");
			} else {
				p.sendMessage(ChatColor.GREEN + "You will start gaining max health in " + (21 - p.getLevel()) + " levels!");
			}
		}

	}


}


