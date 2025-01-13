package de.lulkas_.stellarstrikers.menu.menus.game;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

public class GameMenu extends Menu {
    public GameMenu(GamePanel gamePanel) {
        super("/assets/textures/menu/game_menu.png", gamePanel);
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new GameDisplayHandler(gamePanel.player, gamePanel.enemyWaveHandler);
    }
}
