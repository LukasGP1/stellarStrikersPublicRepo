package de.lulkas_.stellarstrikers.attributes;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.display.TemporaryIntDisplay;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

public class PlayerAttributeHandler {
    public Integer score;
    private Integer level;
    public int levelingCooldown = 200;
    private int levelPoints;
    public Integer levelingCost;
    private Integer skinDisplayed;
    private final GamePanel gamePanel;
    private Thread apiThread1;
    private Thread apiThread2;

    public PlayerAttributeHandler(int score, int level, int levelPoints, int levelingCost, int skinDisplayed, GamePanel gamePanel) {
        this.score = score;
        this.level = level;
        this.levelPoints = levelPoints;
        this.levelingCost = levelingCost;
        this.skinDisplayed = skinDisplayed;
        this.gamePanel = gamePanel;
    }

    public Integer getSkinDisplayed() {
        return skinDisplayed;
    }

    public void setSkinDisplayed(Integer skinDisplayed) {
        if(skinDisplayed >= 0 && skinDisplayed <= 18) {
            this.skinDisplayed = skinDisplayed;
        }
    }

    public Integer getLevel() {
        return level;
    }

    public void levelUp() {
        score -= levelingCost;
        levelingCost += 5;
        level++;
        levelPoints++;
        SoundHandler.playSound("/assets/sounds/menu/level_up.wav", -20f, gamePanel);
        ((TemporaryIntDisplay) gamePanel.mainMenu.displayHandler.displays.get(2)).appear(1000);
        apiThread2 = new Thread(this::levelUpApi);
        apiThread2.start();
        apiThread1 = null;
        switch (level) {
            case 5 -> apiThread1 = new Thread(this::trophyLevel5);
            case 20 -> apiThread1 = new Thread(this::trophyLevel20);
            case 100 -> apiThread1 = new Thread(this::trophyLevel100);
            case 1000 -> apiThread1 = new Thread(this::trophyLevel1000);
        }
        if(apiThread1 != null) {
            apiThread1.start();
        }
    }

    private void levelUpApi() {
        gamePanel.api.addHighscore(968402, level.toString(), level);
        gamePanel.api.achieveTrophy(253100);
    }

    public void trophyLevel5() {
        gamePanel.api.achieveTrophy(253102);
    }
    public void trophyLevel20() {
        gamePanel.api.achieveTrophy(253129);
    }
    public void trophyLevel100() {
        gamePanel.api.achieveTrophy(253130);
    }
    public void trophyLevel1000() {
        gamePanel.api.achieveTrophy(253131);
    }

    public void tick() {
        if(levelingCooldown <= 0) {
            if(score >= levelingCost) {
                levelUp();
            }
            levelingCooldown = 200;
        }

        levelingCooldown--;
    }

    public Integer getLevelPoints() {
        return levelPoints;
    }

    public void spendLevelPoints(int amount) {
        levelPoints -= amount;
    }

    public void gainLevelPoints(int amount) {
        levelPoints += amount;
    }

    public void resetLevelPoints() {
        levelPoints = 0;
    }
}
