package de.lulkas_.stellarstrikers.level.powerUp;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.util.Random;

import java.util.ArrayList;
import java.util.List;

public class PowerUpHandler {
    public List<PowerUp> powerUps = new ArrayList<>();
    private GameObjectHandler gameObjectHandler;

    public PowerUpHandler(GameObjectHandler gameObjectHandler) {
        this.gameObjectHandler = gameObjectHandler;
    }

    public void tick() {
        for(int i = 0; i < powerUps.size(); i++){
            if(powerUps.get(i).dead) {
                powerUps.remove(i);
            }
        }

        for(int i = 0; i < powerUps.size(); i++){
            powerUps.get(i).tick();
        }
    }

    public void killedEnemy(int wave) {
        if(Random.randomChance(0.1f)) {
            if(Random.randomChance(0.33f)) {
                powerUps.add(new PowerUp(Random.randomInt(900, 0), 0, gameObjectHandler, wave, PowerUp.PowerUpType.DAMAGE, 1500));
            } else {
                if(Random.randomChance(0.9f)) {
                    powerUps.add(new PowerUp(Random.randomInt(900, 0), 0, gameObjectHandler, wave, PowerUp.PowerUpType.INCOME, 1500));
                } else {
                    powerUps.add(new PowerUp(Random.randomInt(900, 0), 0, gameObjectHandler, 10, PowerUp.PowerUpType.SHOOTING_SPEED, 1500));
                }
            }
        }
    }
}
