package core.items;

import java.util.HashMap;
import java.util.Map;

import api.items.Item;

public class Inventory extends Item{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3954599670327523026L;
	private Map<Long,Item> inventory=new HashMap<>(); 
	public Inventory(Item...item) {
		for(int i=0;i<item.length;i++) {
			inventory.put(item[i].getId(),item[i]);
		}
		
	}
	public Map<Long, Item> getInventory() {
		return inventory;
	}

	public void setInventory(Map<Long, Item> inventory) {
			inventory.forEach((key, val) -> this.inventory.put(key, val));
	}
	
	public void put(Item value) {
		inventory.put(value.getId(),value);
	}

	public Item get(Long key) {
		Item value=inventory.get(key);
		return value;
	}
	public String toString() {
		return inventory.toString();
		
	}
}
