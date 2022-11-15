package core;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import utils.Pair;
import java.util.concurrent.ConcurrentHashMap;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class GalaxyLife extends TelegramLongPollingBot
{
    private ConcurrentHashMap<Long, Game> _games;
    private ConcurrentHashMap<Long, Pair<Long, Long>> _times;
    private ExecutorService exec;
    int count;
    boolean flag;
    
    public GalaxyLife() {
        _games = new ConcurrentHashMap<Long, Game>();
        _times = new ConcurrentHashMap<Long, Pair<Long, Long>>();
        exec = Executors.newCachedThreadPool();
        count = 4;
        _games.put(0L, new Game());
    }
    
    public String getBotUsername() {
        return "toobig_bot";
    }
    
    public String getBotToken() {
        return "1348674363:AAH8YVxo0F9B9fn-dfv06brPcBvTJw9nyXs";
    }
    
    public void onUpdateReceived(final Update update) {
        try {
            this.switchUpdate(update);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    public boolean[] buildFlags() {
        final boolean[] temp = { false, false };
        return temp;
    }
    
    private void switchUpdate(final Update update) throws TelegramApiException {
    	if(update.hasMessage() && update.getMessage().hasText()) {
        _games.computeIfAbsent(update.getMessage().getChatId(), key -> new Game(update.getMessage().getChatId()));
    	}
    	else if (update.hasCallbackQuery()) {
    	_games.computeIfAbsent((long)update.getCallbackQuery().getFrom().getId(), key -> new Game((long)update.getCallbackQuery().getFrom().getId()));
    	}
    	else if (update.hasInlineQuery()) {
    	_games.computeIfAbsent((long)update.getInlineQuery().getFrom().getId(), key -> new Game((long)update.getInlineQuery().getFrom().getId()));
    	}
        exec.submit((Runnable)new ExecThread(this, update));
    }
    
    public ConcurrentHashMap<Long, Game> getGames() {
        return this._games;
    }
    
    public void setGames(final ConcurrentHashMap<Long, Game> _games) {
        this._games = _games;
    }
}
