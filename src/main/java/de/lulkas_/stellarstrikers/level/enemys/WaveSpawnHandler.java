package de.lulkas_.stellarstrikers.level.enemys;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.LevelFileFormatException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WaveSpawnHandler {
    public static void spawnWave(int wave, GameObjectHandler gameObjectHandler, EnemyWaveHandler enemyWaveHandler) {
        List<Entity> enemies;
        try {
            enemies = readWave(wave, gameObjectHandler);
            System.out.println("from file");
        } catch (NullPointerException e) {
            enemies = generateWave(wave, gameObjectHandler);
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

    private static List<Entity> readWave(int wave, GameObjectHandler gameObjectHandler) {
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

                float x = Float.parseFloat(enemy.substring(firstReadIndex, lastReadIndex));

                firstReadIndex = lastReadIndex + 1;
                lastReadIndex = firstReadIndex;

                while(enemy.charAt(lastReadIndex) != ';') {
                    lastReadIndex++;
                }

                float y = Float.parseFloat(enemy.substring(firstReadIndex, lastReadIndex));

                firstReadIndex = lastReadIndex + 1;
                lastReadIndex = firstReadIndex;

                while(enemy.charAt(lastReadIndex) != ';') {
                    lastReadIndex++;
                }

                float speed = Float.parseFloat(enemy.substring(firstReadIndex, lastReadIndex));

                firstReadIndex = lastReadIndex + 1;
                lastReadIndex = firstReadIndex;

                while(enemy.charAt(lastReadIndex) != ';') {
                    lastReadIndex++;
                }

                float health = Float.parseFloat(enemy.substring(firstReadIndex, lastReadIndex));

                if(enemy.charAt(0) == 'e') {
                    if(enemy.charAt(2) == 'N') {
                        toReturn.add(new Enemy(x, y, speed, ((int) health), gameObjectHandler, Enemy.EnemyType.NORMAL));
                    } else if(enemy.charAt(2) == 'S') {
                        toReturn.add(new Enemy(x, y, speed, ((int) health), gameObjectHandler, Enemy.EnemyType.SNIPER));
                    } else if (enemy.charAt(2) == 'G') {
                        toReturn.add(new Enemy(x, y, speed, ((int) health), gameObjectHandler, Enemy.EnemyType.GUNNER));
                    } else {
                        throw new LevelFileFormatException();
                    }
                } else if(enemy.charAt(0) == 'b') {
                    if(enemy.charAt(2) == 'N') {
                        toReturn.add(new Boss(x, y, ((int) health), speed, gameObjectHandler, Boss.BossType.NORMAL));
                    } else if(enemy.charAt(2) == 'S') {
                        toReturn.add(new Boss(x, y, ((int) health), speed, gameObjectHandler, Boss.BossType.SNIPER));
                    } else if (enemy.charAt(2) == 'G') {
                        toReturn.add(new Boss(x, y, ((int) health), speed, gameObjectHandler, Boss.BossType.GUNNER));
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

    private static List<Entity> generateWave(int wave, GameObjectHandler gameObjectHandler) {
        List<Entity> toReturn = new ArrayList<>();

        if(((int) (wave / 5)) * 5 == wave) {
            Boss.BossType type = Boss.BossType.NORMAL;

            if(wave > 5) {
                type = Boss.BossType.SNIPER;
            }

            if(wave > 10) {
                type = Boss.BossType.GUNNER;
            }

            toReturn.add(new Boss(400, 300, (wave / 5) * 25, 3.0f, gameObjectHandler, type));
        } else {
            Enemy.EnemyType type1 = Enemy.EnemyType.NORMAL;
            Enemy.EnemyType type2 = Enemy.EnemyType.NORMAL;
            if(wave > 5) {
                type2 = Enemy.EnemyType.SNIPER;
            }
            if(wave > 10) {
                type1 = Enemy.EnemyType.GUNNER;
            }
            toReturn.add(new Enemy(200, 400, 0.1f, 2 + wave - 3, gameObjectHandler, type1));
            toReturn.add(new Enemy(420, 400, 0.1f, 3 + wave - 3, gameObjectHandler, type1));
            toReturn.add(new Enemy(630, 400, 0.1f, 2 + wave - 3, gameObjectHandler, type1));
            toReturn.add(new Enemy(520, 200, 0.1f, 1 + wave - 3, gameObjectHandler, type2));
            toReturn.add(new Enemy(310, 200, 0.1f, 1 + wave - 3, gameObjectHandler, type2));
        }

        return toReturn;
    }
}
