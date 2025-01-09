package de.lulkas_.stellarstrikers.menu.menus.skin;

import de.lulkas_.stellarstrikers.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SkinDisplay {
    private final int x, y;
    private final List<Image> images = new ArrayList<>();
    private final Image lock;
    public int displayed;
    private final GamePanel gamePanel;

    public SkinDisplay(int x, int y, List<String> imagePaths, int displayed, String lockPath, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.displayed = displayed;
        this.lock = importImage(lockPath);
        this.gamePanel = gamePanel;

        for(String path : imagePaths) {
            images.add(importImage(path));
        }
    }

    protected Image importImage(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        try {
            return ImageIO.read(is).getScaledInstance(64, 52, Image.SCALE_DEFAULT);
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
        g.drawImage(images.get(displayed), x,  y, null);
        if(gamePanel.playerAttributeHandler.getLevel() < displayed) {
            g.drawImage(lock, x, y, null);
        }
        return g;
    }
}
