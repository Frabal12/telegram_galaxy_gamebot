package core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import api.Command;
import api.entities.Stat;
import api.items.Item;
import api.items.UpgradeableItem;
import api.planet.Planet;
import api.planet.Valore;
import core.entities.Alien;
import core.entities.Player;
import core.items.Bag;
import core.items.Inventory;
import core.entities.Life;
import core.entities.Merchant;
import core.items.others.Bucket;
import core.items.others.Bullet;
import core.items.others.Pickaxe;
import api.items.weapons.Material;
import api.items.weapons.Weapon;
import core.items.weapons.Gun;
import core.items.weapons.Knife;
import core.items.weapons.Sword;
import core.planet.GraphPlanet;
import core.planet.LinearPlanet;
import core.planet.RandomPlanet;
import core.items.others.MatterCondenser;
import state_machine.api.TransitionNotFoundException;
import state_machine.core.Event;
import state_machine.core.FiniteStateMachine;
import state_machines.StateMachines;
import state_machines.UserPlanetState;

import utils.Interaction;

public class ExecThread extends Thread implements Command {

	private ConcurrentHashMap<Long, Game> _games = new ConcurrentHashMap<>();
	private Game game;
	private GalaxyLife bot;
	private long l_chatId;
	public String chatId;
	private String messageText = "";
	private String queryText = "";
	private String iqueryText = "", iQid = "";
	private Keyboard keyboard;
	private UserPlanetState userPState;
	private Inventory inventory;
	private Bag bag;
	private Life beings;
	private Player player;
	private boolean[] flags;// index 1-2 change player menu, 3 marketView
	private long[] identifiers = new long[3];
	private Random random;
	private FiniteStateMachine literalNum;

	public ExecThread(GalaxyLife bot, Update update) throws TelegramApiRequestException {
		this.bot = bot;
		if (update.hasMessage() && update.getMessage().hasText()) {
			messageText = update.getMessage().getText();
			l_chatId = update.getMessage().getChatId();
			chatId = String.valueOf(l_chatId);
		} else if (update.hasCallbackQuery()) {
			queryText = update.getCallbackQuery().getData();
			chatId = update.getCallbackQuery().getFrom().getId() + "";
			l_chatId = Long.valueOf(chatId);
		} else if (update.hasInlineQuery()) {
			iqueryText = update.getInlineQuery().getQuery();
			chatId = update.getInlineQuery().getFrom().getId() + "";
			l_chatId = Long.valueOf(chatId);
			iQid = update.getInlineQuery().getId();
		}
		_games = bot.getGames();
		game = _games.get(l_chatId);
		keyboard = game.getKeyboard();
		userPState = game.getUserPState();
		beings = game.getEntities();
		flags = game.getFlags();
		literalNum = game.getLiteralNum();
		random = new Random();
		for (int i = 0; i < identifiers.length; i++) {
			String temp = Long.valueOf(i) + "" + Math.abs(l_chatId);
			identifiers[i] = Long.valueOf(temp);
		}

	}

