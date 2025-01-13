package de.lulkas_.stellarstrikers.level.projectile;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.Textured;
import de.lulkas_.stellarstrikers.util.CoordConversion;

import java.awt.*;
import java.util.List;

public class Bomb extends Entity {
    private final float travelDirectionY;
    private final float travelDirectionX;
    public final Textured firedBy;
    private int ticks;
    public boolean detonated = false;
    private final GamePanel gamePanel;
    private final Color color;
    private final Color detonatedColor;

    public Bomb(float startX, float startY, GamePanel gamePanel, float travelDirectionY, float travelDirectionX, Textured firedBy, int ticks, GamePanel gamePanel1, Color color, Color detonatedColor) {
        super(36, 64, startX, startY, 1, gamePanel);
        this.travelDirectionY = travelDirectionY;
        this.travelDirectionX = travelDirectionX;
        this.firedBy = firedBy;
        this.ticks = ticks;
        this.gamePanel = gamePanel1;
        this.color = color;
        this.detonatedColor = detonatedColor;
    }

    @Override
    protected void updateHitbox() {
        if(detonated) {
            hitbox = new Rectangle(((int) (gameX - gameWidth / 2)), ((int) (gameY - gameHeight / 2)), ((int) (gameWidth * 1.5f)), ((int) (gameHeight * 1.5f)));
        } else {
            hitbox = new Rectangle((int) gameX, (int) gameY, ((int) (gameWidth / 8 * 3)), ((int) (gameHeight / 8 * 3)));
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
        return List.of(gamePanel.player);
    }

    @Override
    public void collideWith(Entity entity) {

    }

    @Override
    public Graphics draw(Graphics g) {
        g = super.draw(g);

        if(detonated) {
            g.setColor(detonatedColor);
            g.fillOval(((int) this.screenCoords[0]) - ((int) (screenSize[0] * 2f)) / 2 + ((int) (screenSize[0] / 8 * 3)) / 2, ((int) screenCoords[1]) - ((int) (screenSize[1] * 2f)) / 2 + ((int) (screenSize[1] / 8 * 3)) / 2, ((int) (screenSize[0] * 2f)), ((int) (screenSize[1] * 2f)));
        }
        g.setColor(color);
        g.fillRect((int) screenCoords[0], (int) screenCoords[1], ((int) (screenSize[0] / 8 * 3)), ((int) (screenSize[1] / 8 * 3)));
        return g;
    }
}
