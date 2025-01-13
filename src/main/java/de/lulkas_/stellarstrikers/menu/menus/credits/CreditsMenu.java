package de.lulkas_.stellarstrikers.menu.menus.credits;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;

public class CreditsMenu extends Menu {
    public CreditsMenu(GamePanel gamePanel) {
        super("/assets/textures/menu/credits_menu.png", gamePanel);

        addButton(new Button(new Rectangle(280, 800, 439, 111), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new CreditsDisplayHandler();
    }
}
