package core.items.others;

import api.items.DestroyableItem;

public class Pickaxe extends DestroyableItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7424814515567280609L;

	public Pickaxe() {
		name="pickaxe";
		cost=random.nextInt(300);
		startResistance();
		id = 210l;
	}
	public String toString(){
		return name;
	}


}
