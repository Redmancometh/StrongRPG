package Util;


import java.util.Random;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import BlackLance.blm;

public class CombatUtil 
{
	Random r = new Random();
	public int level;
	public CombatUtil()
	{

	}
	public double generateHit(Player phitter)
	{
		if(phitter.getItemInHand()!=null&&phitter.getItemInHand().hasItemMeta()&&phitter.getItemInHand().getItemMeta().hasLore())
		{
			int lowdmg = 0;
			int highdmg = 0;
			if(phitter.getItemInHand().getItemMeta().getLore().toString().contains("Damage"))
			{
				String damage = phitter.getItemInHand().getItemMeta().getLore().toString();
				if(damage.charAt(10)=='-'){lowdmg = Integer.parseInt(damage.substring(9,10));}
				else{lowdmg=Integer.parseInt(damage.substring(9,11));}
				String[] hcheck = damage.split("-");

				if(hcheck.length==1)
				{
					highdmg=Integer.parseInt(hcheck[1].substring(0,1));
				}
				if(hcheck.length==2)
				{
					if(hcheck[1].charAt(1)==','||hcheck[1].charAt(1)==']'){highdmg=Integer.parseInt(hcheck[1].substring(0,1));}
					else{highdmg=Integer.parseInt(hcheck[1].substring(0,2));}
				}
				if(hcheck.length==3)
				{
					if(hcheck[1].charAt(2)==','||hcheck[1].charAt(2)==']'){highdmg=Integer.parseInt(hcheck[1].substring(0,2));}
					else{highdmg=Integer.parseInt(hcheck[1].substring(0,3));}
				}
				double Hit = (r.nextInt(highdmg)+lowdmg)+(phitter.getLevel()*1.25)+2;
				return Hit;
			}
			else
			{
				phitter.sendMessage(ChatColor.DARK_RED+"Use a weapon to attack.");
				return 0;
			}
		}
		else
		{				
			phitter.sendMessage(ChatColor.DARK_RED+"Use a weapon to attack.");
			return 0;
		}		
	}
	public double getAbsorption(Player phit) 
	{
		double absorb = 0;
		if(phit.getLevel()<25){absorb = phit.getLevel()*.45;}
		else{absorb = (phit.getLevel()*.1);}
		if(phit.getEquipment().getBoots()!=null&&phit.getEquipment().getBoots().hasItemMeta())
		{
			String boots = phit.getEquipment().getBoots().getItemMeta().getLore().toString();
			absorb += Character.getNumericValue(boots.charAt(10));
		}
		if(phit.getEquipment().getHelmet()!=null&&phit.getEquipment().getHelmet().hasItemMeta())
		{
			String helmet = phit.getEquipment().getHelmet().getItemMeta().getLore().toString();
			absorb += Character.getNumericValue(helmet.charAt(10));
		}
		if(phit.getEquipment().getLeggings()!=null&&phit.getEquipment().getLeggings().hasItemMeta())
		{
			String leggings =phit.getEquipment().getLeggings().getItemMeta().getLore().toString();
			absorb += Character.getNumericValue(leggings.charAt(10));
		}
		if(phit.getEquipment().getChestplate()!=null&&phit.getEquipment().getChestplate().hasItemMeta())
		{
			String chestplate = phit.getEquipment().getChestplate().getItemMeta().getLore().toString();
			absorb += Character.getNumericValue(chestplate.charAt(10));
		}
		return absorb*.9;
	}
	public double getmobDamage(LivingEntity paul)
	{
		level = getLevel(paul);
		return (((level*2.7)+r.nextInt(level+3))+2);
	}
	public double getArrowDamage(LivingEntity paul)
	{
		int level = getLevel(paul);
		return ((level*1.5)+2);
	}
	public double getmobAbsorb(LivingEntity paul)
	{
		double absorption = 0;
		int level = getLevel(paul);
		//Change to switch later -Redman		
		if(level<=20){absorption = ((level*2.3));}
		return absorption;		
	}
	public int getLevel(LivingEntity paul)
	{
		NPC peter = CitizensAPI.getNPCRegistry().getNPC(paul);
		if(peter.isSpawned())
		{
			if(peter.hasTrait(blm.class))
			{
				blm blminstance = peter.getTrait(blm.class);
				level = blminstance.getLevel();
			}
		}
		return level;
	}

}
