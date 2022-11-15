package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import core.GalaxyLife;

public class Backup {

	private static <K, V> void save(Map<K, V> savableFile) throws IOException, NoSuchFieldException, SecurityException {
		String name = createName(savableFile);
		FileOutputStream fos = new FileOutputStream("backup/" + name + ".pln");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(savableFile);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	private static <K, V> ConcurrentHashMap<K, V> open(String name)
			throws IOException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		FileInputStream fis = new FileInputStream("backup/" + name + ".pln");
		ObjectInputStream ois = new ObjectInputStream(fis);
		ConcurrentHashMap<K, V> fileReaded = (ConcurrentHashMap<K, V>) ois.readObject();
		ois.close();
		return fileReaded;
	}

	@SuppressWarnings("unchecked")
	private static <K, V, T> String createName(Map<K, V> savableFile) throws NoSuchFieldException, SecurityException {
		StringBuilder name = new StringBuilder(200);
		Map<K, V> verify = savableFile;
		String keyType, keyValue;
		while (true) {
			keyType = verify.keySet().toArray()[0].getClass().getSimpleName();
			keyValue = verify.values().toArray()[0].getClass().getSimpleName();
			name.append(keyType);
			name.append("_");
			name.append(keyValue);
			if (keyValue.equals("HashMap"))
				name.append("__");
			if (!(verify.values().toArray()[0] instanceof HashMap))
				return name.toString();
			verify = (Map<K, V>) verify.values().toArray()[0];
		}
	}

	public static void save(GalaxyLife bot) throws IOException, NoSuchFieldException, SecurityException {
		save(bot.getGames());
	}

	public static void open(GalaxyLife bot)
			throws IOException, NoSuchFieldException, SecurityException, ClassNotFoundException {
		bot.setGames(open("Long_Game"));
	}
}
