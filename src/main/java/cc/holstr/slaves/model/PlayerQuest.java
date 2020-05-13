package cc.holstr.slaves.model;

public class PlayerQuest {
    private String uuid;
    private String[] quests;

    public PlayerQuest(String uuid, String[] quests) {
        this.uuid = uuid;
        this.quests = quests;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String[] getQuests() {
        return quests;
    }

    public void setQuests(String[] quests) {
        this.quests = quests;
    }
}
