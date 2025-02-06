package de.lulkas_.stellarstrikers.level.projectile;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.Entity;

import java.awt.*;
import java.util.List;

public class Bomb extends Entity {
    private final float travelDirectionY;
    private final float travelDirectionX;
    public final Entity firedBy;
    private int ticks;
    public boolean detonated = false;
    private final GameObjectHandler gameObjectHandler;

    public Bomb(float startX, float startY, GameObjectHandler gameObjectHandler, float travelDirectionY, float travelDirectionX, Entity firedBy, int ticks) {
        super(36, 64, startX, startY, 1, gameObjectHandler);
        this.travelDirectionY = travelDirectionY;
        this.travelDirectionX = travelDirectionX;
        this.firedBy = firedBy;
        this.ticks = ticks;
        this.gameObjectHandler = gameObjectHandler;
    }

    @Override
    protected void updateHitbox() {
        if(detonated) {
            hitbox = new Rectangle(((int) (gameX - gameWidth / 2f)), ((int) (gameY - gameHeight / 2f)), ((int) (gameWidth * 1.5f)), ((int) (gameHeight * 1.5f)));
        } else {
            hitbox = new Rectangle((int) gameX, (int) gameY, gameWidth / 8 * 3, gameHeight / 8 * 3);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.health <= 0) {
            this.dead = true;
        }

        ticks--;
        if(!detonated) {
            if(ticks <= 0) {
                detonated = true;
                ticks = 200;
            } else {
                gameY += travelDirectionY;
                gameX += travelDirectionX;
            }
        } else {
            if(ticks <= 0) {
                dead = true;
            }
        }

        if(this.gameY < 0 || this.gameY > 960) {
            this.dead = true;
        }
    }

    @Override
    public List<Entity> getCollideWith() {
        return List.of(gameObjectHandler.player);
    }

    @Override
    public void collideWith(Entity entity) {

    }

    public List<?> getPosData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }

    public List<?> getMiscData() {
        return List.of(0, detonated ? 1 : 0);
    }
}
