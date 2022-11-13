package api.items.weapons;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import api.items.DestroyableItem;

public abstract class Weapon extends DestroyableItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6737001620204026394L;
	protected int defence;
	protected int damage;
	protected int speed;
	protected int handle[] = {0,0,0,100};// 0 - gold, 1 - iron, 2 - rock, 3 - wood
	transient protected BufferedImage weapon;
	protected byte[] serializableImage=null;
	protected final Color GOLD = new Color(255, 215, 0);
	protected final Color IRON = new Color(102, 92, 92);
	protected final Color ROCK = new Color(53,54,0);
	protected final Color WOOD = new Color(75,44,13);

	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getDefence() {
		return defence;
	}
	public void setDefence(int defence) {
		this.defence = defence;
	}
	
	@Override
	public String toString() {
		return "un'arma generica [damage=" + damage + ", speed=" + speed + ", resistance=" + resistance + "]";
	}
	public byte[] getWeaponImage(){
		return serializableImage;
		}
	
}
