package api.items;

import java.io.Serializable;

public abstract class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5987857980185443642L;
	protected String name="";
	protected Long id=0l;
	protected int cost=0;
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object obj) {
		if (this == obj)return true;
		if (obj == null)return false;
		if (!(obj instanceof Item))return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
