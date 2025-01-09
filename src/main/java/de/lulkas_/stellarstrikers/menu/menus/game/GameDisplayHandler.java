package de.lulkas_.stellarstrikers.menu.menus.game;

import de.lulkas_.stellarstrikers.Main;
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
        addDisplay(new IntDisplay("Score: ", Main.game.window.getWidth() - 260, 5, 48, Color.WHITE, "", enemyWaveHandler::getSingleGameScore));
        addDisplay(new ImageBarDisplay(50, 5, 5, "/textures/menu/icon/heart.png", () -> player.health));
        addDisplay(new TemporaryIntDisplay("Wave ", Main.game.window.getWidth() / 2 - 80, Main.game.window.getHeight() / 2 - 80, 80, Color.WHITE, "", () -> (this.enemyWaveHandler.wave)));
    }
}
