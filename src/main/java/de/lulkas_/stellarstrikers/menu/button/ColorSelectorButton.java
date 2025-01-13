package de.lulkas_.stellarstrikers.menu.button;

import de.lulkas_.stellarstrikers.util.CoordConversion;

import javax.swing.*;
import java.awt.*;

public class ColorSelectorButton implements Clickable {
    private final Rectangle gameClickingArea;
    private Rectangle screenClickingArea;
    private Color color;
    private final Runnable onCLick;

    public ColorSelectorButton(Rectangle gameClickingArea, Color startColor, Runnable onCLick) {
        this.gameClickingArea = gameClickingArea;
        this.color = startColor;
        this.onCLick = onCLick;
        screenClickingArea = CoordConversion.gameToScreen(gameClickingArea);
    }

    @Override
    public Rectangle getClickingArea() {
        return screenClickingArea;
    }

    @Override
    public Runnable getWhenCLicked() {
        return () -> {
            Color selected = JColorChooser.showDialog(null, "Choose a color", color);
            if(selected != null) {
                color = selected;
            }
            onCLick.run();
        };
    }

    @Override
    public void tick() {
        screenClickingArea = CoordConversion.gameToScreen(gameClickingArea);
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
