package de.lulkas_.stellarstrikers.menu.menus.skin;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;

public class SkinMenu extends Menu {
    public SkinMenu(GameObjectHandler gameObjectHandler) {
        super(gameObjectHandler);

        addButton(new Button(new Rectangle(280, 800, 439, 111), () -> {
            if(!gameObjectHandler.playerLocalDataHandler.isSkinSeen(gameObjectHandler.skinDisplay.displayed) && gameObjectHandler.playerAttributeHandler.getLevel() >= gameObjectHandler.skinDisplay.displayed) {
                gameObjectHandler.playerLocalDataHandler.seeSkin(gameObjectHandler.skinDisplay.displayed);
            }

            gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new Button(new Rectangle(280, 189, 46, 56), () -> {
            gameObjectHandler.skinDisplay.shiftLeft();
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new Button(new Rectangle(669, 191, 46, 56), () -> {
            gameObjectHandler.skinDisplay.shiftRight();
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new Button(new Rectangle(280, 632, 439, 113), () -> {
            if(gameObjectHandler.playerAttributeHandler.getLevel() >= gameObjectHandler.skinDisplay.displayed) {
                gameObjectHandler.playerAttributeHandler.setSkinDisplayed(gameObjectHandler.skinDisplay.displayed);
                SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
            } else {
                SoundHandler.playSound("/sounds/menu/error.wav", -2f, gameObjectHandler);
            }
        }));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new SkinDisplayHandler(gameObjectHandler.skinDisplay);
    }
}
