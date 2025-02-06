package de.lulkas_.stellarstrikers.menu.button;

import de.lulkas_.stellarstrikers.util.CoordConversion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    public List<?> getPosData() {
        return List.of(0f, gameClickingArea.x / 1000f, gameClickingArea.y / 1000f, gameClickingArea.width / 1000f, gameClickingArea.height / 1000f);
    }

    public List<?> getMiscData() {
        return List.of(0f, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    public Color getColor() {
        return color;
    }
}
