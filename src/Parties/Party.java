package Parties;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import BlackLance.RPGPlayer;

public class Party
{
    //CHECK FOR LEADER ON LEAVE!
    private RPGPlayer[] partyMembers = new RPGPlayer[5];
    private List<RPGPlayer> invites = new ArrayList<RPGPlayer>();
    public Party(RPGPlayer Leader)
    {
	partyMembers[0]=Leader;
    }
    public String[] getList()
    {
	String[] test = new String[5];
	for(int x = 0; x<partyMembers.length; x++)
	{
	    if(partyMembers[x]!=null)
	    {
		test[x]=partyMembers[x].p.getName()+x;
	    }
	}
	return test;
    }
    public static void CreateParty(RPGPlayer partyLeader)
    {
	Party party = new Party(partyLeader);
	Parties.parties.put(partyLeader, party);
    }
    public void addPartyMember(RPGPlayer toAdd)
    {
	Parties.parties.put(toAdd, this);
	for(int x = 0; x<partyMembers.length; x++)
	{
	    if(partyMembers[x]==null)
	    {
		partyMembers[x]=toAdd;
		break;
	    }
	}
    }
    public void removeLeader()
    {
	partyMembers[0]=null;
	boolean disband = true;
	for(int x = 1; x<partyMembers.length; x++)
	{
	    RPGPlayer rp = partyMembers[x];
	    if(rp!=null)
	    {
		disband=false;
		partyMembers[x-1]=rp;
	    }
	    else{partyMembers[x-1]=null;}
	}
    }
    public void kickPlayer(RPGPlayer rp)
    {
	partyMembers[0].p.sendMessage(ChatColor.AQUA+"You have kicked "+rp.p.getName()+" from your party!");
	this.sendMessage(rp.p.getName()+"Was kicked from your party by "+partyMembers[0].p.getName());
	rp.p.sendMessage(ChatColor.AQUA+partyMembers[0].p.getName()+" has kicked you from the party!");
	removePartyMember(rp,"kicked");
    }
    public void removePartyMember(RPGPlayer rp, String reason)
    {
	for(int x = 1; x<partyMembers.length; x++)
	{
	    RPGPlayer player = partyMembers[x];
	    if(player!=null)
	    {
		if(rp==player)
		{
		    if(x==5){partyMembers[5]=null;}
		    else
		    {
			for(int y = x+1; y<partyMembers.length; y++)
			{
			    if(partyMembers[y]!=null){partyMembers[y]=partyMembers[y-1];}
			}
		    }
		}
	    }
	}
	if(reason.equalsIgnoreCase("kicked"))
	{
	    kickPlayer(rp);
	}
	if(reason.equalsIgnoreCase("left"))
	{
	    rp.p.sendMessage(ChatColor.AQUA+"You have left your party!");
	    this.sendMessage(rp.p.getName()+" Has left your party!");
	}
    }
    public int getPartySize(){return partyMembers.length;}
    public void sendMessage(String message)
    {
	Bukkit.broadcastMessage("ACCEPTED");
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp!=null)rp.p.sendMessage(ChatColor.AQUA+"[Party]: "+message);
	}
    }
    public void sendMessage(String message, RPGPlayer sender)
    {
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp!=null)rp.p.sendMessage(ChatColor.AQUA+"[Party] "+"["+sender.p.getName()+"]: "+ChatColor.AQUA+message);
	}
    }
    public boolean isFull()
    {
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp==null){return false;}
	}
	return true;
    }
    public boolean isInParty(RPGPlayer player)
    {
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp==player){return true;}
	}
	return false;
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
    public void invitePlayer(final RPGPlayer invited)
    {
	if(!this.isInParty(invited))
	{
	    if(!this.isFull())
	    {
		if(!Parties.parties.containsKey(invited))
		{
		    invites.add(invited);
		    invited.p.sendMessage(ChatColor.AQUA+"You have been invited to join a party by "+this.partyMembers[0].p.getName());
		    invited.invited=this;
		    new BukkitRunnable()
		    {
		    	public void run()
		    	{
		    	    Party.this.invites.remove(invited);
		    	    if(!Party.this.isInParty(invited))
		    	    {
		    		Party.this.partyMembers[0].p.sendMessage(ChatColor.AQUA+invited.p.getName()+" Did not respond to your invite");
		    	    }
		    	}
		    
		    }.runTaskLater(BlackLance.BlackLance.getPlugin(), 600);
		}else{partyMembers[0].p.sendMessage(ChatColor.AQUA+"Player is already in a party!");}
	    }else{partyMembers[0].p.sendMessage(ChatColor.AQUA+"Your party is full!");}
	}else{partyMembers[0].p.sendMessage(ChatColor.AQUA+"This player is already in your party");}
    }
    public boolean isInvited(RPGPlayer toCheck)
    {
	for(RPGPlayer rp : invites)
	{
	    if(rp==toCheck){return true;}
	}
	return false;
    }
    public void acceptInvite(RPGPlayer rp)
    {
	rp.invited=null;
	this.addPartyMember(rp);
	this.sendMessage(rp.p.getName()+" Has joined your party!");
    }
    public void declineInvite(RPGPlayer rp){invites.remove(rp);}
    public void giveXP(double xp)
    {
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp!=null)
	    {
		int levelDifference=rp.p.getLevel()-this.getAvgLevel();
		xp-=levelDifference*.15;
		if(xp>0){rp.addXP(xp-(levelDifference*.15));}
	    }
	}
    }
    public int getAvgLevel()
    {
	int average=0;
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp!=null)
	    {
		average+=rp.p.getLevel();
	    }
	}
	return average/partyMembers.length;
    }
}
