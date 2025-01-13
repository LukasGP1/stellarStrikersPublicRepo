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
        super(3, 26, startX, startY, 1, gamePanel);
        this.travelDirectionY = travelDirectionY;
        this.travelDirectionX = travelDirectionX;
        this.firedBy = firedBy;
        this.gamePanel = gamePanel;
        this.color = color;
    }

    public static final int UP = -1;
    public static final int DOWN = 1;

    @Override
    public Graphics draw(Graphics g) {
        g = super.draw(g);
        g.setColor(color);
        g.fillRect((int) screenCoords[0], (int) screenCoords[1], ((int) screenSize[0]), ((int) screenSize[1]));
        return g;
    }

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
