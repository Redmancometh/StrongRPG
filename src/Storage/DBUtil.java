package Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import BlackLance.RPGPlayer;
public class DBUtil
{
    public static void setup(Connection c) throws SQLException
    {
	Statement statement = c.createStatement();
	statement.execute("CREATE TABLE Players(UUID varchar(50), uid INT primary key NOT NULL AUTO_INCREMENT, health INT, maxhealth INT, exp INT, x INT, y INT, z INT);");
    }  
    public static void addPlayer(Player p) throws SQLException
    {
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = BlackLance.BlackLance.getConnection().prepareStatement("INSERT INTO Players(UUID,health,maxhealth,exp,x,y,z) VALUES (?,?,?,?,?,?,?)");
	ps.setString(1, uuid);
	ps.setInt(2, 30);
	ps.setInt(3, 30);
	ps.setInt(4, 0);
	ps.setInt(5, 0);
	ps.setInt(6, 0);
	ps.setInt(7, 0);
	ps.execute();
    }
    public static boolean doesPlayerExist(Player p) throws SQLException
    {
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = BlackLance.BlackLance.getConnection().prepareStatement("SELECT uid FROM Players where UUID=?");
	ps.setString(1, uuid);
	ResultSet rs=ps.executeQuery();
	if(rs.next()){return true;}
	else{return false;}
    }

 
    public static int getUID(Player p) throws SQLException
    {
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = BlackLance.BlackLance.getConnection().prepareStatement("SELECT uid FROM Players where UUID=?");
	ps.setString(1, uuid);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
	    return rs.getInt(1);
	}
	return 0;
    }
    public static int getPlayerMaxHealth(int uid) throws SQLException
    {
	PreparedStatement ps = null;
	ps = BlackLance.BlackLance.getConnection().prepareStatement("SELECT maxhealth FROM Players where uid=?");
	ps.setInt(1, uid);
	return ps.executeQuery().getInt(1);
    }
    public static int getPlayerHealth(int uid) throws SQLException
    {
	PreparedStatement ps = null;
	ps = BlackLance.BlackLance.getConnection().prepareStatement("SELECT health FROM Players where uid=?");
	ps.setInt(1, uid);
	return ps.executeQuery().getInt(1);
    }
    public static int getPlayerXP(int uid) throws SQLException
    {
	PreparedStatement ps = null;
	ps = BlackLance.BlackLance.getConnection().prepareStatement("SELECT exp FROM Players where uid=?");
	ps.setInt(1, uid);
	return ps.executeQuery().getInt(1);
    }
    public static ResultSet getPlayerData(Player p) throws SQLException
    {
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = BlackLance.BlackLance.getConnection().prepareStatement("SELECT * FROM Players where uuid=?");
	ps.setString(1, uuid);
	return ps.executeQuery();
    }
    public static Location getLocation(Connection c, Player p) throws SQLException
    {
	List<Integer> locData = new ArrayList<Integer>();
	String uuid = p.getUniqueId().toString();
	Statement statement = BlackLance.BlackLance.getConnection().createStatement();
	locData.add(0,(statement.executeQuery("SELECT x from Players where uuid="+uuid).getInt(0)));
	locData.add(1,(statement.executeQuery("SELECT y from Players where uuid="+uuid).getInt(0)));
	locData.add(2,(statement.executeQuery("SELECT z from Players where uuid="+uuid).getInt(0)));
	return new Location(p.getWorld(),locData.get(0), locData.get(1), locData.get(2));
    }    
    public static void saveDataByID(int uid, RPGPlayer rp) throws SQLException
    {
	Location loc1 = rp.getPlayer().getLocation();
	int x = (int) loc1.getX();
	int y = (int) loc1.getY();
	int z = (int) loc1.getZ();
	PreparedStatement ps = null;
	ps = BlackLance.BlackLance.getConnection().prepareStatement("UPDATE Players SET exp=?, health=?, maxhealth=?, x=?, y=?, z=? WHERE uid=?");
	ps.setInt(1, rp.getXP());
	ps.setInt(2, rp.getHealth());
	ps.setInt(3, rp.getMaxHealth());
	ps.setInt(4, x);
	ps.setInt(5, y);
	ps.setInt(6, z);
	ps.setInt(7, rp.getUID());
	ps.execute();
    }
}
