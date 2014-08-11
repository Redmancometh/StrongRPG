package Trade;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerTrade {
    public static final Map<Player, PlayerTrade> tradeMap = new HashMap<Player, PlayerTrade>();
    public static final Map<Player, List<PlayerTrade>> tradesToPlayerMap = new HashMap<Player, List<PlayerTrade>>();
    public static final Map<Player, Inventory> tradeInventories = new HashMap<Player, Inventory>();

    public static boolean isRequestingTrade(List<PlayerTrade> trades, Player tradeWith) {
        for(PlayerTrade trade : trades) {
            if(trade.trader == tradeWith) {
                return true;
            }
        }
        return false;
    }

    public static void tryTrade(Player trader, Player tradeWith) {
        PlayerTrade trade = tradeMap.get(trader);
        if(trade != null && trade.state != TradeState.REQUEST) {
            return;
        }

        List<PlayerTrade> trades = tradesToPlayerMap.get(trader);
        if(trades == null) {
            trades = new ArrayList<PlayerTrade>();
            tradesToPlayerMap.put(trader, trades);
        }
        for(PlayerTrade t : trades) {
            System.out.println(t.trader.getDisplayName());
        }

        if(isRequestingTrade(trades, tradeWith)) {
            trader.sendMessage("Accepted trade request.");
            tradeWith.sendMessage(trader.getDisplayName() + " accepted your trade request.");
            startTrade(tradeMap.get(tradeWith));
        } else {
            requestTrade(trader, tradeWith);
        }
    }

    public static void startTrade(PlayerTrade trade) {
        System.out.println(trade);
        System.out.println(trade.tradeWith);
        tradesToPlayerMap.get(trade.tradeWith).clear();
        tradeMap.put(trade.tradeWith, trade);
        trade.traderInv = Bukkit.createInventory(null, 9 * 4, "Trade - " + trade.tradeWith.getName());
        trade.tradeWithInv = Bukkit.createInventory(null, 9 * 4, "Trade - " + trade.trader.getName());
        tradeInventories.put(trade.trader, trade.traderInv);
        tradeInventories.put(trade.tradeWith, trade.tradeWithInv);
        for(int i = 0; i < 2; i++) {
            trade.traderInv.setItem(i * 9 + 4, new ItemStack(Material.WOOL, 1, (short)0, (byte)5));
            trade.tradeWithInv.setItem(i * 9 + 4, new ItemStack(Material.WOOL, 1, (short)0, (byte)5));
        }
        for(int i = 2; i < 4; i++) {
            trade.traderInv.setItem(i * 9 + 4, new ItemStack(Material.WOOL, 1, (short)0, (byte)14));
            trade.tradeWithInv.setItem(i * 9 + 4, new ItemStack(Material.WOOL, 1, (short)0, (byte)14));
        }
        trade.trader.openInventory(trade.traderInv);
        trade.tradeWith.openInventory(trade.tradeWithInv);
        trade.state = TradeState.TRADE;
    }

    public static void requestTrade(Player trader, Player tradeWith) {
        PlayerTrade trade;
        PlayerTrade prevTrade = tradeMap.get(trader);
        if(prevTrade != null) {
            if(prevTrade.tradeWith == tradeWith) {
                // TODO: Refresh trade counter.
                return;
            }

            // Cancel the original trade.
            prevTrade.tradeWith.sendMessage(trader.getDisplayName() + " has cancelled the trade.");
            tradesToPlayerMap.get(prevTrade.tradeWith).remove(prevTrade);
        }

        trader.sendMessage("Sent trade request to " + tradeWith.getDisplayName());
        tradeWith.sendMessage(trader.getDisplayName() + " wants to trade with you.");

        // Create the trade and add it.
        trade = new PlayerTrade(trader, tradeWith);
        tradeMap.put(trader, trade);
        List<PlayerTrade> trades = tradesToPlayerMap.get(tradeWith);
        if(trades == null) {
            trades = new ArrayList<PlayerTrade>();
            tradesToPlayerMap.put(tradeWith, trades);
        }
        trades.add(trade);
    }

    public static void setAccept(Player player, boolean accept) {
        PlayerTrade trade = tradeMap.get(player);
        if(trade != null && trade.state != TradeState.REQUEST) {
            if(trade.trader == player) {
                trade.tradeWithAccepted = accept;
                return;
            }
            trade.traderAccepted = accept;
        }
    }

    public static boolean getAccept(Player player) {
        PlayerTrade trade = tradeMap.get(player);
        if(trade != null && trade.state != TradeState.REQUEST) {
            if(trade.trader == player) {
                return trade.tradeWithAccepted;
            }
            return trade.traderAccepted;
        }
        return false;
    }

    public static void accept(Player player) {
        PlayerTrade trade = tradeMap.get(player);
        Player partner = getPartner(player);
        System.out.println(getAccept(player));
        System.out.println(getAccept(partner));
        if(!getAccept(player)) {
            setAccept(player, true);
            if(trade.state == TradeState.TRADE) {
                player.sendMessage("Accepting...");
                partner.sendMessage(player.getDisplayName() + " has accepted.");
            } else if(trade.state == TradeState.CONFIRM) {
                player.sendMessage("Confirming...");
                partner.sendMessage(player.getDisplayName() + " has confirmed.");
            }
        }
        System.out.println(getAccept(player));
        System.out.println(getAccept(partner));
        if(getAccept(partner)) {
            if(trade.state == TradeState.TRADE) {
                String str = "Confirming trade...";
                player.sendMessage(str);
                partner.sendMessage(str);
                setAccept(player, false);
                setAccept(partner, false);
                trade.state = TradeState.CONFIRM;
            } else if(trade.state == TradeState.CONFIRM) {
                player.sendMessage("Trade confirmed!");
                partner.sendMessage("Trade confirmed!");
                Inventory playerInv = tradeInventories.get(player);
                Inventory partnerInv = tradeInventories.get(partner);
                for(int i = 0; i < 30; i += 5) {
                    for(; i % 9 < 4; i++) {
                        ItemStack stack = playerInv.getItem(i);
                        if(stack != null) {
                            partner.getInventory().addItem(stack);
                        }
                    }
                }
                for(int i = 0; i < 30; i += 5) {
                    for(; i % 9 < 4; i++) {
                        ItemStack stack = partnerInv.getItem(i);
                        if(stack != null) {
                            player.getInventory().addItem(stack);
                        }
                    }
                }
                player.updateInventory();
                partner.updateInventory();
                remove(trade);
                player.closeInventory();
                partner.closeInventory();
            }
        }
    }

    public static void decline(Player player) {
        PlayerTrade trade = tradeMap.get(player);
        Player partner = getPartner(player);
        if(getAccept(player)) {
            setAccept(player, false);
            if(trade.state == TradeState.TRADE) {
                player.sendMessage("Cancelled accepting.");
                partner.sendMessage(player.getDisplayName() + " cancelled accepting.");
            } else if(trade.state == TradeState.CONFIRM) {
                player.sendMessage("Cancelled confirming.");
                partner.sendMessage(player.getDisplayName() + " cancelled confirming.");
            }
        } else {
            if(trade.state == TradeState.TRADE) {
                player.sendMessage("You declined the trade.");
                partner.sendMessage(player.getDisplayName() + " declined the trade.");
                reclaimItems(player);
                reclaimItems(partner);
                remove(trade);
                player.closeInventory();
                partner.closeInventory();
            } else if(trade.state == TradeState.CONFIRM) {
                player.sendMessage("Declined confirmation.");
                partner.sendMessage(player.getDisplayName() + " declined confirmation.");
                trade.state = TradeState.TRADE;
                setAccept(partner, false);
            }
        }
    }

    public static void reclaimItems(Player player) {
        InventoryView view = player.getOpenInventory();
        Inventory tradeInv = view.getTopInventory();
        Inventory playerInv = view.getBottomInventory();
        for(int i = 0; i < 30; i += 5) {
            for(; i % 9 < 4; i++) {
                ItemStack stack = tradeInv.getItem(i);
                if(stack == null) {
                    return;
                }
                playerInv.addItem(stack);
            }
        }
        player.updateInventory();
    }

    public static int getNextSlot(Inventory inventory) {
        for(int i = 0; i < 30; i += 5) {
            for(; i % 9 < 4; i++) {
                ItemStack stack = inventory.getItem(i);
                if(stack == null) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void remove(PlayerTrade trade) {
        if(tradeMap.containsKey(trade.trader)) {
            tradeMap.remove(trade.trader);
        }
        if(tradeMap.containsKey(trade.tradeWith)) {
            tradeMap.remove(trade.tradeWith);
        }
        if(tradesToPlayerMap.containsKey(trade.tradeWith)) {
            tradesToPlayerMap.get(trade.tradeWith).remove(trade);
        }
    }

    public static Player getPartner(Player trader) {
        PlayerTrade trade = tradeMap.get(trader);
        if(trade != null && trade.state != TradeState.REQUEST) {
            if(trade.trader == trader) {
                return trade.tradeWith;
            }
            return trade.trader;
        }
        return null;
    }

    public enum TradeState {
        REQUEST, TRADE, CONFIRM
    }

    public TradeState state = TradeState.REQUEST;
    public Inventory traderInv;
    public Inventory tradeWithInv;
    public Player trader;
    public Player tradeWith;
    public boolean traderAccepted;
    public boolean tradeWithAccepted;

    public PlayerTrade(Player trader, Player tradeWith) {
        this.trader = trader;
        this.tradeWith = tradeWith;
    }
}