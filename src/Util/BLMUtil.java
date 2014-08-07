package Util;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import BlackLance.blm;

public class BLMUtil 
{
	public void dealDamage(LivingEntity paul, int hit, Player damager)
	{
		if(CitizensAPI.getNPCRegistry().isNPC(paul))
		{
			NPC peter = CitizensAPI.getNPCRegistry().getNPC(paul);
			if(peter.hasTrait(blm.class))
			{
				blm blminstance = peter.getTrait(blm.class);
				blminstance.removeMobHealth(hit, damager);
			}
		}
	}
}
