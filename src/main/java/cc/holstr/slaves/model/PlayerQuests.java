package cc.holstr.slaves.model;

import org.bukkit.entity.Player;

public class PlayerQuests {
    private PlayerQuest[] players;

    public PlayerQuests() {
        players = new PlayerQuest[1];
    }

    public PlayerQuests(PlayerQuest[] players) {
        this.players = players;
    }

    public PlayerQuest[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerQuest[] players) {
        this.players = players;
    }

    /**
     * Add a quest to a players quest list. Inefficient because of array resizing & copy
     * @param uuid
     * @param questId
     */
    public void addQuestToPlayer(String uuid, String questId) {
        //check if player exists
        boolean createPlayer = true;
        for(PlayerQuest pq : players) {
            if(uuid.equals(pq.getUuid())) {
                createPlayer = false;
            }
        }

        // if the player needs to be created, do so, add them to the player list, add the questId, return
        if(createPlayer) {
            String[] quests = new String[1];
            quests[0] = questId;
            PlayerQuest newPQ = new PlayerQuest(uuid, quests);

            //to add a new player to the playerquests array we must make a new array of a greater size and copy over
            PlayerQuest[] newPQuests = new PlayerQuest[players.length + 1];
            for(int i = 0; i < players.length; i++) {
                newPQuests[i] = players[i];
            }
            newPQuests[players.length] = newPQ;
            return;
        }

        for(PlayerQuest pq : players) {
            if(uuid.equals(pq.getUuid())) {
                String[] questStrs = pq.getQuests();
                String[] newQuestStrs = new String[questStrs.length + 1];
                //copy over old questStrs
                for(int i = 0; i < questStrs.length; i++) {
                    newQuestStrs[i] = questStrs[i];
                }
                //add the new quest
                newQuestStrs[questStrs.length] = questId;
                pq.setQuests(newQuestStrs);
            }
        }
    }

    /**
     * Remove a quest from a players quest list. Not as terribly inefficient as adding.
     * @param uuid
     * @param questId
     */
    public void removeQuestFromPlayer(String uuid, String questId) {
        for(PlayerQuest pq : players) {
            if(uuid.equals(pq.getUuid())) {
                String[] playerQuestStrs = pq.getQuests();
                for(int i = 0; i < playerQuestStrs.length; i++) {
                    if(questId.equals(playerQuestStrs[i])) {
                        playerQuestStrs[i] = null;
                        return;
                    }
                }
            }
        }
    }

    /**
     * Get quest name list (string) from player uuid
     * @param uuid
     * @return the list or null if not found
     */
    public String[] getQuestsForPlayer(String uuid) {
        for(PlayerQuest pq : players) {
            if(uuid.equalsIgnoreCase(pq.getUuid())) {
                return pq.getQuests();
            }
        }
        return null;
    }
}
