package de.lulkas_.stellarstrikers.menu.button;

import de.lulkas_.stellarstrikers.util.CoordConversion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class SwitchButton implements Clickable {
    private final Image img1;
    private final Image img2;
    private boolean state;
    private final Rectangle gameClickingArea;
    private Rectangle screenClickingArea;
    private final Runnable whenClicked;

    public SwitchButton(Rectangle gameClickingArea, Runnable whenClicked, String path1, String path2, boolean state) {
        this.gameClickingArea = gameClickingArea;
        this.whenClicked = whenClicked;
        this.img1 = importImage(path1);
        this.img2 = importImage(path2);
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

    @Override
    public Graphics draw(Graphics g) {
        if(state) {
            g.drawImage(img1.getScaledInstance(getClickingArea().width, getClickingArea().height, Image.SCALE_DEFAULT), getClickingArea().x, getClickingArea().y, null);
        } else {
            g.drawImage(img2.getScaledInstance(getClickingArea().width, getClickingArea().height, Image.SCALE_DEFAULT), getClickingArea().x, getClickingArea().y, null);
        }

        return g;
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
