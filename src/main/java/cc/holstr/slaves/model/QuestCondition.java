package cc.holstr.slaves.model;

public class QuestCondition {
    private String type;
    private String id;
    private int number;

    public QuestCondition(String type, String id, int number) {
        this.type = type;
        this.id = id;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
