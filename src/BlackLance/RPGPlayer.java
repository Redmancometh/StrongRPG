package BlackLance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import Storage.DBUtil;
import Storage.RPGPlayers;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

public class RPGPlayer
{
    public HashMap<Integer, RPGWeapon> weapons = new HashMap<Integer, RPGWeapon>();
    private int xp;
    private int health;
    private int maxhealth;
    private int uid;
    public Player p;
    public BukkitTask regenTask;
    private Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    public RPGPlayer(Player p, int uid, int xp, int health, int maxhealth)
    {
	this.p = p;
	this.uid = uid;
	this.xp = xp;
	this.health = health;
	this.maxhealth = maxhealth;
    }
    public int getXP()
    {
	return xp;
    }

    public int getHealth(){return health;}
    public int getMaxHealth(){return maxhealth;}
    public void addXP(double d){xp = xp += d;}
    public void setXP(int d){this.xp = d;}
    public void setMaxHealth(final Player p, final boolean levelup)
    {
	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	scheduler.scheduleSyncDelayedTask(BlackLance.pl, new Runnable()
	{
	    public void run()
	    {
		maxhealth = ((p.getLevel() * 10) + 30);
		if(levelup){health = maxhealth;}
	    }
	});
    }

    public void healPlayer(double d, Player p)
    {
	if (health + d < maxhealth)
	{
	    health += d;
	}
	else
	{
	    health = maxhealth;
	}
    }

    public void damagePlayer(int damage, Player p)
    {
	if (this.health - damage <= 0)
	{
	    health = 0;
	}
	else
	{
	    this.health -= damage;
	}
	p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Combat" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + "You have been hit for " + damage + " damage");
	p.setHealth(Math.abs((double) health / (double) maxhealth * 20));
	if (health <= 0)
	{
	    p.setHealth(0);
	}
    }

    public User getUser(Player p)
    {
	User u = ess.getUser(p);
	return u;
    }

    public Player getPlayer()
    {
	return this.p;
    }

    public int getUID()
    {
	return this.uid;
    }

    public static RPGPlayer createRPGPLayer(ResultSet rs, Player p) throws SQLException
    {
	int id = 0;
	int health = 30;
	int maxhealth = 30;
	int exp = 0;
	while (rs.next())
	{
	    id = rs.getInt(2);
	    health = rs.getInt(3);
	    maxhealth = rs.getInt(4);
	    exp = rs.getInt(5);
	    return new RPGPlayer(p, id, exp, health, maxhealth);
	}
	return null;
    }

    public static void createRPGPlayers() throws SQLException
    {
	for (Player p : Bukkit.getOnlinePlayers())
	{
	    if (DBUtil.doesPlayerExist(p))
	    {
		ResultSet rs = DBUtil.getPlayerData(p);
		RPGPlayer rp = RPGPlayer.createRPGPLayer(rs, p);
		RPGPlayers.addRPGPlayer(p, rp);
		try{RPGWeapon.makeWeapons(rp);}
		catch (SQLException e1){e1.printStackTrace();}
		rp.scheduleHeals();
	    }
	    else
	    {
		DBUtil.addPlayer(p);
		RPGPlayer rp = new RPGPlayer(p, DBUtil.getUID(p), 0, 30, 30);
		RPGPlayers.addRPGPlayer(p, rp);
		try{RPGWeapon.makeWeapons(rp);}
		catch (SQLException e1){e1.printStackTrace();}
		rp.scheduleHeals();
	    }

	}
    }
    public void scheduleHeals()
    {
	regenTask = new BukkitRunnable()
	{
		public void run()
		{
		    if (!p.isDead())
		    {
			RPGPlayer.this.healPlayer(((p.getLevel()) + 2), p);
			p.setHealth((double)RPGPlayer.this.getHealth() / (double)RPGPlayer.this.getMaxHealth() * 20);
			String healthdisplay = ChatColor.DARK_GREEN + "Health:  " + ChatColor.DARK_RED + RPGPlayer.this.getHealth() + "/" + RPGPlayer.this.getMaxHealth();
			float health = ((float)RPGPlayer.this.getHealth()/(float)RPGPlayer.this.getMaxHealth());
			BarAPI.setMessage(p, healthdisplay, health*100);
		    }
		}
	}.runTaskTimer(BlackLance.pl, 10, 85);
    }
    public void cancelHeals(){this.regenTask.cancel();}
    public int[] getHitDamage(ItemStack i)
    {
	RPGWeapon w = weapons.get(Integer.parseInt(i.getItemMeta().getLore().get(2).replaceAll("§", "")));
	return new int[] {w.mindmg,w.maxdmg};
    }
}
