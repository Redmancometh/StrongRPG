package Quests;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import Storage.DataGetter;
import Storage.DataSetter;

public class KnightQuest 
{
	NPCRightClickEvent event;
	JavaPlugin blacklance;
	private DataGetter dg;
	private Player p;
	private DataSetter ds;
//Ignore this part
	public KnightQuest(NPCRightClickEvent event, JavaPlugin blacklance, Player p)
	{
		this.p=p;
		this.event=event;
		this.blacklance=blacklance;
		this.dg =  new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}
	//This is the quest entry
	public void knightsQuest()
	{
		Player p = event.getClicker(); 
		if(p.getItemInHand().hasItemMeta())
		{
			// The line under this is the first thing to change. It checks the name of the item in the players hand, so just change it to what it should be
			if(p.getItemInHand().getItemMeta().getDisplayName().equals("Raw Shrimp Meat"))
			{
				int amount = p.getItemInHand().getAmount();
				if(p.getItemInHand().getAmount()>=10&&!(dg.checkCompleted(1, p))) //change the 1 to your quest ID
				{
					//change the 1 to your quest ID
					ds.completeQuest(1, p);
					//The next 2 items check to see if a player has the amount of the item you want, change this to whatever amount for the quest
					if(amount!=10){p.getItemInHand().setAmount(amount-10);}
					else{p.getInventory().remove(event.getClicker().getItemInHand());}
					//These are the messages it sends if the player has enough of the item, and the quest is being completed
					p.sendMessage(ChatColor.DARK_GREEN+"Knight: "+ChatColor.DARK_GREEN+" Thank you so much "+p.getName()+" this should help.");
					p.sendMessage(ChatColor.DARK_GREEN+"Knight: "+ChatColor.DARK_GREEN+" Here is some spare iron, and some experience potions.");
					p.sendMessage(ChatColor.GOLD+"You have received 10 iron ore and some experience potions!");
					//This loop gives the player 10 of each of these items. I can set this up for you
					for(int x = 0; x<10; x++)
					{
						p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
						p.getInventory().addItem(new ItemStack(Material.IRON_ORE));
					}
				}
			}
			//Not Shrimp Meat
			else
			{
				//The quest isn't in progress, but the quest isn't completed. Just change the 1 to whatever quest ID you want to use.
				if(!dg.checkInProgress(1,p)&&(!dg.checkCompleted(1,p)))
				{
					ds.addQIP(1,p); //change the 1 to the quest ID you want, the next line is the intro message to the player for the quest
					p.sendMessage(ChatColor.DARK_GREEN+"Knight: "+ChatColor.GREEN+"I'm very hungry, could you bring me 10 shrimp meat from these evil shrimp? I'm too weak to fight right now.");
				}
				//In progress, but not completed message; again, change the 1 to your quest ID
				if(dg.checkInProgress(1,p)&&!dg.checkCompleted(1,p)){p.sendMessage(ChatColor.DARK_GREEN+"Knight: "+ChatColor.GREEN+"Please hury!");}
				else
				{
					//Message for after the Quest is Completed, change the 1 to whatever your Quest ID is
					if(dg.checkCompleted(1,p)){p.sendMessage(ChatColor.DARK_GREEN+"Knight: "+ChatColor.GREEN+"Talk to the mayor of the town on the other side of this tunnel for another quest! They urgently require aid adventurer!");}
				}
			}
		}
		//IF a Player is holding air in their hand
		else
		{
			//The quest is not completed or in progress yet, change the 1s to your quest ID
			if(!dg.checkInProgress(1,p)&&(!dg.checkCompleted(1,p)))
			{
				//Set the quest to "in progress" change the 1 to your quest ID
				ds.addQIP(1,p);
				//The starting quest message
				p.sendMessage(ChatColor.DARK_GREEN+"Knight: "+ChatColor.GREEN+"I'm very hungry, could you bring me 10 shrimp meat from these evil shrimp? I'm too weak to fight right now.");
			}
			else
			{
				//The quest is in progress, but is not completed message. Replace the 1 with your quest ID 
				if(dg.checkInProgress(1,p)&&!dg.checkCompleted(1,p)){p.sendMessage(ChatColor.DARK_GREEN+"Knight: "+ChatColor.GREEN+"Please hury!");}
				//The quest is completed message, change 1 to your quest ID
				else{if(dg.checkCompleted(1,p)){p.sendMessage(ChatColor.DARK_GREEN+"Knight: "+ChatColor.GREEN+"Talk to the mayor of the town on the other side of this tunnel for another quest! They urgently require aid adventurer!");}}
			}
		}
	}
}
