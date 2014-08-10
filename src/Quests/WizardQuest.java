package Quests;

import Storage.DataGetter;
import Storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class WizardQuest {
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;

	public WizardQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) {
		this.p = p;
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}

	public void wizardsQuest() {
		Player p = event.getClicker();
		if (p.getItemInHand().hasItemMeta()) {
			if (p.getItemInHand().getItemMeta().getDisplayName().equals("Magma ball")) {
				int amount = p.getItemInHand().getAmount();
				if (p.getItemInHand().getAmount() >= 10 && !(dg.checkCompleted(17, p))) {
					ds.completeQuest(17, p);
					if (amount != 10) {
						p.getItemInHand().setAmount(amount - 10);
					} else {
						p.getInventory().remove(event.getClicker().getItemInHand());
					}
					p.sendMessage(ChatColor.DARK_GREEN + "Wizard: " + ChatColor.GREEN + " Thank you for the fine ingredients.");
					p.sendMessage(ChatColor.DARK_GREEN + "Wizard: " + ChatColor.GREEN + " You better like this. Now please leave me to my work.");
					p.sendMessage(ChatColor.GOLD + "You have received a reward.");
					p.sendMessage(ChatColor.DARK_GREEN + "Wizard: " + ChatColor.GREEN + " Use this Book and quill well, " + p.getName() + ".");
					for (int x = 0; x < 10; x++) {
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					p.getInventory().addItem(new ItemStack(Material.BOOK_AND_QUILL));

					ItemStack is = new ItemStack(Material.POTION);

					Potion pot = new Potion(PotionType.STRENGTH);
					pot.extend();
					pot.apply(is);

					ItemMeta imIs = is.getItemMeta();
					imIs.setDisplayName("Potion of Ogre Strength");
					is.setItemMeta(imIs);
					p.getInventory().addItem(is);
					p.updateInventory();

				}
			} else {
				if (!dg.checkInProgress(17, p) && (!dg.checkCompleted(17, p))) {
					ds.addQIP(17, p);
					p.sendMessage(ChatColor.DARK_GREEN + "Wizard: " + ChatColor.GREEN + "I need ingredients for my new spell. Go get me about 10 Magma balls.");
				}
				//if(dg.checkInProgress(17,p)&&!dg.checkCompleted(17,p)){p.sendMessage(ChatColor.DARK_GREEN+"Reiner: "+ChatColor.GREEN+"Do you have my Leather?");}
				else {

					if (dg.checkCompleted(17, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Wizard: " + ChatColor.GREEN + "My spell worked! Hope you are using the Book and Quill I gave you. Helps keep track of your life.");
					}
				}
			}
		} else {
			if (!dg.checkInProgress(17, p) && (!dg.checkCompleted(17, p))) {
				ds.addQIP(17, p);
				p.sendMessage(ChatColor.DARK_GREEN + "Wizard: " + ChatColor.GREEN + "I need ingredients for my new spell. Go get me about 10 Magma balls.");
			} else {
				if (dg.checkInProgress(17, p) && !dg.checkCompleted(17, p)) {
					p.sendMessage(ChatColor.DARK_GREEN + "Wizard: " + ChatColor.GREEN + "I asked you for 10 Magma balls.");
				} else {
					if (dg.checkCompleted(17, p)) {
						p.sendMessage(ChatColor.DARK_GREEN + "Wizard: " + ChatColor.GREEN + "My spell worked! Hope you are using the Book and Quill I gave you. Helps keep track of your life.");
					}
				}
			}
		}
	}
}