package com.strongholdcraft.blacklance.quests;

import com.strongholdcraft.blacklance.storage.DataGetter;
import com.strongholdcraft.blacklance.storage.DataSetter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class StarterQuest2 {
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


	public StarterQuest2(NPCRightClickEvent event, JavaPlugin blacklance) {
		this.event = event;
		this.blacklance = blacklance;
		this.dg = new DataGetter(blacklance);
		this.ds = new DataSetter(blacklance);
	}
	/*public void starterQuest()
	{
		if(dg.checkCompleted(0))
		{
			if(!dg.checkInProgress(2)&&(!dg.checkCompleted(2)))
			{
				ds.addQIP(2);
				p.sendMessage(ChatColor.GREEN+"Thanks for your help earlier! Talk to the Stable Hand in the next town, Crasmeer ");
			}
			else
			{
				if(dg.checkInProgress(2)&&!dg.checkCompleted(2))
				{
					p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Have you gotten me some zombie bones or iron ore? Either way here's another sword!"); 
					
				}
				if(dg.checkCompleted(2))
				{
					p.sendMessage(ChatColor.DARK_GREEN+"Guide:"+ChatColor.GREEN+" Thanks again for your help adventurer! Here is a sword and pick if you need them!"); 
				}
			}
		}
	}*/
}