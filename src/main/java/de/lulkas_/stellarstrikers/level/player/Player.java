package de.lulkas_.stellarstrikers.level.player;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.Main;
import de.lulkas_.stellarstrikers.attributes.PlayerSkillHandler;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.Textured;
import de.lulkas_.stellarstrikers.level.enemys.Boss;
import de.lulkas_.stellarstrikers.level.enemys.Enemy;
import de.lulkas_.stellarstrikers.level.projectile.Bullet;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends Textured {
    private final float speed;
    private GamePanel gamePanel;
    public List<Bullet> bullets = new ArrayList<>();
    private int bulletCooldown = 0;
    private final Image damage1;
    private final Image damage2;
    private final Image empty;
    public final boolean invincible;
    public PlayerSkillHandler playerSkillHandler;
    public int maxBulletCooldown = 500;

    public Player(float speed, GamePanel gamePanel, float startX, float startY, boolean invincible, PlayerSkillHandler playerSkillHandler, int skin) {
        super(startX, startY, "/textures/player/" + (skin + 1) + ".png", 64, 52, playerSkillHandler.getHealth(), gamePanel);
        this.speed = speed;
        this.gamePanel = gamePanel;
        this.invincible = invincible;
        this.playerSkillHandler = playerSkillHandler;
        damage1 = importImage("/textures/player/damage_overlay_1.png");
        damage2 = importImage("/textures/player/damage_overlay_2.png");
        empty = importImage("/textures/player/empty.png");
    }

    @Override
    public Graphics draw(Graphics g) {
        g = super.draw(g);
        g.drawImage(getDamageOverlay(), ((int) this.x), ((int) this.y), null);
        g = drawBullets(g);
        return g;
    }

    private Graphics drawBullets(Graphics g) {
        for (int i = 0; i < this.bullets.size(); i++) {
            g = this.bullets.get(i).draw(g);
        }
        return g;
    }

    @Override
    public void tick() {
        if(dead) {
            gamePanel.gameState = GamePanel.GameState.ENTER_LOST_MENU;
        }
        super.tick();

        this.maxBulletCooldown = gamePanel.playerPowerUpHandler.getMaxBulletCooldown();

        if(this.gamePanel.getKeyboardInputs().isaPressed() && this.x - this.speed >= 0){
            this.x -= this.speed;
        }

        if(this.gamePanel.getKeyboardInputs().isdPressed() && this.x + this.speed <= Main.game.window.getWidth() - 64){
            this.x += this.speed;
        }

        if(this.gamePanel.getKeyboardInputs().isSpacePressed() && this.bulletCooldown <= 0) {
            this.bullets.add(new Bullet(this.x + 32, this.y, -5.0f * gamePanel.gameMenu.yScale, 0, this, gamePanel, gamePanel.playerOptionsHandler.getPlayerBulletColor()));
            SoundHandler.playSound("/sounds/level/shoot_player.wav", -20f, gamePanel);
            this.bulletCooldown = maxBulletCooldown;
        }

        if(this.bulletCooldown != 0) {
            this.bulletCooldown--;
        }

        updateBullets();
    }

    @Override
    public List<Entity> getCollideWith() {
        List<Entity> toReturn = new ArrayList<>(List.of());
        for(Enemy enemy : gamePanel.enemyWaveHandler.enemies){
            toReturn.addAll(enemy.bullets);
        }
        for(Boss boss : gamePanel.enemyWaveHandler.bosses) {
            toReturn.addAll(boss.bombs);
        }
        return toReturn;
    }

    @Override
    public void collideWith(Entity entity) {
        if(!this.invincible) this.health -= 1;
        entity.dead = true;
        SoundHandler.playSound("/sounds/level/break.wav", -2f, gamePanel);
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
}
