package Quests;

import java.util.ArrayList;
import java.util.List;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import Storage.DataGetter;
import Storage.DataSetter;

public class PaulQuest{
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private List<String> lore = new ArrayList<String>();
	private DataGetter dg;
	private Player p;
	private DataSetter ds;
	public PaulQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p){
		this.p=p;
		this.event=event;
		this.blacklance=blacklance;
		this.dg =  new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}
	public void paulsQuest(){
		Player p = event.getClicker(); 
		if(p.getItemInHand().hasItemMeta()){
			if(p.getItemInHand().getItemMeta().getDisplayName().equals("Dark Bone")){
				int amount = p.getItemInHand().getAmount();
				if(p.getItemInHand().getAmount()>=40&&!(dg.checkCompleted(20, p))){
					ds.completeQuest(20, p);
					if(amount!=40){p.getItemInHand().setAmount(amount-40);}
					else{p.getInventory().remove(event.getClicker().getItemInHand());}
					p.sendMessage(ChatColor.DARK_GREEN+"Paul: "+ChatColor.GREEN+" Oh, you got me Bones instead? Well I can live with that. I'll just turn them into bone meal... Thanks a ton "+p.getName()+"!");
					p.sendMessage(ChatColor.DARK_GREEN+"Paul: "+ChatColor.GREEN+" This should be worth your time!");
					p.sendMessage(ChatColor.GOLD+"You have received some experience points and hoe.");
					for(int x = 0; x<20; x++){
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
					}
						ItemStack is = new ItemStack(Material.GOLD_HOE);
						ItemMeta imIs = is.getItemMeta();
						imIs.setDisplayName(ChatColor.GOLD+p.getName()+"'s Golden Hoe");
						lore.add("Damage: 12-17");
						is.addEnchantment(Enchantment.KNOCKBACK, 1);
						imIs.setLore(lore);
						lore.clear();
						is.setItemMeta(imIs);
						p.getInventory().addItem(is);
						event.getClicker().getInventory().addItem(new ItemStack(Material.LADDER));
						p.updateInventory();
				}
			}
			else{
				if(!dg.checkInProgress(20,p)&&(!dg.checkCompleted(20,p))){
					ds.addQIP(20,p);
					p.sendMessage(ChatColor.DARK_GREEN+"Paul: "+ChatColor.GREEN+"My crops take forever to grow! Can you get me some Bone Meal?");
				}
				//if(dg.checkInProgress(20,p)&&!dg.checkCompleted(20,p)){p.sendMessage(ChatColor.DARK_GREEN+"Paul: "+ChatColor.GREEN+"My crops need some of that Bone Meal...");}
				else{
					if(dg.checkCompleted(20,p)){p.sendMessage(ChatColor.DARK_GREEN+"Paul: "+ChatColor.GREEN+"Hi again, "+p.getName()+".");}
				}
			}
		}
		else
		{
			if(!dg.checkInProgress(20,p)&&(!dg.checkCompleted(20,p)))
			{
				ds.addQIP(20,p);
				p.sendMessage(ChatColor.DARK_GREEN+"Paul: "+ChatColor.GREEN+"My crops take forever to grow! Can you get me some Bone Meal?");
			}
			else{
				if(dg.checkInProgress(20,p)&&!dg.checkCompleted(20,p)){p.sendMessage(ChatColor.DARK_GREEN+"Paul: "+ChatColor.GREEN+"My crops need some of that Bone Meal...");}
				else{if(dg.checkCompleted(20,p)){p.sendMessage(ChatColor.DARK_GREEN+"Paul: "+ChatColor.GREEN+"Hi again, "+p.getName()+".");}}
			}
		}
	}
}