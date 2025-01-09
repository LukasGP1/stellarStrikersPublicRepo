package de.lulkas_.stellarstrikers.menu.menus.options;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.button.ColorSelectorButton;
import de.lulkas_.stellarstrikers.menu.button.SwitchButton;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;

public class OptionsMenu extends Menu {
    public OptionsMenu(GamePanel gamePanel) {
        super("/textures/menu/options_menu.png", gamePanel);

        addButton(new Button(new Rectangle(130, 750, 700, 100), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_MAIN_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new SwitchButton(new Rectangle(650, 200, 150, 50), () -> {
            ((SwitchButton) this.clickables.get(1)).changeState();
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
            gamePanel.playerOptionsHandler.changePlayingSound();
        }, "/textures/menu/icon/on.png", "/textures/menu/icon/off.png", gamePanel.playerOptionsHandler.isPlayingSound()));

        addButton(new SwitchButton(new Rectangle(650, 275, 150, 50), () -> {
            ((SwitchButton) this.clickables.get(2)).changeState();
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
            gamePanel.playerOptionsHandler.changePlayingMusic();
        }, "/textures/menu/icon/on.png", "/textures/menu/icon/off.png", gamePanel.playerOptionsHandler.isPlayingMusic()));

        addButton(new ColorSelectorButton(new Rectangle(650, 350, 150, 50), gamePanel.playerOptionsHandler.getPlayerBulletColor(), () -> {
            gamePanel.playerOptionsHandler.setPlayerBulletColor(((ColorSelectorButton) this.clickables.get(3)).getColor());
        }));

        addButton(new ColorSelectorButton(new Rectangle(650, 425, 150, 50), gamePanel.playerOptionsHandler.getEnemyBulletColor(), () -> {
            gamePanel.playerOptionsHandler.setEnemyBulletColor(((ColorSelectorButton) this.clickables.get(4)).getColor());
        }));

        addButton(new ColorSelectorButton(new Rectangle(650, 500, 150, 50), gamePanel.playerOptionsHandler.getBombColor(), () -> {
            gamePanel.playerOptionsHandler.setBombColor(((ColorSelectorButton) this.clickables.get(5)).getColor());
        }));

        addButton(new ColorSelectorButton(new Rectangle(650, 575, 150, 50), gamePanel.playerOptionsHandler.getDetonatedBombColor(), () -> {
            gamePanel.playerOptionsHandler.setDetonatedBombColor(((ColorSelectorButton) this.clickables.get(6)).getColor());
        }));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new DisplayHandler();
    }
}
