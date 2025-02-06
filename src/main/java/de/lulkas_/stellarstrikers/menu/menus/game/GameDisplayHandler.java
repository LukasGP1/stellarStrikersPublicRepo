package de.lulkas_.stellarstrikers.menu.menus.game;

import de.lulkas_.stellarstrikers.level.enemys.EnemyWaveHandler;
import de.lulkas_.stellarstrikers.level.player.Player;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.ImageBarDisplay;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;

public class GameDisplayHandler extends DisplayHandler {
    public GameDisplayHandler(Player player, EnemyWaveHandler enemyWaveHandler) {
        addDisplay(new IntDisplay(870, 5, enemyWaveHandler::getSingleGameScore, 22, 55));
        addDisplay(new ImageBarDisplay(5, 5, () -> player.health, 30, 50));
        addDisplay(new IntDisplay(870, 65, () -> enemyWaveHandler.wave, 22, 55));
        //addDisplay(new TemporaryIntDisplay("Wave ", 400, 400, 80, Color.WHITE, "", () -> (enemyWaveHandler.wave), 3, 300, 100));
    }
}
