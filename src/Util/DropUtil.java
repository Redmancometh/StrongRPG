package Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityDestroy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.example.EntityHider;
import com.comphenix.example.EntityHider.Policy;

import BlackLance.BlackLance;
import Quests.QuestChecks;

public class DropUtil implements Listener 
{
	private int hdmg;
	private int lowdmg;
	private int armwep;
	private int defense;
	private int level;
	private double type;
	private List<String> lore = new ArrayList<String>();
	private String prefix = "";
	private String suffix = "";
	private String value  = "Sell Value: ";
	private ItemStack drop = new ItemStack(Material.WOOD);
	private ItemMeta dropmeta = drop.getItemMeta();
	private Player p;
	private World w;
	private Location loc1;
	private EntityDeathEvent event;
	private boolean armor = false;
	private JavaPlugin blacklance;
	private Random t = new Random();
	
	public DropUtil(JavaPlugin blacklance)
	{
		this.blacklance=blacklance;
		if(event!=null)
		{
			this.event=event;
			this.p=this.event.getEntity().getKiller();
			this.w=this.event.getEntity().getWorld();
			this.loc1=this.event.getEntity().getLocation();
		}
	}
	public void clear(EntityDeathEvent event)
	{
		event.getDrops().clear();
    	event.getEntity().getEquipment().clear();
    	event.setDroppedExp(0);
	}
	
