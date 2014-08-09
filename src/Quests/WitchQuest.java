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

import Storage.DataGetter;
import Storage.DataSetter;

public class WitchQuest 
{
	private NPCRightClickEvent event;
	private JavaPlugin blacklance;
	private Player p;
	private List<String> lore = new ArrayList<String>();
	private DataGetter dg;
	private DataSetter ds;
	
	public WitchQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p) 
	{
		this.p=p;
		this.blacklance=blacklance;
		this.event=event;
		this.dg =  new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
		WitchQuest();
	}
	public void WitchQuest()
	{
		Player p = event.getClicker();
		ItemStack i = event.getClicker().getItemInHand();
		Material m = i.getType();
		if(m!=Material.SPIDER_EYE)
		{
			if(!dg.checkInProgress(3,p)&&(!dg.checkCompleted(3,p)))
			{
					ds.addQIP(3,p);
					p.sendMessage(ChatColor.DARK_GREEN+"Witch:"+ChatColor.GREEN+" Greetings "+p.getName()+". Bring me 15 spider eyes and I'll make you some healing potions and experience potions!");
			}
			else
			{
				if(dg.checkInProgress(3,p)&&!dg.checkCompleted(3,p))
				{
					p.sendMessage(ChatColor.DARK_GREEN+"Witch: "+ChatColor.GREEN+"I grow impatient! Where are my spider eyes?");
				}
				if(dg.checkCompleted(3,p)){p.sendMessage(ChatColor.DARK_GREEN+"Witch:"+ChatColor.GREEN+" I may have more work for you later...");}
			}
		}
		else
		{
			if(!dg.checkCompleted(3,p))
			{
					int amount = p.getItemInHand().getAmount();
					if(amount>=15)
					{
						ItemStack potion = new ItemStack(Material.POTION);
						ItemMeta potmeta = potion.getItemMeta();
						lore.add("Heals Light Damage");
						potmeta.setDisplayName("Light Healing Potion");
						potmeta.setLore(lore);
						lore.clear();
						potion.setItemMeta(potmeta);
						if(amount>15){p.getItemInHand().setAmount(amount-15);ds.completeQuest(3,p);}
						if(amount==15){p.getItemInHand().setType(Material.AIR);ds.completeQuest(3,p);}
						p.updateInventory();
						p.sendMessage(ChatColor.DARK_GREEN+"Witch:"+ChatColor.GREEN+" Thank you for your help "+p.getName()+" now leave me be!"); 
						p.sendMessage(ChatColor.GOLD+"The witch has given you some healing potions and experience potions!");
						for(int x = 0; x<3; x++)
							{
								p.getInventory().addItem(potion);
								p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
							}
					}
					else{p.sendMessage(ChatColor.DARK_GREEN+"Witch:"+ChatColor.GREEN+" I told you I need 15! Get out of my sight until you get 15!");}
			}
			else
			{
				p.sendMessage(ChatColor.DARK_GREEN+"Witch:"+ChatColor.GREEN+" I don't need any more spider eyes for now!");
			}
		}
	}
	
}
