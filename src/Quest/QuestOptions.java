package Quest;

import com.gmail.filoghost.holograms.api.Hologram;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

public class QuestOptions {
    public Player player;
    public NPC npc;
    public Hologram[] holograms;

    public QuestOptions(Player player, NPC npc, Hologram[] holograms) {
        this.player = player;
        this.npc = npc;
        this.holograms = holograms;
    }
}