package Parties;

import java.util.HashMap;

import BlackLance.RPGPlayer;

public class Parties
{
    public HashMap<RPGPlayer, Party> parties = new HashMap<RPGPlayer, Party>();
    public void removeParty(Party party){parties.values().remove(party);}
    
}

