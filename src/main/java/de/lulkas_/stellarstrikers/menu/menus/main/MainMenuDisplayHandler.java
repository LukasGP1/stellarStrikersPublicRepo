package de.lulkas_.stellarstrikers.menu.menus.main;

import de.lulkas_.stellarstrikers.playerData.PlayerAttributeHandler;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;

public class MainMenuDisplayHandler extends DisplayHandler {
    private final PlayerAttributeHandler playerAttributeHandler;

    public MainMenuDisplayHandler(PlayerAttributeHandler playerAttributeHandler) {
        this.playerAttributeHandler = playerAttributeHandler;
        addDisplay(new IntDisplay(570, 200, () -> this.playerAttributeHandler.score, 22, 55));
        addDisplay(new IntDisplay(570, 140, () -> this.playerAttributeHandler.levelingCost - this.playerAttributeHandler.score, 22, 55));
        addDisplay(new IntDisplay(570, 80, this.playerAttributeHandler::getLevel, 22, 55));
        addDisplay(new IntDisplay(570, 20, () -> playerAttributeHandler.getLevel() + 2, 22, 55));
    }
}
