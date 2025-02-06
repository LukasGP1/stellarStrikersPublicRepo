package de.lulkas_.stellarstrikers.menu.button;

import de.lulkas_.stellarstrikers.util.CoordConversion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SwitchButton implements Clickable {
    private boolean state;
    private final Rectangle gameClickingArea;
    private Rectangle screenClickingArea;
    private final Runnable whenClicked;

    public SwitchButton(Rectangle gameClickingArea, Runnable whenClicked, boolean state) {
        this.gameClickingArea = gameClickingArea;
        this.whenClicked = whenClicked;
        this.state = state;
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

    public List<?> getPosData() {
        return List.of(0f, gameClickingArea.x / 1000f, gameClickingArea.y / 1000f, gameClickingArea.width / 1000f, gameClickingArea.height / 1000f);
    }

    public List<?> getMiscData() {
        return List.of(0, state ? 1 : 0);
    }

    public void changeState() {
        state = !state;
    }

    protected Image importImage(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
