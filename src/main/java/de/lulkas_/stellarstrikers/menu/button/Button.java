package de.lulkas_.stellarstrikers.menu.button;

import java.awt.*;

public class Button implements Clickable {
    private final Rectangle originalClickingArea;
    private final Runnable whenClicked;
    private int xShifted;
    private float yScale;

    public Button(Rectangle originalClickingArea, Runnable whenClicked) {
        this.originalClickingArea = originalClickingArea;
        this.whenClicked = whenClicked;
    }

    @Override
    public Rectangle getClickingArea() {
        Rectangle toReturn = new Rectangle(originalClickingArea);
        toReturn.setBounds(((int) (xShifted + originalClickingArea.x * yScale)), ((int) (originalClickingArea.y * yScale)), ((int) (originalClickingArea.width * yScale)), ((int) (originalClickingArea.height * yScale)));
        return toReturn;
    }

    @Override
    public Runnable getWhenCLicked() {
        return whenClicked;
    }

    @Override
    public void setXShifted(int value) {
        xShifted = value;
    }

    @Override
    public void setYScale(float value) {
        yScale = value;
    }

    @Override
    public void tick() {

    }

    @Override
    public Graphics draw(Graphics g) {
        return g;
    }
}
