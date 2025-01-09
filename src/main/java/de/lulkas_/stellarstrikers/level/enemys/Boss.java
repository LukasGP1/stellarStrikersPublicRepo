package de.lulkas_.stellarstrikers.level.enemys;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.Textured;
import de.lulkas_.stellarstrikers.level.projectile.Bomb;
import de.lulkas_.stellarstrikers.sound.SoundHandler;
import de.lulkas_.stellarstrikers.util.Random;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Boss extends Textured {
    public List<Bomb> bombs = new ArrayList<>();
    private int bombCooldown;
    private final float startX, startY;
    private final float speed;
    private Enemy.MovementState movementState = Enemy.MovementState.LEFT;
    private final Image damage1;
    private final Image damage2;
    private final Image empty;
    private final GamePanel gamePanel;
    private final BossType type;

    public Boss(float startX, float startY, int health, float speed, GamePanel gamePanel, BossType type) {
        super(startX, startY, type.texture, 200, 200, health, gamePanel);
        this.startX = startX;
        this.startY = startY;
        this.gamePanel = gamePanel;
        this.speed = speed;
        this.type = type;
        this.bombCooldown = type.bombCooldown;
        damage1 = importImage("/textures/enemy/damage_overlay_1.png");
        damage2 = importImage("/textures/enemy/damage_overlay_2.png");
        empty = importImage("/textures/enemy/empty.png");
    }

    @Override
    public void tick() {
        super.tick();

        updateBombs();

        if(this.bombCooldown <= 0) {
            shoot();
            this.bombCooldown = Random.randomInt(type.bombCooldown + 200, type.bombCooldown - 200);
        } else {
            bombCooldown--;
        }

        updateMovement();
    }

    private void shoot() {
        this.bombs.add(new Bomb(x + 100, y + 100, gamePanel, type.bulletSpeed * gamePanel.gameMenu.yScale, getBulletTravelDirectionX(), this, ((int) getBulletTicks()), gamePanel, gamePanel.playerOptionsHandler.getBombColor(), gamePanel.playerOptionsHandler.getDetonatedBombColor()));
        SoundHandler.playSound("/sounds/level/shoot_enemy.wav", -10f, gamePanel);
    }

    public float getBulletTicks() {
        float bulletYDistance = gamePanel.player.getY() - this.y - 64;
        return bulletYDistance / type.bulletSpeed;
    }

    public float getBulletTravelDirectionX() {
        if(type.aims) {
            float bulletXDistance = gamePanel.player.getX() - this.x - 32;
            return bulletXDistance / getBulletTicks();
        } else {
            return 0;
        }
    }

    @Override
    public List<Entity> getCollideWith() {
        List<Entity> toReturn = new ArrayList<>(List.of());
        toReturn.addAll(gamePanel.player.bullets);
        return toReturn;
    }

    @Override
    public void collideWith(Entity entity) {
        this.health -= gamePanel.playerSkillHandler.getDamage();
        entity.dead = true;
        SoundHandler.playSound("/sounds/level/ding_break.wav", -2f, gamePanel);
    }

    private void updateMovement() {
        if(this.movementState == Enemy.MovementState.LEFT) {
            if(Math.abs(this.x - this.startX) < (float) (0.42 * Main.game.window.getWidth())) {
                this.x -= this.speed;
            } else {
                this.movementState = Enemy.MovementState.RIGHT;
                this.x += this.speed;
            }
        } else {
            if(Math.abs(this.x - this.startX) < (float) (0.42 * Main.game.window.getWidth())) {
                this.x += this.speed;
            } else {
                this.movementState = Enemy.MovementState.LEFT;
                this.x -= this.speed;
            }
        }
    }

    @Override
    public Graphics draw(Graphics g) {
        g = super.draw(g);
        g.drawImage(getDamageOverlay(), ((int) x), ((int) y), null);
        g = drawBombs(g);
        return g;
    }

    private Graphics drawBombs(Graphics g) {
        for (int i = 0; i < this.bombs.size(); i++) {
            g = this.bombs.get(i).draw(g);
        }
        return g;
    }

    private void updateBombs() {
        List<Integer> toRemove = new ArrayList<>();
        for(int i = 0; i < this.bombs.size(); i++) {
            this.bombs.get(i).tick();
            if(this.bombs.get(i).dead) {
                toRemove.add(i);
            }
        }
        for(int i = 0; i < toRemove.size(); i++) {
            this.bombs.remove(i);
        }
    }

    private Image getDamageOverlay() {
        float percentHealthLeft = ((float) this.health) / ((float) this.maxHealth);

        if(percentHealthLeft < 0.4f) {
            return damage2;
        } else if (percentHealthLeft < 0.8f) {
            return damage1;
        } else {
            return empty;
        }
    }

    public enum BossType {
        NORMAL("/textures/enemy/enemy.png", false, 1.0f, 150),
        SNIPER("/textures/enemy/sniper.png", true, 1.0f, 250),
        GUNNER("/textures/enemy/gunner.png", false, 1.0f, 100);

        public final String texture;
        public final boolean aims;
        public final float bulletSpeed;
        public final int bombCooldown;

        BossType(String texture, boolean aims, float bulletSpeed, int bombCooldown) {
            this.texture = texture;
            this.aims = aims;
            this.bulletSpeed = bulletSpeed;
            this.bombCooldown = bombCooldown;
        }
    }
}
