package Listeners;

import Wands.WandProcessor;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;

public class ItemListeners implements Listener {
	//private boolean canmove=true;

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

	@EventHandler
	public void wandEvent(PlayerInteractEvent event) {
		if (event.hasItem()) {
			if (event.getItem().getType() == Material.STICK) {
				WandProcessor wp = new WandProcessor(event.getItem(), event, pl);
			} else {
				return;
			}
		}
	}
}
