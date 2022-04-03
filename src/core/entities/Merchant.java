package core.entities;

import api.entities.Human;
import api.items.Item;
import api.items.weapons.Weapon;
import core.GalaxyLife;
import core.items.Inventory;
import core.items.others.Bullet;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Merchant extends Human {
    /**
     *
     */
    private static final long serialVersionUID = -1889208345057230951L;
    private Inventory inventory = new Inventory();
    private ArrayList<Item> invList;
    private int messageId, index;
    private InlineKeyboardMarkup scrollingView = new InlineKeyboardMarkup();
    private List<List<InlineKeyboardButton>> buttonsList = new ArrayList<>();
    private List<InlineKeyboardButton> buttons = new ArrayList<>();
    private InlineKeyboardMarkup sellW = new InlineKeyboardMarkup();
    private InlineKeyboardMarkup sellI = new InlineKeyboardMarkup();
    private List<List<InlineKeyboardButton>> weapons = new ArrayList<>();
    private List<InlineKeyboardButton> swords = new ArrayList<>();
    private List<InlineKeyboardButton> knifes = new ArrayList<>();
    private List<InlineKeyboardButton> guns = new ArrayList<>();
    private List<List<InlineKeyboardButton>> items = new ArrayList<>();
    private List<InlineKeyboardButton> bucket = new ArrayList<>();
    private List<InlineKeyboardButton> pickaxe = new ArrayList<>();
    private List<InlineKeyboardButton> bullets = new ArrayList<>();
    private int numBullets;

    public Merchant(Inventory inventory) {
        Random random = new Random();
        numBullets = random.nextInt(50) + 20;
        name = "Merchant";
        this.inventory = inventory;
        buildMarket();
    }

    public void incIndex() {
        // (a% b + b)% b expression to make the modulus of a negative number so that it
        // is circular scrolling
        index = ((index + 1) % invList.size() + invList.size()) % invList.size();
    }

    public void decIndex() {
        index = ((index - 1) % invList.size() + invList.size()) % invList.size();
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getNumBullets() {
        return numBullets;
    }

    public void setNumBullets(int numBullets) {
        this.numBullets = numBullets;
    }

    public void addNumBullets(int numBullets) {
        this.numBullets += numBullets;
    }

    public void subNumBullets(int numBullets) {
        this.numBullets -= numBullets;
    }

    public void setResponseW(SendMessage message) {
        message.setReplyMarkup(sellW);
    }

    public void setResponseI(SendMessage message) {
        message.setReplyMarkup(sellI);
    }

    public void displayCurrentItem(GalaxyLife bot, String chatId) throws TelegramApiException {
        try {
            if (messageId != 0) {
                DeleteMessage dm = DeleteMessage.builder().chatId(chatId).messageId(messageId).build();
                bot.execute(dm);
            }
            Item item = invList.get(index);
            buttonsList.clear();
            buttons.clear();
            buttons.add(InlineKeyboardButton.builder().text("indietro").callbackData("indietro").build());
            buttons.add(InlineKeyboardButton.builder().text("buy").callbackData(item.getId() + "").build());
            buttons.add(InlineKeyboardButton.builder().text("avanti").callbackData("avanti").build());
            buttonsList.add(buttons);
            scrollingView.setKeyboard(buttonsList);
            Message m;
            if (item instanceof Weapon) {
                InputStream is = new ByteArrayInputStream(((Weapon) item).getWeaponImage());
                SendPhoto foto = SendPhoto.builder().chatId(chatId).parseMode(ParseMode.MARKDOWN)
                        .photo(new InputFile(is, item.getName()))
                        .caption(item.toString() + "\n\n                                     *" + (index + 1) + "\\"
                                + (invList.size()) + "*")
                        .build();
                foto.setReplyMarkup(scrollingView);
                m = bot.execute(foto);
                messageId = m.getMessageId();
            } else if (item instanceof Bullet) {
                SendMessage msg = SendMessage.builder().chatId(chatId).parseMode(ParseMode.MARKDOWN)
                        .text("Proiettili: " + numBullets + "\n\n                                    *" + (index + 1)
                                + "\\" + (invList.size()) + "*")
                        .build();
                msg.setReplyMarkup(scrollingView);
                m = bot.execute(msg);
                messageId = m.getMessageId();
            } else {
                SendMessage msg = SendMessage.builder().chatId(chatId).parseMode(ParseMode.MARKDOWN)
                        .text(item.toString() + "\n\n                                    *" + (index + 1) + "\\"
                                + (invList.size()) + "*")
                        .build();
                msg.setReplyMarkup(scrollingView);
                m = bot.execute(msg);
                messageId = m.getMessageId();
            }
        } catch (Exception e) {
            SendMessage msg = SendMessage.builder().chatId(chatId).text("Articoli finiti.").build();
            bot.execute(msg);
        }
    }

    public void buildMarket() {

        // list view
        for (List<InlineKeyboardButton> l : weapons) l.clear();
        for (List<InlineKeyboardButton> l : items) l.clear();
        weapons.clear();
        items.clear();
        Map<Long, Item> inventory = this.inventory.getInventory();
        for (Long key : inventory.keySet()) {
            if (key.toString().substring(0, 2).equals("11")) {
                swords.add(InlineKeyboardButton.builder().text(inventory.get(key).getName()).callbackData(key + "")
                        .build());
            } else if (key.toString().substring(0, 2).equals("12")) {
                guns.add(InlineKeyboardButton.builder().text(inventory.get(key).getName()).callbackData(key + "")
                        .build());
            } else if (key.toString().substring(0, 2).equals("13")) {
                knifes.add(InlineKeyboardButton.builder().text(inventory.get(key).getName()).callbackData(key + "")
                        .build());
            } else if (key.toString().substring(0, 2).equals("21")) {
                pickaxe.add(InlineKeyboardButton.builder().text(inventory.get(key).getName()).callbackData(key + "")
                        .build());
            } else if (key.toString().substring(0, 2).equals("22")) {
                bucket.add(InlineKeyboardButton.builder().text(inventory.get(key).getName()).callbackData(key + "")
                        .build());
            } else if (key.toString().substring(0, 2).equals("23")) {
                bullets.add(
                        InlineKeyboardButton.builder().text("bullets x " + numBullets).callbackData(key + "").build());
            } else if (key.toString().substring(0, 2).equals("24")) {
                bullets.add(InlineKeyboardButton.builder().text(inventory.get(key).getName()).callbackData(key + "")
                        .build());
            }

        }
        weapons.add(swords);
        weapons.add(guns);
        weapons.add(knifes);
        items.add(bucket);
        items.add(pickaxe);
        items.add(bullets);
        sellW.setKeyboard(weapons);
        sellI.setKeyboard(items);
        invList = new ArrayList<>(inventory.values());
    }

    public void delItem(Item item) {
        InlineKeyboardButton verify = InlineKeyboardButton.builder().text(item.getName())
                .callbackData(item.getId() + "").build();
        if (item instanceof Weapon) {
            inventory.getInventory().remove(item.getId());
            for (List<InlineKeyboardButton> l : weapons) {
                for (InlineKeyboardButton button : l) {
                    if (button.getCallbackData().equals(verify.getCallbackData())) {
                        l.remove(button);
                        return;
                    }
                }
            }
        } else {
            for (List<InlineKeyboardButton> l : items) {
                for (InlineKeyboardButton button : l) {
                    // 230 is the bullet id
                    if (button.getCallbackData().equals(230 + "") && numBullets > 0) {
                        InlineKeyboardButton bullets = InlineKeyboardButton.builder().text("bullets x " + numBullets)
                                .callbackData(item.getId() + "").build();
                        l.remove(button);
                        l.add(bullets);
                    } else if (button.getCallbackData().equals(verify.getCallbackData())) {
                        inventory.getInventory().remove(item.getId());
                        l.remove(button);
                        return;
                    }
                }
            }
        }
    }

    public void removeFromList() {
        Item item = invList.get(index);
        if (((item instanceof Bullet && this.numBullets <= 0)) || !(item instanceof Bullet)) {
            invList.remove(index);
            inventory.getInventory().remove(item.getId());
        }
    }
}
