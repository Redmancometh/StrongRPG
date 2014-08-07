package BlackLance;

import java.util.HashMap;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

public class RPGPlayer
{
private HashMap<Player, RPGPlayer> RPGPlayers = new HashMap();
private double xp;
private float health;
private float maxhealth;
private JavaPlugin pl;
private Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

	public RPGPlayer(JavaPlugin pl)
	{
		this.pl=pl;
	}
	public double getXP(){return xp;}
	public float getHealth(){return health;}
	public float getMaxHealth(){return maxhealth;}
	public void addXP(double added){xp=xp+=added;}
	public void setXP(double xp){this.xp=xp;}
	public void setHealth(final Player p)
	{
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(pl, new Runnable() 
        {
            public void run() 
            {
            	maxhealth = ((p.getLevel()*10)+30);
            	health=maxhealth;
            }
        });
    }
	public void healPlayer(double d, Player p)
	{
		if(health+d<maxhealth){health+=d;}
		else{health=maxhealth;}
    	String healthdisplay = ChatColor.DARK_GREEN+"Health:  "+ChatColor.DARK_RED+health+"/"+maxhealth;
 		BarAPI.setMessage(p, healthdisplay, (Math.abs(health/maxhealth)*100));
	}
	public void damagePlayer(int damage, Player p)
	{
		if(this.health-damage<=0){health=0;}
		else{this.health-=damage;}
		p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.RED+"Combat"+ChatColor.DARK_GRAY+"] "+ChatColor.GOLD+"You have been hit for "+damage+" damage");
		p.setHealth(Math.abs(health/maxhealth*20));
		if(health<=0){p.setHealth(0);}
	}
	public User getUser(Player p)
	{
		User u = ess.getUser(p);
		return u;
	}

}

