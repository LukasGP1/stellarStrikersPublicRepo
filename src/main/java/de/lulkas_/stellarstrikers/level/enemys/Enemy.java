package de.lulkas_.stellarstrikers.level.enemys;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.Textured;
import de.lulkas_.stellarstrikers.level.projectile.Bullet;
import de.lulkas_.stellarstrikers.sound.SoundHandler;
import de.lulkas_.stellarstrikers.util.Random;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Textured {
    public List<Bullet> bullets = new ArrayList<>();
    private int bulletCooldown;
    private final float startX, startY;
    private final float speed;
    private MovementState movementState = MovementState.LEFT;
    private final Image damage1;
    private final Image damage2;
    private final Image empty;
    private final GamePanel gamePanel;
    private final EnemyType type;

    public Enemy(float startX, float startY, float speed, int health, GamePanel gamePanel, EnemyType type) {
        super(startX, startY, type.texture, 64, 52, health, gamePanel);
        this.startX = startX;
        this.startY = startY;
        this.speed = speed;
        this.gamePanel = gamePanel;
        this.type = type;
        this.bulletCooldown = Random.randomInt(type.shootCooldown + 200, type.shootCooldown);
        damage1 = importImage("/textures/enemy/damage_overlay_1.png");
        damage2 = importImage("/textures/enemy/damage_overlay_2.png");
        empty = importImage("/textures/enemy/empty.png");
    }

    @Override
    public void tick() {
        super.tick();

        updateBullets();

        if(this.bulletCooldown == 0) {
            shoot();
            this.bulletCooldown = Random.randomInt(type.shootCooldown + 2000, type.shootCooldown);
        } else {
            bulletCooldown--;
        }

        updateMovement();
    }

    public void shoot() {
        this.bullets.add(new Bullet(this.x + 32, this.y + 64, type.bulletSpeed * gamePanel.gameMenu.yScale, getBulletTravelDirectionX(), this, gamePanel, gamePanel.playerOptionsHandler.getEnemyBulletColor()));
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
        if(this.movementState == MovementState.LEFT) {
            if(Math.abs(this.x - this.startX) < (float) (0.05 * Main.game.window.getWidth())) {
                this.x -= this.speed;
            } else {
                this.movementState = MovementState.RIGHT;
                this.x += this.speed;
            }
        } else {
            if(Math.abs(this.x - this.startX) < (float) (0.05 * Main.game.window.getWidth())) {
                this.x += this.speed;
            } else {
                this.movementState = MovementState.LEFT;
                this.x -= this.speed;
            }
        }
    }

    @Override
    public Graphics draw(Graphics g) {
        g = super.draw(g);
        g.drawImage(getDamageOverlay(), ((int) x), ((int) y), null);
        g = drawBullets(g);
        return g;
    }

    private Graphics drawBullets(Graphics g) {
        for (int i = 0; i < this.bullets.size(); i++) {
            g = this.bullets.get(i).draw(g);
        }
        return g;
    }

    private void updateBullets() {
        List<Integer> toRemove = new ArrayList<>();
        for(int i = 0; i < this.bullets.size(); i++) {
            this.bullets.get(i).tick();
            if(this.bullets.get(i).dead) {
                toRemove.add(i);
            }
        }
        for(int i = 0; i < toRemove.size(); i++) {
            this.bullets.remove(i);
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

    public enum MovementState {
        RIGHT,
        LEFT
    }

    public enum EnemyType {
        NORMAL("/textures/enemy/enemy.png", false, 3.0f, 300),
        SNIPER("/textures/enemy/sniper.png", true, 1.0f, 400),
        GUNNER("/textures/enemy/gunner.png", false, 3.0f, 100);

        public final String texture;
        public final boolean aims;
        public final float bulletSpeed;
        public final int shootCooldown;

        EnemyType(String s, boolean aims, float bulletSpeed, int shootCooldown) {
            this.texture = s;
            this.aims = aims;
            this.bulletSpeed = bulletSpeed;
            this.shootCooldown = shootCooldown;
        }
    }
}
