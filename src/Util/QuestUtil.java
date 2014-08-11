package Util;

import BlackLance.BlackLance;
import Quest.QuestOptions;
import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class QuestUtil {
    public static final Map<Hologram, QuestOptions> questOptions = new HashMap<>();
    public static final Map<Hologram, Integer> optionIndices = new HashMap<>();

    public static QuestOptions createOptions(Player player, NPC npc, String[] lines) {
        World world = npc.getEntity().getWorld();
        Hologram[] holograms = new Hologram[lines.length];
        QuestOptions options = new QuestOptions(player, npc, holograms);
        Vector pos = npc.getEntity().getLocation().toVector().getMidpoint(player.getLocation().toVector()).normalize().multiply(1.5f).add(npc.getEntity().getLocation().toVector()).add(new Vector(0, 2, 0));
        player.sendMessage("placing at " + pos);
        for(int i = 0; i < holograms.length; i++) {
            pos.setY(pos.getY() - i * 0.5f);
            holograms[i] = HolographicDisplaysAPI.createIndividualHologram(BlackLance.getPlugin(), pos.toLocation(world), player, lines[i]);
            questOptions.put(holograms[i], options);
            optionIndices.put(holograms[i], i);
            holograms[i].setTouchHandler(BlackLance.optionManager);
        }
        return options;
    }
}