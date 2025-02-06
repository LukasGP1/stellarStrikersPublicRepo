package de.lulkas_.stellarstrikers.menu.display;

import de.lulkas_.stellarstrikers.util.CoordConversion;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Display {
    protected int gameX, gameY;
    protected int screenX, screenY;
    protected int gameWidth, gameHeight;
    protected int screenWidth, screenHeight;

    public Display(int gameX, int gameY, int gameWidth, int gameHeight) {
        this.gameX = gameX;
        this.gameY = gameY;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    public void tick() {
        float[] screenCoords = CoordConversion.gameToScreen(new Float[]{((float) gameX), ((float) gameY)});
        screenX = ((int) screenCoords[0]);
        screenY = ((int) screenCoords[1]);

        float[] screenSize = CoordConversion.gameToScreen(new Float[]{((float) gameWidth), ((float) gameHeight)});
        screenWidth = ((int) screenSize[0]);
        screenHeight = ((int) screenSize[1]);
    }
}