	public void run() {
		SendMessage message = SendMessage.builder().text("").chatId(chatId).build();
		// I initialize states
		beings.getEntities().computeIfAbsent(l_chatId, key -> new Player("", l_chatId));
		player = (Player) beings.get(l_chatId);
		Alien alien = (Alien) beings.getEntities().computeIfAbsent(identifiers[1], key -> null);
		inventory = player.getInventory();
		bag = player.getBag();
		if (literalNum == null)
			literalNum = StateMachines.numericInputMachineBuilder();
		if (inventory == null) {
			inventory = new Inventory();
			player.setInventory(inventory);
		}
		if(messageText.equals("exit"))
			try {exit();} catch (TelegramApiException e) {e.printStackTrace();}
		if (bag == null)
			bag = new Bag();
		if (player.haveCondenser())
			flags[2] = true;
		try {
			if (iqueryText.equals("inventario"))
				inventory();
			if (!queryText.equals("")) {
				if (queryText.equals("avanti")) {
					((Merchant) beings.get(identifiers[2])).incIndex();
					merch();
				} else if (queryText.equals("indietro")) {
					((Merchant) beings.get(identifiers[2])).decIndex();
					merch();
				} else
					showMerch(Long.valueOf(queryText));
			}
			if (literalNum.isCurrentState(new state_machine.core.State("literalInput")) && !messageText.equals("")) {

				// set username
				if (!player.isNameSetted()) {
					if (!keyboard.verify(messageText) && messageText.matches("[a-zA-Z0-9_]{0,10}"))
						player.nameSetted();
					else {
						message = SendMessage.builder().text("inserire un username di massimo 10 caratteri")
								.chatId(chatId).build();
						bot.execute(message);
						return;
					}
				}
				if (player.isNameSetted()) {
					if (player.getName().equals("")) {
						player.setName(messageText);
						message = SendMessage.builder().text("complimenti username settato, benvenuto!").chatId(chatId)
								.build();
						buttonsChose(message);
						bot.execute(message);
						set();
						return;
					}
					if (alien == null || alien.getStat(Stat.LIFE) <= 0) {

						// verify that message is present among the buttons that can be displayed
						if (keyboard.verify(messageText) || (messageText.equals("/help"))||(messageText.equals("/commands")) || 
								(messageText.equals("/play"))) {

							if (messageText.contains("id item: ")) {
								String[] tmp = messageText.split("id item: ");
								Item item = inventory.get(Long.valueOf(tmp[tmp.length - 1]));
								seeItem(item);
								message.setText(
										"scrivi la quantià di materiale che vuoi aggiungere all'arma, o scegli un valore!");
								player.setWeaponToPowerUp((Weapon) item);
								buttonsNumChose(message, player.getBag().materialMin());
								bot.execute(message);
								literalNum.change(new Event("litToPowerUp"));
								set();
								return;
							} else
								chose(messageText);

						} else if (messageText.contains("id: ")) {
							String[] tmp = messageText.split("id: ");
							Item item = inventory.get(Long.valueOf(tmp[tmp.length - 1]));
							seeItem(item);
							if (item instanceof Gun) {
								message.setText("cosa vuoi fare?");
								player.setWeaponToEquip((Weapon) item);
								buttonsEquipGun(message);
								bot.execute(message);
							} else if (item instanceof Weapon) {
								message.setText("vuoi equipaggiare l'arma?");
								player.setWeaponToEquip((Weapon) item);
								buttonsEquip(message);
								bot.execute(message);
							}					
						}else
							unknown();
					} 
					else if((!messageText.equals("/help"))&&(!messageText.equals("/commands")))
						chose("Combatti");
					else {
						chose(messageText);
					}
				}
			} else if (isInt(messageText)) {
				if (literalNum.isCurrentState(new state_machine.core.State("refillBullet"))) {
					refill();
					set();
					return;
				}
				if (literalNum.isCurrentState(new state_machine.core.State("materialToPowerUp"))) {
					choseMaterial();
					set();
					return;
				}
				if (literalNum.isCurrentState(new state_machine.core.State("buyBullets"))) {
					buy();
					set();
					return;
				}
				if (literalNum.isCurrentState(new state_machine.core.State("rockToGenerate"))) {
					generates();
					set();
					return;
				}
			} else if (!messageText.equals("")) {
				unknown();
				return;
			}

		} catch (TelegramApiException | TransitionNotFoundException | ClassNotFoundException | IOException
				| InterruptedException e) {
			e.printStackTrace();
		}
		set();
	}

	@Override
	public void commands() throws TelegramApiException {
		String lista = "la lista dei comandi è: \n/command \n/help \n/play \n\nIn seguito per giocare, "
				+ "puoi premere sui bottoni o usare i comandi corrispondenti scrivendoli in chat";
		SendMessage message = SendMessage.builder().text(lista).chatId(chatId).build();
		bot.execute(message);

	}

	@Override
	public void help() throws TelegramApiException {
		String spiegazione = "Salve, il bot funziona così:\n\nPer prima cosa digita /play o clicca sul bottone corrispondente, "
				+ "ti verrà richiesta la classe, scegliendone una determinerai le tue statistiche iniziali "
				+ "e la quantità di monete nella tua borsa.\n\n"
				+ "-Per vedere le tue statistiche clicca sul tasto \"Statistiche\".\n"
				+ "-Per cercare un nuovo pianeta clicca sul tasto \"Viaggia\".\n"
				+ "-Per far partire un nuovo combattimento clicca sul tasto \"Combatti\".";
		SendMessage message = SendMessage.builder().text(spiegazione).chatId(chatId).build();
		bot.execute(message);
	}

	@Override
	public void unknown() throws TelegramApiException {
		if (!beings.get(Long.valueOf("0" + chatId)).getName().equals(messageText)) {
			String messageText = "Comando sconosciuto, riprova e controlla che tutti i caratteri siano scritti correttamente!";
			SendMessage message = SendMessage.builder().text(messageText).chatId(chatId).build();
			bot.execute(message);
		}
	}

