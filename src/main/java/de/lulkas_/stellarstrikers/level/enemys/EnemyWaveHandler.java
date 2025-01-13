package de.lulkas_.stellarstrikers.level.enemys;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.level.player.Player;
import de.lulkas_.stellarstrikers.menu.display.TemporaryIntDisplay;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyWaveHandler {
    public Integer wave = 0;
    public WaveState waveState = WaveState.DEFEATED;
    public List<Enemy> enemies = new ArrayList<>();
    public List<Boss> bosses = new ArrayList<>();
    private GamePanel gamePanel;
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

        tickEnemys();
        tickBosses();

        if(this.waveState == WaveState.SPAWNED && this.enemies.size() == 0 && this.bosses.size() == 0) {
            this.waveState = WaveState.DEFEATED;
        }

        if(this.waveState == WaveState.DEFEATED) {
            apiThread = new Thread(() -> gamePanel.api.addHighscore(968819, wave.toString(), wave));
            apiThread.start();
            this.wave += 1;
            if(wave == waves + 1) {
                gamePanel.gameState = GamePanel.GameState.ENTER_WON_MENU;
            } else {
                waveState = WaveState.SPAWNING;
                ((TemporaryIntDisplay) gamePanel.gameMenu.displayHandler.displays.get(2)).appear(120);
            }
        }

        if(this.waveState == WaveState.SPAWNING) {
            spawningTicks--;
            if(spawningTicks <= 60) {
                this.player.health = this.player.maxHealth;
            }
            if(spawningTicks <= 0) {
                spawnEnemys();
            }
        }
    }

    private void tickEnemys() {
        for(int i = 0; i < this.enemies.size(); i++) {
            this.enemies.get(i).tick();
        }
    }

    private void tickBosses() {
        for(int i = 0; i < this.bosses.size(); i++) {
            this.bosses.get(i).tick();
        }
    }

    public EnemyWaveHandler(int startScore, GamePanel gamePanel, Player player, int waves) {
        this.singleGameScore = startScore;
        this.gamePanel = gamePanel;
        this.player = player;
        this.waves = waves;
    }

    private void spawnEnemys() {
        WaveSpawnHandler.spawnWave(this.wave, gamePanel, this);

        waveState = WaveState.SPAWNED;
        spawningTicks = 120;
    }

    public Graphics draw(Graphics g) {
        for(int i = 0; i < enemies.size(); i++) {
            g = enemies.get(i).draw(g);
        }
        for(int i = 0; i < bosses.size(); i++) {
            g = bosses.get(i).draw(g);
        }
        return g;
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
}
