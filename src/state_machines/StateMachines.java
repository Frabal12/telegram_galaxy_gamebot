package state_machines;

import state_machine.core.Event;
import state_machine.core.FiniteStateMachine;
import state_machine.core.State;
import state_machine.core.Transition;

public class StateMachines {

	public static FiniteStateMachine nameMachineBuilder() {
		State locked = new State("locked");
		State unlocked = new State("unlocked");
		Transition unlock = new Transition(locked, unlocked, new Event("E_unlock"));
		FiniteStateMachine nameMachine = new FiniteStateMachine(locked, unlocked);
		nameMachine.addTransition(unlock);
		return nameMachine;
	}
	
	public static FiniteStateMachine statsMachineBuilder() {
		State setted = new State("setted");
		State not_setted = new State("not_setted");
		Transition set = new Transition(not_setted, setted, new Event("E_statsSet"));
		FiniteStateMachine statsMachine = new FiniteStateMachine(not_setted, setted);
		statsMachine.addTransition(set);
		return statsMachine;
	}
	
	public static FiniteStateMachine turnMachineBuilder() {
		State playerT = new State("playerT");
		State alienT = new State("alienT");
		Transition plaToAl = new Transition(playerT, alienT, new Event("E_alienTurn"));
		Transition alToPla = new Transition(alienT, playerT, new Event("E_playerTurn"));
		FiniteStateMachine turnMachine = new FiniteStateMachine(playerT, alienT);
		turnMachine.addTransition(plaToAl);
		turnMachine.addTransition(alToPla);
		return turnMachine;
	}
	
	public static FiniteStateMachine numericInputMachineBuilder() {
		State lInput = new State("literalInput");
		State refillBullets = new State("refillBullet");
		State materialToPowerUp = new State("materialToPowerUp");
		State buyBullets = new State("buyBullets");
		State rockToGenerate = new State("rockToGenerate");
		Transition litToRefill = new Transition(lInput, refillBullets, new Event("litToRefill"));
		Transition litToPowerUp = new Transition(lInput, materialToPowerUp, new Event("litToPowerUp"));
		Transition litToBuyBullets = new Transition(lInput, buyBullets, new Event("litToBuyBullets"));
		Transition litToRockGen = new Transition(lInput, rockToGenerate, new Event("litToRockGen"));
		Transition refillToLit = new Transition(refillBullets, lInput, new Event("refillToLit"));
		Transition powerUpToLit = new Transition(materialToPowerUp, lInput, new Event("powerUpToLit"));
		Transition buyBulletsToLit = new Transition(buyBullets, lInput, new Event("buyBulletsToLit"));
		Transition rockGenToLit = new Transition(rockToGenerate, lInput, new Event("rockGenToLit"));
		
		FiniteStateMachine turnMachine = new FiniteStateMachine(lInput, refillBullets, 
				materialToPowerUp, buyBullets, rockToGenerate);
		turnMachine.addTransition(litToRefill);
		turnMachine.addTransition(refillToLit);
		turnMachine.addTransition(litToPowerUp);
		turnMachine.addTransition(powerUpToLit);
		turnMachine.addTransition(litToBuyBullets);
		turnMachine.addTransition(buyBulletsToLit);
		turnMachine.addTransition(litToRockGen);
		turnMachine.addTransition(rockGenToLit);
		return turnMachine;
	}
}

