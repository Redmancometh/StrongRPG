package BlackLance;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import Alchemy.HerbloreListeners;
import Listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import code.husky.mysql.MySQL;
import Objectives.ObjectiveProcessor;
import Smithing.SmeltingListeners;
import Smithing.SmithingListeners;
import Storage.DBUtil;
import Storage.RPGPlayers;
import Util.Horses;
import Util.Mine;
import Util.ResourceNodes;
public class BlackLance extends JavaPlugin
{
    BukkitTask connect;
    static MySQL MySQL;
    static Plugin pl;
    static Connection c;
    public static OptionManager optionManager = new OptionManager();
    public void onEnable()
    {
	Bukkit.broadcastMessage("RELOADING TEST FIVE");
	pl=this;
	MySQL=new MySQL((Plugin) this, "localhost", "3306", "RPG", "root", "enter11284");
	MySQL.openConnection();
	connect = new BukkitRunnable(){public void run(){if (!MySQL.checkConnection()){BlackLance.openConnect();}}}.runTaskTimer(this, 20, 60);
	c=MySQL.getConnection();
	try{ RPGPlayer.createRPGPlayers();}
	catch (SQLException e){e.printStackTrace();}
	try{ResourceNodes.generateHay();ResourceNodes.generateSweetGum();}
	catch(SQLException e){ e.printStackTrace();}
	File configFile = new File(this.getDataFolder(), "config.yml");
	PluginManager pm = getServer().getPluginManager();
	pm.registerEvents(new CombatListeners(this), this);
	pm.registerEvents(new Horses(), this);
	pm.registerEvents(new PlayerListeners(this, configFile), this);
	pm.registerEvents(new ExperienceListeners(), this);
	pm.registerEvents(new ObjectiveProcessor(this), this);
	pm.registerEvents(new DropListeners(this), this);
	pm.registerEvents(new NPCListeners(this), this);
	pm.registerEvents(new SmeltingListeners(), this);
	pm.registerEvents(new SmithingListeners(), this);
	pm.registerEvents(new HerbloreListeners(), this);
	pm.registerEvents(new BlockListeners(this), this);
	pm.registerEvents(new MenuListeners(this), this);
	pm.registerEvents(new ItemListeners(this), this);
	pm.registerEvents(new ConsumableListener(this), this);
	pm.registerEvents(new TradeListener(), this);
	pm.registerEvents(new Alchemy.MenuListeners(), this);
	getCommand("horse").setExecutor(new CommandParser());
	getCommand("setup").setExecutor(new CommandParser());
	getCommand("blevel").setExecutor(new CommandParser());
	getCommand("blreload").setExecutor(new CommandParser());
	getCommand("lookup").setExecutor(new CommandParser());
	getCommand("resource").setExecutor(new CommandParser());
	net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(blm.class).withName("blm"));
	HashMap<Location, Material> toRegen = Mine.getRegen();
	Iterable<Location> locar = toRegen.keySet();
	for (locar.iterator(); locar.iterator().hasNext();)
	{
	    Location l1 = locar.iterator().next();
	    Bukkit.broadcastMessage("Loc: " + l1.getX());
	    l1.getBlock().setType(toRegen.get(l1));
	}
	if (!configFile.exists())
	{
	    this.saveDefaultConfig();
	}
    }
    public void onDisable()
    {
	Bukkit.getScheduler().cancelAllTasks();
	for(Player p : Bukkit.getOnlinePlayers())
	{
	    RPGPlayer rp = RPGPlayers.getRPGPlayer(p);
	    try{DBUtil.saveDataByID(rp.getUID(), rp);}
	    catch (SQLException e){e.printStackTrace(); }
	}
	MySQL.closeConnection();
    }
    public void saveIt()
    {
	this.saveConfig();
    }
    public static Connection getConnection(){return c;}
    public static void openConnect(){MySQL.openConnection();}
    public static Plugin getPlugin(){return pl;}
}