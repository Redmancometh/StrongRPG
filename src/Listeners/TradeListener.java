package Listeners;

import Trade.PlayerTrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TradeListener implements Listener {
    @EventHandler
    public void startTrade(PlayerInteractEntityEvent event) {
        Entity entTradeWith = event.getRightClicked();
        if(!(entTradeWith instanceof Player)) return;
        Player trader = event.getPlayer();
        Player tradeWith = (Player)entTradeWith;
        PlayerTrade.tryTrade(trader, tradeWith);
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        PlayerTrade trade = PlayerTrade.tradeMap.get(event.getPlayer());
        if(trade != null && trade.state != PlayerTrade.TradeState.REQUEST) {
            Player player = (Player)event.getPlayer();
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
        if(trade != null) {
            player = event.getPlayer();
            Player partner = PlayerTrade.getPartner(player);
            player.sendMessage("Your trade partner has left the game.");
            PlayerTrade.reclaimItems(player);
            PlayerTrade.reclaimItems(partner);
            PlayerTrade.remove(trade);
            partner.closeInventory();
        }
        List<PlayerTrade> trades = PlayerTrade.tradesToPlayerMap.get(event.getPlayer());
        if(trades != null) {
            player = event.getPlayer();
            for(PlayerTrade t : trades) {
                t.trader.sendMessage("Your trade partner has left the game.");
                PlayerTrade.remove(t);
            }
        }
    }

    @EventHandler
    public void clickItem(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Inventory inventory = event.getInventory();
        Inventory tradeInv = view.getTopInventory();
        if(PlayerTrade.tradeInventories.containsValue(inventory)) {
            Inventory playerInv = view.getBottomInventory();
            Player player = (Player)event.getWhoClicked();
            PlayerTrade trade = PlayerTrade.tradeMap.get(player);
            event.setCancelled(true);
            int slot = event.getRawSlot();
            System.out.println(slot);
            // Check for clicking the accept button.
            if((slot - 4) % 9 == 0) {
                if(slot <= 13) {
                    PlayerTrade.accept(player);
                    return;
                } else if(slot <= 31) {
                    PlayerTrade.decline(player);
                    return;
                }
            }

            Player partner = PlayerTrade.getPartner(player);
            Inventory partnerInv = PlayerTrade.tradeInventories.get(partner);
            ItemStack stack = event.getCurrentItem();

            if(trade.state != PlayerTrade.TradeState.TRADE) return;
            if(slot > 35) {
                // Add to trade.
                if(stack.getType() == Material.GHAST_TEAR && stack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Coin Purse")) {
                    // Don't let the player trade the coin purse.
                    return;
                }
                int nextSlot = PlayerTrade.getNextSlot(tradeInv);
                if(nextSlot != -1) {
                    inventory.setItem(nextSlot, event.getCurrentItem());
                    partnerInv.setItem(nextSlot + 5, event.getCurrentItem());
                    playerInv.removeItem(event.getCurrentItem());
                }
            } else if(slot != -999 && slot % 9 < 4) {
                // Remove from trade.
                playerInv.addItem(stack);
                tradeInv.removeItem(stack);
                partner.getOpenInventory().getTopInventory().removeItem(stack);
            }
        }
    }
}
