package de.lulkas_.stellarstrikers.menu.menus.won;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

import java.awt.*;

public class WonMenu extends Menu {
    public WonMenu(GameObjectHandler gameObjectHandler) {
        super(gameObjectHandler);
        addButton(new Button(new Rectangle(0, 0, 1000, 1000), () -> gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_MAIN_MENU));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new WonDisplayHandler(gameObjectHandler.gameTimer, gameObjectHandler.enemyWaveHandler);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
