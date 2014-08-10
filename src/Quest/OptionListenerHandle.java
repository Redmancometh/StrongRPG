package Quest;

import org.bukkit.entity.Player;

public class OptionListenerHandle {
    public OptionListener listener;
    public Player npc;

    public OptionListenerHandle(OptionListener listener, Player npc) {
        this.listener = listener;
        this.npc = npc;
    }
}
