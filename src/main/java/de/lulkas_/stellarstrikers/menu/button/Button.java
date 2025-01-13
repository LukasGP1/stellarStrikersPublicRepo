package de.lulkas_.stellarstrikers.menu.button;

import de.lulkas_.stellarstrikers.util.CoordConversion;

import java.awt.*;

public class Button implements Clickable {
    private final Rectangle gameClickingArea;
    private Rectangle screenClickingArea;
    private final Runnable whenClicked;

    public Button(Rectangle gameClickingArea, Runnable whenClicked) {
        this.gameClickingArea = gameClickingArea;
        this.whenClicked = whenClicked;
        screenClickingArea = CoordConversion.gameToScreen(gameClickingArea);
    }

    @Override
    public Rectangle getClickingArea() {
        return screenClickingArea;
    }

    @Override
    public Runnable getWhenCLicked() {
        return whenClicked;
    }

    @Override
    public void tick() {
        screenClickingArea = CoordConversion.gameToScreen(gameClickingArea);
    }

    @Override
    public Graphics draw(Graphics g) {
        return g;
    }
}
