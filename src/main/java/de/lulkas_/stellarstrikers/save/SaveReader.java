package de.lulkas_.stellarstrikers.save;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.playerData.PLayerLocalDataHandler;
import de.lulkas_.stellarstrikers.playerData.PlayerAttributeHandler;
import de.lulkas_.stellarstrikers.playerData.PlayerOptionsHandler;
import de.lulkas_.stellarstrikers.playerData.PlayerSkillHandler;
import org.gamejolt.DataStore;
import org.gamejolt.GameJoltAPI;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SaveReader {
    private int score = 0;
    private int level = 0;
    private int levelPoints = 0;
    private int levelingCost = 4;
    private int damage = 1;
    private int health = 3;
    private int income = 1;
    private int skin = 0;
    private final GameJoltAPI api;
    private boolean playingSound = true;
    private boolean playingMusic = true;
    private Color playerBulletColor = new Color(0, 255, 0);
    private Color enemyBulletColor = new Color(0, 255, 0);
    private Color bombColor = new Color(255, 255, 0);
    private Color detonatedBombColor = new Color(255, 0, 0);
    private Integer lastSkinSeen = 0;

    public SaveReader(GameJoltAPI api) {
        this.api = api;
        getFromSave();
    }

    public PlayerAttributeHandler getPlayerAttributeHandler(GameObjectHandler gameObjectHandler) {
        return new PlayerAttributeHandler(score, level, levelPoints, levelingCost, skin, gameObjectHandler);
    }

    public PlayerSkillHandler getPlayerSkillHandler(GameObjectHandler gameObjectHandler) {
        return new PlayerSkillHandler(damage, health, income, gameObjectHandler);
    }

    public PlayerOptionsHandler getPLayerOptionsHandler() {
        return new PlayerOptionsHandler(playingSound, playingMusic, playerBulletColor, enemyBulletColor, bombColor, detonatedBombColor);
    }

    public PLayerLocalDataHandler getPlayerLocalDataHandler() {
        return  new PLayerLocalDataHandler(lastSkinSeen);
    }

    public void reset() {
        score = 0;
        level = 0;
        levelPoints = 0;
        levelingCost = 4;
        damage = 1;
        health = 3;
        income = 1;
    }

    public void getFromSave() {
        try {
            score = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "score").getData());
            level = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "level").getData());
            levelPoints = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "level_points").getData());
            levelingCost = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "leveling_cost").getData());
            damage = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "damage").getData());
            health = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "health").getData());
            income = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "income").getData());
            skin = Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "skin").getData());
            playingSound = Boolean.parseBoolean(api.getDataStore(DataStore.DataStoreType.USER, "is_playing_sound").getData());
            playingMusic = Boolean.parseBoolean(api.getDataStore(DataStore.DataStoreType.USER, "is_playing_music").getData());
            playerBulletColor = new Color(Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "player_bullet_color").getData()));
            enemyBulletColor = new Color(Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "enemy_bullet_color").getData()));
            bombColor = new Color(Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "bomb_color").getData()));
            detonatedBombColor = new Color(Integer.parseInt(api.getDataStore(DataStore.DataStoreType.USER, "detonated_bomb_color").getData()));
        } catch (NullPointerException | NumberFormatException | NoSuchElementException e) {
            System.out.println("no data saved in api");
        }

        try {
            File localDataFile = new File("localData.dat");
            Scanner scanner = new Scanner(localDataFile);
            lastSkinSeen = Integer.valueOf(scanner.nextLine());
        } catch (FileNotFoundException | NoSuchElementException e) {
            System.out.println("No local data saved");
        }
    }
}