	public double dropDecider(int level, Player killer, Entity paul)
	{
		this.level=level;
		QuestChecks check = new QuestChecks(blacklance);
		check.checkQuestMob(paul, killer);
		double doDrop = Math.random();
		if(doDrop<(.30))
		{
			double dropWheel = Math.random();
			if(dropWheel<(.55)){dropCommon(level, killer, paul);}
			if(dropWheel>=(.55)&&dropWheel<=(.95)){dropUncommon(level, killer, paul);}
			if(dropWheel>(.95)&&dropWheel<(98)){dropRare(level, killer, paul);}
			if(dropWheel>=(.98)&&dropWheel<=(1)){this.dropEpic(level, event);}
			/*if(dropWheel>(.98)){this.dropLegendary(level, event);}*/
		}
		if(doDrop>(.85)){dropPotion(killer, paul);}
		return doDrop;
	}
	public void dropPotion(Player killer, Entity paul)
	{
		ItemStack potion = new ItemStack(Material.POTION);
		ItemMeta potmeta = potion.getItemMeta();
		if(level<5)
		{
			potmeta.setDisplayName("Minor Healing Potion");
			lore.add("Heals Minor Damage");
			lore.add(ChatColor.BLUE+"Sell Value: 3");
			lore.add("PlayerI"+killer.getUniqueId());
		}
		if(level>5)
		{
			potmeta.setDisplayName("Light Healing Potion");
			lore.add("Heals Light Damage");
			lore.add(ChatColor.BLUE+"Sell Value: 7");
			lore.add("PlayerI"+killer.getUniqueId());
		}
		potmeta.setLore(lore);
		potion.setItemMeta(potmeta);
		lore.clear();
		CraftItem ci = (CraftItem) paul.getWorld().dropItem(paul.getLocation(), potion);
		hide(killer,ci);
	}
	public void dropCommon(int level, Player killer, Entity paul)
	{
		hdmg=t.nextInt(level+3)+1;if(hdmg>5)hdmg=5;
		lowdmg=t.nextInt(level)+1;if(lowdmg>4)lowdmg=4;
		armwep=t.nextInt(2);
		double subtype = Math.random();
		type=Math.random();
		if(lowdmg>hdmg){lowdmg-=(lowdmg-hdmg);}
		if(lowdmg==hdmg){hdmg++;}
		if(armwep==0)
		{
			if(level<=10)
			{
				if(type<=.25){drop.setType(Material.WOOD_SWORD);suffix="Wooden Sword";}
				if(type>.25&&type<=.50){drop.setType(Material.WOOD_AXE);suffix="Wooden Axe";}
				if(type>.50&&type<=.75){drop.setType(Material.SHEARS);suffix="Claws";}
				if(type>.75&&type<=1){drop.setType(Material.IRON_HOE);suffix="Scythe";}
				if(hdmg<=level){prefix=ChatColor.GRAY+"Drunk-Made ";}
				if(hdmg>=level+1){prefix=ChatColor.GRAY+"Crappy ";}
				if(hdmg>=level+2){prefix=ChatColor.GRAY+"Low-Quality ";}
			}
			if(level>10&&level<=20)
			{
				if(type<=.25){drop.setType(Material.STONE_AXE);suffix="Stone Axe";}
				if(type>.25&&type<=.50){drop.setType(Material.STONE_SWORD);suffix="Stone Sword";}
				if(type>.50&&type<=.75){drop.setType(Material.SHEARS);suffix="Assassin Claws";}
				if(type>.75&&type<=1){drop.setType(Material.IRON_HOE);suffix="Sickle";}
				if(hdmg<=level)
				{
					if(subtype>=50){prefix=ChatColor.GRAY+"Poorly Made ";}
					if(subtype<50){prefix=ChatColor.GRAY+"Broken ";hdmg=0;}
				}
				if(hdmg>=level+1)
				{
					if(subtype>=50){prefix=ChatColor.GRAY+"Badly Made ";}
					if(subtype<50){prefix=ChatColor.GRAY+"Shoddy ";hdmg-=2;}
				}
				if(hdmg>=level+2)
				{
					if(subtype>=50){prefix=ChatColor.GRAY+"Mediocre ";}
					if(subtype<50){prefix=ChatColor.GRAY+"Dull ";hdmg-=1;}
				}
				hdmg+=1;
				lowdmg+=1;
			}
			if(level>20)
			{
				if(type<=.25){drop.setType(Material.STONE_AXE);suffix="Maul";}
				if(type>.25&&type<=.50){drop.setType(Material.IRON_SWORD);suffix="Iron Sword";}
				if(type>.50&&type<=.75){drop.setType(Material.SHEARS);suffix="Katar";}
				if(type>.75&&type<=1){drop.setType(Material.IRON_HOE);suffix="Sickle";}
				if(hdmg<=level+1)
				{
					if(subtype>=50){prefix=ChatColor.GRAY+"Splintered ";}
					if(subtype<50){prefix=ChatColor.GRAY+"Broken ";hdmg=0;}
				}
				if(hdmg>=level+2)
				{
					if(subtype>=50){prefix=ChatColor.GRAY+"Edentate ";}
					if(subtype<50){prefix=ChatColor.GRAY+"Blunt ";hdmg=0;}
				}
				if(hdmg>=level+3)
				{
					if(subtype>=50){prefix=ChatColor.GRAY+"Jagged ";}
					if(subtype<50){prefix=ChatColor.GRAY+"Barbed ";hdmg=0;}
				}
				hdmg+=4;
				lowdmg+=2;
			}
			value=value+((hdmg+lowdmg)-2);
		}
		if(armwep==1)
		{
			armor = true;
			if(level<=10)
			{
				defense = t.nextInt(level+2);
				if(type<=.25){drop.setType(Material.LEATHER_BOOTS);suffix=ChatColor.GRAY+"Leather Boots";}
				if(type<=.50&&type>.25){drop.setType(Material.LEATHER_CHESTPLATE);suffix=ChatColor.GRAY+"Leather Body Armor";}
				if(type>.50&&type<=.75){drop.setType(Material.LEATHER_HELMET);suffix=ChatColor.GRAY+"Leather Coif";}
				if(type>.75){drop.setType(Material.LEATHER_LEGGINGS);suffix=ChatColor.GRAY+"Leather Chaps";}
				if(defense<=level){prefix=ChatColor.GRAY+"Drunk-Made ";}
				if(defense>=level+1){prefix=ChatColor.GRAY+"Crummy ";}
				if(defense>=level+2){prefix=ChatColor.GRAY+"Low-Quality ";}
			}
			if(level>10&&level<=20)
			{
				defense = t.nextInt(level+3);
				if(type<=.25){drop.setType(Material.LEATHER_BOOTS);suffix=ChatColor.GRAY+"Leather Boots";}
				if(type<=.50&&type>.25){drop.setType(Material.LEATHER_CHESTPLATE);suffix=ChatColor.GRAY+"Leather Body Armor";}
				if(type>.50&&type<=.75){drop.setType(Material.LEATHER_HELMET);suffix=ChatColor.GRAY+"Leather Coif";}
				if(type>.75){drop.setType(Material.LEATHER_LEGGINGS);suffix=ChatColor.GRAY+"Leather Chaps";}
				if(defense<=level){prefix=ChatColor.GRAY+"Cracked ";}
				if(defense>=level+1){prefix=ChatColor.GRAY+"Threadbare ";}
				if(defense>=level+2){prefix=ChatColor.GRAY+"Torn ";}
				if(defense>=level+3){prefix=ChatColor.GRAY+"Moth-eaten ";}
			}
			if(level>20)
			{
				if(type<=.25){drop.setType(Material.CHAINMAIL_LEGGINGS);suffix=ChatColor.GRAY+"Iron Woven Leggings";}
				if(type<=.50&&type>.25){drop.setType(Material.CHAINMAIL_CHESTPLATE);suffix=ChatColor.GRAY+"Chain Weave Plackart";}
				if(type>.50&&type<=.75){drop.setType(Material.CHAINMAIL_HELMET);suffix=ChatColor.GRAY+"Reinforced Helmet";}
				if(type>.75){drop.setType(Material.CHAINMAIL_BOOTS);suffix=ChatColor.GRAY+"Chain Boots";}
				if(defense<=level){prefix=ChatColor.GRAY+"Sun Baked ";}
				if(defense>=level+1){prefix=ChatColor.GRAY+"Rusty ";}
				if(defense>=level+2){prefix=ChatColor.GRAY+"Degraded ";}
				if(defense>=level+3){prefix=ChatColor.GRAY+"Stiff ";}
			}
			if(defense>1){value=value+(defense-1);}else{value=value+1;}
		}
			drop(killer, paul);
	}
	public void dropUncommon(int level, Player killer, Entity paul)
	{
		hdmg=t.nextInt(level+2)+2;
		lowdmg=t.nextInt(level)+1;
		armwep=t.nextInt(2);
		type=Math.random();
		if(lowdmg>hdmg){lowdmg-=(lowdmg-hdmg);}
		if(armwep==0)
		{
			
			if(level<=10)
			{
				if(type<.50){drop.setType(Material.WOOD_SWORD);suffix="Wooden Sword";}
				if(level>=4&&Math.random()>.50){hdmg+=1;}
				if(type>=.50&&type<=.65){drop.setType(Material.IRON_SWORD);suffix="Iron Sword";}
				if(type>.65){drop.setType(Material.WOOD_AXE);suffix="Wooden Axe";}
				if(hdmg<=level+1){prefix="Decent ";}
				if(hdmg>=level+2){prefix="Average ";}
				if(hdmg>=level+3){prefix="Above-Average ";}
				if(hdmg>=level+4){prefix="Dull ";}

			}
			if(level>10)
			{
				if(type<.50){drop.setType(Material.WOOD_SWORD);suffix="Wooden Sword";}
				if(level>=4&&Math.random()>.50){hdmg+=1;}
				if(type>=.50&&type<=.65){drop.setType(Material.STONE_SWORD);suffix="Stone Sword";}
				if(type>.65){drop.setType(Material.WOOD_AXE);suffix="Wooden Axe";}
				if(hdmg<=level+1){prefix="Mediocre ";}
				if(hdmg>=level+2){prefix="Suitable ";}
				if(hdmg>=level+3){prefix="Unblemished ";}
				if(hdmg>=level+4){prefix="Sharp ";}
			}
			if(level>20)
			{
			
			}
			value=value+((hdmg+lowdmg)-2);
		}
		if(armwep==1)
		{
			armor = true;
			if(level<=10)
			{
				defense = t.nextInt(level+2);
				if(type<=.25){drop.setType(Material.IRON_BOOTS);suffix="Basic Greaves";}
				if(type<=.50&&type>.25){drop.setType(Material.LEATHER_CHESTPLATE);suffix="Leather Body Armor";}
				if(type>.50&&type<=.75){drop.setType(Material.IRON_HELMET);suffix="Helmet";}
				if(type>.75){drop.setType(Material.LEATHER_LEGGINGS);suffix="Leather Chaps";}
				if(defense<=level+1){prefix="Mediocre ";}
				if(defense>=level+2){prefix="Average ";}
				if(defense>=level+3){prefix="Above-Average ";}
				defense+=1;
				if(defense>1){value=value+(defense-1);}else{value=value+1;}
			}
			if(level>10&&level<=25)
			{
				defense = t.nextInt(level+4);
				if(type<=.25){drop.setType(Material.IRON_BOOTS);suffix="Basic Greaves";}
				if(type<=.50&&type>.25){drop.setType(Material.LEATHER_CHESTPLATE);suffix="Leather Body Armor";}
				if(type>.50&&type<=.75){drop.setType(Material.IRON_HELMET);suffix="Basic Helmet";}
				if(type>.75){drop.setType(Material.LEATHER_LEGGINGS);suffix="Leather Chaps";}
				if(defense<=level+1){prefix="Beaten ";}
				if(defense>=level+2){prefix="Standard ";}
				if(defense>=level+3){prefix="Solid ";}
				if(defense>=level+3){prefix="Reinforced ";}
				defense+=1;
				if(defense>1){value=value+(defense-1);}else{value=value+1;}
			}
		}
			drop(killer, paul);
		
	}
	public void dropRare(int level, Player killer, Entity paul)
	{
		double decider = Math.random();
		armwep=t.nextInt(2);
		if(armwep==0)
		{
			if(level<=10)
			{
				if(decider<=.10){prefix=(ChatColor.GREEN+"The Knicker");suffix="";drop.setType(Material.IRON_AXE);hdmg=6;lowdmg=4;}
				if(decider<=.40&&decider>.10){prefix=(ChatColor.GREEN+"GutRipper");suffix="";drop.setType(Material.STONE_SWORD);hdmg=10;lowdmg=2;}
				if(decider<=1&&decider>.40){prefix=(ChatColor.GREEN+"Armot's Spear");suffix="";drop.setType(Material.IRON_SPADE);hdmg=10;lowdmg=4;}
			//lowlvl-rare loot table
			}
			if(level>10&&level<=25)
			{
				if(decider<=.10){prefix=(ChatColor.GREEN+"Night's Edge");suffix="";drop.setType(Material.GOLD_AXE);hdmg=19;lowdmg=9;}
				if(decider<=.40&&decider>.10){prefix=(ChatColor.GREEN+"Nightbane");suffix="";drop.setType(Material.IRON_SWORD);hdmg=18;lowdmg=9;}
				if(decider<=1&&decider>.40){prefix=(ChatColor.GREEN+"Brainhacker");suffix="";drop.setType(Material.GOLD_AXE);hdmg=16;lowdmg=9;}
			}
			if(level>20&&level<30)
			{
			
			}
			value=value+((hdmg+lowdmg)+15);
		}
		if(armwep==1)
		{
			armor=true;
			if(level<=10)
			{
				if(decider<=.10){prefix=(ChatColor.GREEN+"Helm of Honor");suffix="";drop.setType(Material.IRON_HELMET);defense=11;}
				if(decider<=.20&&decider>.10){prefix=(ChatColor.GREEN+"Warboots");suffix="";drop.setType(Material.IRON_BOOTS);defense=12;}
				if(decider<=.40&&decider>.20){prefix=(ChatColor.GREEN+"Cuirass of the Wind");suffix="";drop.setType(Material.IRON_CHESTPLATE);defense=15;}
				if(decider<=1&&decider>.40){prefix=(ChatColor.GREEN+"Jade Infused Legplates");suffix="";drop.setType(Material.IRON_LEGGINGS);defense=11;}
			//lowlvl-rare loot table
			}
			if(level>10&&level<=25)
			{
				if(decider<=.10){prefix=(ChatColor.GREEN+"Helm of Valor");suffix="";drop.setType(Material.GOLD_HELMET);defense=14;}
				if(decider<=.20&&decider>.10){prefix=(ChatColor.GREEN+"Emporer's Sabatons");suffix="";drop.setType(Material.GOLD_HELMET);defense=15;}
				if(decider<=1&&decider>.20){prefix=(ChatColor.GREEN+"King's Breastplate");suffix="";drop.setType(Material.GOLD_CHESTPLATE);defense=17;}
			}
			if(level>20&&level<30)
			{
				if(decider<=.10){prefix=(ChatColor.GREEN+"Mercurial Helm");suffix="";drop.setType(Material.GOLD_HELMET);defense=14;}
				if(decider<=.20&&decider>.10){prefix=(ChatColor.GREEN+"Gladiators Treads");suffix="";drop.setType(Material.GOLD_BOOTS);defense=15;}
				if(decider<=.40&&decider>.20){prefix=(ChatColor.GREEN+"Helm of Bartuc");suffix="";drop.setType(Material.LEATHER_HELMET);defense=17;}
				if(decider<=1&&decider>.40){prefix=(ChatColor.GREEN+"Juggernaught Plates");suffix="";drop.setType(Material.IRON_CHESTPLATE);defense=17;}
			}
			value=value+(defense+15);
		}
		drop(killer, paul);
	}
	public void dropEpic(int level, EntityDeathEvent event)
	{
		int epicWheel=t.nextInt();
		if(level<10)
		{
			prefix=(ChatColor.DARK_PURPLE+"The Hallowed Scythe");suffix="";drop.setType(Material.IRON_HOE);hdmg=26;lowdmg=18;
		}
		if(level>10&&level<20)
		{
			prefix=(ChatColor.DARK_PURPLE+"Omen Claws");suffix="";drop.setType(Material.SHEARS);hdmg=20;lowdmg=13;
		}
		if(level>20&&level<30)
		{
			prefix=(ChatColor.DARK_PURPLE+"The Hallowed Scythe");suffix="";drop.setType(Material.IRON_HOE);hdmg=26;lowdmg=18;
		}
		value=value+((hdmg+lowdmg)+40);

	}
	public void dropLegendary(int level, EntityDeathEvent event)
	{
		//Legendary Loot table.
		int legendWheel=t.nextInt();
	}
	public void drop(Player killer, Entity paul)
	{
		if(armor==false){lore.add("Damage: "+lowdmg+"-"+hdmg);}
		if(armor==true){lore.add("Defense: "+defense);}
		lore.add(ChatColor.BLUE+value);
		dropmeta.setDisplayName(prefix+suffix+" ");
		drop.setDurability((short)0);
		armor=false;
		lore.add("PlayerI"+killer.getUniqueId());
		dropmeta.setLore(lore);		
		drop.setItemMeta(dropmeta);
		if(dropmeta.getDisplayName().equals("Helm of Bartuc"))
		{
			LeatherArmorMeta lm = (LeatherArmorMeta)drop.getItemMeta();
			lm.setColor(Color.RED);
			drop.setItemMeta(lm);
		}
		lore.clear();
		CraftItem ci = (CraftItem)paul.getWorld().dropItem(paul.getLocation(), drop);
		hide(killer,ci);
	}
	public void hide(Player p, CraftItem ci)
	{
		EntityHider eh = new EntityHider(blacklance, Policy.BLACKLIST);
		List<CraftItem> cil = new ArrayList<CraftItem>();
		List<Player> pl = new ArrayList<Player>();
		Location loc1 = p.getLocation();
		for(Iterator<Entity> iter = p.getNearbyEntities(loc1.getX(), loc1.getY(), loc1.getZ()).iterator(); iter.hasNext();)
		{
			Entity e = iter.next();
			if(e instanceof Player)
			{
				if(!e.equals(p)&&!CitizensAPI.getNPCRegistry().isNPC(e))
				{
					eh.hideEntity((Player)e, ci);
				}
			}
		}
	}

}
