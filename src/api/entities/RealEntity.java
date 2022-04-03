package api.entities;

import state_machine.api.TransitionNotFoundException;
import state_machine.core.Event;
import state_machine.core.FiniteStateMachine;
import state_machine.core.State;

public abstract class RealEntity extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 3475322533463110542L;
    protected int life, strength, protection, agility = 0;
    protected FiniteStateMachine statsMachine;
    private State not_setted = new State("not_setted");

    public abstract void elude();

    public abstract void attack();

    public abstract void defend();

    public Boolean isStatsSetted() {
        return !statsMachine.isCurrentState(not_setted);
    }

    public void statsSetted() throws TransitionNotFoundException {
        statsMachine.change(new Event("E_statsSet"));
    }

    public void setStats(int life, int strength, int protection, int agility) {
        this.life = life;
        this.strength = strength;
        this.protection = protection;
        this.agility = agility;
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
        }
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
        }
        return i;
    }


}
