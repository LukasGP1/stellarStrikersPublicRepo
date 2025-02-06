package de.lulkas_.stellarstrikers.menu.menus.options;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.button.ColorSelectorButton;
import de.lulkas_.stellarstrikers.menu.button.SwitchButton;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;

public class OptionsMenu extends Menu {
    public OptionsMenu(GameObjectHandler gameObjectHandler) {
        super(gameObjectHandler);

        addButton(new Button(new Rectangle(280, 800, 439, 111), () -> {
            gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new SwitchButton(new Rectangle(606, 188, 94, 57), () -> {
            ((SwitchButton) this.clickables.get(1)).changeState();
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
            gameObjectHandler.playerOptionsHandler.changePlayingSound();
        }, gameObjectHandler.playerOptionsHandler.isPlayingSound()));

        addButton(new SwitchButton(new Rectangle(606, 272, 94, 57), () -> {
            ((SwitchButton) this.clickables.get(2)).changeState();
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
            gameObjectHandler.playerOptionsHandler.changePlayingMusic();
        }, gameObjectHandler.playerOptionsHandler.isPlayingMusic()));

        addButton(new ColorSelectorButton(new Rectangle(606, 356, 94, 57), gameObjectHandler.playerOptionsHandler.getPlayerBulletColor(), () -> {
            gameObjectHandler.playerOptionsHandler.setPlayerBulletColor(((ColorSelectorButton) this.clickables.get(3)).getColor());
        }));

        addButton(new ColorSelectorButton(new Rectangle(605, 438, 94, 57), gameObjectHandler.playerOptionsHandler.getEnemyBulletColor(), () -> {
            gameObjectHandler.playerOptionsHandler.setEnemyBulletColor(((ColorSelectorButton) this.clickables.get(4)).getColor());
        }));

        addButton(new ColorSelectorButton(new Rectangle(606, 522, 94, 57), gameObjectHandler.playerOptionsHandler.getBombColor(), () -> {
            gameObjectHandler.playerOptionsHandler.setBombColor(((ColorSelectorButton) this.clickables.get(5)).getColor());
        }));

        addButton(new ColorSelectorButton(new Rectangle(606, 606, 94, 57), gameObjectHandler.playerOptionsHandler.getDetonatedBombColor(), () -> {
            gameObjectHandler.playerOptionsHandler.setDetonatedBombColor(((ColorSelectorButton) this.clickables.get(6)).getColor());
        }));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new DisplayHandler();
    }
}
