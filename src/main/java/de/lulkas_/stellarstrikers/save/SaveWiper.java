package de.lulkas_.stellarstrikers.save;

import de.lulkas_.stellarstrikers.GamePanel;
import org.gamejolt.DataStore;
import org.gamejolt.GameJoltAPI;

public class SaveWiper {
    private final GameJoltAPI api;
    private final GamePanel gamePanel;

    public SaveWiper(GameJoltAPI api, GamePanel gamePanel) {
        this.api = api;
        this.gamePanel = gamePanel;
    }

    public void wipe() {
        api.removeDataStore(DataStore.DataStoreType.USER, "score");
        api.removeDataStore(DataStore.DataStoreType.USER, "level");
        api.removeDataStore(DataStore.DataStoreType.USER, "level_points");
        api.removeDataStore(DataStore.DataStoreType.USER, "leveling_cost");
        api.removeDataStore(DataStore.DataStoreType.USER, "damage");
        api.removeDataStore(DataStore.DataStoreType.USER, "health");
        api.removeDataStore(DataStore.DataStoreType.USER, "income");
        api.removeDataStore(DataStore.DataStoreType.USER, "skin");
        api.removeDataStore(DataStore.DataStoreType.USER, "is_playing_sound");
        api.removeDataStore(DataStore.DataStoreType.USER, "is_playing_music");
        api.removeDataStore(DataStore.DataStoreType.USER, "player_bullet_color");
        api.removeDataStore(DataStore.DataStoreType.USER, "enemy_bullet_color");
        api.removeDataStore(DataStore.DataStoreType.USER, "bomb_color");
        api.removeDataStore(DataStore.DataStoreType.USER, "detonated_bomb_color");

        api.sessionClose();

        System.exit(0);
    }
}
