package de.lulkas_.stellarstrikers.save;

import de.lulkas_.stellarstrikers.GamePanel;
import org.gamejolt.GameJoltAPI;

public class SaveWriter {
    private final GamePanel gamePanel;
    private final GameJoltAPI api;
    private Thread saveThread;

    public SaveWriter(GamePanel gamePanel, GameJoltAPI api) {
        this.gamePanel = gamePanel;
        this.api = api;
    }

    public void save() {
        this.saveThread = new Thread(new SaveThread(api, gamePanel));
        saveThread.start();
    }
}