	public void chosePlayer()
			throws TelegramApiException, IOException, ClassNotFoundException, TransitionNotFoundException {
		player = ((Player) beings.get(l_chatId));
		SendMessage message = SendMessage.builder().text("seleziona un'operazione corretta").chatId(chatId).build();
		if (player.isStatsSetted()) {
			buttonsPlayerMenu(message);
			game();
		} else {
			buttonsChose(message);
			switch (messageText) {
			case "Mercante": {
				message.setText("hai scelto la classe Mercante, statistiche settate");
				player.setClassPlayer("Mercante");
				player.setStats(600, 6, 4, 5, 13, 4);
				player.setMoney(200);
				player.statsSetted();
				buttonsPlayerMenu(message);
				break;
			}
			case "Combattente": {
				message.setText("hai scelto la classe Combattente, statistiche settate");
				player.setClassPlayer("Combattente");
				player.setStats(600, 10, 7, 6, 4, 5);
				player.setMoney(60);
				player.statsSetted();
				buttonsPlayerMenu(message);
				break;
			}
			case "Guardiano": {
				message.setText("hai scelto la classe Guardiano, statistiche settate");
				player.setClassPlayer("Guardiano");
				player.setStats(600, 3, 7, 9, 4, 9);
				player.setMoney(70);
				player.statsSetted();
				buttonsPlayerMenu(message);
				break;
			}
			}
			bot.execute(message);
		}

		///// TEST///////
		if (player.getId().equals(161430933l)) {
			player.addMoney(10000);
		}
	}

	@Override
	public void exit() throws TelegramApiException {
		if (l_chatId == 161430933) {
			System.exit(0);
		}
		SendMessage message = SendMessage.builder().chatId(chatId)
				.text("Comando sconosciuto, riprova e controlla che tutti i caratteri siano scritti correttamente!")
				.build();
		bot.execute(message);
	}

