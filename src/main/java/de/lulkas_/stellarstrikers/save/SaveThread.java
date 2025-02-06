package de.lulkas_.stellarstrikers.save;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import org.gamejolt.DataStore;
import org.gamejolt.GameJoltAPI;

public class SaveThread implements Runnable {
    private final GameJoltAPI api;
    private final GameObjectHandler gameObjectHandler;

    public SaveThread(GameJoltAPI api, GameObjectHandler gameObjectHandler) {
        this.api = api;
        this.gameObjectHandler = gameObjectHandler;
    }

    @Override
    public void run() {
        api.setDataStore(DataStore.DataStoreType.USER, "score", gameObjectHandler.playerAttributeHandler.score.toString());
        api.setDataStore(DataStore.DataStoreType.USER, "level", gameObjectHandler.playerAttributeHandler.getLevel().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "level_points", gameObjectHandler.playerAttributeHandler.getLevelPoints().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "leveling_cost", gameObjectHandler.playerAttributeHandler.levelingCost.toString());
        api.setDataStore(DataStore.DataStoreType.USER, "damage", gameObjectHandler.playerSkillHandler.getRealDamage().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "health", gameObjectHandler.playerSkillHandler.getHealth().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "income", gameObjectHandler.playerSkillHandler.getRealIncome().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "skin", gameObjectHandler.playerAttributeHandler.getSkinDisplayed().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "is_playing_sound", gameObjectHandler.playerOptionsHandler.isPlayingSound().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "is_playing_music", gameObjectHandler.playerOptionsHandler.isPlayingMusic().toString());
        api.setDataStore(DataStore.DataStoreType.USER, "player_bullet_color", ((Integer) gameObjectHandler.playerOptionsHandler.getPlayerBulletColor().hashCode()).toString());
        api.setDataStore(DataStore.DataStoreType.USER, "enemy_bullet_color", ((Integer) gameObjectHandler.playerOptionsHandler.getEnemyBulletColor().hashCode()).toString());
        api.setDataStore(DataStore.DataStoreType.USER, "bomb_color", ((Integer) gameObjectHandler.playerOptionsHandler.getBombColor().hashCode()).toString());
        api.setDataStore(DataStore.DataStoreType.USER, "detonated_bomb_color", ((Integer) gameObjectHandler.playerOptionsHandler.getDetonatedBombColor().hashCode()).toString());
    }
}
