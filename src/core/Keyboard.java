package core;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import api.items.Item;
import api.items.weapons.Weapon;
import core.items.Inventory;

public class Keyboard extends ReplyKeyboardMarkup {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1973671381309951961L;

	private List<KeyboardRow> keyboardL = new ArrayList<>();

	public void buildK_YNButtons() {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();

		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("SI"));
		secondKey.add(new KeyboardButton("NO"));
		thirdKey.add(new KeyboardButton("Menù"));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		this.setKeyboard(keyboardL);
	}
	public void buildK_beginButtons() {

		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();

		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("/play"));
		secondKey.add(new KeyboardButton("/help"));
		thirdKey.add(new KeyboardButton("/commands"));
	
		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		this.setKeyboard(keyboardL);
	}

	public void buildK_playerFirstAction() {

		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();
		
		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Viaggia"));
		secondKey.add(new KeyboardButton("Statistiche giocatore"));
		thirdKey.add(new KeyboardButton("Menù"));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		this.setKeyboard(keyboardL);
	}
	
	public void buildK_playerMenu() {

		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();
		KeyboardRow fourthKey = new KeyboardRow();

		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Viaggia"));
		secondKey.add(new KeyboardButton("Esplora il pianeta"));
		thirdKey.add(new KeyboardButton("Statistiche giocatore"));
		fourthKey.add(new KeyboardButton("Menù"));
		

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		keyboardL.add(fourthKey);
		this.setKeyboard(keyboardL);
	}
	public void buildK_playerMenuWithCondenser() {

		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();
		KeyboardRow fourthKey = new KeyboardRow();
		KeyboardRow fifthKey = new KeyboardRow();
		
		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Viaggia"));
		secondKey.add(new KeyboardButton("Esplora il pianeta"));
		thirdKey.add(new KeyboardButton("Statistiche giocatore"));
		fourthKey.add(new KeyboardButton("Usa il condensatore"));
		fifthKey.add(new KeyboardButton("Menù"));
		

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		keyboardL.add(fourthKey);
		keyboardL.add(fifthKey);
		this.setKeyboard(keyboardL);
	}
	public void buildK_GCButtons() {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();

		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Genera Pietra"));
		secondKey.add(new KeyboardButton("Migliora un'arma"));
		thirdKey.add(new KeyboardButton("Menù"));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		this.setKeyboard(keyboardL);
	}

	public void buildK_playerFirstActionWC() {

		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();
		KeyboardRow fourthKey = new KeyboardRow();
		KeyboardRow fifthKey = new KeyboardRow();
		KeyboardRow sixthKey = new KeyboardRow();
		KeyboardRow seventhKey = new KeyboardRow();

		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Combatti"));
		secondKey.add(new KeyboardButton("Viaggia"));
		thirdKey.add(new KeyboardButton("Analizza"));
		fourthKey.add(new KeyboardButton("Mercante Locale"));
		fifthKey.add(new KeyboardButton("Statistiche giocatore"));
		sixthKey.add(new KeyboardButton("Cerca materiali"));
		seventhKey.add(new KeyboardButton("Menù"));
		
		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		keyboardL.add(fourthKey);
		keyboardL.add(fifthKey);
		keyboardL.add(sixthKey);
		keyboardL.add(seventhKey);
		this.setKeyboard(keyboardL);
	}

	public void buildK_chosePlayer() {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();

		// Create a keyboard

		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Mercante"));
		secondKey.add(new KeyboardButton("Combattente"));
		thirdKey.add(new KeyboardButton("Guardiano"));
		

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		this.setKeyboard(keyboardL);
	}

	public void buildK_combatPhase() {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();
		KeyboardRow fourthKey = new KeyboardRow();


		// Create a keyboard

		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Attacca"));
		secondKey.add(new KeyboardButton("Difenditi"));
		thirdKey.add(new KeyboardButton("Fuggi"));
		fourthKey.add(new KeyboardButton("Avversario"));
		
		

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		keyboardL.add(fourthKey);

		this.setKeyboard(keyboardL);
	}
	public void buildK_numberChose(int i) {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();

		// Create a keyboard

		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton(i/i+""));
		secondKey.add(new KeyboardButton(i/2+""));
		thirdKey.add(new KeyboardButton(i+""));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		this.setKeyboard(keyboardL);
	}
	
	public void buildK_Inventory(Inventory inventory) {
		keyboardL.clear();
		KeyboardRow kRow;
		
		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);
		
		for(Item i : inventory.getInventory().values()) {
			if(i instanceof Weapon) {
			   kRow = new	KeyboardRow();
			   kRow.add(new KeyboardButton(i.getName()+" id item: "+i.getId()));
			   keyboardL.add(kRow);
			}
		}
		this.setKeyboard(keyboardL);
	}
	public void buildK_Equip() {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();

		// Create a keyboard

		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Equipaggia"));
		secondKey.add(new KeyboardButton("Menù"));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		this.setKeyboard(keyboardL);
	}
	public void buildK_EquipGun() {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();

		// Create a keyboard

		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Equipaggia"));
		secondKey.add(new KeyboardButton("Ricarica Proiettili"));
		thirdKey.add(new KeyboardButton("Menù"));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		this.setKeyboard(keyboardL);
	}
	public void buildK_choseMaterials() {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		KeyboardRow thirdKey = new KeyboardRow();

		// Create a keyboard

		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Oro"));
		secondKey.add(new KeyboardButton("Ferro"));
		thirdKey.add(new KeyboardButton("Roccia"));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		keyboardL.add(thirdKey);
		this.setKeyboard(keyboardL);
	}
	public void buildK_powerUpParts() {
		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();

		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Parte Superiore"));
		secondKey.add(new KeyboardButton("Manico"));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		this.setKeyboard(keyboardL);
	}
	public void buildK_choseMarketView() {

		keyboardL.clear();
		KeyboardRow firstKey = new KeyboardRow();
		KeyboardRow secondKey = new KeyboardRow();
		
		// Create a keyboard
		this.setSelective(true);
		this.setResizeKeyboard(false);
		this.setOneTimeKeyboard(false);

		// keys
		firstKey.add(new KeyboardButton("Visuale a lista"));
		secondKey.add(new KeyboardButton("Visuale a scorrimento"));

		keyboardL.add(firstKey);
		keyboardL.add(secondKey);
		this.setKeyboard(keyboardL);
	}

	
	//set the replyMarkup every time it's maked the build, very important !!
	public boolean verify(String buttonValue) {
		KeyboardRow rowToTest = new KeyboardRow();
		rowToTest.add(new KeyboardButton(buttonValue));
		return keyboardL.contains(rowToTest);
	}

}
