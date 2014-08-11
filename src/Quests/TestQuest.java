package Quests;

import Listeners.OptionManager;
import Quest.OptionListener;
import Quest.QuestOptions;
import Util.QuestUtil;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TestQuest implements OptionListener {
    private NPCRightClickEvent event;
    private JavaPlugin plugin;
    private Player player;

    public TestQuest(NPCRightClickEvent event, JavaPlugin plugin) {
        OptionManager.register(this, event.getNPC());
        this.event = event;
        this.plugin = plugin;
        this.player = event.getClicker();
    }

    public void testQuest() {
        Player player = event.getClicker();
        player.sendMessage("Hey, choose an option!");
        QuestUtil.createOptions(player, event.getNPC(), new String[] {
                "Yo man we got options",
                "I'm an option!",
                "WEEEEEEEEEEEEEE"
        });
    }

    @Override
    public void chooseOption(QuestOptions options, int option) {
        player.sendMessage("You picked: '" + options.holograms[option].getLines()[0] + "'");
    }
}