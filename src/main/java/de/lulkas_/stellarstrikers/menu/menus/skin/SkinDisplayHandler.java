package de.lulkas_.stellarstrikers.menu.menus.skin;

import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.TemporaryIntDisplay;

import java.awt.*;

public class SkinDisplayHandler extends DisplayHandler {
    private SkinDisplay skinDisplay;

    public SkinDisplayHandler(SkinDisplay skinDisplay) {
        this.skinDisplay = skinDisplay;
        addDisplay(new TemporaryIntDisplay("You need to be Level ", 100, 80, 48, Color.RED, " to use this", () -> skinDisplay.displayed));
    }
}
