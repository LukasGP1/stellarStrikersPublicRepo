package de.lulkas_.stellarstrikers.level.powerUp;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.util.Random;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PowerUpHandler {
    private List<PowerUp> powerUps = new ArrayList<>();
    private GamePanel gamePanel;

    public PowerUpHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

    public Graphics draw(Graphics g) {
        for(int i = 0; i < powerUps.size(); i++){
            g = powerUps.get(i).draw(g);
        }
        return g;
    }

    public void killedEnemy(int maxHealth, int wave) {
        if(Random.randomChance(0.1f)) {
            if(Random.randomChance(0.33f)) {
                powerUps.add(new PowerUp(Random.randomInt(900, 0), 0, gamePanel, wave, PowerUp.PowerUpType.DAMAGE, maxHealth * 500));
            } else {
                if(Random.randomChance(0.5f)) {
                    powerUps.add(new PowerUp(Random.randomInt(900, 0), 0, gamePanel, wave, PowerUp.PowerUpType.INCOME, maxHealth * 500));
                } else {
                    powerUps.add(new PowerUp(Random.randomInt(900, 0), 0, gamePanel, 25, PowerUp.PowerUpType.SHOOTING_SPEED, maxHealth * 500));
                }
            }
        }
    }
}
