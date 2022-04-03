package core.entities;

import api.entities.Entity;
import api.entities.Human;
import api.entities.Monster;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Life implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4407722661916131385L;
    private Map<Long, Entity> entities = new HashMap<>();
    private Long playerId;

    public Life(Entity... entities) {
        String id = "";
        //in costruzione mancano altre entità
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] instanceof Human && !(entities[i] instanceof Player)) {
                id += "2";
            } else if (entities[i] instanceof Player) {
                playerId = entities[i].getId();
                id = entities[i].getId() + "";
            }
            if (entities[i] instanceof Monster) {
                id += "1";
            }
            if (!(entities[i] instanceof Player)) id += "" + playerId;
            this.entities.put(Long.valueOf(id), entities[i]);
            id = "";
        }

    }

    public Map<Long, Entity> getEntities() {
        return entities;
    }

    public void setEntities(Map<Long, Entity> entities) {
        entities.forEach((key, val) -> this.entities.put(key, val));
    }

    public void put(Long key, Entity value) {
        entities.put(key, value);
    }

    public Entity get(Long key) {
        Entity value = entities.get(key);
        return value;
    }

    public String toString() {
        return entities.toString();

    }


}