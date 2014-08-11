package Trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerTrade {
    public static final Map<Player, PlayerTrade> tradeMap = new HashMap<Player, PlayerTrade>();
    public static final Map<Player, List<PlayerTrade>> tradesToPlayerMap = new HashMap<Player, List<PlayerTrade>>();

    public static PlayerTrade getTrade(Player player) {
        return tradeMap.get(player);
    }

    public static boolean isRequestingTrade(List<PlayerTrade> trades, Player tradeWith) {
        for(PlayerTrade trade : trades) {
            if(trade.trader == tradeWith) {
                return true;
            }
        }
        return false;
    }

    public static void tryTrade(Player trader, Player tradeWith) {
        System.out.println("a");
        PlayerTrade trade = tradeMap.get(trader);
        if(trade != null && trade.started) {
            return;
        }
        System.out.println("b");

        List<PlayerTrade> trades = tradesToPlayerMap.get(trader);
        if(trades == null) {
            System.out.println("c");
            trades = new ArrayList<PlayerTrade>();
            tradesToPlayerMap.put(trader, trades);
        }
        System.out.println("d");
        for(PlayerTrade t : trades) {
            System.out.println(t.trader.getDisplayName());
        }
        System.out.println("tradeWith=" + tradeWith.getDisplayName());

        if(isRequestingTrade(trades, tradeWith)) {
            System.out.println("e");
            tradeWith.sendMessage("Accepted trade request.");
            trader.sendMessage(tradeWith.getDisplayName() + " accepted your trade request.");
            startTrade(tradeMap.get(tradeWith));
        } else {
            System.out.println("f");
            requestTrade(trader, tradeWith);
        }
    }

    public static void startTrade(PlayerTrade trade) {
        System.out.println(trade);
        System.out.println(trade.tradeWith);
        tradeMap.put(trade.tradeWith, trade);
        Inventory tradeInv = Bukkit.createInventory(null, 27, "Trade");
        trade.inventory = tradeInv;
        trade.trader.openInventory(tradeInv);
        trade.tradeWith.openInventory(tradeInv);
        trade.started = true;
    }

    public static void requestTrade(Player trader, Player tradeWith) {
        PlayerTrade trade;
        PlayerTrade prevTrade = tradeMap.get(trader);
        if(prevTrade != null) {
            System.out.println("g");
            if(prevTrade.tradeWith == tradeWith) {
                System.out.println("h");
                // Just refresh the trade counter.
                return;
            }
            System.out.println("i");
            // Cancel the original trade.
            prevTrade.tradeWith.sendMessage(trader.getDisplayName() + " has cancelled the trade.");
            tradesToPlayerMap.get(prevTrade.tradeWith).remove(prevTrade);
        }
        System.out.println("j");

        // Create the trade and add it.
        trade = new PlayerTrade(trader, tradeWith);
        tradeMap.put(trader, trade);
        List<PlayerTrade> trades = tradesToPlayerMap.get(tradeWith);
        if(trades == null) {
            System.out.println("k");
            trades = new ArrayList<PlayerTrade>();
            tradesToPlayerMap.put(tradeWith, trades);
        }
        System.out.println("l");
        trades.add(trade);
    }

    public static void remove(PlayerTrade trade) {
        tradeMap.remove(trade.trader);
        if(trade.started) {
            tradeMap.remove(trade.tradeWith);
        } else {
            tradesToPlayerMap.get(trade.tradeWith).remove(trade);
        }
    }

    public static List<PlayerTrade> getTrades(Player player) {
        return tradesToPlayerMap.get(player);
    }

    public static Player getPartner(Player trader) {
        PlayerTrade trade = tradeMap.get(trader);
        if(trade != null && trade.started) {
            if(trade.trader == trader) {
                return trade.tradeWith;
            }
            return trade.trader;
        }
        return null;
    }

    public boolean started;
    public Inventory inventory;
    public Player trader;
    public Player tradeWith;

    public PlayerTrade(Player trader, Player tradeWith) {
        this.trader = trader;
        this.tradeWith = tradeWith;
    }
}
