package de.lulkas_.stellarstrikers.menu.menus.skill;

import de.lulkas_.stellarstrikers.attributes.PlayerAttributeHandler;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;
import de.lulkas_.stellarstrikers.menu.display.TemporaryDisplay;

import java.awt.*;

public class SkillDisplayHandler extends DisplayHandler {

    public SkillDisplayHandler(PlayerAttributeHandler playerAttributeHandler) {
        addDisplay(new IntDisplay("Points to spent: ", 440, 200, 48, Color.WHITE, "", playerAttributeHandler::getLevelPoints, 3, 250, 50));
        addDisplay(new TemporaryDisplay(Color.RED, 48, 320, 80, "You need two points to upgrade a skill", 450, 50));
    }
}
