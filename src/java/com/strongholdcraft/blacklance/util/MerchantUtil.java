package com.strongholdcraft.blacklance.util;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MerchantUtil implements Listener {
	static String name = "";
	static ItemMeta meta;
	int sellprice = 0;
	int buyprice = 0;
	private ItemStack clicked = new ItemStack(Material.AIR);
	static boolean needsclear = false;
	private Player clicker;
	private int hits = 0;

	@SuppressWarnings("deprecation")
	public MerchantUtil(ItemStack clicked, Player clicker) {
		this.clicked = clicked;
		this.clicker = clicker;
	}

	public void Buy(ItemStack clicked, Player clicker) throws NoLoanPermittedException, UserDoesNotExistException {
		if (clicked.getItemMeta().hasLore()) {
			if (clicked.getItemMeta().getLore().size() > 0) {
				meta = clicked.getItemMeta();
				name = clicked.getItemMeta().getDisplayName();
				String[] money = name.split("-");
				if (money[1].length() == 15) buyprice = Integer.parseInt(money[1].substring(1, 3));
				if (money[1].length() == 16) buyprice = Integer.parseInt(money[1].substring(1, 4));
				if (money[1].length() == 17) buyprice = Integer.parseInt(money[1].substring(1, 5));
				if (money[1].length() == 18) buyprice = Integer.parseInt(money[1].substring(1, 6));
				Economy.subtract(clicker.getName(), buyprice);
				meta.setDisplayName(money[0]);
				ItemStack newclicked = clicked.clone();
				newclicked.setItemMeta(meta);
				clicker.getInventory().addItem(newclicked);
				if (newclicked.getType() != Material.ARROW)
					clicker.sendMessage(ChatColor.GOLD + "You bought " + newclicked.getItemMeta().getDisplayName() + ChatColor.GOLD + "for " + buyprice + " coins!");
			}
		}
	}

	public void sell(ItemStack clicked, Player clicker, int Slot) throws NumberFormatException, NoLoanPermittedException, UserDoesNotExistException {
		if (clicked.getItemMeta().hasLore()) {
			if (clicked.getItemMeta().getLore().size() > 1) {
				String raw = clicked.getItemMeta().getLore().get(1);
				if (raw.length() == 15) sellprice = Integer.parseInt(raw.substring(14, 15));
				if (raw.length() == 16) sellprice = Integer.parseInt(raw.substring(14, 16));
				if (raw.length() == 17) sellprice = Integer.parseInt(raw.substring(14, 17));
				if (raw.length() == 18) sellprice = Integer.parseInt(raw.substring(14, 18));
				clicker.sendMessage(ChatColor.GOLD + "You sold " + clicked.getItemMeta().getDisplayName() + ChatColor.GOLD + "for " + sellprice + " coins!");
				Economy.add(clicker.getName(), sellprice);
				if (clicked.getAmount() > 1) {
					clicked.setAmount(clicked.getAmount() - 1);
				} else {
					clicker.getInventory().setItem(Slot, new ItemStack(Material.AIR));
				}
			}
		}
	}

	public ItemStack getClicked() {
		return clicked;
	}

	public Player getClicker() {
		return clicker;
	}
}
