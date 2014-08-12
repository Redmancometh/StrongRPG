package Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;

import Storage.DBUtil;

public class ResourceNodes
{
    public static List<Location> hayLocations = new ArrayList<Location>();
    public static List<Location> sweetGumlocations = new ArrayList<Location>();
    public static HashMap<Block, Hologram> resourceLabels = new HashMap<Block, Hologram>();
    public static void generateHay() throws SQLException
    {
	Hologram[] holos = HolographicDisplaysAPI.getHolograms(BlackLance.BlackLance.getPlugin());
	ResultSet rs = DBUtil.getResourceNodes("hay");
	while(rs.next())
	{
	    Location loc1 = new Location(Bukkit.getWorld("world"),rs.getInt(1),rs.getInt(2),rs.getInt(3));
	    hayLocations.add(loc1);
	}
	for(Location loc:hayLocations){loc.getBlock().setType(Material.AIR);}
	Collections.shuffle(hayLocations);
	for(int x = 0; x<8; x++)
	{
	    String[] nig = {"test","hay"};
	    Location loc1 = hayLocations.get(x);
	    Block b = loc1.getBlock();
	    b.setType(Material.HAY_BLOCK);
	    loc1.add(.5, 1.2, .5);
	    Hologram hologram = HolographicDisplaysAPI.createHologram(BlackLance.BlackLance.getPlugin(), loc1, ChatColor.DARK_GRAY+"Hay");
	    resourceLabels.put(b, hologram);
	}

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
    public static void replaceHay(Block oldBlock)
    {
	boolean set = false;
	while(!set)
	{
	    Collections.shuffle(ResourceNodes.hayLocations);
	    Block b = ResourceNodes.hayLocations.get(0).getBlock();
	    if(b.getType()!=Material.HAY_BLOCK&&!b.getLocation().equals(oldBlock))
	    {
		b.setType(Material.HAY_BLOCK);
		Location loc1 = b.getLocation().add(.5, 1.2, .5);
		Hologram hologram = HolographicDisplaysAPI.createHologram(BlackLance.BlackLance.getPlugin(), loc1, ChatColor.DARK_GRAY+"Hay");
		resourceLabels.put(b, hologram);
		Bukkit.broadcastMessage(b.getLocation().toString());
		set=true;
	    }
	    else{Collections.shuffle(ResourceNodes.hayLocations);}
	}
    }
}
