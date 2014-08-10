package Quest;

import com.gmail.filoghost.holograms.api.Hologram;
import org.bukkit.entity.Player;

public class QuestOptions {
    public Player player;
    public Player npc;
    public Hologram[] holograms;

    public QuestOptions(Player player, Player npc, Hologram[] holograms) {
        this.player = player;
        this.npc = npc;
        this.holograms = holograms;
    }
}
