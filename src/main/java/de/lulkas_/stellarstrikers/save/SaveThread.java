package de.lulkas_.stellarstrikers.save;

import de.lulkas_.stellarstrikers.GamePanel;
import org.gamejolt.DataStore;
import org.gamejolt.GameJoltAPI;

public class SaveThread implements Runnable {
    private final GameJoltAPI api;
    private final GamePanel gamePanel;

    public SaveThread(GameJoltAPI api, GamePanel gamePanel) {
        this.api = api;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        api.setDataStore(DataStore.DataStoreType.USER, "score", gamePanel.playerAttributeHandler.score.toString());
        api.setDataStore(DataStore.DataStoreType.USER, "level", gamePanel.playerAttributeHandler.getLevel().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "level_points", gamePanel.playerAttributeHandler.getLevelPoints().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "leveling_cost", gamePanel.playerAttributeHandler.levelingCost.toString());
        api.setDataStore(DataStore.DataStoreType.USER, "damage", gamePanel.playerSkillHandler.getRealDamage().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "health", gamePanel.playerSkillHandler.getHealth().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "income", gamePanel.playerSkillHandler.getRealIncome().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "skin", gamePanel.playerAttributeHandler.getSkinDisplayed().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "is_playing_sound", gamePanel.playerOptionsHandler.isPlayingSound().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "is_playing_music", gamePanel.playerOptionsHandler.isPlayingMusic().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "player_bullet_color", ((Integer) gamePanel.playerOptionsHandler.getPlayerBulletColor().hashCode()).toString());
        api.setDataStore(DataStore.DataStoreType.USER, "enemy_bullet_color", ((Integer) gamePanel.playerOptionsHandler.getEnemyBulletColor().hashCode()).toString());
        api.setDataStore(DataStore.DataStoreType.USER, "bomb_color", ((Integer) gamePanel.playerOptionsHandler.getBombColor().hashCode()).toString());
        api.setDataStore(DataStore.DataStoreType.USER, "detonated_bomb_color", ((Integer) gamePanel.playerOptionsHandler.getDetonatedBombColor().hashCode()).toString());
    }
}
