package api.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3921695868276200942L;
    protected String name = "";
    protected int gold;
    protected String description = "";
    protected Long id = 0l;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return gold;
    }

    public void setMoney(int gold) {
        this.gold = gold;
    }

    public void addMoney(int gold) {
        this.gold += gold;
    }

    public void subMoney(int gold) {
        this.gold -= gold;
    }

    public String readDescr() {
        return description;
    }

    public abstract void talk();

    public abstract void action();

}
