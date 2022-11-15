package core.items;

import api.items.Item;
import api.items.weapons.Material;

public class Bag extends Item {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4373170604841067950L;
	private int gold,iron,earth,water,rock;
	
	public void setMaterial(Material material,int val) {
		switch (material) {
		case GOLD:gold = val;break;
		case IRON:iron = val;break;
		case ROCK:rock = val;break;
		case EARTH:earth = val;break;
		case WATER:water = val;break;
		default:break;
		}
		}
	public void addMaterial(Material material,int val) {
		switch (material) {
		case GOLD:gold += val;break;
		case IRON:iron += val;break;
		case ROCK:rock += val;break;
		case EARTH:earth += val;break;
		case WATER:water += val;break;
		default:break;
		}
		}
	public void subMaterial(Material material,int val) {
		switch (material) {
		case GOLD:gold -= val;break;
		case IRON:iron -= val;break;
		case ROCK:rock -= val;break;
		case EARTH:earth -= val;break;
		case WATER:water -= val;break;
		default:break;
		}
		}
	public int getMaterial(Material material) {
		int i = 0;
		switch (material) {
		case GOLD:i = gold;break;
		case IRON:i = iron;break;
		case ROCK:i = rock;break;
		case EARTH:i = earth;break;
		case WATER:i = water;break;
		default:break;
		}
		return i;
		}
	public int materialMin() {
		int i;
		i=Math.min(gold, iron);
		i=Math.min(i, rock);
		return i;
		}
	@Override
	public String toString() {
		return "i materiali che hai nella borsa sono :\n[oro=" + gold + ", ferro=" + iron + ", terra=" + earth + ", acqua=" + water + ", pietra=" + rock
				+ "]";
	}
	
	
	
}
