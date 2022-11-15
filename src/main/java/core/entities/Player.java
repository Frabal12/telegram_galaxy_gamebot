package core.entities;

import api.entities.Human;
import api.items.weapons.Weapon;
import core.items.Bag;
import core.items.Inventory;
import core.items.others.Bucket;
import core.items.others.MatterCondenser;
import core.items.others.Pickaxe;
import state_machines.StateMachines;

public class Player extends Human {

	private static final long serialVersionUID = -5920546736181754866L;
	private String classPlayer = "";
	private long interstedObject = 0l;
	private Weapon equippedWeapon,weaponToEquip,weaponToPowerUp = null;
	private int startAg, startSt, startP;
	private Inventory inventory;
	private boolean nameSetted=false;
	private boolean bucket=false;
	private boolean pickaxe=false;
	private boolean condenser=false;
	private int numBullets,numMaterialToAdd;
	private Bag bag;
	private String typeOfMatToPowerUp="";
	
	

	public Player(String name, Long id) {
		this.name = name;
		this.id = id;
		statsMachine = StateMachines.statsMachineBuilder();

	}

	public Weapon getequippedWeapon() {
		return equippedWeapon;
	}

	public void equipeWeapon(Weapon equippedWeapon) {

		if (this.equippedWeapon == null) {
			startAg = agility;
			startSt = strength;
			startP = protection;
			agility += equippedWeapon.getSpeed();
			strength += equippedWeapon.getDamage();
			protection += equippedWeapon.getDefence();
			this.equippedWeapon = equippedWeapon;
		} else {
			agility=startAg;
			strength=startSt;
			protection=startP;
			agility += equippedWeapon.getSpeed();
			strength += equippedWeapon.getDamage();
			protection += equippedWeapon.getDefence();
			this.equippedWeapon = equippedWeapon;
		}
	}

	public Long getInterstedObject() {
		return interstedObject;
	}

	public void setInterstedObject(Long interstedObject) {
		this.interstedObject = interstedObject;
	}

	public void setClassPlayer(String classPlayer) {
		this.classPlayer = classPlayer;
	}

	public String getClassPlayer() {
		return classPlayer;
	}
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Boolean isNameSetted() {
		return nameSetted;
	}

	public void nameSetted() {
		nameSetted=true;
	}
	public boolean haveBucket() {
		if(!bucket) {
			for(api.items.Item i : inventory.getInventory().values()) {
				if(i instanceof Bucket) {
					bucket=true;
					break;
				}
			}
		}
		return bucket;
	}
	public boolean havePickaxe() {
		if(!pickaxe) {
			for(api.items.Item i : inventory.getInventory().values()) {
				if(i instanceof Pickaxe) {
					pickaxe=true;
					break;
				}
			}
		}
		return pickaxe;
	}
	public boolean haveCondenser() {
		if(!condenser) {
			for(api.items.Item i : inventory.getInventory().values()) {
				if(i instanceof MatterCondenser) {
					condenser=true;
					break;
				}
			}
		}
		return condenser;
	}
	public Bag getBag() {
		return bag;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
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
	public Weapon getWeaponToEquip() {
		return weaponToEquip;
	}

	public void setWeaponToEquip(Weapon weaponToEquip) {
		this.weaponToEquip = weaponToEquip;
	}

	public Weapon getWeaponToPowerUp() {
		return weaponToPowerUp;
	}

	public void setWeaponToPowerUp(Weapon weaponToPowerUp) {
		this.weaponToPowerUp = weaponToPowerUp;
	}

	public int getNumMaterialToAdd() {
		return numMaterialToAdd;
	}

	public void setNumMaterialToAdd(int numMaterialToAdd) {
		this.numMaterialToAdd = numMaterialToAdd;
	}

	public String getTypeOfMatToPowerUp() {
		return typeOfMatToPowerUp;
	}

	public void setTypeOfMatToPowerUp(String typeOfMatToPowerUp) {
		this.typeOfMatToPowerUp = typeOfMatToPowerUp;
	}
	public boolean isEquipped(){
		return true;
	}
}
