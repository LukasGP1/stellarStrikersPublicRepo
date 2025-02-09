package de.lulkas_.stellarstrikers.menu.menus.game;

import de.lulkas_.stellarstrikers.level.enemys.EnemyHandler;
import de.lulkas_.stellarstrikers.level.player.Player;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.ImageBarDisplay;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;

public class GameDisplayHandler extends DisplayHandler {
    public GameDisplayHandler(Player player, EnemyHandler enemyHandler) {
        addDisplay(new IntDisplay(870, 5, enemyHandler::getSingleGameScore, 22, 55));
        addDisplay(new ImageBarDisplay(5, 5, () -> player.health, 30, 50));
        addDisplay(new IntDisplay(870, 65, () -> enemyHandler.wave, 22, 55));
        //addDisplay(new TemporaryIntDisplay("Wave ", 400, 400, 80, Color.WHITE, "", () -> (enemyWaveHandler.wave), 3, 300, 100));
    }
}
