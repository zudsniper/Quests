package cc.holstr.slaves;

import cc.holstr.slaves.model.PlayerQuests;
import cc.holstr.slaves.model.Quest;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinEvent implements Listener {
    public static Server plugin;

    private PlayerQuests playerQuests;
    private List<Quest> quests;
    private Quest defaultQuest;

    public JoinEvent(PlayerQuests playerQuests, List<Quest> quests, Quest defaultQuest) {
        this.playerQuests = playerQuests;
        this.quests = quests;
        this.defaultQuest = defaultQuest;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinerUUID = event.getPlayer().getUniqueId().toString();
        playerQuests.addQuestToPlayer(joinerUUID, defaultQuest.getId());
    }
}
