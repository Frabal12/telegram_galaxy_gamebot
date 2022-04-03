package api.entities;

public abstract class Human extends RealEntity {
    /**
     *
     */
    private static final long serialVersionUID = -3910689911789495228L;
    private int fascination, charism = 0;
    public Human() {
        gold = 0;
        description = "Un essere umano; le sue caratteristiche sono: forza, difesa, fascino, carisma, agilit�";
        name = "umano";
    }

    public void setStats(int life, int strength, int protection, int fascination, int charism, int agility) {
        this.life = life;
        this.strength = strength;
        this.protection = protection;
        this.fascination = fascination;
        this.charism = charism;
        this.agility = agility;
    }

    public int getStat(Stat stat) {
        int i = 0;
        switch (stat) {
            case STRENGTH:
                i = strength;
                break;
            case PROTECTION:
                i = protection;
                break;
            case AGILITY:
                i = agility;
                break;
            case LIFE:
                i = life;
                break;
            case FASCINATION:
                i = fascination;
                break;
            case CHARISM:
                i = charism;
                break;

        }
        return i;
    }

    public void setStat(Stat stat, int val) {
        switch (stat) {
            case STRENGTH:
                this.strength = val;
                break;
            case PROTECTION:
                this.protection = val;
                break;
            case AGILITY:
                this.agility = val;
                break;
            case LIFE:
                this.life = val;
                break;
            case FASCINATION:
                this.fascination = val;
                break;
            case CHARISM:
                this.charism = val;
                break;
        }
    }

    public String toString() {
        String string = String.format(
                "%nnome: %s %nvita: %d%nforza: %d%ndifesa: %d%nagilit�: %d%nfascino: %d%ncarisma: %d%nmonete d'oro: %d", name, life, strength, protection,
                agility, fascination, charism, gold);
        return string;
    }

    @Override
    public void talk() {
    }

    @Override
    public void action() {
    }

    @Override
    public void elude() {
    }

    @Override
    public void attack() {
    }

    @Override
    public void defend() {
    }

    enum Stat {
        LIFE, STRENGTH, PROTECTION, FASCINATION, CHARISM, AGILITY
    }

}
