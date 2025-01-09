package de.lulkas_.stellarstrikers.level;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.level.enemys.Boss;
import de.lulkas_.stellarstrikers.level.enemys.Enemy;

import java.awt.*;
import java.util.List;

public abstract class Entity {
    public Rectangle hitbox;
    protected final int width;
    protected final int height;
    protected float x;
    protected float y;
    public int health;
    public boolean dead = false;
    public final int maxHealth;
    protected final GamePanel gamePanel;
    private List<Entity> collideWith;

    public Entity(int width, int height, float startX, float startY, int health, GamePanel gamePanel) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.gamePanel = gamePanel;
        this.hitbox = new Rectangle((int) x, (int) y, this.width, this.height);
        this.health = health;
        this.maxHealth = health;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    protected void tick() {
        updateHitbox();
        if(this.health <= 0) {
            this.dead = true;
            if(this instanceof Enemy) {
                gamePanel.enemyWaveHandler.score(gamePanel.playerSkillHandler.getIncome());
                gamePanel.playerStatisticsHandler.killEnemy();
                gamePanel.powerUpHandler.killedEnemy(maxHealth, gamePanel.enemyWaveHandler.wave);
            }

            if(this instanceof Boss) {
                gamePanel.enemyWaveHandler.score(gamePanel.playerSkillHandler.getIncome() + 4);
                gamePanel.playerStatisticsHandler.killBoss();
                gamePanel.powerUpHandler.killedEnemy(maxHealth, gamePanel.enemyWaveHandler.wave);
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
        this.hitbox = new Rectangle((int) x, (int) y, this.width, this.height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
