package de.lulkas_.stellarstrikers.level;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.util.CoordConversion;
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
    protected float[] screenCoords;
    protected float[] screenSize;
    public int health;
    public boolean dead = false;
    public final int maxHealth;
    protected final GamePanel gamePanel;
    private List<Entity> collideWith;

    public Entity(int gameWidth, int gameHeight, float startX, float startY, int health, GamePanel gamePanel) {
        this.gameX = startX;
        this.gameY = startY;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.gamePanel = gamePanel;
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

    public Graphics draw(Graphics g) {
        screenCoords = CoordConversion.gameToScreen(new Float[]{this.gameX, this.gameY});
        screenSize = CoordConversion.gameToScreen(new Float[]{Float.valueOf(this.gameWidth), Float.valueOf(this.gameHeight)});
        return g;
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
