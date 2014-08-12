package BlackLance;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;
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
		    RPGPlayer rp = RPGPlayers.getRPGPlayer(killer);
		    DropUtil du = new DropUtil(plugin);
		    du.dropDecider(this.level, killer, this.npc.getBukkitEntity());
		    SentryTrait st = this.npc.getTrait(SentryTrait.class);
		    st.getInstance().die(true, DamageCause.ENTITY_ATTACK);
		    double differenceXP = (level*2+20)*((killer.getLevel()-this.level)*.20);
		    double xp = (level*2+30)-differenceXP;
		    if(xp>0)
		    {
			if(Parties.Parties.parties.containsKey(rp))
			{
			    Parties.Parties.parties.get(rp).giveXP(xp);
			}
			else{rp.addXP(xp);}
		    }
		}
	    }
	}
	public void addHealth(int addhealth)
	{
		this.health+=addhealth;
	}
}
