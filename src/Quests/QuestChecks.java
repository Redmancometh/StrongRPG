package Quests;

import java.util.ArrayList;
import java.util.List;

import net.citizensnpcs.api.CitizensAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.healthbar.api.HealthBarAPI;

import BlackLance.BlackLance;
import Objectives.ObjectiveProcessor;
import Util.DropUtil;

public class QuestChecks
{
	private EntityDeathEvent event;
	private ItemStack drop = new ItemStack(Material.WOOD);
	private ItemMeta dropmeta = drop.getItemMeta();
	private JavaPlugin blacklance;
	private boolean dodrop;
	public QuestChecks(JavaPlugin blacklance)
	{
		this.blacklance=blacklance;
	}
	public List<String> lore = new ArrayList<String>();

	public void checkQuestMob(Entity Paul, Player killer)
	{
		String mobname;
		if(Paul instanceof Player&&CitizensAPI.getNPCRegistry().isNPC(Paul))
		{
			Player p = (Player)Paul;
			mobname = p.getName();
		}
		else
		{
			mobname = HealthBarAPI.getMobName((LivingEntity)Paul);
		}
		double isdropped = Math.random();
		DropUtil du = new DropUtil(blacklance);
		if(mobname.contains("Shrimp"))
		{	
			drop.setType(Material.POISONOUS_POTATO);
			dropmeta.setDisplayName("Raw Shrimp Meat");
			lore.add("Probably Poisonous Without Cooking...");
			dodrop=true;
		}
		if(mobname.contains("Zombie"))
		{
			drop.setType(Material.BONE);
			dropmeta.setDisplayName("Zombie Bones");
			lore.add("Very slimy...");
			lore.add(ChatColor.BLUE+"Sell Value: 1");
			dodrop=true;
		}
		if(mobname.contains("Spider"))
		{	
			drop.setType(Material.SPIDER_EYE);
			dropmeta.setDisplayName("Gooey Spider Eye ");
			lore.add("Gross...");
			lore.add(ChatColor.BLUE+"Sell Value: 2");
			dodrop=true;
		}
		if(mobname.contains("Servant"))
		{	
			drop.setType(Material.EYE_OF_ENDER);
			dropmeta.setDisplayName("Dark Servant's Eye ");
			lore.add("Sends a Brutal Message..");
			lore.add(ChatColor.BLUE+"Sell Value: 3");
			dodrop=true;
		}	
		if(dodrop&&isdropped<=.48)
		{
			lore.add("PlayerI"+killer.getUniqueId());
			dropmeta.setLore(lore);
			lore.clear();
			drop.setItemMeta(dropmeta);
			CraftItem ci = (CraftItem) Paul.getWorld().dropItem(Paul.getLocation(), drop);
			du.hide(killer, ci);
		}
	}
	
}