	@Override
	public void startG() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("menù iniziale").build();
		buttonsBegin(message);
		bot.execute(message);
	}

	@Override
	public void game() throws TelegramApiException, IOException, ClassNotFoundException, TransitionNotFoundException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("inizia il gioco").build();
		buttonsPlayerMenu(message);
		bot.execute(message);

	}

	@Override
	public void stats() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId).text(player.toString() + "\n\n"
				+ player.getBag().toString() + "\n\n~" + player.getNumBullets() + " proiettili~").build();
		bot.execute(message);
	}

	private void showMerch(Long key) throws TelegramApiException, IOException {
		Merchant merchant = (Merchant) beings.get(identifiers[2]);
		SendMessage message = SendMessage.builder().chatId(chatId).text("").build();
		player.setInterstedObject(key);
		Item item = merchant.getInventory().get(key);
		buttonsSelectYN(message);
		if (flags[3]) {
			if (item instanceof Bullet)
				message.setText("Sei interessato all'acquisto?, prezzo: " + "" + item.getCost() + " monete a proiettile");
			else if (item instanceof Weapon) {
				InputStream is = new ByteArrayInputStream(((Weapon) item).getWeaponImage());
				SendPhoto foto = new SendPhoto();
				foto.setCaption(item.toString());
				foto.setChatId(chatId);
				foto.setPhoto(new InputFile(is, "sword"));
				bot.execute(foto);
				message.setText("Sei interessato all'acquisto?, prezzo: " + "" + item.getCost() + " monete");
			} else {
				message.setText(item.toString());
				bot.execute(message);
				message.setText("Sei interessato all'acquisto?, prezzo: " + "" + item.getCost() + " monete");
			}
		}
		else {
			if (item instanceof Bullet)
				message.setText("Sei interessato all'acquisto?, prezzo: " + "" + item.getCost() + " monete a proiettile");
			else {
				message.setText("Sei interessato all'acquisto?, prezzo: " + "" + item.getCost() + " monete");
			}
		}
		bot.execute(message);
	}

	private void merch() throws TelegramApiException, IOException {
		Merchant merchant = (Merchant) beings.get(identifiers[2]);
		SendMessage message = SendMessage.builder().chatId(chatId).text("dai uno sguardo alle armi.").build();
		buttonsPlayerMenu(message);
		if (merchant == null) {
			message.setText("ecco le armi");
			Inventory inventory = new Inventory();
			for (int i = 0; i < random.nextInt(3) + 2; i++) {
				Weapon sword = new Sword(userPState.getPlanet());
				inventory.put(sword);
			}
			for (int i = 0; i < random.nextInt(3) + 2; i++) {
				Weapon gun = new Gun(userPState.getPlanet());
				inventory.put(gun);
			}
			for (int i = 0; i < random.nextInt(3) + 2; i++) {
				Weapon knife = new Knife(userPState.getPlanet());
				inventory.put(knife);
			}
			inventory.put(new Bucket());
			if (userPState.getPlanet().getValue(Valore.ACQUA) > 10f
					&& userPState.getPlanet().getValue(Valore.TERRA) > 30f)
				inventory.put(new Pickaxe());
			if (userPState.getPlanet().getValue(Valore.FERRO) > 20f
					&& userPState.getPlanet().getValue(Valore.ORO) > 10f)
				inventory.put(new Bullet());
			if (userPState.getPlanet().getValue(Valore.ORO) > 20f
					&& userPState.getPlanet().getValue(Valore.FERRO) > 60f)
				inventory.put(new MatterCondenser());
			merchant = new Merchant(inventory);
			beings.getEntities().put(identifiers[2], merchant);

		}
		if (flags[3]) {
			merchant.setResponseW(message);
			bot.execute(message);
			message.setText("ecco degli altri oggetti che potrebbero interessarti");
			merchant.setResponseI(message);
			bot.execute(message);
		} else {
			merchant.displayCurrentItem(bot, chatId);
		}

	}

	public void inventory() throws TelegramApiException {
		AnswerInlineQuery response = AnswerInlineQuery.builder().inlineQueryId(iQid).build();
		List<InlineQueryResult> inventoryList = new ArrayList<>();
		InlineQueryResultArticle item;
		InputTextMessageContent itemIdReturn;
		int numI = 0;
		for (Item i : inventory.getInventory().values()) {
			if (i instanceof Sword) {
				itemIdReturn = new InputTextMessageContent();
				item = new InlineQueryResultArticle();
				itemIdReturn.setMessageText("nome : " + i.getName() + " id: " + i.getId());
				item.setDescription(i.toString());
				item.setTitle(i.getName());
				item.setId(numI++ + "");
				item.setThumbUrl("https://i.postimg.cc/ctChXVrc/sword.png");
				item.setInputMessageContent(itemIdReturn);
				inventoryList.add(item);
			} else if (i instanceof Gun) {
				itemIdReturn = new InputTextMessageContent();
				item = new InlineQueryResultArticle();
				itemIdReturn.setMessageText("nome : " + i.getName() + " id: " + i.getId());
				item.setDescription(i.toString());
				item.setTitle(i.getName());
				item.setId(numI++ + "");
				item.setThumbUrl("https://i.postimg.cc/1n9JNgS2/gun.png");
				item.setInputMessageContent(itemIdReturn);
				inventoryList.add(item);
			} else if (i instanceof Knife) {
				itemIdReturn = new InputTextMessageContent();
				item = new InlineQueryResultArticle();
				itemIdReturn.setMessageText("nome : " + i.getName() + " id: " + i.getId());
				item.setDescription(i.toString());
				item.setTitle(i.getName());
				item.setId(numI++ + "");
				item.setThumbUrl("https://i.postimg.cc/XGQxKV7J/knife.png");
				item.setInputMessageContent(itemIdReturn);
				inventoryList.add(item);
			} else {
				itemIdReturn = new InputTextMessageContent();
				item = new InlineQueryResultArticle();
				itemIdReturn.setMessageText("nome : " + i.getName() + " id: " + i.getId());
				item.setDescription(i.toString());
				item.setTitle(i.getName());
				item.setId(numI++ + "");
				item.setInputMessageContent(itemIdReturn);
				inventoryList.add(item);
			}
		}
		// cacheTime 0 to reset the query
		response.setCacheTime(0);
		response.setResults(inventoryList);
		bot.execute(response);

	}

	private void seeItem(Item item) throws TelegramApiException {
		if (item instanceof Weapon) {
			InputStream is = new ByteArrayInputStream(((Weapon) item).getWeaponImage());
			SendPhoto foto = new SendPhoto();
			foto.setCaption(item.toString());
			foto.setChatId(chatId);
			foto.setPhoto(new InputFile(is, "sword"));
			bot.execute(foto);
		} else {
			SendMessage message = SendMessage.builder().chatId(chatId).text("").build();
			message.setText(item.toString());
			bot.execute(message);
		}

	}

	public void planet() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("").build();
		beings.getEntities().put(identifiers[2], null);
		int temp = random.nextInt(2);
		Planet planet;
		if (temp == 0) {
			planet = new LinearPlanet(250, 250);
			userPState = new UserPlanetState("Linear", planet, new GraphPlanet(planet));
			message.setText(
					"trovato un nuovo pianeta, fare l'analisi per vederne le caratteristiche, o cliccare su combatti per avviare un nuovo combattimento");
		} else {
			planet = new RandomPlanet(250, 250);
			userPState = new UserPlanetState("Random", planet, new GraphPlanet(planet));
			message.setText(
					"trovato un nuovo pianeta, fare l'analisi per vederne le caratteristiche, o cliccare su combatti per avviare un nuovo combattimento");
		}
		buttonsOnPlanet(message);
		if (!flags[1])
			flags[1] = true;
		InputStream is = new ByteArrayInputStream(planet.getPlanetImage());
		SendPhoto foto = SendPhoto.builder().chatId(chatId).photo(new InputFile(is, "planet")).build();
		bot.execute(message);
		bot.execute(foto);

	}

	@Override
	public void explore() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("cosa vuoi fare?").build();
		Planet planet = userPState.getPlanet();
		buttonsOnPlanet(message);
		InputStream is = new ByteArrayInputStream(planet.getPlanetImage());
		SendPhoto foto = new SendPhoto();
		foto.setChatId(chatId);
		foto.setPhoto(new InputFile(is, "planet"));
		bot.execute(message);
		bot.execute(foto);

	}

	@Override
	public void combat() throws TelegramApiException, TransitionNotFoundException, InterruptedException {
		player = ((Player) beings.get(l_chatId));
		Interaction interaction = new Interaction();
		if (beings.get(identifiers[1]) == null || ((Alien)beings.get(identifiers[1])).getStat(Stat.LIFE)==0 )
			beings.put(identifiers[1], new Alien(userPState.getPlanet()));
		Alien alien = (Alien) beings.get(identifiers[1]);
		SendMessage message = SendMessage.builder().chatId(chatId).text(
				"inizia il combattimento con l'alieno: " + alien.getName() + "\n\nStatistiche:" + alien.toString())
				.build();
		buttonsCombat(message);
		switch (messageText) {
		case "Menù":
			message.setText("sei ancora in combattimento");
			bot.execute(message);
			return;
		case "Avversario":
			message.setText(alien.toString());
			bot.execute(message);
			return;
		}

		if (!alien.isAlienTurn()) {
			switch (messageText) {
			case "Attacca":
				interaction.attack(player, alien);
				if (player.getStat(Stat.LIFE) <= 0) {
					beings.getEntities().computeIfPresent(l_chatId,
							(key, value) -> new Player(player.getName(), l_chatId));
					beings.put(identifiers[1], null);
					message.setText("hai perso!!!!");
					buttonsBegin(message);
				}
				if (alien.getStat(Stat.LIFE) <= 0) {
					message.setText(interaction.getIssue() + ", alieno sconfitto!!!!!");
					player.addMoney(alien.getMoney());
					buttonsPlayerMenu(message);
					beings.put(identifiers[1], null);
				} else
					message.setText(interaction.getIssue() + "\n\nla tua vita è: " + player.getStat(Stat.LIFE)
							+ " \nla vita dell'alieno è: " + alien.getStat(Stat.LIFE));
				alien.changeTurn();
				flags[0] = false;
				break;
			case "Difenditi":
				player.setStat(Stat.PROTECTION, player.getStat(Stat.PROTECTION) + 2);
				message.setText("la tua difesa è: " + player.getStat(Stat.PROTECTION));
				alien.changeTurn();
				flags[0] = false;
				break;
			case "Fuggi":
				if (interaction.escape(player, alien)) {
					message.setText(interaction.getIssue());
					beings.put(identifiers[1], null);
					buttonsPlayerMenu(message);
				} else
					message.setText(interaction.getIssue());
				alien.changeTurn();
				flags[0] = false;
				break;
			}
			bot.execute(message);
		}
		alienTurn(alien);

	}

	private void alienTurn(Alien alien) throws TelegramApiException, InterruptedException, TransitionNotFoundException {
		if (alien.isAlienTurn()) {
			SendMessage message = SendMessage.builder().chatId(chatId).text("").build();
			Interaction interaction = new Interaction();
			if (!flags[0]) {
				message.setText("il nemico sta attaccando");
				bot.execute(message);
				flags[0] = true;
				TimeUnit.MILLISECONDS.sleep(700);
				interaction.attack(alien, player);
				if (player.getStat(Stat.LIFE) <= 0) {
					beings.getEntities().computeIfPresent(l_chatId,
							(key, value) -> new Player(player.getName(), l_chatId));
					beings.put(identifiers[1], null);
					message.setText("hai perso!!!!");
					buttonsBegin(message);
				}
				if (alien.getStat(Stat.LIFE) <= 0) {
					message.setText(interaction.getIssue() + ", alieno sconfitto!!!!!");
					player.addMoney(alien.getMoney());
					buttonsPlayerMenu(message);
					beings.put(identifiers[1], null);
				} else
					message.setText(interaction.getIssue() + "\n\nla tua vita è: " + player.getStat(Stat.LIFE)
							+ " \nla vita dell'alieno è: " + alien.getStat(Stat.LIFE));
				bot.execute(message);
			}
			alien.changeTurn();
			return;
		}
	}

	@Override
	public void analysis() throws TelegramApiException {
		Planet planet = userPState.getGraph();
		InputStream is = new ByteArrayInputStream(planet.getPlanetImage());
		SendPhoto foto = SendPhoto.builder().chatId(chatId).photo(new InputFile(is, "planet"))
				.caption(planet.toString()).build();
		bot.execute(foto);

	}

	public void buy() throws TelegramApiException, TransitionNotFoundException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("ok come preferisci.").build();
		Merchant merchant = (Merchant) beings.get(identifiers[2]);
		Item item = merchant.getInventory().get(player.getInterstedObject());
		buttonsOnPlanet(message);
		int cost = 0;
		if (literalNum.isCurrentState(new state_machine.core.State("buyBullets"))) {
			int numBullets = Integer.parseInt(messageText);
			Bullet bullet = new Bullet();
			cost = bullet.getCost() * numBullets;
			if (player.getMoney() >= cost && numBullets <= merchant.getNumBullets()) {
				player.subMoney(cost);
				player.addNumBullets(numBullets);
				merchant.subNumBullets(numBullets);
				message.setText("interessante acquisto!");
				if(flags[3])merchant.delItem(item);
				else merchant.removeFromList();
				merchant.buildMarket();
			} else if (numBullets >= merchant.getNumBullets())
				message.setText("non ho tutti questi proiettili mi dispiace!");
			else
				message.setText("non hai abbastanza soldi!");
			literalNum.change(new Event("buyBulletsToLit"));
		}
		if (messageText.equals("SI")) {
			if (literalNum.isCurrentState(new state_machine.core.State("literalInput"))) {
				if (item instanceof Bullet) {
					literalNum.change(new Event("litToBuyBullets"));
					message.setText(
							"Digita il numero di proiettili a cui sei interessato o scegli fra le opzioni sotto.");
					buttonsNumChose(message, merchant.getNumBullets());
					bot.execute(message);
					literalNum.change(new Event("litToBuyBullets"));
					return;
				} else if (player.getMoney() >= cost) {
					player.subMoney(cost);
					inventory.put(item);
					if(flags[3])merchant.delItem(item);
					else merchant.removeFromList();
					message.setText("ottimo acquisto hai occhio!");
					merchant.buildMarket();
				} else
					message.setText("non hai abbastanza soldi!");
			}
		}
		if(flags[3]) {
			bot.execute(message);
			message.setText("dai ancora uno sguardo, vuoi delle armi?");
		    merchant.setResponseW(message);
		    bot.execute(message);
		    message.setText("o degli oggetti?");
		    merchant.setResponseI(message);
		    bot.execute(message);
		}
		else {
			bot.execute(message);
			merchant.displayCurrentItem(bot, chatId);
			message.setText("dai ancora uno sguardo!");
			bot.execute(message);
		}

	}

	public void mine() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("").build();
		StringBuilder sb = new StringBuilder();
		Planet planet = userPState.getPlanet();
		player = ((Player) beings.get(l_chatId));
		int earth = Math.round(planet.getValue(Valore.TERRA)) * random.nextInt(50) + 50;
		bag.addMaterial(Material.EARTH, earth);
		sb.append("Hai raccolto :\nterra: " + earth);
		if (!player.havePickaxe()) {
			sb.append("\n~non puoi minare oro e ferro se non hai un piccone~");
		} else {
			int gold = Math.round(planet.getValue(Valore.ORO)) * random.nextInt(50) + 50;
			int iron = Math.round(planet.getValue(Valore.FERRO)) * random.nextInt(50) + 50;
			bag.addMaterial(Material.GOLD, gold);
			bag.addMaterial(Material.IRON, iron);
			sb.append("\noro: " + gold + "\nferro: " + iron);
		}
		if (!player.haveBucket()) {
			sb.append("\n~non puoi prendere acqua senza un secchio~");
		} else {
			int water = Math.round(planet.getValue(Valore.ACQUA)) * random.nextInt(50) + 50;
			bag.addMaterial(Material.WATER, water);
			sb.append("\nacqua: " + water);
		}
		message.setText(sb.toString());
		bot.execute(message);
	}

	public void powerUp() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("scegli l'arma da potenziare?").build();
		buttonsInventory(message, player.getInventory());
		bot.execute(message);
	}

	public void choseMaterial() throws TelegramApiException, TransitionNotFoundException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("scegli il materiale da usare?").build();
		if (literalNum.isCurrentState(new state_machine.core.State("materialToPowerUp"))) {
			player.setNumMaterialToAdd(Integer.parseInt(messageText));
			buttonsChoseMaterials(message);
			literalNum.change(new Event("powerUpToLit"));
		} else if (literalNum.isCurrentState(new state_machine.core.State("literalInput"))) {
			message.setText("scegli quale parte dell'arma vuoi migliorare.");
			int value = player.getNumMaterialToAdd();
			player.setTypeOfMatToPowerUp(messageText);
			if (messageText.equals("Oro") && player.getBag().getMaterial(Material.GOLD) < value) {
				message.setText("non hai abbastanza oro.");
				buttonsPlayerMenu(message);
				bot.execute(message);
				return;
			}
			if (messageText.equals("Ferro") && player.getBag().getMaterial(Material.IRON) < value) {
				message.setText("non hai abbastanza ferro.");
				buttonsPlayerMenu(message);
				bot.execute(message);
				return;
			}
			if (messageText.equals("Roccia") && player.getBag().getMaterial(Material.ROCK) < value) {
				message.setText("non hai abbastanza roccia.");
				buttonsPlayerMenu(message);
				bot.execute(message);
				return;
			}
			buttonsPowerUpParts(message);
		}
		bot.execute(message);
	}

	public void choseParts() throws TelegramApiException, IOException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("arma migliorata correttamente").build();
		buttonsPlayerMenu(message);
		int value = player.getNumMaterialToAdd();
		UpgradeableItem item = (UpgradeableItem) player.getWeaponToPowerUp();
		String typeOfMat = player.getTypeOfMatToPowerUp();
		if (messageText.equals("Parte Superiore")) {
			if (typeOfMat.equals("Oro")) {
				item.powerUpblade(value, 0, 0);
				bag.subMaterial(Material.GOLD, value);
			} else if (typeOfMat.equals("Ferro")) {
				item.powerUpblade(0, value, 0);
				bag.subMaterial(Material.IRON, value);
			} else if (typeOfMat.equals("Roccia")) {
				item.powerUpblade(0, 0, value);
				bag.subMaterial(Material.ROCK, value);
			}
		}
		if (messageText.equals("Manico")) {
			if (typeOfMat.equals("Oro")) {
				item.powerUpHandle(value, 0, 0);
				bag.subMaterial(Material.GOLD, value);
			} else if (typeOfMat.equals("Ferro")) {
				item.powerUpHandle(0, value, 0);
				bag.subMaterial(Material.IRON, value);
			} else if (typeOfMat.equals("Roccia")) {
				item.powerUpHandle(0, 0, value);
				bag.subMaterial(Material.ROCK, value);
			}
		}
		if (((Weapon) item).equals(player.getequippedWeapon()))
			player.equipeWeapon((Weapon) item);
		player.setTypeOfMatToPowerUp("");
		bot.execute(message);
	}

	public void generates() throws TelegramApiException, TransitionNotFoundException {
		int valueEarth = bag.getMaterial(Material.EARTH) / 5;
		int valueWater = bag.getMaterial(Material.WATER) / 2;
		int valueMax = Math.min(valueEarth, valueWater);
		SendMessage message = SendMessage.builder().chatId(chatId)
				.text("per creare della roccia hai bisogno di acqua e terra, il"
						+ " rapporto è 2 porzioni di acqua e 5 di terra generano 3 porzioni di roccia"
						+ "\nDigita il numero di pietra che vuoi generare o scegli una delle opzioni.")
				.build();
		if (literalNum.isCurrentState(new state_machine.core.State("literalInput"))) {
			buttonsNumChose(message, valueMax);
			literalNum.change(new Event("litToRockGen"));
		} else if (literalNum.isCurrentState(new state_machine.core.State("rockToGenerate"))) {
			buttonsPlayerMenu(message);
			int value = Integer.parseInt(messageText);
			if (value <= valueMax) {
				bag.subMaterial(Material.EARTH, (value * 5));
				bag.subMaterial(Material.WATER, (value * 2));
				bag.addMaterial(Material.ROCK, value * 3);
				message.setText("hai generato " + value + " porzioni di roccia.");
			} else {
				message.setText("non hai abbastanza materiali!");
			}
			literalNum.change(new Event("rockGenToLit"));
		}
		bot.execute(message);
	}

	public void condense() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId)
				.text("vuoi potenziare un'arma o vuoi creare della pietra?").build();
		buttonsGC(message);
		bot.execute(message);
	}

	public void equip() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId).text("scegli un'arma da equipaggiare.").build();
		if (player.getWeaponToEquip() != null) {
			player.equipeWeapon(player.getWeaponToEquip());
			message.setText("arma equipaggiata.");
		}
		bot.execute(message);
	}

	public void refill() throws TelegramApiException, TransitionNotFoundException {
		SendMessage message = SendMessage.builder().chatId(chatId)
				.text("digita il numero di proiettili o scegli tra le opzioni sotto.").build();
		if (literalNum.isCurrentState(new state_machine.core.State("literalInput"))) {
			buttonsNumChose(message, player.getNumBullets());
			bot.execute(message);
			literalNum.change(new Event("litToRefill"));
		} else if (literalNum.isCurrentState(new state_machine.core.State("refillBullet"))) {
			int nBullets = Integer.parseInt(messageText);
			if (nBullets <= 0)
				message.setText("digita un numero valido");
			if (nBullets > player.getNumBullets())
				message.setText("non hai abbastanza proiettili");
			else {
				message.setText("pistola ricaricata");
				Gun gun = (Gun) player.getWeaponToEquip();
				gun.addBullet(nBullets);
				player.subNumBullets(nBullets);
				buttonsPlayerMenu(message);
				literalNum.change(new Event("refillToLit"));
			}
			bot.execute(message);
		}
	}

	public void choseView() throws TelegramApiException {
		SendMessage message = SendMessage.builder().chatId(chatId)
				.text("scegli il tipo di vista che preferisci per guardare gli oggetti esposti.").build();
		buttonsChoseMarketView(message);
		bot.execute(message);
	}

	public void changeView() throws TelegramApiException, IOException {
		SendMessage message = SendMessage.builder().chatId(chatId)
				.text("salve, " + player.getName() + " ti interessa qualcosa?").build();
		if (messageText.equals("Visuale a lista")) {
			flags[3] = true;
		} else if (messageText.equals("Visuale a scorrimento")) {
			flags[3] = false;
		}
		buttonsPlayerMenu(message);
		bot.execute(message);
		merch();
	}

	private void buttonsPlayerMenu(SendMessage message) {

		if (flags[1] && !flags[2]) {
			keyboard.buildK_playerMenu();
			message.setReplyMarkup(keyboard);

		} else if (!flags[1] && !flags[2]) {
			keyboard.buildK_playerFirstAction();
			message.setReplyMarkup(keyboard);
		} else {
			keyboard.buildK_playerMenuWithCondenser();
			message.setReplyMarkup(keyboard);
		}
	}

	private void buttonsOnPlanet(SendMessage message) {
		keyboard.buildK_playerFirstActionWC();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsSelectYN(SendMessage message) {
		keyboard.buildK_YNButtons();
		message.setReplyMarkup(keyboard);

	}

	private void buttonsChose(SendMessage message) {
		keyboard.buildK_chosePlayer();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsBegin(SendMessage message) {
		keyboard.buildK_beginButtons();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsCombat(SendMessage message) {
		keyboard.buildK_combatPhase();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsNumChose(SendMessage message, int i) {
		keyboard.buildK_numberChose(i);
		message.setReplyMarkup(keyboard);
	}

	private void buttonsGC(SendMessage message) {
		keyboard.buildK_GCButtons();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsInventory(SendMessage message, Inventory inventory) {
		keyboard.buildK_Inventory(inventory);
		message.setReplyMarkup(keyboard);
	}

	private void buttonsEquip(SendMessage message) {
		keyboard.buildK_Equip();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsEquipGun(SendMessage message) {
		keyboard.buildK_EquipGun();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsChoseMaterials(SendMessage message) {
		keyboard.buildK_choseMaterials();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsPowerUpParts(SendMessage message) {
		keyboard.buildK_powerUpParts();
		message.setReplyMarkup(keyboard);
	}

	private void buttonsChoseMarketView(SendMessage message) {
		keyboard.buildK_choseMarketView();
		message.setReplyMarkup(keyboard);
	}

	private void set() {
		// setting the new data
		player.setInventory(inventory);
		player.setBag(bag);
		game.setKeyboard(keyboard);
		game.setUserPState(userPState);
		game.setEntities(beings);
		game.setFlags(flags);
		game.setLiteralNum(literalNum);
		bot.setGames(_games);
	}

	private boolean isInt(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}