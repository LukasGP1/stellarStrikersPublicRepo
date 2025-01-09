package de.lulkas_.stellarstrikers.level;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.level.enemys.Boss;
import de.lulkas_.stellarstrikers.level.enemys.Enemy;
import de.lulkas_.stellarstrikers.level.enemys.EnemyWaveHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WaveSpawnHandler {
    public static void spawnWave(int wave, GamePanel gamePanel, EnemyWaveHandler enemyWaveHandler) {
        List<Entity> enemies;
        try {
            enemies = readWave(wave, gamePanel);
            System.out.println("from file");
        } catch (NullPointerException e) {
            enemies = generateWave(wave, gamePanel);
            System.out.println("generated");
        }
        for(Entity entity : enemies) {
            if(entity instanceof Enemy) {
                enemyWaveHandler.enemies.add(((Enemy) entity));
            } else {
                enemyWaveHandler.bosses.add(((Boss) entity));
            }
        }
    }

    private static List<Entity> readWave(int wave, GamePanel gamePanel) {
        InputStream inputStream = WaveSpawnHandler.class.getResourceAsStream("/waves/" + wave + ".wdat");
        Scanner scanner = new Scanner(inputStream);
        List<StringBuilder> enemies = new ArrayList<>();
        while(scanner.hasNext()) {
            enemies.add(new StringBuilder(scanner.nextLine()));
        }

        List<Entity> toReturn = new ArrayList<>();

        for (StringBuilder enemy : enemies) {
            try {
                int lastReadIndex = 4;
                int firstReadIndex = 4;

                while(enemy.charAt(lastReadIndex) != ';') {
                    lastReadIndex++;
                }

                float x = Float.parseFloat(enemy.substring(firstReadIndex, lastReadIndex)) * Main.game.window.getWidth();

                firstReadIndex = lastReadIndex + 1;
                lastReadIndex = firstReadIndex;

                while(enemy.charAt(lastReadIndex) != ';') {
                    lastReadIndex++;
                }

                float y = Float.parseFloat(enemy.substring(firstReadIndex, lastReadIndex)) * gamePanel.gameMenu.yScale;

                firstReadIndex = lastReadIndex + 1;
                lastReadIndex = firstReadIndex;

                while(enemy.charAt(lastReadIndex) != ';') {
                    lastReadIndex++;
                }

                float speed = Float.parseFloat(enemy.substring(firstReadIndex, lastReadIndex)) * (Main.game.window.getWidth() / 960);

                firstReadIndex = lastReadIndex + 1;
                lastReadIndex = firstReadIndex;

                while(enemy.charAt(lastReadIndex) != ';') {
                    lastReadIndex++;
                }

                float health = Float.parseFloat(enemy.substring(firstReadIndex, lastReadIndex));

                if(enemy.charAt(0) == 'e') {
                    if(enemy.charAt(2) == 'N') {
                        toReturn.add(new Enemy(x, y, speed, ((int) health), gamePanel, Enemy.EnemyType.NORMAL));
                    } else if(enemy.charAt(2) == 'S') {
                        toReturn.add(new Enemy(x, y, speed, ((int) health), gamePanel, Enemy.EnemyType.SNIPER));
                    } else if (enemy.charAt(2) == 'G') {
                        toReturn.add(new Enemy(x, y, speed, ((int) health), gamePanel, Enemy.EnemyType.GUNNER));
                    } else {
                        throw new LevelFileFormatException();
                    }
                } else if(enemy.charAt(0) == 'b') {
                    if(enemy.charAt(2) == 'N') {
                        toReturn.add(new Boss(x, y, ((int) health), speed, gamePanel, Boss.BossType.NORMAL));
                    } else if(enemy.charAt(2) == 'S') {
                        toReturn.add(new Boss(x, y, ((int) health), speed, gamePanel, Boss.BossType.SNIPER));
                    } else if (enemy.charAt(2) == 'G') {
                        toReturn.add(new Boss(x, y, ((int) health), speed, gamePanel, Boss.BossType.GUNNER));
                    } else {
                        throw new LevelFileFormatException();
                    }
                } else {
                    throw new LevelFileFormatException();
                }
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                e.printStackTrace();
                throw new LevelFileFormatException();
            }
        }

        return toReturn;
    }

    private static List<Entity> generateWave(int wave, GamePanel gamePanel) {
        List<Entity> toReturn = new ArrayList<>();

        if(((int) (wave / 5)) * 5 == wave) {
            Boss.BossType type = Boss.BossType.NORMAL;

            if(wave > 5) {
                type = Boss.BossType.SNIPER;
            }

            if(wave > 10) {
                type = Boss.BossType.GUNNER;
            }

            toReturn.add(new Boss((float) (0.4 * Main.game.window.getWidth()), 300 * gamePanel.gameMenu.yScale, (wave / 5) * 25, 3.0f * (Main.game.window.getWidth() / 960), gamePanel, type));
        } else {
            Enemy.EnemyType type1 = Enemy.EnemyType.NORMAL;
            Enemy.EnemyType type2 = Enemy.EnemyType.NORMAL;
            if(wave > 5) {
                type2 = Enemy.EnemyType.SNIPER;
            }
            if(wave > 10) {
                type1 = Enemy.EnemyType.GUNNER;
            }
            toReturn.add(new Enemy((float) (0.2 * Main.game.window.getWidth()), 400 * gamePanel.gameMenu.yScale, 0.1f * (Main.game.window.getWidth() / 960), 2 + wave - 3, gamePanel, type1));
            toReturn.add(new Enemy((float) (0.42 * Main.game.window.getWidth()), 400 * gamePanel.gameMenu.yScale, 0.1f * (Main.game.window.getWidth() / 960), 3 + wave - 3, gamePanel, type1));
            toReturn.add(new Enemy((float) (0.63 * Main.game.window.getWidth()), 400 * gamePanel.gameMenu.yScale, 0.1f * (Main.game.window.getWidth() / 960), 2 + wave - 3, gamePanel, type1));
            toReturn.add(new Enemy((float) (0.52 * Main.game.window.getWidth()), 200 * gamePanel.gameMenu.yScale, 0.1f * (Main.game.window.getWidth() / 960), 1 + wave - 3, gamePanel, type2));
            toReturn.add(new Enemy((float) (0.31 * Main.game.window.getWidth()), 200 * gamePanel.gameMenu.yScale, 0.1f * (Main.game.window.getWidth() / 960), 1 + wave - 3, gamePanel, type2));
        }

        return toReturn;
    }
}
