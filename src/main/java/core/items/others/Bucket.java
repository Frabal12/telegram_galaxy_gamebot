package core.items.others;

import api.items.DestroyableItem;

public class Bucket extends DestroyableItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3776257617276949550L;

	public Bucket() {
		name="bucket";
		cost=random.nextInt(300);
		startResistance();
		id = 220l;
	}
	public String toString() {
		return name;
	}
}
