package de.lulkas_.stellarstrikers.menu.menus.main;

import de.lulkas_.stellarstrikers.attributes.PlayerAttributeHandler;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;
import de.lulkas_.stellarstrikers.menu.display.TemporaryIntDisplay;
import de.lulkas_.stellarstrikers.menu.display.ThreeIntDisplay;

import java.awt.*;

public class MainMenuDisplayHandler extends DisplayHandler {
    private final PlayerAttributeHandler playerAttributeHandler;

    public MainMenuDisplayHandler(PlayerAttributeHandler playerAttributeHandler) {
        this.playerAttributeHandler = playerAttributeHandler;
        addDisplay(new IntDisplay("Score: ", 400, 200, 48, Color.WHITE, "", () -> (this.playerAttributeHandler.score)));
        addDisplay(new ThreeIntDisplay(Color.WHITE, 48, 400, 130, "Level: ", this.playerAttributeHandler::getLevel, " (", () -> this.playerAttributeHandler.score, " out of ", () -> this.playerAttributeHandler.levelingCost, ")"));
        addDisplay(new TemporaryIntDisplay("You can now play up to wave ", 125, 30, 48, Color.GREEN, "", () -> playerAttributeHandler.getLevel() + 2));
    }
}
