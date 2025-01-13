package de.lulkas_.stellarstrikers.menu.menus.game;

import de.lulkas_.stellarstrikers.level.enemys.EnemyWaveHandler;
import de.lulkas_.stellarstrikers.level.player.Player;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.ImageBarDisplay;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;
import de.lulkas_.stellarstrikers.menu.display.TemporaryIntDisplay;

import java.awt.*;

public class GameDisplayHandler extends DisplayHandler {
    private Player player;
    private EnemyWaveHandler enemyWaveHandler;

    public GameDisplayHandler(Player player, EnemyWaveHandler enemyWaveHandler) {
        this.player = player;
        this.enemyWaveHandler = enemyWaveHandler;
        addDisplay(new IntDisplay("Score: ", 870, 5, 48, Color.WHITE, "", enemyWaveHandler::getSingleGameScore, 4, 150, 50));
        addDisplay(new ImageBarDisplay(50, 5, 5, "/assets/textures/menu/icon/heart.png", () -> player.health, 30, 50));
        addDisplay(new TemporaryIntDisplay("Wave ", 400, 400, 80, Color.WHITE, "", () -> (this.enemyWaveHandler.wave), 3, 300, 100));
    }
}
