package de.lulkas_.stellarstrikers.level;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.enemys.Boss;
import de.lulkas_.stellarstrikers.level.enemys.Enemy;

import java.awt.*;
import java.util.List;

public abstract class Entity {
    public Rectangle hitbox;
    protected final int gameWidth;
    protected final int gameHeight;
    protected float gameX;
    protected float gameY;
    public int health;
    public boolean dead = false;
    public final int maxHealth;
    protected final GameObjectHandler gameObjectHandler;
    private List<Entity> collideWith;

    public Entity(int gameWidth, int gameHeight, float startX, float startY, int health, GameObjectHandler gameObjectHandler) {
        this.gameX = startX;
        this.gameY = startY;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.gameObjectHandler = gameObjectHandler;
        this.hitbox = new Rectangle((int) gameX, (int) gameY, this.gameWidth, this.gameHeight);
        this.health = health;
        this.maxHealth = health;
    }

    public void setGameY(float gameY) {
        this.gameY = gameY;
    }

    public void setGameX(float gameX) {
        this.gameX = gameX;
    }

    protected void tick() {
        updateHitbox();
        if(this.health <= 0) {
            this.dead = true;
            if(this instanceof Enemy) {
                gameObjectHandler.enemyWaveHandler.score(gameObjectHandler.playerSkillHandler.getIncome());
                gameObjectHandler.playerStatisticsHandler.killEnemy();
                gameObjectHandler.powerUpHandler.killedEnemy(gameObjectHandler.enemyWaveHandler.wave);
            }

            if(this instanceof Boss) {
                gameObjectHandler.enemyWaveHandler.score(gameObjectHandler.playerSkillHandler.getIncome() + 4);
                gameObjectHandler.playerStatisticsHandler.killBoss();
                gameObjectHandler.powerUpHandler.killedEnemy(gameObjectHandler.enemyWaveHandler.wave);
            }
        }
        this.collideWith = getCollideWith();
        checkCollisions();
    }

    private void checkCollisions() {
        for(int i = 0; i < collideWith.size(); i++) {
            if(this.hitbox.intersects(collideWith.get(i).hitbox)) {
                collideWith(collideWith.get(i));
            }
        }
    }

    public abstract List<Entity> getCollideWith();

    public abstract void collideWith(Entity entity);

    protected void updateHitbox() {
        this.hitbox = new Rectangle((int) gameX, (int) gameY, this.gameWidth, this.gameHeight);
    }

    public float getGameX() {
        return gameX;
    }

    public float getGameY() {
        return gameY;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public int getGameWidth() {
        return gameWidth;
    }
}
