package Util;

import BlackLance.BlackLance;
import Quest.QuestOptions;
import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class QuestUtil {
    public static final Map<Hologram, QuestOptions> questOptions = new HashMap<>();
    public static final Map<Hologram, Integer> optionIndices = new HashMap<>();

    public static QuestOptions createOptions(Player player, Player npc, String lines) {
        World world = npc.getWorld();
        String[] sepLines = lines.split("\n");
        Hologram[] holograms = new Hologram[sepLines.length];
        QuestOptions options = new QuestOptions(player, npc, holograms);
        Vector pos = npc.getLocation().toVector().getMidpoint(player.getLocation().toVector()).normalize().multiply(0.5f).add(new Vector(0, 2, 0));
        for(int i = 0; i < holograms.length; i++) {
            pos.setY(pos.getY() - i * 0.5f);
            holograms[i] = HolographicDisplaysAPI.createIndividualHologram(BlackLance.getPlugin(), pos.toLocation(world), player, lines);
            questOptions.put(holograms[i], options);
            optionIndices.put(holograms[i], i);
        }
        return options;
    }
}
