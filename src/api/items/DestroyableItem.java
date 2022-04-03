package api.items;

import java.util.Random;

public abstract class DestroyableItem extends Item {
    /**
     *
     */
    private static final long serialVersionUID = -376006983764602886L;
    protected int resistance = 0;
    protected Random random = new Random();

    public boolean isDestroyed() {
        return !(resistance == 0);
    }

    public void startResistance() {
        resistance = random.nextInt(500) + 200;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }
}
