package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import Storage.RPGPlayers;
import Util.CombatUtil;

public class ExperienceListeners  implements Listener 
{
	Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	@EventHandler
	public void onDeath(PlayerDeathEvent event){event.setKeepLevel(true);}
	@EventHandler
	public void onLevel(PlayerExpChangeEvent event)
	{
		Player p = event.getPlayer();
		if(event.getAmount()>=(1-p.getExp()) * p.getExpToLevel())
		{
			p.sendMessage(ChatColor.GOLD+"You have gained a level! Gratz on getting level "+(p.getLevel()+1)+"!");
			if(p.getLevel()>21)
			{
				p.sendMessage("You have gained some maximum health!");
			}
			else
			{
				p.sendMessage(ChatColor.GREEN+"You will start gaining max health in "+(21-p.getLevel())+" levels!");
			}
		}
		
	}

	
	
	
}


