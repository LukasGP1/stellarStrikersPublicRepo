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
        super("/textures/menu/main_menu.png", gamePanel);

        addButton(new Button(new Rectangle(300, 300, 400, 200), () -> {
            this.gamePanel.gameState = GamePanel.GameState.STARTING_GAME;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(300, 550, 400, 150), () -> {
            this.gamePanel.gameState = GamePanel.GameState.ENTER_SKILL_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(300, 750, 400, 100), gamePanel.saveWiper::wipe));

        addButton(new Button(new Rectangle(40, 324, 220, 151),() -> {
            this.gamePanel.gameState = GamePanel.GameState.ENTER_CREDITS_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(720, 325, 200, 155), () -> {
            gamePanel.api.sessionClose();
            System.exit(0);
        }));

        addButton(new Button(new Rectangle(40, 550, 220, 150), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_SKIN_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
        }));

        addButton(new Button(new Rectangle(730, 550, 200, 150), this::joinDiscord));

        addButton(new Button(new Rectangle(40, 750, 210, 100), () -> {
            gamePanel.gameState = GamePanel.GameState.ENTER_OPTIONS_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gamePanel);
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
