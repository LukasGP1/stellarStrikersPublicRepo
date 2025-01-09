package de.lulkas_.stellarstrikers.menu.menus.skill;

import de.lulkas_.stellarstrikers.attributes.PlayerAttributeHandler;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;
import de.lulkas_.stellarstrikers.menu.display.TemporaryDisplay;

import java.awt.*;

public class SkillDisplayHandler extends DisplayHandler {

    public SkillDisplayHandler(PlayerAttributeHandler playerAttributeHandler) {
        addDisplay(new IntDisplay("Points to spent: ", 320, 200, 48, Color.WHITE, "", playerAttributeHandler::getLevelPoints));
        addDisplay(new TemporaryDisplay(Color.RED, 48, 80, 80, "You need two points to upgrade a skill"));
    }
}
