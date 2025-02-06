package de.lulkas_.stellarstrikers.menu.menus.skin;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.ExclamationMark;

import java.util.List;

public class SkinDisplay {
    private final int gameX, gameY;
    private final int gameWidth, gameHeight;
    public int displayed;
    private final GameObjectHandler gameObjectHandler;
    public final ExclamationMark exclamationMark;

    public SkinDisplay(int gameX, int gameY, int displayed, GameObjectHandler gameObjectHandler) {
        this.gameX = gameX;
        this.gameY = gameY;
        this.displayed = displayed;
        this.gameObjectHandler = gameObjectHandler;
        this.exclamationMark = new ExclamationMark(gameX - 10, gameY - 50, 45, 80, () -> {
            return (!gameObjectHandler.playerLocalDataHandler.isSkinSeen(gameObjectHandler.skinDisplay.displayed)) &&
                    gameObjectHandler.playerAttributeHandler.getLevel() >= gameObjectHandler.skinDisplay.displayed;
        }, gameObjectHandler);

        gameWidth = 53;
        gameHeight = 78;
    }

    public void shiftLeft() {
        if(!gameObjectHandler.playerLocalDataHandler.isSkinSeen(displayed) && gameObjectHandler.playerAttributeHandler.getLevel() >= displayed) {
            gameObjectHandler.playerLocalDataHandler.seeSkin(displayed);
        }

        displayed--;
        if(displayed == -1) {
            displayed = 18;
        }
    }

    public void shiftRight() {
        if(!gameObjectHandler.playerLocalDataHandler.isSkinSeen(displayed) && gameObjectHandler.playerAttributeHandler.getLevel() >= displayed) {
            gameObjectHandler.playerLocalDataHandler.seeSkin(displayed);
        }

        displayed++;
        if(displayed == 19) {
            displayed = 0;
        }
    }

    public List<?> getPosData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }

    public List<?> getMiscData() {
        return List.of(0, displayed + 1, gameObjectHandler.playerAttributeHandler.getLevel() < displayed ? 1 : 0);
    }
}
