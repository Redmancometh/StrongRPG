package Parties;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import BlackLance.RPGPlayer;

public class Party
{
    private RPGPlayer[] partyMembers = new RPGPlayer[5];
    private List<RPGPlayer> invites = new ArrayList<RPGPlayer>();
    public Party(RPGPlayer Leader)
    {
	partyMembers[0]=Leader;
    }
    public void addPartyMember(RPGPlayer toAdd)
    {
	if(!this.isFull())
	{
	    for(int x = 0; x<=partyMembers.length; x++)
	    {
		if(partyMembers[x]==null){partyMembers[x]=toAdd;}
	    }
	}
    }
    public int getPartySize(){return partyMembers.length;}
    public void sendMessage(String message)
    {
	for(RPGPlayer rp : partyMembers){rp.p.sendMessage(ChatColor.BLUE+"[P]: "+message);}
    }
    public boolean isFull()
    {
	if(partyMembers.length>=5){return true;}
	else{return false;}
    }
    public boolean isLeader(RPGPlayer rp)
    {
	if(partyMembers[0]==rp){return true;}
	else{return false;}	
    }
    public void changeLeader(RPGPlayer newLeader)
    {
	for(int x = 0; x<partyMembers.length;)
	{
	    if(partyMembers[x] == newLeader){partyMembers[x]=partyMembers[0];}
	}
	partyMembers[0]=newLeader;
    }
    public void invitePlayer(RPGPlayer invited){invites.add(invited);}
    public boolean isInvited(RPGPlayer toCheck)
    {
	for(RPGPlayer rp : invites)
	{
	    if(rp==toCheck){return true;}
	}
	return false;
    }
    public void declineInvite(RPGPlayer rp){invites.remove(rp);}
    public void giveXP(double xp)
    {
	for(RPGPlayer rp : partyMembers)
	{
	    int levelDifference=rp.p.getLevel()-this.getAvgLevel();
	    rp.addXP(xp-(levelDifference*.15));
	}
    } //Need to add avg level modifier
    public int getAvgLevel()
    {
	int average=0;
	for(RPGPlayer rp : partyMembers)
	{
	    average+=rp.p.getLevel();
	}
	return average/partyMembers.length;
    }
}
