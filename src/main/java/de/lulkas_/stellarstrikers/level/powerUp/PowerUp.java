package de.lulkas_.stellarstrikers.level.powerUp;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.Entity;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.util.List;

public class PowerUp extends Entity {
    private final int amount;
    private final int ticks;
    private final PowerUpType type;

    public PowerUp(float startX, float startY, GameObjectHandler gameObjectHandler, int amount, PowerUpType type, int ticks) {
        super(45, 80, startX, startY, 1, gameObjectHandler);
        this.amount = amount;
        this.ticks = ticks;
        this.type = type;
    }

    @Override
    public void tick() {
        super.tick();
        this.gameY += 0.02f;
    }

    @Override
    public List<Entity> getCollideWith() {
        List<Entity> toReturn = new java.util.ArrayList<>(List.of(gameObjectHandler.player));
        toReturn.addAll(gameObjectHandler.player.bullets);
        return toReturn;
    }

    @Override
    public void collideWith(Entity entity) {
        if(type == PowerUpType.DAMAGE) {
            gameObjectHandler.playerPowerUpHandler.getDamagePowerUp(amount, ticks);
        }

        if(type == PowerUpType.INCOME) {
            gameObjectHandler.playerPowerUpHandler.getIncomePowerUp(amount, ticks);
        }

        if(type == PowerUpType.SHOOTING_SPEED) {
            gameObjectHandler.playerPowerUpHandler.getMaxBulletCooldownPowerUp(amount, ticks);
        }

        SoundHandler.playSound("/sounds/level/power_up/collect.wav", -2f, gameObjectHandler);
        this.dead = true;
    }

    public List<?> getData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, type.shaderEncode);
    }

    public enum PowerUpType {
        DAMAGE("/assets/textures/power_up/damage.png", 0f),
        SHOOTING_SPEED("/assets/textures/power_up/shooting_speed.png", 0.5f),
        INCOME("/assets/textures/power_up/income.png", 1.0f);

        public final String texture;

        public final float shaderEncode;

        PowerUpType(String s, float shaderEncode) {
            texture = s;
            this.shaderEncode = shaderEncode;
        }
    }
}
