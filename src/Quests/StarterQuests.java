package Quests;

import java.util.ArrayList;
import java.util.List;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import Merchants.SpawnMerchant;
import Storage.DataGetter;
import Storage.DataSetter;
import Storage.RPGPlayers;

public class StarterQuests 
{
	private DataGetter dg;
	private DataSetter ds;
	ItemStack noobsword = new ItemStack(Material.WOOD_SWORD);
	ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
	ItemMeta pickmeta = pickaxe.getItemMeta();
	ItemMeta swordmeta = noobsword.getItemMeta();
	private NPCRightClickEvent event;
	private JavaPlugin blacklance;
	private Player p;
	List<String> swordlore = new ArrayList<String>(); 
	List<String> picklore = new ArrayList<String>(); 


	public StarterQuests(NPCRightClickEvent event, JavaPlugin blacklance)
	{
		this.event=event;
		this.blacklance=blacklance;
		this.dg =  new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}
	public void starterQuest()
	{
		swordlore.add("Damage: 1-3");
		picklore.add("Good for mining things..");
		swordmeta.setDisplayName("Basic Wooden Sword");
		pickmeta.setDisplayName("Basic Pickaxe");
		swordmeta.setLore(swordlore);
		pickmeta.setLore(picklore);
		noobsword.setItemMeta(swordmeta);
		pickaxe.setItemMeta(pickmeta);
		swordlore.remove(0);
		picklore.remove(0);
		p=event.getClicker();
		Material m = event.getClicker().getItemInHand().getType();
		if(m!=Material.BONE&&m!=Material.IRON_ORE)
		{
			if(!dg.checkInProgress(0,p)&&(!dg.checkCompleted(0,p)))
			{
				ds.addQIP(0,p);
				p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Here is a basic sword and pickaxe for your adventures!");
				p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Bring me 10 iron ore or 10 zombie bones and I will give you a proper helm and some training!");
				if(!p.getInventory().contains(noobsword)){p.getInventory().addItem(noobsword);}
				if(!p.getInventory().contains(pickaxe)){p.getInventory().addItem(pickaxe);}
				p.updateInventory();
			}
			else
			{
				if(dg.checkInProgress(0,p)&&!dg.checkCompleted(0,p))
				{
					p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Have you gotten me some zombie bones or iron ore? Either way here's another sword!"); 
					if(!p.getInventory().contains(noobsword)){p.getInventory().addItem(noobsword);}
					if(!p.getInventory().contains(pickaxe)){p.getInventory().addItem(pickaxe);}
					p.updateInventory();
				}
				if(dg.checkCompleted(0,p))
				{
					p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Thanks again for your help adventurer! Here is a sword and pick if you need them!"); 
					if(!p.getInventory().contains(noobsword)){p.getInventory().addItem(noobsword);}
					if(!p.getInventory().contains(pickaxe)){p.getInventory().addItem(pickaxe);}
					p.updateInventory();
				}
				
			}
		}
		else
		{
			if(!dg.checkCompleted(0,p))
			{
				int amount = p.getItemInHand().getAmount();
				if(m.equals(Material.BONE))
				{
					if(amount>=10)
					{
						ds.completeQuest(0,p);
						SpawnMerchant sm = new SpawnMerchant(event);
						if(amount!=10){p.getItemInHand().setAmount(amount-10);}
						else{p.getInventory().remove(event.getClicker().getItemInHand());}
						p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Good job here is your stuff!");
						p.sendMessage(ChatColor.GOLD+"You have received a Crummy Leather Helmet");
						p.sendMessage(ChatColor.GOLD+"The guide has trained you in combat, and you have gained some xp!");
						ItemStack helm = sm.returnLeatherHelm();
						ItemMeta hm = helm.getItemMeta();
						hm.setDisplayName("Crummy Leather Helm");
						helm.setItemMeta(hm);
						p.getInventory().addItem(sm.returnLeatherHelm());
						RPGPlayers.addXP(p, 1200);
						p.updateInventory();
					}
				}
				if(m.equals(Material.IRON_ORE))
				{
					if(amount>=10)
					{
						ds.completeQuest(0,p);
						SpawnMerchant sm = new SpawnMerchant(event);
						if(amount!=10){p.getItemInHand().setAmount(amount-10);}
						else{p.getInventory().remove(event.getClicker().getItemInHand());}
						p.getItemInHand().setAmount(amount-10);
						p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Good job here is your stuff!");
						p.sendMessage(ChatColor.GOLD+"You have received a Crummy Iron Sword!");
						p.getInventory().addItem(sm.returnCrummySword());
						RPGPlayers.addXP(p, 1200);
						p.updateInventory();
					}
				}
			}
			else
			{
				p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Thanks again for your help adventurer! Here is a sword and pick if you need them!"); 
				if(!p.getInventory().contains(noobsword)){p.getInventory().addItem(noobsword);}
				if(!p.getInventory().contains(pickaxe)){p.getInventory().addItem(pickaxe);}
			}
		}
	}
}
