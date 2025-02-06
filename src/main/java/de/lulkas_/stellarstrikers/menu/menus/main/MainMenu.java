package de.lulkas_.stellarstrikers.menu.menus.main;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.ExclamationMark;
import de.lulkas_.stellarstrikers.menu.Menu;
import de.lulkas_.stellarstrikers.menu.button.Button;
import de.lulkas_.stellarstrikers.menu.display.DisplayHandler;
import de.lulkas_.stellarstrikers.sound.SoundHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Menu {
    public ExclamationMark skillExclamationMark;
    public ExclamationMark skinExclamationMark;

    public MainMenu(GameObjectHandler gameObjectHandler) {
        super(gameObjectHandler);

        addButton(new Button(new Rectangle(387, 300, 250, 221), () -> {
            this.gameObjectHandler.gameState = GameObjectHandler.GameState.STARTING_GAME;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new Button(new Rectangle(387, 578, 250, 166), () -> {
            this.gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_SKILL_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new Button(new Rectangle(387, 800, 251, 111), gameObjectHandler.saveWiper::wipe));

        addButton(new Button(new Rectangle(224, 328, 138, 167),() -> {
            this.gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_CREDITS_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new Button(new Rectangle(649, 328, 126, 172), () -> {
            gameObjectHandler.api.sessionClose();
            System.exit(0);
        }));

        addButton(new Button(new Rectangle(224, 578, 139, 167), () -> {
            gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_SKIN_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        addButton(new Button(new Rectangle(656, 578, 126, 168), this::joinDiscord));

        addButton(new Button(new Rectangle(224, 800, 133, 111), () -> {
            gameObjectHandler.gameState = GameObjectHandler.GameState.ENTER_OPTIONS_MENU;
            SoundHandler.playSound("/sounds/menu/click.wav", -2f, gameObjectHandler);
        }));

        this.skillExclamationMark = new ExclamationMark(370, 550, 45, 80, () -> gameObjectHandler.playerAttributeHandler.getLevelPoints() >= 2, gameObjectHandler);
        this.skinExclamationMark = new ExclamationMark(200, 555, 45, 80, () -> {
            List<Integer> skinsToSee = new ArrayList<>();
            for(int i = 0; i < gameObjectHandler.playerAttributeHandler.getLevel(); i++) {
                skinsToSee.add(i + 1);
            }
            boolean toReturn = false;
            for(int skin : skinsToSee) {
                toReturn = !gameObjectHandler.playerLocalDataHandler.isSkinSeen(skin);
            }
            return toReturn;
        }, gameObjectHandler);
    }

    @Override
    protected DisplayHandler createDisplayHandler() {
        return new MainMenuDisplayHandler(gameObjectHandler.playerAttributeHandler);
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
