package Listeners;

import Quest.OptionListener;
import Quest.OptionListenerHandle;
import Quest.QuestOptions;
import Util.QuestUtil;
import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.TouchHandler;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionManager implements TouchHandler {
    public static Map<NPC, List<OptionListener>> listeners = new HashMap<>();

    public static OptionListenerHandle register(OptionListener listener, NPC npc) {
        List<OptionListener> l = listeners.get(npc);
        if(l == null) {
            l = new ArrayList<>();
        }
        l.add(listener);
        return new OptionListenerHandle(listener, npc);
    }

    public static void remove(OptionListenerHandle handle) {
        OptionListener listener = handle.listener;
        NPC npc = handle.npc;
        List<OptionListener> l = listeners.get(npc);
        if(l == null) {
            return;
        }
        l.remove(listener);
    }

    @Override
    public void onTouch(Hologram hologram, Player player) {
        QuestOptions options = QuestUtil.questOptions.get(hologram);
        int index = QuestUtil.optionIndices.get(hologram);
        List<OptionListener> l = listeners.get(options.npc);
        if(l == null) {
            return;
        }
        for(OptionListener listener : l) {
            listener.chooseOption(options, index);
        }
    }
}