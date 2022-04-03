package core.items.others;

import api.items.Item;

public class Bullet extends Item {
    /**
     *
     */
    private static final long serialVersionUID = -3885973419044511449L;

    public Bullet() {
        cost = 6;
        name = "bullet";
        id = 230l;
    }

    public String toString() {
        return name;
    }


}
