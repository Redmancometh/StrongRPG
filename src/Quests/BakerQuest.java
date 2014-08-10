package Quests;

import Storage.DataGetter;
import Storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BakerQuest {
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;

	public BakerQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}

	public void bakersQuest() {
		Player p = event.getClicker();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName().equals("Chicken Egg")) {
				int amount = p.getItemInHand().getAmount();
				if (p.getItemInHand().getAmount() >= 16 && !(dg.checkCompleted(15, p))) {
					ds.completeQuest(15, p);
					if (amount != 16) {
						p.getItemInHand().setAmount(amount - 16);
					} else {
						p.getInventory().remove(event.getClicker().getItemInHand());
					}
					p.sendMessage(ChatColor.DARK_GREEN + "Baker: " + ChatColor.GREEN + " Eggs! Awesome! Now I can start baking a cake!");
					p.sendMessage(ChatColor.DARK_GREEN + "Baker: " + ChatColor.GREEN + " Thanks for the hard work! Unfortunately, this is all I can give you... Hope you like it!");
					p.sendMessage(ChatColor.GOLD + "You have received some experience potions!");
					for (int x = 0; x < 5; x++) {
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					p.updateInventory();
				}
			} else {
				if (!dg.checkInProgress(15, p) && (!dg.checkCompleted(15, p))) {
					ds.addQIP(15, p);
					p.sendMessage(ChatColor.DARK_GREEN + "Baker: " + ChatColor.GREEN + "I need some eggs to bake a cake. But I have no chickens here, so no eggs. Could you get me some?"); //The chickens are supposed to be in another place.
				}
				//if(dg.checkInProgress(15,p)&&!dg.checkCompleted(15,p)){p.sendMessage(ChatColor.DARK_GREEN+"Baker: "+ChatColor.GREEN+"Can't wait to make that cake!");}
				else {
					if (dg.checkCompleted(15, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Baker: " + ChatColor.GREEN + "The cake was very good! Thanks again " + p.getName() + "!");
					}
				}
			}
		} else {
			if (!dg.checkInProgress(15, p) && (!dg.checkCompleted(15, p))) {
				ds.addQIP(15, p);
				p.sendMessage(ChatColor.DARK_GREEN + "Baker: " + ChatColor.GREEN + "I need some eggs to bake a cake. But I have no chickens here, so no eggs. Could you get me some?");
			} else {
				if (dg.checkInProgress(15, p) && !dg.checkCompleted(15, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Baker: " + ChatColor.GREEN + "Can't wait to make that cake!");
				} else {
					if (dg.checkCompleted(15, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Baker: " + ChatColor.GREEN + "The cake was very good! Thanks again " + p.getName() + "!");
					}
				}
			}
		}
	}
}