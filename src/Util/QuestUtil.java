package Util;

import BlackLance.BlackLance;
import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import org.bukkit.entity.Player;

public class QuestUtil {
    public static void createOptions(Player player, Player npc, String lines) {
        Hologram hologram = HolographicDisplaysAPI.createIndividualHologram(BlackLance.getPlugin(), null, player, lines);
    }
}
