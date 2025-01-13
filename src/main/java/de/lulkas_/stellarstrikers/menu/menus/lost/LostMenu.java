package de.lulkas_.stellarstrikers.menu.menus.lost;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;

import java.awt.*;

public class LostMenu extends Menu {
    public LostMenu(GamePanel gamePanel) {
        super("/assets/textures/menu/lost_menu.png", gamePanel);
        addButton(new Button(new Rectangle(0, 0, 1000, 1000), () -> gamePanel.gameState = GamePanel.GameState.ENTER_MAIN_MENU));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new DisplayHandler();
    }
}
