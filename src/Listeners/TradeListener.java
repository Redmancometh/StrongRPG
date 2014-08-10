package Listeners;

import Storage.RPGPlayers;
import Trade.PlayerTrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TradeListener implements Listener {
	@EventHandler
	public void startTrade(PlayerInteractEntityEvent event) {
		Entity entTradeWith = event.getRightClicked();
		if (!(RPGPlayers.RPGPlayers.containsKey(entTradeWith))) return;
		Player trader = event.getPlayer();
		Player tradeWith = (Player) entTradeWith;
		PlayerTrade.tryTrade(trader, tradeWith);
	}

	@EventHandler
	public void closeInventory(InventoryCloseEvent event) {
		PlayerTrade trade = PlayerTrade.tradeMap.get(event.getPlayer());
		if (trade != null && trade.state != PlayerTrade.TradeState.REQUEST) {
			Player player = (Player) event.getPlayer();
			Player partner = PlayerTrade.getPartner(player);
			// Decline the trade.
			player.sendMessage("You declined the trade.");
			partner.sendMessage(player.getDisplayName() + " declined the trade.");
			PlayerTrade.reclaimItems(player);
			PlayerTrade.reclaimItems(partner);
			PlayerTrade.remove(trade);
			partner.closeInventory();
		}
	}

	@EventHandler
	public void playerDisconnect(PlayerQuitEvent event) {
		PlayerTrade trade = PlayerTrade.tradeMap.get(event.getPlayer());
		Player player;
		if (trade != null) {
			player = event.getPlayer();
			Player partner = PlayerTrade.getPartner(player);
			player.sendMessage("Your trade partner has left the game.");
			PlayerTrade.reclaimItems(player);
			PlayerTrade.reclaimItems(partner);
			PlayerTrade.remove(trade);
			partner.closeInventory();
		}
		List<PlayerTrade> trades = PlayerTrade.tradesToPlayerMap.get(event.getPlayer());
		if (trades != null) {
			player = event.getPlayer();
			for (PlayerTrade t : trades.toArray(new PlayerTrade[0])) {
				t.trader.sendMessage("Your trade partner has left the game.");
				PlayerTrade.remove(t);
			}
		}
	}

	@EventHandler
	public void playerDied(PlayerDeathEvent event) {
		PlayerTrade trade = PlayerTrade.tradeMap.get(event.getEntity());
		Player player;
		if (trade != null) {
			player = event.getEntity();
			Player partner = PlayerTrade.getPartner(player);
			player.sendMessage("Your trade partner has died. :(");
			PlayerTrade.reclaimItems(player);
			PlayerTrade.reclaimItems(partner);
			PlayerTrade.remove(trade);
			partner.closeInventory();
		}
		List<PlayerTrade> trades = PlayerTrade.tradesToPlayerMap.get(event.getEntity());
		if (trades != null) {
			player = event.getEntity();
			for (PlayerTrade t : trades.toArray(new PlayerTrade[0])) {
				t.trader.sendMessage("Your trade partner has died. :(");
				PlayerTrade.remove(t);
			}
		}
	}

	@EventHandler
	public void playerMoved(PlayerMoveEvent event) {
		PlayerTrade trade = PlayerTrade.tradeMap.get(event.getPlayer());
		Player player = event.getPlayer();
		Player partner;
		if (trade != null) {
			partner = PlayerTrade.getPartner(player);
			if (partner != null && partner.getLocation().distance(player.getLocation()) > 10) {
				player.sendMessage(partner.getLocation().distance(player.getLocation()) + " dist");
				player.sendMessage("You moved too far from your trade partner.");
				partner.sendMessage("Your trade partner moved to far away from you.");
				PlayerTrade.reclaimItems(player);
				PlayerTrade.reclaimItems(partner);
				PlayerTrade.remove(trade);
				player.closeInventory();
				partner.closeInventory();
			}
		}
		List<PlayerTrade> trades = PlayerTrade.tradesToPlayerMap.get(player);
		if (trades != null) {
			for (PlayerTrade t : trades.toArray(new PlayerTrade[0])) {
				partner = t.trader;
				if (partner != null && partner.getLocation().distance(player.getLocation()) > 10) {
					partner.sendMessage("Your trade partner moved to far away from you.");
					PlayerTrade.remove(t);
				}
			}
		}
	}

	@EventHandler
	public void clickItem(InventoryClickEvent event) {
		InventoryView view = event.getView();
		Inventory inventory = event.getInventory();
		Inventory tradeInv = view.getTopInventory();
		if (PlayerTrade.tradeInventories.containsValue(inventory)) {
			Inventory playerInv = view.getBottomInventory();
			Player player = (Player) event.getWhoClicked();
			PlayerTrade trade = PlayerTrade.tradeMap.get(player);
			event.setCancelled(true);
			int slot = event.getRawSlot();
			System.out.println(slot);
			// Check for clicking the accept button.
			if ((slot - 4) % 9 == 0) {
				if (slot <= 13) {
					PlayerTrade.accept(player);
					return;
				} else if (slot <= 31) {
					PlayerTrade.decline(player);
					return;
				}
			}

			Player partner = PlayerTrade.getPartner(player);
			Inventory partnerInv = PlayerTrade.tradeInventories.get(partner);
			ItemStack stack = event.getCurrentItem();

			if (trade.state != PlayerTrade.TradeState.TRADE) return;
			if (slot > 35) {
				// Add to trade.
				if (stack.getType() == Material.GHAST_TEAR && stack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Coin Purse")) {
					// Don't let the player trade the coin purse.
					return;
				}
				int nextSlot = PlayerTrade.getNextSlot(tradeInv);
				if (nextSlot != -1) {
					inventory.setItem(nextSlot, event.getCurrentItem());
					partnerInv.setItem(nextSlot + 5, event.getCurrentItem());
					playerInv.removeItem(event.getCurrentItem());
				}
			} else if (slot != -999 && slot % 9 < 4) {
				// Remove from trade.
				playerInv.addItem(stack);
				tradeInv.removeItem(stack);
				partner.getOpenInventory().getTopInventory().removeItem(stack);
			}
		}
	}
}
