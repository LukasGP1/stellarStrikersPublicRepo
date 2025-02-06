package de.lulkas_.stellarstrikers.level.projectile;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.enemys.Enemy;

import java.awt.*;
import java.util.List;

public class Bullet extends Entity {
    private final float travelDirectionY;
    private final float travelDirectionX;
    public final Entity firedBy;
    private final GameObjectHandler gameObjectHandler;

    public Bullet(float startX, float startY, float travelDirectionY, float travelDirectionX, Entity firedBy, GameObjectHandler gameObjectHandler, Color color) {
        super(3, 26, startX, startY, 1, gameObjectHandler);
        this.travelDirectionY = travelDirectionY;
        this.travelDirectionX = travelDirectionX;
        this.firedBy = firedBy;
        this.gameObjectHandler = gameObjectHandler;
    }

    public List<Float> getData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }

    public static final int UP = -1;
    public static final int DOWN = 1;

    public void tick() {
        this.gameY += this.travelDirectionY;
        this.gameX += this.travelDirectionX;
        if(this.gameY + travelDirectionY < 0 || this.gameY + travelDirectionY + 5 > 1000) {
            this.dead = true;
        }
        super.tick();
    }

    @Override
    public List<Entity> getCollideWith() {
        List<Entity> toReturn = new java.util.ArrayList<>(List.of());
        if(firedBy instanceof Enemy) {
            toReturn.add(gameObjectHandler.player);
        } else {
            toReturn.addAll(gameObjectHandler.enemyWaveHandler.enemies);
        }
        return toReturn;
    }

    @Override
    public void collideWith(Entity entity) {

    }
}
