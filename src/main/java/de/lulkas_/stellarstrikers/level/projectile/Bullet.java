package de.lulkas_.stellarstrikers.level.projectile;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.Textured;
import de.lulkas_.stellarstrikers.level.enemys.Enemy;

import java.awt.*;
import java.util.List;

public class Bullet extends Entity {
    private final float travelDirectionY;
    private final float travelDirectionX;
    public final Textured firedBy;
    private final GamePanel gamePanel;
    private final Color color;

    public Bullet(float startX, float startY, float travelDirectionY, float travelDirectionX, Textured firedBy, GamePanel gamePanel, Color color) {
        super(3, 15, startX, startY, 1, gamePanel);
        this.travelDirectionY = travelDirectionY;
        this.travelDirectionX = travelDirectionX;
        this.firedBy = firedBy;
        this.gamePanel = gamePanel;
        this.color = color;
    }

    public static final int UP = -1;
    public static final int DOWN = 1;

    public Graphics draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, this.width, this.height);
        return g;
    }

    public void tick() {
        this.y += this.travelDirectionY;
        this.x += this.travelDirectionX;
        if(this.y < 0 || this.y > 960) {
            this.dead = true;
        }
        super.tick();
    }

    @Override
    public List<Entity> getCollideWith() {
        List<Entity> toReturn = new java.util.ArrayList<>(List.of());
        if(firedBy instanceof Enemy) {
            toReturn.add(gamePanel.player);
        } else {
            toReturn.addAll(gamePanel.enemyWaveHandler.enemies);
        }
        return toReturn;
    }

    @Override
    public void collideWith(Entity entity) {

    }
}
