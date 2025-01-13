package de.lulkas_.stellarstrikers.menu.menus.skin;

import de.lulkas_.stellarstrikers.GamePanel;
import de.lulkas_.stellarstrikers.util.CoordConversion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SkinDisplay {
    private final int gameX, gameY;
    private int screenX, screenY;
    private final int gameWidth, gameHeight;
    private int screenWidth, screenHeight;
    private final List<Image> images = new ArrayList<>();
    private final Image lock;
    public int displayed;
    private final GamePanel gamePanel;

    public SkinDisplay(int gameX, int gameY, List<String> imagePaths, int displayed, String lockPath, GamePanel gamePanel) {
        this.gameX = gameX;
        this.gameY = gameY;
        this.displayed = displayed;
        this.lock = importImage(lockPath);
        this.gamePanel = gamePanel;

        gameWidth = 53;
        gameHeight = 78;

        for(String path : imagePaths) {
            images.add(importImage(path));
        }
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

    public void shiftLeft() {
        displayed--;
        if(displayed == -1) {
            displayed = images.size() - 1;
        }
    }

    public void shiftRight() {
        displayed++;
        if(displayed == images.size()) {
            displayed = 0;
        }
    }

    public Graphics draw(Graphics g) {
        float[] screenCoords = CoordConversion.gameToScreen(new Float[]{((float) gameX), ((float) gameY)});
        screenX = ((int) screenCoords[0]);
        screenY = ((int) screenCoords[1]);

        float[] screenSize = CoordConversion.gameToScreen(new Float[]{((float) gameWidth), ((float) gameHeight)});
        screenWidth = ((int) screenSize[0]);
        screenHeight = ((int) screenSize[1]);

        g.drawImage(images.get(displayed).getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT), screenX, screenY, null);
        if(gamePanel.playerAttributeHandler.getLevel() < displayed) {
            g.drawImage(lock.getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT), screenX, screenY, null);
        }
        return g;
    }
}
