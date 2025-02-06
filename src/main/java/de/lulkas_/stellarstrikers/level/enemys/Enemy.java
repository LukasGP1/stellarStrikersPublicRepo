package de.lulkas_.stellarstrikers.level.enemys;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.projectile.Bullet;
import de.lulkas_.stellarstrikers.sound.SoundHandler;
import de.lulkas_.stellarstrikers.util.Random;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity {
    public List<Bullet> bullets = new ArrayList<>();
    private int bulletCooldown;
    private final float startX;
    private final float speed;
    private MovementState movementState = MovementState.LEFT;
    private final GameObjectHandler gameObjectHandler;
    private final EnemyType type;

    public Enemy(float startX, float startY, float speed, int health, GameObjectHandler gameObjectHandler, EnemyType type) {
        super(53, 78, startX, startY, health, gameObjectHandler);
        this.startX = startX;
        this.speed = speed;
        this.gameObjectHandler = gameObjectHandler;
        this.type = type;
        this.bulletCooldown = Random.randomInt(type.shootCooldown + 200, type.shootCooldown);
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
        this.bullets.add(new Bullet(this.gameX + ((int) (0.5 * gameWidth)), this.gameY + gameHeight, type.bulletSpeed, getBulletTravelDirectionX(), this, gameObjectHandler, gameObjectHandler.playerOptionsHandler.getEnemyBulletColor()));
        SoundHandler.playSound("/sounds/level/shoot_enemy.wav", -10f, gameObjectHandler);
    }

    public float getBulletTicks() {
        float bulletYDistance = gameObjectHandler.player.getGameY() - this.gameY - 64;
        return bulletYDistance / type.bulletSpeed;
    }

    public float getBulletTravelDirectionX() {
        if(type.aims) {
            float bulletXDistance = gameObjectHandler.player.getGameX() - this.gameX - 32;
            return bulletXDistance / getBulletTicks();
        } else {
            return 0;
        }
    }

    @Override
    public List<Entity> getCollideWith() {
        List<Entity> toReturn = new ArrayList<>(List.of());
        toReturn.addAll(gameObjectHandler.player.bullets);
        return toReturn;
    }

    @Override
    public void collideWith(Entity entity) {
        this.health -= gameObjectHandler.playerSkillHandler.getDamage();
        entity.dead = true;
        SoundHandler.playSound("/sounds/level/ding_break.wav", -2f, gameObjectHandler);
    }

    private void updateMovement() {
        if(this.movementState == MovementState.LEFT) {
            if(Math.abs(this.gameX - this.startX) < (float) (0.05 * Main.gameLoop.window.getWidth())) {
                this.gameX -= this.speed;
            } else {
                this.movementState = MovementState.RIGHT;
                this.gameX += this.speed;
            }
        } else {
            if(Math.abs(this.gameX - this.startX) < (float) (0.05 * Main.gameLoop.window.getWidth())) {
                this.gameX += this.speed;
            } else {
                this.movementState = MovementState.LEFT;
                this.gameX -= this.speed;
            }
        }
    }

    public List<?> getPosData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }

    public List<?> getMiscData() {
        return List.of(0, type.shaderValue, getDamageOverlay());
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

    private int getDamageOverlay() {
        float percentHealthLeft = ((float) this.health) / ((float) this.maxHealth);

        if(percentHealthLeft < 0.4f) {
            return 2;
        } else if (percentHealthLeft < 0.8f) {
            return 5;
        } else {
            return 12;
        }
    }

    public enum MovementState {
        RIGHT,
        LEFT
    }

    public enum EnemyType {
        NORMAL("/assets/textures/enemy/enemy.png", false, 3.0f, 300, 0),
        SNIPER("/assets/textures/enemy/sniper.png", true, 1.0f, 400, 1),
        GUNNER("/assets/textures/enemy/gunner.png", false, 3.0f, 100, 2);

        public final String texture;
        public final boolean aims;
        public final float bulletSpeed;
        public final int shootCooldown;
        public final int shaderValue;

        EnemyType(String s, boolean aims, float bulletSpeed, int shootCooldown, int shaderValue) {
            this.texture = s;
            this.aims = aims;
            this.bulletSpeed = bulletSpeed;
            this.shootCooldown = shootCooldown;
            this.shaderValue = shaderValue;
        }
    }
}
