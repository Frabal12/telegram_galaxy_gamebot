package api;

import java.io.IOException;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import state_machine.api.TransitionNotFoundException;

public interface Command {
	
	void commands() throws TelegramApiException;
    void help() throws TelegramApiException;
	void startG()throws TelegramApiException;
	void unknown()throws TelegramApiException, IOException;
	void game()throws TelegramApiException,IOException, ClassNotFoundException, TransitionNotFoundException;
	void exit()throws TelegramApiException;
	void chosePlayer() throws TelegramApiException, IOException, ClassNotFoundException, TransitionNotFoundException;
	void stats() throws TelegramApiException;
	void combat() throws TelegramApiException, TransitionNotFoundException, InterruptedException;
	void planet() throws TelegramApiException;
	void analysis() throws TelegramApiException;
	void buy() throws TelegramApiException, TransitionNotFoundException;
	void explore() throws TelegramApiException;
	void inventory() throws TelegramApiException;
	void mine() throws TelegramApiException;
	void powerUp()throws TelegramApiException;
	void condense()throws TelegramApiException;
	void generates()throws TelegramApiException, TransitionNotFoundException;
	void equip()throws TelegramApiException;
	void refill()throws TelegramApiException, TransitionNotFoundException;
	void choseMaterial()throws TelegramApiException, TransitionNotFoundException;
	void choseParts() throws TelegramApiException, IOException;
	void choseView()throws TelegramApiException;
	void changeView() throws TelegramApiException, IOException;
	default void chose(String messageText)throws TelegramApiException, IOException, ClassNotFoundException, TransitionNotFoundException, InterruptedException{
		switch(messageText) {
		case "/help":help();break;
		case "/commands":commands();break;
		case "/start","Menù":startG();break;
		case "Mercante Locale":choseView();break;
		case "/play","Mercante","Combattente","Guardiano":chosePlayer();break;
		case "Statistiche giocatore":stats();break;
		case "Cerca materiali":mine();break;
		case "Usa il condensatore":condense();break;
		case "Genera Pietra":generates();break;
		case "Migliora un'arma":powerUp();break;
		case "Combatti":combat();break;
		case "Viaggia":planet();break;
		case "Analizza": analysis();break;
		case "Esplora il pianeta": explore();break;
		case "Equipaggia": equip();break;
		case "Ricarica Proiettili": refill();break;
		case "Oro","Ferro","Roccia": choseMaterial();break;
		case "SI","NO": buy();break;
		case "Parte Superiore","Manico": choseParts();break;
		case "Visuale a lista","Visuale a scorrimento": changeView();break;
		default: unknown();break;
		}
	}
}
