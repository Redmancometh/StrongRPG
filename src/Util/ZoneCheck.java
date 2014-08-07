package Util;

import org.bukkit.Location;

public class ZoneCheck
{
	public static boolean checkWindfall(Location loc1)
	{
		double x = loc1.getX();
		double y = loc1.getY();
		double z = loc1.getZ();
		if(x>1700&&x<1913&&z>1740&&z<1888){return true;}else{return false;}
	}
	public static boolean checkGalannas(Location loc1)
	{
		double x = loc1.getX();
		double y = loc1.getY();
		double z = loc1.getZ();
		if(x>42&&x<98&&z>209&&z<258){return true;}else{return false;}
	}
	public static boolean checkDaggerfall(Location loc1)
	{
		double x = loc1.getX();
		double y = loc1.getY();
		double z = loc1.getZ();
		if(x>118&&x<174&&z>211&&z<260){return true;}else{return false;}
	}
}
