package de.lulkas_.stellarstrikers.util;

import de.lulkas_.stellarstrikers.Main;

import java.awt.*;
import java.util.Arrays;

public class CoordConversion {
    public static float[] screenToGame(float[] screenCoords) {
        if(screenCoords.length != 2) {
            throw new RuntimeException("Wrong coordinate format");
        }
        float[] toReturn = new float[2];

        toReturn[0] = (screenCoords[0] / Main.gameLoop.window.gameObjectHandler.getWidth()) * 1000;
        toReturn[1] = (screenCoords[1] / Main.gameLoop.window.gameObjectHandler.getHeight()) * 1000;

        return toReturn;
    }

    public static float[] gameToScreen(Float[] gameCoords) {
        if(gameCoords.length != 2 || gameCoords[0] < 0 || gameCoords[0] > 1000 || gameCoords[1] < 0 || gameCoords[1] > 1000) {
            throw new RuntimeException("Wrong coordinate format: " + Arrays.deepToString(gameCoords));
        }
        float[] toReturn = new float[2];

        toReturn[0] = (gameCoords[0] / 1000) * Main.gameLoop.window.gameObjectHandler.getWidth();
        toReturn[1] = (gameCoords[1] / 1000) * Main.gameLoop.window.gameObjectHandler.getHeight();

        return toReturn;
    }

    public static Rectangle gameToScreen(Rectangle gameCoords) {
        int x = ((int) ((gameCoords.x / 1000f) * Main.gameLoop.window.gameObjectHandler.getWidth()));
        int width = ((int) ((gameCoords.width / 1000f) * Main.gameLoop.window.gameObjectHandler.getWidth()));
        int y = ((int) ((gameCoords.y / 1000f) * Main.gameLoop.window.gameObjectHandler.getHeight()));
        int height = ((int) ((gameCoords.height / 1000f) * Main.gameLoop.window.gameObjectHandler.getHeight()));

        return new Rectangle(x, y, width, height);
    }
}
