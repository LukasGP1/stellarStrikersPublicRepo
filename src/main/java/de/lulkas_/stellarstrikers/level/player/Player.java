package de.lulkas_.stellarstrikers.level.player;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.MovingEntity;
import de.lulkas_.stellarstrikers.playerData.PlayerSkillHandler;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.enemys.Boss;
import de.lulkas_.stellarstrikers.level.enemys.Enemy;
import de.lulkas_.stellarstrikers.level.projectile.Bullet;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.util.ArrayList;
import java.util.List;

public class Player extends MovingEntity {
    private final float SPEED;
    private GameObjectHandler gameObjectHandler;
    public List<Bullet> bullets = new ArrayList<>();
    private int bulletCooldown = 0;
    public final boolean invincible;
    public PlayerSkillHandler playerSkillHandler;
    public int maxBulletCooldown = 500;
    private boolean wasSpacePressed = false;

    public Player(float speed, GameObjectHandler gameObjectHandler, float startX, float startY, boolean invincible, PlayerSkillHandler playerSkillHandler) {
        super(53, 78, startX, startY, playerSkillHandler.getHealth(), gameObjectHandler, .95f, 1);
        this.SPEED = speed;
        this.gameObjectHandler = gameObjectHandler;
        this.invincible = invincible;
        this.playerSkillHandler = playerSkillHandler;
    }

    @Override
    public void tick() {
        if(dead) {
            gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_LOST_MENU;
        }
        super.tick();

        if(this.gameObjectHandler.getKeyboardInputs().isaPressed() && this.gameX - this.SPEED >= 0){
            this.velocities[0] -= this.SPEED;
        }
        if(this.gameObjectHandler.getKeyboardInputs().isdPressed() && this.gameX + this.SPEED <= 930){
            this.velocities[0] += this.SPEED;
        }

        this.maxBulletCooldown = gameObjectHandler.playerPowerUpHandler.getMaxBulletCooldown();
        boolean isSpacePressed = this.gameObjectHandler.getKeyboardInputs().isSpacePressed();
        if(isSpacePressed && this.bulletCooldown <= 0 && !wasSpacePressed) {
            this.bullets.add(new Bullet(this.gameX + this.gameWidth / 2f, this.gameY, -5.0f, 0, this, gameObjectHandler, gameObjectHandler.playerOptionsHandler.getPlayerBulletColor()));
            SoundHandler.playSound("/sounds/level/shoot_player.wav", -20f, gameObjectHandler);
            this.bulletCooldown = maxBulletCooldown;
        }
        if(this.bulletCooldown != 0) {
            this.bulletCooldown--;
        }
        updateBullets();
        wasSpacePressed = isSpacePressed;
    }

    @Override
    public List<Entity> getCollideWith() {
        List<Entity> toReturn = new ArrayList<>(List.of());
        for(Enemy enemy : gameObjectHandler.enemyHandler.enemies){
            toReturn.addAll(enemy.bullets);
        }
        for(Boss boss : gameObjectHandler.enemyHandler.bosses) {
            toReturn.addAll(boss.bombs);
        }
        return toReturn;
    }

    @Override
    public void collideWith(Entity entity) {
        if(!this.invincible && !entity.dead) this.health -= 1;
        entity.dead = true;
        SoundHandler.playSound("/sounds/level/break.wav", -2f, gameObjectHandler);
    }

    private void updateBullets() {
        for (int i = 0; i < this.bullets.size(); i++) {
            if(this.bullets.get(i).dead) {
                bullets.remove(i);
            }
        }

        for(int i = 0; i < this.bullets.size(); i++) {
            this.bullets.get(i).tick();
        }
    }

    public float getDamageOverlay() {
        float percentHealthLeft = ((float) this.health) / ((float) this.maxHealth);

        if(percentHealthLeft < 0.4f) {
            return 0.3f;
        } else if (percentHealthLeft < 0.8f) {
            return 0.8f;
        } else {
            return 1.2f;
        }
    }
}
