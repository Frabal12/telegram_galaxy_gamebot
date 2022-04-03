package api.items;

import java.io.IOException;

public interface UpgradeableItem {
    void powerUpHandle(int gold, int iron, int rock) throws IOException;

    void powerUpblade(int gold, int iron, int rock) throws IOException;
}
