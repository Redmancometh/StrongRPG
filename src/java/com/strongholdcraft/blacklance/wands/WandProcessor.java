package com.strongholdcraft.blacklance.wands;

public class WandProcessor {
	/*JavaPlugin plugin;
	ItemStack wand;
	PlayerInteractEvent event;
	static HashMap<Player, Boolean> casters = new HashMap();
	public WandProcessor(ItemStack wand, PlayerInteractEvent event, JavaPlugin plugin)
	{
		this.event=event;
		this.wand=wand;
		this.plugin=plugin;
		if(wand.getItemMeta().getDisplayName().contains("Wand")){cast();}
	}
	public void cast()
	{
		final Player p = this.event.getPlayer();
		if(!casters.containsKey(p)||casters.get(p)==false)
		{
			casters.put(p, true);
			Location loc1 = p.getEyeLocation();
			p.sendMessage(ChatColor.GOLD+"Casting..");
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(plugin, new Runnable() 
			{
				public void run() 
				{
					p.getLocation().getDirection().normalize().multiply(1);
					Fireball f = p.launchProjectile(Fireball.class);
	            	f.setShooter(p);
	            	f.setYield(0);
	            	casters.put(p, false);
				}
			},27);
		}
		else
		{
			p.sendMessage(ChatColor.RED+"You're already casting that spell");
			return;
		}
		
	}*/
}