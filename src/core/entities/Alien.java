package core.entities;

import api.entities.Monster;
import api.planet.Planet;
import api.planet.Valore;
import state_machine.api.TransitionNotFoundException;
import state_machine.core.Event;
import state_machine.core.FiniteStateMachine;
import state_machine.core.State;
import state_machines.StateMachines;

import java.util.Random;

public class Alien extends Monster {

    private static final long serialVersionUID = -7924780092466403732L;
    private String type = "";
    private int count = 32;
    private FiniteStateMachine turnMachine;
    private State alienT = new State("alienT");


    public Alien(Planet planet) {
        name = "Alien";
        Random random = new Random();
        turnMachine = StateMachines.turnMachineBuilder();
        int temp = 0;
        this.life = random.nextInt(101);
        if (count > 0) temp = random.nextInt(15);
        this.strength = temp;
        if (count - temp > 0) count -= temp;
        else {
            count = 0;
            temp = 0;
        }
        if (count > 0) temp = random.nextInt(15);
        this.protection = temp;
        if (count - temp > 0) count -= temp;
        else {
            count = 0;
            temp = 0;
        }
        if (count > 0) temp = random.nextInt(15);
        this.agility = temp;
        if (count - temp > 0) count -= temp;
        else {
            count = 0;
            temp = 0;
        }
        if (count > 0) temp = random.nextInt(15);
        this.fear = temp;
        if (count - temp > 0) count -= temp;
        else {
            count = 0;
            temp = 0;
        }
        if (count > 0) temp = random.nextInt(15);
        this.anger = temp;
        if (count - temp > 0) count -= temp;
        else {
            count = 0;
            temp = 0;
        }

        if (planet.getValue(Valore.FERRO) > 40) {
            this.protection += 5;
            this.type += "_iron_";
        }
        if (planet.getValue(Valore.ORO) > 30) {
            this.gold += 200;
            this.type += "_gold_";
        }
        if ((planet.getValue(Valore.ACQUA) < 20) && (planet.getValue(Valore.TERRA) > 40)) {
            this.agility += 6;
            this.type += "_fast_";
        }
        name = "Alien" + type;


    }

    public String getType() {
        return type;
    }

    public Boolean isAlienTurn() {
        return turnMachine.isCurrentState(alienT);
    }

    public void changeTurn() throws TransitionNotFoundException {
        if (turnMachine.isCurrentState(alienT)) turnMachine.change(new Event("E_playerTurn"));
        else turnMachine.change(new Event("E_alienTurn"));
    }
}
