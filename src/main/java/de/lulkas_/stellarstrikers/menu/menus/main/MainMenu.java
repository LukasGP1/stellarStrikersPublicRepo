package de.lulkas_.stellarstrikers.menu.menus.main;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainMenu extends Menu {

    public MainMenu(GamePanel gamePanel) {
        super("/assets/textures/menu/main_menu.png", gamePanel);

        addButton(new Button(new Rectangle(387, 300, 250, 221), () -> {
            this.gamePanel.gameState = GamePanel.GameState.STARTING_GAME;
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(387, 578, 250, 166), () -> {
            this.gamePanel.gameState = GamePanel.GameState.ENTER_SKILL_MENU;
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(387, 800, 251, 111), gamePanel.saveWiper::wipe));

        addButton(new Button(new Rectangle(224, 328, 138, 167),() -> {
            this.gamePanel.gameState = GamePanel.GameState.ENTER_CREDITS_MENU;
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(649, 328, 126, 172), () -> {
            gamePanel.api.sessionClose();
            System.exit(0);
        }));

        addButton(new Button(new Rectangle(224, 578, 139, 167), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_SKIN_MENU;
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(656, 578, 126, 168), this::joinDiscord));

        addButton(new Button(new Rectangle(224, 800, 133, 111), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_OPTIONS_MENU;
            SoundHandler.playSound("/assets/sounds/menu/click.wav", -2f, gamePanel);
        }));
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new MainMenuDisplayHandler(gamePanel.playerAttributeHandler);
    }

    private void joinDiscord() {
        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI("https://discord.gg/UFjEJjysbw");
                desktop.browse(uri);
            } catch (URISyntaxException e) {
                System.out.println("Syntax error in discord link");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error while opening link");
                e.printStackTrace();
            }
        }
    }
}
