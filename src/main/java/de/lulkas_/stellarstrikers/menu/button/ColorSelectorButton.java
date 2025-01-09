package de.lulkas_.stellarstrikers.menu.button;

import javax.swing.*;
import java.awt.*;

public class ColorSelectorButton implements Clickable {
    private final Rectangle originalClickingArea;
    private Color color;
    private final Runnable onCLick;
    private int xShifted;
    private float yScale;

    public ColorSelectorButton(Rectangle originalClickingArea, Color startColor, Runnable onCLick) {
        this.originalClickingArea = originalClickingArea;
        this.color = startColor;
        this.onCLick = onCLick;
    }

    @Override
    public Rectangle getClickingArea() {
        Rectangle toReturn = new Rectangle(originalClickingArea);
        toReturn.setBounds(((int) (xShifted + originalClickingArea.x * yScale)), ((int) (originalClickingArea.y * yScale)), ((int) (originalClickingArea.width * yScale)), ((int) (originalClickingArea.height * yScale)));
        return toReturn;
    }

    @Override
    public Runnable getWhenCLicked() {
        return () -> {
            color = JColorChooser.showDialog(null, "Choose a color", color);
            onCLick.run();
        };
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
        g.setColor(color);
        g.fillRect(getClickingArea().x, getClickingArea().y, getClickingArea().width, getClickingArea().height);
        return g;
    }

    public Color getColor() {
        return color;
    }
}
