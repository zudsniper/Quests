package cc.holstr.slaves.model;

public class Quest {
    private String id;
    private String info;
    private String objectives;
    private String next;
    private QuestCondition[] conditions;

    public Quest(String id, String info, String objectives, String next, QuestCondition[] conditions) {
        this.id = id;
        this.info = info;
        this.objectives = objectives;
        this.next = next;
        this.conditions = conditions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public QuestCondition[] getConditions() {
        return conditions;
    }

    public void setConditions(QuestCondition[] conditions) {
        this.conditions = conditions;
    }
}
