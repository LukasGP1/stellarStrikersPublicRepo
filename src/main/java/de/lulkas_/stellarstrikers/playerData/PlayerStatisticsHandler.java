package de.lulkas_.stellarstrikers.playerData;

import org.gamejolt.DataStore;
import org.gamejolt.GameJoltAPI;

public class PlayerStatisticsHandler {
    private Integer enemiesKilled = 0;
    private Integer gamesWon = 0;
    private Integer bossesKilled = 0;
    private final GameJoltAPI api;

    public PlayerStatisticsHandler(GameJoltAPI api) {
        this.api = api;
        getFromApi();
    }

    public void getFromApi() {
        try {
            int dataFromApi = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "enemies_killed").getData());
            enemiesKilled = dataFromApi;
            dataFromApi = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "games_won").getData());
            gamesWon = dataFromApi;
            dataFromApi = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "bosses_killed").getData());
            bossesKilled = dataFromApi;
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Nothing stored in api");
        }
    }

    public void killEnemy() {
        enemiesKilled++;
        (new Thread(() -> {
            api.setDataStore(DataStore.DataStoreType.USER, "enemies_killed", enemiesKilled.toString());
            api.addHighscore(968822, enemiesKilled.toString(), enemiesKilled);
        })).start();
    }
    public void killBoss() {
        bossesKilled++;
        (new Thread(() -> {
            api.setDataStore(DataStore.DataStoreType.USER, "bosses_killed", bossesKilled.toString());
            api.addHighscore(970832, bossesKilled.toString(), bossesKilled);
        })).start();
    }

    public void winGame() {
        gamesWon++;
        (new Thread(() -> {
            api.setDataStore(DataStore.DataStoreType.USER, "games_won", gamesWon.toString());
            api.addHighscore(968824, gamesWon.toString(), gamesWon);
        })).start();
    }

    public Integer getEnemiesKilled() {
        return enemiesKilled;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }
}
