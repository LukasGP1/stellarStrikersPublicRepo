package de.lulkas_.stellarstrikers.menu.menus.game;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

public class GameMenu extends Menu {
    public GameMenu(GameObjectHandler gameObjectHandler) {
        super(gameObjectHandler);
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new GameDisplayHandler(gameObjectHandler.player, gameObjectHandler.enemyHandler);
    }
}
