package api.entities;

import state_machine.core.State;

public abstract class Monster extends RealEntity {
    /**
     *
     */
    private static final long serialVersionUID = -1101368383353699132L;
    protected int fear, anger = 0;
    protected State defeated = new State("defeated");
    public Monster() {
        gold = 0;
        description = "Un mostro generico; le sue caratteristiche sono: forza, difesa, spavento, rabbia, agilit�";
        name = "mostro";
    }

    public void setStats(int life, int strength, int protection, int fear, int anger, int agility) {
        this.life = life;
        this.strength = strength;
        this.protection = protection;
        this.fear = fear;
        this.anger = anger;
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
            case FEAR:
                i = fear;
                break;
            case ANGER:
                i = anger;
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
            case FEAR:
                this.fear = val;
                break;
            case ANGER:
                this.anger = val;
                break;
        }
    }

    public String toString() {
        String string = String.format(
                "%nnome: %s%nvita: %d%nforza: %d%ndifesa: %d%nagilit�: %d%nspavento: %d%nrabbia: %d%nmonete d'oro: %d", name, life, strength, protection,
                agility, fear, anger, gold);
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

    protected enum Stat {
        LIFE, STRENGTH, PROTECTION, FEAR, ANGER, AGILITY
    }

}