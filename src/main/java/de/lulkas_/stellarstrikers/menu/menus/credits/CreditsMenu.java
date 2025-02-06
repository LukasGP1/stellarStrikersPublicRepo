package de.lulkas_.stellarstrikers.menu.menus.credits;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;

public class CreditsMenu extends Menu {
    public CreditsMenu(GameObjectHandler gameObjectHandler) {
        super(gameObjectHandler);

        addButton(new Button(new Rectangle(280, 800, 439, 111), () -> {
            gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new CreditsDisplayHandler();
    }
}
