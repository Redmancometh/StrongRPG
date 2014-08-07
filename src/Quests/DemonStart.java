package Quests;
	
import java.util.ArrayList;
import java.util.List;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import Storage.DataGetter;
import Storage.DataSetter;

public class DemonStart 
{
	private NPCRightClickEvent event;
	private JavaPlugin blacklance;
	private Player p;
	private List<String> lore = new ArrayList<String>();
	private DataGetter dg;
	private DataSetter ds;
		
	public DemonStart(NPCRightClickEvent event, JavaPlugin blacklance, Player p) 
	{
		this.p=p;
		this.blacklance=blacklance;
		this.event=event;
		this.dg =  new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
		EnderEyes();
		}
	public void EnderEyes()
	{
		Player p = event.getClicker();
		ItemStack i = event.getClicker().getItemInHand();
		Material m = i.getType();
		if(m!=Material.EYE_OF_ENDER)
		{
			if(!dg.checkInProgress(4,p)&&(!dg.checkCompleted(4,p)))
			{
						ds.addQIP(4,p);
						p.sendMessage(ChatColor.DARK_GREEN+"Mayor:"+ChatColor.GREEN+" Greetings "+p.getName()+" may I ask you for your help?");
						p.sendMessage(ChatColor.DARK_GREEN+"Mayor:"+ChatColor.GREEN+p.getName()+", we are under siege by the demon Uxaerodun, and his servants are at our very gates!");
						p.sendMessage(ChatColor.DARK_GREEN+"Mayor:"+ChatColor.GREEN+" Please kill 15 of them and bring their eyes back so that we may make a weapon to destroy them! You will be greatly rewarded!");
			}
			else
			{
				if(dg.checkInProgress(4,p)&&!dg.checkCompleted(4,p))
				{
					p.sendMessage(ChatColor.DARK_GREEN+"Mayor: "+ChatColor.GREEN+"We badly need help!");
				}
				if(dg.checkCompleted(4,p)){p.sendMessage(ChatColor.DARK_GREEN+"Mayor:"+ChatColor.GREEN+" I may have more work for you later...");}
			}
		}
		else
		{
			if(!dg.checkCompleted(4,p))
			{
					int amount = p.getItemInHand().getAmount();
					if(amount>=15)
					{
						ItemStack Avenger = new ItemStack(Material.GOLD_SWORD);
						ItemMeta Ameta = Avenger.getItemMeta();
						lore.add("Damage: 10-15");
						Ameta.setDisplayName(ChatColor.GOLD+p.getName()+"'s Avenger");
						Avenger.addEnchantment(Enchantment.FIRE_ASPECT, 1);
						Ameta.setLore(lore);
						lore.clear();
						Avenger.setItemMeta(Ameta);
						if(amount>15){p.getItemInHand().setAmount(amount-15);ds.completeQuest(4,p);}
						if(amount==15){p.getItemInHand().setType(Material.AIR);ds.completeQuest(4,p);}
						p.sendMessage(ChatColor.DARK_GREEN+"Mayor:"+ChatColor.GREEN+" Thank you for your help "+p.getName()+" we cannot thank you enough!"); 
						p.sendMessage(ChatColor.GOLD+"The mayor has given you a highly enchanted sword and some experience potions!");
						for(int x = 0; x<5; x++)
						{
							p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
						}
						p.getInventory().addItem(Avenger);
						p.updateInventory();
					}
						else{p.sendMessage(ChatColor.DARK_GREEN+"Mayor:"+" I want 15 of their eyes!");}
			}
			else
			{
				p.sendMessage(ChatColor.DARK_GREEN+"Mayor:"+ChatColor.GREEN+" We thank you for your assistance champion! Talk to me later for the next step!");
			}
		}
	}
		
}

