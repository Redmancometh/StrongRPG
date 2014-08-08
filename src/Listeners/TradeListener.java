package Listeners;

import Trade.PlayerTrade;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class TradeListener implements Listener {
    @EventHandler
    public void startTrade(PlayerInteractEntityEvent event) {
        System.out.println("right clicking something!");
        Entity entTradeWith = event.getRightClicked();
        if(!(entTradeWith instanceof Player)) return;
        System.out.println("it's a player!");
        Player trader = event.getPlayer();
        Player tradeWith = (Player)entTradeWith;
        PlayerTrade.tryTrade(trader, tradeWith);
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        System.out.println("closed some inventory");
        PlayerTrade trade = PlayerTrade.tradeMap.get(event.getPlayer());
        if(trade != null && trade.started) {
            System.out.println("closed trade inventory");
            Player player = (Player)event.getPlayer();
            // Decline the trade.
            player.sendMessage("You declined the trade.");
            trade.tradeWith.sendMessage(trade.trader.getDisplayName() + " declined the trade.");
            PlayerTrade.tradeMap.put(trade.trader, null);
            PlayerTrade.tradeMap.put(trade.tradeWith, null);
            trade.tradeWith.closeInventory();
        }
    }

    @EventHandler
    public void playerDisconnect(PlayerQuitEvent event) {
        PlayerTrade trade = PlayerTrade.tradeMap.get(event.getPlayer());
        Player player;
        if(trade != null) {
            player = (Player)event.getPlayer();
            player.sendMessage("Your trade partner has left the game.");
            PlayerTrade.remove(trade);
        }
        List<PlayerTrade> trades = PlayerTrade.tradesToPlayerMap.get(event.getPlayer());
        if(trades != null) {
            player = (Player)event.getPlayer();
            for(PlayerTrade t : trades) {
                t.trader.sendMessage("Your trade partner has left the game.");
                PlayerTrade.remove(t);
            }
        }
    }
}
