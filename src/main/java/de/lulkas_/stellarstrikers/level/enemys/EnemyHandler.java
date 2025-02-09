package de.lulkas_.stellarstrikers.level.enemys;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.player.Player;

import java.util.ArrayList;
import java.util.List;

public class EnemyHandler {
    public Integer wave = 0;
    public WaveState waveState = WaveState.DEFEATED;
    public List<Enemy> enemies = new ArrayList<>();
    public List<Boss> bosses = new ArrayList<>();
    private GameObjectHandler gameObjectHandler;
    private Player player;
    public int spawningTicks = 120;
    private int waves;
    private int singleGameScore;
    private Thread apiThread;

    public void tick() {
        for (int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).dead) {
                enemies.remove(i);
            }
        }

        for (int i = 0; i < bosses.size(); i++) {
            if(bosses.get(i).dead) {
                bosses.remove(i);
            }
        }

        tickEnemies();
        tickBosses();

        if(this.waveState == WaveState.SPAWNED && this.enemies.size() == 0 && this.bosses.size() == 0) {
            this.waveState = WaveState.DEFEATED;
        }

        if(this.waveState == WaveState.DEFEATED) {
            apiThread = new Thread(() -> gameObjectHandler.api.addHighscore(968819, wave.toString(), wave));
            apiThread.start();
            this.wave += 1;
            if(wave == waves + 1) {
                gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_WON_MENU;
            } else {
                waveState = WaveState.SPAWNING;
            }
        }

        if(this.waveState == WaveState.SPAWNING) {
            spawningTicks--;
            if(spawningTicks <= 60) {
                this.player.health = this.player.maxHealth;
            }
            if(spawningTicks <= 0) {
                spawnEnemies();
            }
        }
    }

    private void tickEnemies() {
        for(int i = 0; i < this.enemies.size(); i++) {
            this.enemies.get(i).tick();
        }
    }

    private void tickBosses() {
        for(int i = 0; i < this.bosses.size(); i++) {
            this.bosses.get(i).tick();
        }
    }

    public EnemyHandler(int startScore, GameObjectHandler gameObjectHandler, Player player, int waves) {
        this.singleGameScore = startScore;
        this.gameObjectHandler = gameObjectHandler;
        this.player = player;
        this.waves = waves;
    }

    private void spawnEnemies() {
        WaveSpawnHandler.spawnWave(this.wave, gameObjectHandler, this);

        waveState = WaveState.SPAWNED;
        spawningTicks = 120;
    }

    public enum WaveState {
        SPAWNED,
        DEFEATED,
        SPAWNING
    }

    public int getSingleGameScore() {
        return singleGameScore;
    }

    public void score(int amount) {
        singleGameScore += amount;
    }

    public int getDisplayBossTicks() {
        if(gameObjectHandler.gameState == GameObjectHandler.GameState.PLAYING) {
            if(((int) (this.wave / 5)) * 5 == this.wave) {
                if(this.waveState == EnemyHandler.WaveState.SPAWNING) {
                    return this.spawningTicks;
                } else {
                    return 0;
                }
            } else if (((int) ((this.wave - 1) / 5)) * 5 == this.wave - 1 && this.wave != 1) {
                if(this.waveState == EnemyHandler.WaveState.SPAWNING) {
                    return 120 - gameObjectHandler.enemyHandler.spawningTicks;
                } else {
                    return 120;
                }
            } else  {
                return 120;
            }
        } else {
            return 120;
        }
    }
}
