package de.lulkas_.stellarstrikers.menu.menus.won;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

import java.awt.*;

public class WonMenu extends Menu {
    public WonMenu(GamePanel gamePanel) {
        super("/assets/textures/menu/won_menu.png", gamePanel);
        addButton(new Button(new Rectangle(0, 0, 1000, 1000), () -> gamePanel.gameState = GamePanel.GameState.ENTER_MAIN_MENU));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new WonDisplayHandler(gamePanel.gameTimer, gamePanel.enemyWaveHandler);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
