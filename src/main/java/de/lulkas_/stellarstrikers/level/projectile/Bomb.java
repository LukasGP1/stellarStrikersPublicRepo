package de.lulkas_.stellarstrikers.level.projectile;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.Textured;

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
        super(40, 40, startX, startY, 1, gamePanel);
        this.travelDirectionY = travelDirectionY;
        this.travelDirectionX = travelDirectionX;
        this.firedBy = firedBy;
        this.ticks = ticks;
        this.gamePanel = gamePanel1;
        this.color = color;
        this.detonatedColor = detonatedColor;
    }

    private void updateHitboxNew() {
        this.hitbox = new Rectangle(((int) (x - (this.width / 2))), ((int) (y - (this.height / 2))), this.width, this.height);
    }

    @Override
    public void tick() {
        updateHitboxNew();

        if(this.health <= 0) {
            this.dead = true;
        }

        ticks--;
        if(!detonated) {
            if(ticks <= 0) {
                detonated = true;
                ticks = 2000;
            } else {
                y += travelDirectionY;
                x += travelDirectionX;
            }
        } else {
            if(ticks <= 0) {
                dead = true;
            }
        }

        if(this.y < 0 || this.y > 960) {
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

    public Graphics draw(Graphics g) {
        if(detonated) {
            g.setColor(detonatedColor);
            g.fillOval(((int) x) - 33, ((int) y) - 33, 80, 80);
        }
        g.setColor(color);
        g.fillRect((int) x, (int) y, 15, 15);
        return g;
    }
}
