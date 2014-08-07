package BlackLance;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import Objectives.ObjectiveProcessor;
import Storage.RPGPlayers;
import Util.CombatUtil;
import Util.DropUtil;

import com.comphenix.example.EntityHider;
import com.comphenix.example.EntityHider.Policy;
import com.earth2me.essentials.Mob;
import com.gmail.filoghost.healthbar.api.HealthBarAPI;

import net.aufdemrand.sentry.Sentry;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;

public class blm extends Trait
{
	DataKey key;
	private int level;
	private int health;
	private int maxhealth;
	private JavaPlugin plugin;
	public blm() 
	{
		super("blm");
		this.plugin = (BlackLance) Bukkit.getServer().getPluginManager().getPlugin("BlackLance");
	}
	public void load(DataKey key)
	{
		level=key.getInt("level");
		if(level<5){this.maxhealth=(level*15)+25;health=maxhealth;}
		else{this.maxhealth=(level*20)+40;health=maxhealth;}
	}
	public void save(DataKey key) 
	{
		key.setInt("level", this.level);
		key.setDouble("health", this.maxhealth);
	}
	public void onSpawn()
	{
		if(level<5){this.maxhealth=(level*15)+25;health=maxhealth;}
		else{this.maxhealth=(level*20)+40;health=maxhealth;}
		this.health=this.maxhealth;
		this.getNPC().getDefaultGoalController().run();
	}
	public int getLevel()
	{
		return this.level;
	}
	public int getHealth()
	{
		return health;
	}
	public int getmHealth()
	{
		return maxhealth;
	}
	public void setLevel(int level)
	{
		this.level=level;
		if(level<5){this.maxhealth=(level*15)+25;health=maxhealth;}
		else{this.maxhealth=(level*20)+40;health=maxhealth;}
	}
	public void setHealth(int health)
	{
		this.health=health;
	}
	public void removeMobHealth(int removehealth, final Player killer)
	{
		if(this.npc.isSpawned())
		{
			this.health-=removehealth;
			this.npc.getBukkitEntity().playEffect(EntityEffect.HURT);
			double calc = ((health*20)/maxhealth);
			if(calc>=1){this.npc.getBukkitEntity().setHealth(calc);}
			if(health<=0)
			{
				
				DropUtil du = new DropUtil(plugin);
				du.dropDecider(this.level, killer, this.npc.getBukkitEntity());
				SentryTrait st = this.npc.getTrait(SentryTrait.class);
				st.getInstance().die(true, DamageCause.ENTITY_ATTACK);
				RPGPlayers.addXP(killer, (level*2)+72);

			}
		}
	}
	public void addHealth(int addhealth)
	{
		this.health+=addhealth;
	}
}
