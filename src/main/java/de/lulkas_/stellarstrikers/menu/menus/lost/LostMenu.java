package de.lulkas_.stellarstrikers.menu.menus.lost;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

import java.awt.*;

public class LostMenu extends Menu {
    public LostMenu(GameObjectHandler gameObjectHandler) {
        super(gameObjectHandler);
        addButton(new Button(new Rectangle(0, 0, 1000, 1000), () -> gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_MAIN_MENU));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new DisplayHandler();
    }
}
