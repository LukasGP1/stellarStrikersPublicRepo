package de.lulkas_.stellarstrikers.menu.menus.skill;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.TemporaryDisplay;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;

public class SkillMenu extends Menu {
    public SkillMenu(GamePanel gamePanel) {
        super("/assets/textures/menu/skill_menu.png", gamePanel);

        addButton(new Button(new Rectangle(280, 800, 439, 111), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(281, 410, 124, 335), this::upgradeIncome));
        addButton(new Button(new Rectangle(436, 410, 124, 335), this::upgradeDamage));
        addButton(new Button(new Rectangle(593, 410, 124, 335), this::upgradeHealth));

        addButton(new Button(new Rectangle(280, 300, 439, 90), this::resetSkills));
    }

    private void resetSkills() {
        gamePanel.playerAttributeHandler.resetLevelPoints();
        gamePanel.playerAttributeHandler.gainLevelPoints(gamePanel.playerAttributeHandler.getLevel());
        gamePanel.playerSkillHandler.resetSkills();
        gamePanel.saveWriter.save();
    }

    private void upgradeIncome() {
        if(gamePanel.playerAttributeHandler.getLevelPoints() >= 2) {
            gamePanel.playerSkillHandler.upgradeIncome(1);
            gamePanel.playerAttributeHandler.spendLevelPoints(2);
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
            gamePanel.saveWriter.save();
        } else {
            SoundHandler.playSound("/assets/sounds/menu/error.wav", -2f, gamePanel);
            ((TemporaryDisplay) displayHandler.displays.get(1)).appear(300);
        }
    }

    private void upgradeDamage() {
        if(gamePanel.playerAttributeHandler.getLevelPoints() >= 2) {
            gamePanel.playerSkillHandler.upgradeDamage(1);
            gamePanel.playerAttributeHandler.spendLevelPoints(2);
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
            gamePanel.saveWriter.save();
        } else {
            SoundHandler.playSound("/assets/sounds/menu/error.wav", -2f, gamePanel);
            ((TemporaryDisplay) displayHandler.displays.get(1)).appear(300);
        }
    }

    private void upgradeHealth() {
        if(gamePanel.playerAttributeHandler.getLevelPoints() >= 2) {
            gamePanel.playerSkillHandler.upgradeHealth(1);
            gamePanel.playerAttributeHandler.spendLevelPoints(2);
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
            gamePanel.saveWriter.save();
        } else {
            SoundHandler.playSound("/assets/sounds/menu/error.wav", -2f, gamePanel);
            ((TemporaryDisplay) displayHandler.displays.get(1)).appear(300);
        }
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new SkillDisplayHandler(gamePanel.playerAttributeHandler);
    }
}
