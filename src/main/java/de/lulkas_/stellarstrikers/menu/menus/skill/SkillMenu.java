package de.lulkas_.stellarstrikers.menu.menus.skill;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.TemporaryDisplay;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;

public class SkillMenu extends Menu {
    public SkillMenu(GameObjectHandler gameObjectHandler) {
        super(gameObjectHandler);

        addButton(new Button(new Rectangle(280, 800, 439, 111), () -> {
            gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new Button(new Rectangle(281, 410, 124, 335), this::upgradeIncome));
        addButton(new Button(new Rectangle(436, 410, 124, 335), this::upgradeDamage));
        addButton(new Button(new Rectangle(593, 410, 124, 335), this::upgradeHealth));

        addButton(new Button(new Rectangle(280, 300, 439, 90), this::resetSkills));
    }

    private void resetSkills() {
        gameObjectHandler.playerAttributeHandler.resetLevelPoints();
        gameObjectHandler.playerAttributeHandler.gainLevelPoints(gameObjectHandler.playerAttributeHandler.getLevel());
        gameObjectHandler.playerSkillHandler.resetSkills();
        gameObjectHandler.saveWriter.save();
    }

    private void upgradeIncome() {
        if(gameObjectHandler.playerAttributeHandler.getLevelPoints() >= 2) {
            gameObjectHandler.playerSkillHandler.upgradeIncome(1);
            gameObjectHandler.playerAttributeHandler.spendLevelPoints(2);
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
            gameObjectHandler.saveWriter.save();
        } else {
            SoundHandler.playSound("/sounds/menu/error.wav", -2f, gameObjectHandler);
            ((TemporaryDisplay) displayHandler.displays.get(1)).appear(300);
        }
    }

    private void upgradeDamage() {
        if(gameObjectHandler.playerAttributeHandler.getLevelPoints() >= 2) {
            gameObjectHandler.playerSkillHandler.upgradeDamage(1);
            gameObjectHandler.playerAttributeHandler.spendLevelPoints(2);
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
            gameObjectHandler.saveWriter.save();
        } else {
            SoundHandler.playSound("/sounds/menu/error.wav", -2f, gameObjectHandler);
            ((TemporaryDisplay) displayHandler.displays.get(1)).appear(300);
        }
    }

    private void upgradeHealth() {
        if(gameObjectHandler.playerAttributeHandler.getLevelPoints() >= 2) {
            gameObjectHandler.playerSkillHandler.upgradeHealth(1);
            gameObjectHandler.playerAttributeHandler.spendLevelPoints(2);
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
            gameObjectHandler.saveWriter.save();
        } else {
            SoundHandler.playSound("/sounds/menu/error.wav", -2f, gameObjectHandler);
            ((TemporaryDisplay) displayHandler.displays.get(1)).appear(300);
        }
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new SkillDisplayHandler(gameObjectHandler.playerAttributeHandler);
    }
}
