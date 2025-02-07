package de.lulkas_.stellarstrikers.util;

import de.lulkas_.stellarstrikers.Main;

import java.awt.*;
import java.util.Arrays;

public class CoordConversion {
    public static Rectangle gameToScreen(Rectangle gameCoords) {
        int x = ((int) ((gameCoords.x / 1000f) * Main.gameLoop.window.gameObjectHandler.getWidth()));
        int width = ((int) ((gameCoords.width / 1000f) * Main.gameLoop.window.gameObjectHandler.getWidth()));
        int y = ((int) ((gameCoords.y / 1000f) * Main.gameLoop.window.gameObjectHandler.getHeight()));
        int height = ((int) ((gameCoords.height / 1000f) * Main.gameLoop.window.gameObjectHandler.getHeight()));

        return new Rectangle(x, y, width, height);
    }
}
