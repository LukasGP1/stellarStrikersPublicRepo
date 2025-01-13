package de.lulkas_.stellarstrikers.menu.menus.won;

import de.lulkas_.stellarstrikers.level.enemys.EnemyWaveHandler;
import de.lulkas_.stellarstrikers.level.time.GameTimer;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;

import java.awt.*;

public class WonDisplayHandler extends DisplayHandler {
    private EnemyWaveHandler enemyWaveHandler;
    private GameTimer gameTimer;

    public WonDisplayHandler(GameTimer gameTimer, EnemyWaveHandler enemyWaveHandler) {
        addDisplay(new IntDisplay("Score: ", 384, 150, 48, Color.WHITE, "", () -> ( enemyWaveHandler.getSingleGameScore()), 4, 200, 50));
        addDisplay(new IntDisplay("Took ", 340, 300, 48, Color.WHITE, " Seconds to beat", () -> ((int) gameTimer.getAccurateSeconds()), 3, 400, 50));
        this.enemyWaveHandler = enemyWaveHandler;
        this.gameTimer = gameTimer;
    }
}
