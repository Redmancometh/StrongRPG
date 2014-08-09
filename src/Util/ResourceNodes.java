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
    public static void generateHay() throws SQLException
    {
	ResultSet rs = DBUtil.getHayNodes();
	List<Location> locations = new ArrayList<Location>();
	while(rs.next())
	{
	    Location loc1 = new Location(Bukkit.getWorld("world"),rs.getInt(1),rs.getInt(2),rs.getInt(3));
	    locations.add(loc1);
	}
	for(Location loc:locations){loc.getBlock().setType(Material.AIR);}
	Collections.shuffle(locations);
	for(int x = 0; x<8; x++){locations.get(x).getBlock().setType(Material.HAY_BLOCK);}
    }
    public static void generateSweetGum() throws SQLException
    {
	ResultSet rs = DBUtil.getSweetgumNodes();
	List<Location> locations = new ArrayList<Location>();
	while(rs.next())
	{
	    Location loc1 = new Location(Bukkit.getWorld("world"),rs.getInt(1),rs.getInt(2),rs.getInt(3));
	    locations.add(loc1);
	}
	for(Location loc:locations){loc.getBlock().setType(Material.AIR);}
	Collections.shuffle(locations);
	for(int x = 0; x<8; x++){locations.get(x).getBlock().setTypeIdAndData(175, (byte)4, true);}

    }
}
