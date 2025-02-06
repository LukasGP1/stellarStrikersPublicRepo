package de.lulkas_.stellarstrikers.menu.menus.skin;

import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;

public class SkinDisplayHandler extends DisplayHandler {
    public SkinDisplayHandler(SkinDisplay skinDisplay) {
        //addDisplay(new TemporaryIntDisplay("You need to be Level ", 300, 80, 48, Color.RED, " to use this", () -> skinDisplay.displayed, 2, 450, 50));
        addDisplay(new IntDisplay(610, 80, () -> skinDisplay.displayed, 22, 55));
    }
}
