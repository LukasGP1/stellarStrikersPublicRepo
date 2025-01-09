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
        super("/textures/menu/skin_menu.png", gamePanel);

        addButton(new Button(new Rectangle(130, 750, 700, 100), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(130, 200, 70, 50), () -> {
            gamePanel.skinDisplay.shiftLeft();
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(750, 200, 70, 50), () -> {
            gamePanel.skinDisplay.shiftRight();
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(130, 600, 700, 100), () -> {
            if(gamePanel.playerAttributeHandler.getLevel() >= gamePanel.skinDisplay.displayed) {
                gamePanel.playerAttributeHandler.setSkinDisplayed(gamePanel.skinDisplay.displayed);
                SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
            } else {
                ((TemporaryIntDisplay) displayHandler.displays.get(0)).appear(300);
                SoundHandler.playSound("/sounds/menu/error.wav", -2f, gamePanel);
            }
        }));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new SkinDisplayHandler(gamePanel.skinDisplay);
    }
}
