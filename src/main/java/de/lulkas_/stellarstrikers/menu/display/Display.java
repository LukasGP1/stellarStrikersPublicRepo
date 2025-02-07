package de.lulkas_.stellarstrikers.menu.display;

public abstract class Display {
    protected int gameX, gameY;
    protected int gameWidth, gameHeight;

    public Display(int gameX, int gameY, int gameWidth, int gameHeight) {
        this.gameX = gameX;
        this.gameY = gameY;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    public void tick() {

    }
}
