package Quests;

import java.util.*;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import Storage.DataGetter;
import Storage.DataSetter;

public class ArcherQuest{
	
	
	
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;
	public ArcherQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p){
		this.p=p;
		this.event=event;
		this.blacklance=blacklance;
		this.dg =  new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}
	public void archersQuest(){
		Player p = event.getClicker(); 
		if(p.getItemInHand().hasItemMeta()){
			if(p.getItemInHand().getItemMeta().getDisplayName().equals("String")){
				int amount = p.getItemInHand().getAmount();
				if(amount>=30&&!(dg.checkCompleted(19, p))){
					ds.completeQuest(19, p);
					if(amount!=30){p.getItemInHand().setAmount(amount-30);}
					else{p.getInventory().remove(event.getClicker().getItemInHand());}
					p.sendMessage(ChatColor.DARK_GREEN+"Archer:"+ChatColor.GREEN+" This should be enough for a few bows. Thank you, "+p.getName()+".");
					p.sendMessage(ChatColor.DARK_GREEN+"Archer:"+ChatColor.GREEN+" This is your reward.");
					p.sendMessage(ChatColor.GOLD+"You have received some ecperience points and a Bow.");
					for(int x = 0; x<10; x++){
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
					ItemStack is = new ItemStack(Material.BOW);
					ItemMeta imIs = is.getItemMeta();
					imIs.setDisplayName(ChatColor.DARK_PURPLE+"Master Bow");
					List<String> lore = new ArrayList<String>();
					lore.add("Damage: 10-15");
					lore.add("A Bow made by a Master Archer");
					imIs.setLore(lore);
					is.setItemMeta(imIs);
					p.getInventory().addItem(is);
					p.updateInventory();
					
				}
			}
			else{
				if(!dg.checkInProgress(19,p)&&(!dg.checkCompleted(19,p))){
					ds.addQIP(19,p);
					p.sendMessage(ChatColor.DARK_GREEN+"Archer: "+ChatColor.GREEN+"I need to craft some bows for my students. Can you get me some strings for them?");
				}
				else{
					if(dg.checkCompleted(19,p)){p.sendMessage(ChatColor.DARK_GREEN+"Archer: "+ChatColor.GREEN+"Aim well friend!");}
				}
			}
		}
		else
		{
			if(!dg.checkInProgress(19,p)&&(!dg.checkCompleted(19,p)))
			{
				ds.addQIP(19,p);
				p.sendMessage(ChatColor.DARK_GREEN+"Archer: "+ChatColor.GREEN+"I need to craft some bows for my students. Can you get me some strings for them?");
			}
			else{
				if(dg.checkInProgress(19,p)&&!dg.checkCompleted(19,p)){p.sendMessage(ChatColor.DARK_GREEN+"Archer: "+ChatColor.GREEN+"How is it going?");}
				else{if(dg.checkCompleted(19,p)){p.sendMessage(ChatColor.DARK_GREEN+"Archer: "+ChatColor.GREEN+"Aim well friend!");}}
			}
		}
	}
}