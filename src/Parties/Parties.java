package Parties;

import java.util.HashMap;

import org.bukkit.entity.Player;

import Storage.RPGPlayers;
import BlackLance.RPGPlayer;

public class Parties
{
    public static HashMap<RPGPlayer, Party> parties = new HashMap<RPGPlayer, Party>();
    public void removeParty(Party party){parties.values().remove(party);}
    public static Party getPartyByPlayer(Player p){return parties.get(RPGPlayers.getRPGPlayer(p));}
}

