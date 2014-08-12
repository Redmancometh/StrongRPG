package Storage;

import java.util.HashMap;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import BlackLance.RPGPlayer;

public class RPGPlayers
{
    public static HashMap<Player, RPGPlayer> RPGPlayers = new HashMap();

    public static HashMap getFullMap()
    {
	return RPGPlayers;
    }

    public static RPGPlayer getRPGPlayer(Player p)
    {
	RPGPlayer rp = RPGPlayers.get(p);
	return rp;
    }

    public static void addRPGPlayer(Player p, RPGPlayer rp)
    {
	RPGPlayers.put(p, rp);
    }
    public static double getXP(Player p)
    {
	return RPGPlayers.get(p).getXP();
    }

}
