package de.lulkas_.stellarstrikers.menu.menus.skill;

import de.lulkas_.stellarstrikers.playerData.PlayerAttributeHandler;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;
import de.lulkas_.stellarstrikers.menu.display.TemporaryDisplay;

public class SkillDisplayHandler extends DisplayHandler {

    public SkillDisplayHandler(PlayerAttributeHandler playerAttributeHandler) {
        addDisplay(new IntDisplay(600, 200, playerAttributeHandler::getLevelPoints, 22, 55));
        addDisplay(new TemporaryDisplay(180, 80, 625, 111));
    }
}
