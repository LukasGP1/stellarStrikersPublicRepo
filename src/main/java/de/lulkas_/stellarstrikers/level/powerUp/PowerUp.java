package de.lulkas_.stellarstrikers.level.powerUp;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.level.Textured;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.util.List;

public class PowerUp extends Textured {
    private final int amount;
    private final int ticks;
    private final PowerUpType type;

    public PowerUp(float startX, float startY, GamePanel gamePanel, int amount, PowerUpType type, int ticks) {
        super(startX, startY, type.texture, 50, 50, 1, gamePanel);
        this.amount = amount;
        this.ticks = ticks;
        this.type = type;
    }

    @Override
    public void tick() {
        super.tick();
        this.y += 0.02f * gamePanel.gameMenu.yScale;
    }

    @Override
    public List<Entity> getCollideWith() {
        List<Entity> toReturn = new java.util.ArrayList<>(List.of(gamePanel.player));
        toReturn.addAll(gamePanel.player.bullets);
        return toReturn;
    }

    @Override
    public void collideWith(Entity entity) {
        if(type == PowerUpType.DAMAGE) {
            gamePanel.playerPowerUpHandler.getDamagePowerUp(amount, ticks);
        }

        if(type == PowerUpType.INCOME) {
            gamePanel.playerPowerUpHandler.getIncomePowerUp(amount, ticks);
        }

        if(type == PowerUpType.SHOOTING_SPEED) {
            gamePanel.playerPowerUpHandler.getMaxBulletCooldownPowerUp(amount, ticks);
        }

        SoundHandler.playSound("/sounds/level/power_up/collect.wav", -2f, gamePanel);
        this.dead = true;
    }

    public enum PowerUpType {
        DAMAGE("/textures/power_up/damage.png"),
        SHOOTING_SPEED("/textures/power_up/shooting_speed.png"),
        INCOME("/textures/power_up/income.png");

        public final String texture;

        PowerUpType(String s) {
            texture = s;
        }
    }
}
