package Quest;

import net.citizensnpcs.api.npc.NPC;

public class OptionListenerHandle {
    public OptionListener listener;
    public NPC npc;

    public OptionListenerHandle(OptionListener listener, NPC npc) {
        this.listener = listener;
        this.npc = npc;
    }
}
