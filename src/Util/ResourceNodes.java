package Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import Storage.DBUtil;

public class ResourceNodes
{
    public static List<Location> hayLocations = new ArrayList<Location>();
    public static List<Location> sweetGumlocations = new ArrayList<Location>();
    public static void generateHay() throws SQLException
    {
	ResultSet rs = DBUtil.getResourceNodes("hay");
	while(rs.next())
	{
	    Location loc1 = new Location(Bukkit.getWorld("world"),rs.getInt(1),rs.getInt(2),rs.getInt(3));
	    hayLocations.add(loc1);
	}
	for(Location loc:hayLocations){loc.getBlock().setType(Material.AIR);}
	Collections.shuffle(hayLocations);
	for(int x = 0; x<8; x++){hayLocations.get(x).getBlock().setType(Material.HAY_BLOCK);}
    }
    public static void generateSweetGum() throws SQLException
    {
	ResultSet rs = DBUtil.getResourceNodes("sweetgum");
	while(rs.next())
	{
	    Location loc1 = new Location(Bukkit.getWorld("world"),rs.getInt(1),rs.getInt(2),rs.getInt(3));
	    sweetGumlocations.add(loc1);
	}
	for(Location loc:sweetGumlocations){loc.getBlock().setType(Material.AIR);}
	Collections.shuffle(sweetGumlocations);
	for(int x = 0; x<8; x++){sweetGumlocations.get(x).getBlock().setTypeIdAndData(175, (byte)4, true);}

    }
}
