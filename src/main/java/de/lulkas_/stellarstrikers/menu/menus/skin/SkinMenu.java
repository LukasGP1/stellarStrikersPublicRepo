package de.lulkas_.stellarstrikers.menu.menus.skin;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.TemporaryIntDisplay;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;

public class SkinMenu extends Menu {
    public SkinMenu(GamePanel gamePanel) {
        super("/assets/textures/menu/skin_menu.png", gamePanel);

        addButton(new Button(new Rectangle(280, 800, 439, 111), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(280, 189, 46, 56), () -> {
            gamePanel.skinDisplay.shiftLeft();
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(669, 191, 46, 56), () -> {
            gamePanel.skinDisplay.shiftRight();
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(280, 632, 439, 113), () -> {
            if(gamePanel.playerAttributeHandler.getLevel() >= gamePanel.skinDisplay.displayed) {
                gamePanel.playerAttributeHandler.setSkinDisplayed(gamePanel.skinDisplay.displayed);
                SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
            } else {
                ((TemporaryIntDisplay) displayHandler.displays.get(0)).appear(300);
                SoundHandler.playSound("/assets/sounds/menu/error.wav", -2f, gamePanel);
            }
        }));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new SkinDisplayHandler(gamePanel.skinDisplay);
    }
}
