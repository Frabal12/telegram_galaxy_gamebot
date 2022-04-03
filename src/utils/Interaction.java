package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import api.entities.RealEntity;
import api.entities.Stat;
import core.entities.Player;

public class Interaction {

	Integer[] randomArr = { 1, 2, 3, 4, 5, 6 };
	List<Integer> random = Arrays.asList(randomArr);
	private final int limitCombat = 12;
	private final int limitAgility = 24;
	private final int limitEscape = 24;
	private String issue = "";

	public void attack(RealEntity first_entity, RealEntity second_entity) {

		boolean att = false, def = false, ag = false;
		int strength_First = first_entity.getStat(Stat.STRENGTH);
		int agility_Second = second_entity.getStat(Stat.AGILITY);
		int protection_Second = second_entity.getStat(Stat.PROTECTION);

		Collections.shuffle(random);
		int successA = random.get(0) * strength_First;
		Collections.shuffle(random);
		int successP = random.get(0) * protection_Second;
		Collections.shuffle(random);
		int successAg = random.get(0) * agility_Second;
		if (successA >= limitCombat)
			att = true;
		if (successP >= limitCombat)
			def = true;
		if (successAg >= limitAgility)
			ag = true;

		if (first_entity instanceof Player) {
			int dannoPlayer=0,dannoAlien=0;
			int lifePlayer = first_entity.getStat(Stat.LIFE);
			int lifeAlien = second_entity.getStat(Stat.LIFE);
			if (ag) {
				issue = "l'avversario schiva l'attacco.";
				return;
			}
			if (att && def && strength_First > protection_Second && successA > successP) {
				dannoPlayer=strength_First - protection_Second;
				second_entity.setStat(Stat.LIFE, lifeAlien - (dannoPlayer));
				issue = "l'attacco è riuscito.";
			} else if (att && def && strength_First < protection_Second && successA < successP) {
				dannoAlien=strength_First - protection_Second;
				first_entity.setStat(Stat.LIFE, lifePlayer + (dannoAlien));
				issue = "l'avversario si è difeso!";
			} else if (att && def && strength_First < protection_Second && successA > successP) {
				if (successAg < successA) {
					dannoPlayer=strength_First - agility_Second;
					second_entity.setStat(Stat.LIFE, lifeAlien - (Math.abs(dannoPlayer)));
					issue = "l'avversario prova a proteggersi ma tu sei più veloce";
				} else {
					issue = "all'ultimo momento l'avversario schiva il tuo attacco";
				}
			} else if (att && def && strength_First > protection_Second && successA < successP) {
				if (successAg > successA) {
					dannoAlien=strength_First - agility_Second;
					first_entity.setStat(Stat.LIFE, lifePlayer - (dannoAlien));
					issue = "un buon attacco ma l'avversario contrattacca";
				} else {
					dannoPlayer=strength_First - protection_Second;
					second_entity.setStat(Stat.LIFE, lifeAlien - (dannoPlayer));
					issue = "l'avversario prova a schivare ma fallisce";
				}
			} else if (!att && def) {
				dannoAlien=protection_Second;
				first_entity.setStat(Stat.LIFE, lifePlayer - (dannoAlien));
				issue = "l'attacco è fallito miseramente!";
			} else if (att && !def) {
				dannoPlayer=strength_First;
				second_entity.setStat(Stat.LIFE, lifeAlien - (dannoPlayer));
				issue = "attacco perfetto!!";
			} else if (!att && !def) {
				issue = "l'attacco era impreciso e non ha colpito l'avversario";
			}
			issue= issue + " subisci: "+ Math.abs(dannoAlien) + " danni e infliggi: " + Math.abs(dannoPlayer)+" danni";

		} else {
			int dannoEnemy=0,dannoPlayer=0;
			int lifeEnemy = first_entity.getStat(Stat.LIFE);
			int lifePlayer = second_entity.getStat(Stat.LIFE);
			if (ag) {
				issue = "schivi l'attacco.";
				return;
			}
			if (att && def && strength_First > protection_Second && successA > successP) {
				dannoEnemy=strength_First - protection_Second;
				second_entity.setStat(Stat.LIFE, lifePlayer - (dannoEnemy));
				issue = "l'avversario ti colpisce.";
			} else if (att && def && strength_First < protection_Second && successA < successP) {
				dannoPlayer=strength_First - protection_Second;
				first_entity.setStat(Stat.LIFE, lifeEnemy + (dannoPlayer));
				issue = "ti difendi con onore!";
			} else if (att && def && strength_First < protection_Second && successA > successP) {
				if (successAg < successA) {
					dannoEnemy=strength_First - agility_Second;
					second_entity.setStat(Stat.LIFE, lifePlayer - (dannoEnemy));
					issue = "provi a difenderti ma l'avversario è più rapido";
				} else {
					issue = "schivi l'attacco per un soffio";
				}
			} else if (att && def && strength_First > protection_Second && successA < successP) {
				if (successAg > successA) {
					dannoPlayer=strength_First - agility_Second;
					first_entity.setStat(Stat.LIFE, lifeEnemy - (dannoPlayer));
					issue = "respingi l'attacco dell'avversario con un ottimo contrattacco";
				} else {
					dannoEnemy=strength_First - protection_Second;
					second_entity.setStat(Stat.LIFE, lifePlayer - (dannoEnemy));
					issue = "provi a schivare ma fallisci";
				}
			} else if (!att && def) {
				dannoPlayer=protection_Second;
				first_entity.setStat(Stat.LIFE, lifeEnemy - (dannoPlayer));
				issue = "capisci facilmente le intenzioni dell'avversario e lo scaraventi per terra!";
			} else if (att && !def) {
				dannoEnemy=strength_First;
				second_entity.setStat(Stat.LIFE, lifePlayer - (dannoEnemy));
				issue = "l'avversario ti colpisce in pieno!!";
			} else if (!att && !def) {
				issue = "l'attacco dell'avversario era impreciso e non ti colpisce";
			}
			issue= issue + " subisci: "+ Math.abs(dannoEnemy) + " danni e infliggi: " + Math.abs(dannoPlayer)+" danni";
		}
	}

	public boolean escape(RealEntity first_entity, RealEntity second_entity) {
		boolean ag = false;
		issue = "la fuga è fallita!";
		int strength_Second = second_entity.getStat(Stat.STRENGTH);
		int agility_First = first_entity.getStat(Stat.AGILITY);
		int protection_First = first_entity.getStat(Stat.PROTECTION);
		int totValue = ((protection_First / 2) + agility_First - strength_Second);
		if (totValue >= limitEscape) {
			ag = true;
		}
		if (ag) {
			issue = "la fuga è riuscita.";
		}
		return ag;
	}

	public String getIssue() {
		return issue;
	}

}
