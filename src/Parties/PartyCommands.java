package Parties;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import BlackLance.RPGPlayer;
import Storage.RPGPlayers;
public class PartyCommands implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	Player p = (Player) sender;
	RPGPlayer rp = RPGPlayers.getRPGPlayer(p);
	if (cmd.getName().equalsIgnoreCase("party") || cmd.getName().equalsIgnoreCase("p"))
	{
	    if (args.length >= 1)
	    {
		String message = "";
		for (String s : args)
		{
		    message += " " + s;
		}
		Parties.getPartyByPlayer(p).sendMessage(message, rp);
	    }
	}
	if (cmd.getName().equalsIgnoreCase("acceptinvite"))
	{
	    if (rp.invited != null){rp.invited.acceptInvite(rp);}
	    else
	    {
		p.sendMessage(ChatColor.DARK_RED + "You have no pending invites");
	    }
	}
	if (cmd.getName().equalsIgnoreCase("invite"))
	{
	    if (args.length < 1)
	    {
		p.sendMessage(ChatColor.DARK_RED + "Please enter the name of who you want to invite!");
		return false;
	    }
	    else
	    {
		Player invitee = Bukkit.getServer().getPlayer(args[0]);
		if (invitee != null)
		{
		    Bukkit.broadcastMessage(Parties.parties.values()+"STUFF");
		    if (Parties.parties.containsKey(rp))
		    {
			p.sendMessage(ChatColor.AQUA + "You have invited " + invitee.getDisplayName() + " to your party!");
			Party party = Parties.getPartyByPlayer(p);
			String[] players = party.getList();
			for(String s : players)
			{
			    if(s!=null)
			    {
				Bukkit.broadcastMessage(s);
			    }
			}
			if(party.isLeader(rp))
			{
			    party.invitePlayer(RPGPlayers.getRPGPlayer(invitee));
			}
		    }
		    else
		    {
			p.sendMessage(ChatColor.DARK_RED + "You are not in a party!");
		    }
		}
	    }
	}
	if (cmd.getName().equalsIgnoreCase("makeparty"))
	{
	    if(!Parties.parties.containsKey(rp))
	    {
		Party.CreateParty(RPGPlayers.getRPGPlayer(p));
		p.sendMessage(ChatColor.AQUA+"You have formed a party!");
	    }
	    else{p.sendMessage(ChatColor.DARK_RED+"You are already in a party!");}
	}
	if(cmd.getName().equalsIgnoreCase("leaveparty"))
	{
	    if(Parties.parties.containsKey(rp))
	    {
		Parties.parties.get(rp).removePartyMember(rp, "left");
	    }
	}
	if(cmd.getName().equalsIgnoreCase("kickfromparty"))
	{
	    if(Parties.parties.containsKey(rp))
	    {
		Parties.parties.get(rp).removePartyMember(rp, "kick");
	    }
	}
	return true;
    }
}
