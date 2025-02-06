package de.lulkas_.stellarstrikers.menu.menus.won;

import de.lulkas_.stellarstrikers.level.enemys.EnemyWaveHandler;
import de.lulkas_.stellarstrikers.level.time.GameTimer;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;

public class WonDisplayHandler extends DisplayHandler {
    public WonDisplayHandler(GameTimer gameTimer, EnemyWaveHandler enemyWaveHandler) {
        addDisplay(new IntDisplay(520, 150, enemyWaveHandler::getSingleGameScore, 22, 55));
        addDisplay(new IntDisplay(520, 300, () -> ((int) gameTimer.getAccurateSeconds()), 22, 55));
    }
}
