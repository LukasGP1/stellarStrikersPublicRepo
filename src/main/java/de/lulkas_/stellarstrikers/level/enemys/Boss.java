package de.lulkas_.stellarstrikers.level.enemys;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.projectile.Bomb;
import de.lulkas_.stellarstrikers.sound.SoundHandler;
import de.lulkas_.stellarstrikers.util.Random;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Entity {
    public List<Bomb> bombs = new ArrayList<>();
    private int bombCooldown;
    private final float startX;
    private final float speed;
    private Enemy.MovementState movementState = Enemy.MovementState.LEFT;
    private final GameObjectHandler gameObjectHandler;
    private final BossType type;

    public Boss(float startX, float startY, int health, float speed, GameObjectHandler gameObjectHandler, BossType type) {
        super(159, 234, startX, startY, health, gameObjectHandler);
        this.startX = startX;
        this.gameObjectHandler = gameObjectHandler;
        this.speed = speed;
        this.type = type;
        this.bombCooldown = type.bombCooldown;
    }

    @Override
    public void tick() {
        super.tick();
        updateBombs();

        if(this.bombCooldown <= 0) {
            shoot();
            this.bombCooldown = Random.randomInt(type.bombCooldown + 100, type.bombCooldown - 100);
        } else {
            bombCooldown--;
        }

        updateMovement();
    }

    private void shoot() {
        this.bombs.add(new Bomb(gameX + gameWidth / 2f, gameY + gameHeight, gameObjectHandler, type.bulletSpeed, getBulletTravelDirectionX(), this, ((int) getBulletTicks())));
        SoundHandler.playSound("/sounds/level/shoot_enemy.wav", -10f, gameObjectHandler);
    }

    public float getBulletTicks() {
        float bombYDistance = gameObjectHandler.player.getGameY() - this.gameY - this.gameHeight + gameObjectHandler.player.getGameHeight();
        return bombYDistance / type.bulletSpeed;
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
        if(this.movementState == Enemy.MovementState.LEFT) {
            if(Math.abs(this.gameX - this.startX) < 300) {
                this.gameX -= this.speed;
            } else {
                this.movementState = Enemy.MovementState.RIGHT;
                this.gameX += this.speed;
            }
        } else {
            if(Math.abs(this.gameX - this.startX) < 300) {
                this.gameX += this.speed;
            } else {
                this.movementState = Enemy.MovementState.LEFT;
                this.gameX -= this.speed;
            }
        }
    }

    public List<?> getPosData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }

    public List<?> getMiscData() {
        return List.of(0f, type.shaderValue, getDamageOverlay());
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

    public float getDamageOverlay() {
        float percentHealthLeft = ((float) this.health) / ((float) this.maxHealth);

        if(percentHealthLeft < 0.4f) {
            return 0.1f;
        } else if (percentHealthLeft < 0.8f) {
            return 0.5f;
        } else {
            return 1.2f;
        }
    }

    public enum BossType {
        NORMAL("/assets/textures/enemy/enemy.png", false, 1.0f, 150, 0f),
        SNIPER("/assets/textures/enemy/sniper.png", true, 1.0f, 250, 0.5f),
        GUNNER("/assets/textures/enemy/gunner.png", false, 1.0f, 100, 1f);

        public final String texture;
        public final boolean aims;
        public final float bulletSpeed;
        public final int bombCooldown;
        public final float shaderValue;

        BossType(String texture, boolean aims, float bulletSpeed, int bombCooldown, float shaderValue) {
            this.texture = texture;
            this.aims = aims;
            this.bulletSpeed = bulletSpeed;
            this.bombCooldown = bombCooldown;
            this.shaderValue = shaderValue;
        }
    }
}
