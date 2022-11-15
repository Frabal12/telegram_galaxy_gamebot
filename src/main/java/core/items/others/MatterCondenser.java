package core.items.others;

import java.util.Random;

import api.items.Item;

public class MatterCondenser extends Item {
	/**
	 * 
	 */
	private static final long serialVersionUID = 139899623042402165L;
	
	public MatterCondenser() {
		Random random = new Random();
		cost=random.nextInt(1000)+1000;
		name="Matter Condenser";
		id = 240l;
	}
	public String toString() {
		return name;
	}
}
